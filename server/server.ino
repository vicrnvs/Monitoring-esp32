#include <ArduinoJson.h>
#include "NetworkUtils.h"
#include "time.h"
#include <ESP32Time.h>
#include <WiFi.h>

Sensors sensors;
NetworkUtils utils;
ESP32Time rtc(1);
int LED_BUILTIN = 2;
String ssid = "My ASU"; //SSID Wifi
String pass = "1020304050"; //Wifi Password
//Identeificador unico
uint8_t mac[6];
int delaySendData = 3600000;
int delayReciveLog = 5000;
TaskHandle_t  taskcommandListnerHandleApi;
TaskHandle_t  taskcommandListnerHandle;
const int maxArraySize = 200;
String list[maxArraySize];
int indexList = -1;
int co2CollectDelay = 2000;
int ammountCollect = 10;
boolean ignoreWarmup = false;

void taskcommandListnerApi(void*);
void taskcommandListner(void*);
void runCommand(String command, String param);
void collectAndSendData();

void setup() {
  Serial.begin(115200);
  //Leitura de mac
  esp_efuse_read_mac(mac);
  Serial.printf("MAC atual :%X\n", mac);
  utils.serverName = "https://monitoring-esp32.herokuapp.com/esp32";

  //Start task
  xTaskCreatePinnedToCore(taskcommandListner, "TaskSerial", 10000, NULL, 2, &taskcommandListnerHandle,0);
  xTaskCreatePinnedToCore(taskcommandListnerApi, "TaskApi", 10000, NULL, 5, &taskcommandListnerHandleApi,0);

  if(ssid == "" && pass == ""){
    Serial.println("Rede de wifi não configurada");
    Serial.println("Configurar utilizando parametros: ssid=nomeRede e pass=senha");
  }
  
  while(!utils.connectWifi(ssid, pass));

  sensors.ignoreWarmupCC811 = ignoreWarmup;
  Serial.println(sensors.startSensors());
  
  Serial.println("Obtendo horario do servidor");
  int epoch;
  while((epoch = utils.getDateTimeFromServer()) == 0){
    Serial.println("Falha ao obter horario com servidor");
    delay(10000);
  }
  
  Serial.println("Horario calibrado");
  rtc.setTime(epoch);
  Serial.println("Iniciando processo de coleta");

}

//Loop de coleta, armazenagem e envio de dados
void loop(){
  delay(delaySendData);
  collectAndSendData();
}

void collectAndSendData(){
  sendStoredData();
  String body;
  DynamicJsonDocument requestBody(1024);
  requestBody.garbageCollect();
  requestBody["epoch"] = rtc.getLocalEpoch();
  requestBody["identificador"] = (int) mac;

  JsonObject sensorData = requestBody.createNestedObject("sensorData");
  sensorData.createNestedObject("erros");
  Serial.println("Coletando dados de CO2");
  sensors.getCo2DataFromSensors(sensorData, ammountCollect, co2CollectDelay);
  Serial.println("Coletando dados de temperatura e umidade");
  sensors.getTempUmidadeFromSensors(sensorData);
  
  serializeJson(requestBody, body);
  boolean response = utils.sendDataRequest(body);
  
  if(!response){
    if(indexList <= maxArraySize){
      indexList++;
      list[indexList] = body.substring(0);
    }else Serial.println("Lista de requests cheia");

  }
  if(WiFi.status() != WL_CONNECTED) utils.connectWifi(ssid, pass);
  int responseTime = utils.getDateTimeFromServer();
  if(responseTime != 0) rtc.setTime(responseTime);

}

void sendStoredData(){
  for(int i = indexList; i > -1;i--){
    String body = list[i];
    Serial.print("Lista de envio indice = ");
    Serial.print(i);
    Serial.println(body);
    int response = utils.sendDataRequest(String(body));
    if(!response) break;
    list[indexList] = ""; 
    indexList--;
  }
}

//Task comunicar parametros com API
void taskcommandListnerApi(void*){
  while(1){
    String responseBody = utils.getParamsFromServer(String((int) mac, DEC));
    if(responseBody != ""){
      DynamicJsonDocument responseJson(512);
      responseJson.garbageCollect();
      deserializeJson(responseJson, responseBody);
      for(int i = 0; i<responseJson.size();i++){
        String command = responseJson[i]["param"].as<String>();
        String param = responseJson[i]["value"].as<String>();
        if(param != "null" && command != "null"){
          runCommand(command, param);
        }
      }
    }
    vTaskDelay(delayReciveLog);
  }
 vTaskDelete(NULL);
}

//Task comunicar parametros com serial
void taskcommandListner(void*){
  while(1){
    String line = Serial.readStringUntil('\n');
    int breakChar = line.indexOf('=');
    String command = line.substring(0, breakChar);
    String param = line.substring(breakChar+1, line.length());
    runCommand(command, param);
    vTaskDelay(3000);
  }
 vTaskDelete(NULL);
}

void runCommand(String command, String param){
    if( command == "ccs.mode") {sensors.setCcs811DriveMode(param.toInt()); Serial.println("Sucesso");}
    if( command == "ccs.reset" && param == "1") {sensors.ccs.SWReset(); Serial.println("Sucesso");}
    if(command == "delayReciveLog"){ delayReciveLog = param.toInt(); Serial.println("Sucesso");}
    if(command == "delaySendData"){ delaySendData = param.toInt(); Serial.println("Sucesso");}
    if(command == "serverName"){ utils.serverName = param; Serial.println("Sucesso");}
    if(command == "colllec"){collectAndSendData(); Serial.println("Sucesso");}
    if(command == "co2CollectDelay"){co2CollectDelay = param.toInt(); Serial.println("Sucesso");}
    if(command == "ammountCollect"){ammountCollect = param.toInt(); Serial.println("Sucesso");}
    if(command == "ssid"){ssid = param; Serial.println("Sucesso");}
    if(command == "pass"){pass = param; Serial.println("Sucesso");}
    if(command == "ignoreWarmup"){ignoreWarmup = true; Serial.println("Sucesso");}
    if(command == "reconectWifi"){utils.connectWifi(ssid, pass); Serial.println("Sucesso");}


}
