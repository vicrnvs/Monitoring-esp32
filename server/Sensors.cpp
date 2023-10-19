#include "Adafruit_CCS811.h"
#include <ArduinoJson.h>
#include "NetworkUtils.h"



String Sensors::startSensors(){
  StaticJsonDocument <200> doc;
    
  if(ccs.begin()){
    Serial.println("Sensor CSS811 Detectado");
    doc["CCS811"] = "Iniciado";
    ccs.setDriveMode(1);
    if(!ignoreWarmupCC811){
      Serial.println("Iniciando processo de calibração");
      delay(1200000);
    }else Serial.println("Calibração ignorado");
    
  }else {
   
    Serial.println("Falha ao detectar sensor CCS811");
    doc["CCS811"] = "Falha ao detectar sensor CCS811";
  }
  if(aht.begin()){
    Serial.println("Sensor AHT10 Detectado");
    doc["AHT10"] = "Iniciado";
  }else{
    Serial.println("Falha ao detectar sensor AHT10");
    doc["AHT10"] = "Falha ao detectar sensor AHT10";
  }
  String message;
  serializeJson(doc, message);
  return message;
}

  
void Sensors::getCo2DataFromSensors(JsonObject sensorData, int ammount, int dela){
  JsonObject errors = sensorData["erros"].as<JsonObject>();
  sensorData["co2"] = NULL;
  sensorData["tvoc"] = NULL;
  
  uint16_t co2 = 0;
  uint16_t tvoc = 0;
  
  for(int i = 0; i<ammount;i++){
    if(ccs.available() && !ccs.readData() && ccs.geteCO2() != 0){
       co2 += ccs.geteCO2();
       Serial.print(ccs.geteCO2());
       Serial.print("  ");
       tvoc += ccs.getTVOC();
      }else{
        co2 = 0;
        break;
      }
    delay(dela);
  }
  
  if(co2 != 0 || tvoc != 0){
    sensorData["co2"] = co2/ammount;
    sensorData["tvoc"] = tvoc/ammount;
  }else errors["CCS811"] = "Falha ao realizar leitura";
}

void Sensors::getTempUmidadeFromSensors(JsonObject sensorData){
  JsonObject errors = sensorData["erros"].as<JsonObject>();
  sensorData["temperatura"] = NULL;
  sensorData["umidade"] = NULL;

  sensors_event_t  temp;
  sensors_event_t  umid;

  if(aht.getEvent(&umid, &temp)){
    sensorData["temperatura"] = temp.temperature;
    sensorData["umidade"] = umid.relative_humidity;
  }else errors["AHT10"] = "Falha ao realizar leitura";
}
  
void Sensors::setCcs811DriveMode(int driveMode){
  ccs.setDriveMode(driveMode);
}
