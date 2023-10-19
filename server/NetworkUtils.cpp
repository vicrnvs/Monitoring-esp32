#include "Arduino.h"
#include "NetworkUtils.h"
#include <HTTPClient.h>
#include <WiFi.h>

boolean NetworkUtils::sendDataRequest(String body){
  if(WiFi.status() != WL_CONNECTED) return false;

  HTTPClient http;
  String serverPath = serverName + "/data";

  http.begin(serverPath);
  http.addHeader("Content-Type", "application/json");
    
  Serial.print("Post request com body: ");
  Serial.println(body);
  int httpResponseCode = http.POST(body);
  
  if(httpResponseCode == 200){
    Serial.println("Dados enviados");
    return true;
  }
  Serial.println("Falha ao enviar dados");
  return false;
}

int NetworkUtils::getDateTimeFromServer(){
  if(WiFi.status() != WL_CONNECTED) return 0;

  HTTPClient http;
  String serverPath = serverName + "/now";

  http.begin(serverPath);
  http.addHeader("Content-Type", "application/json");
    
  int httpResponseCode = http.GET();

  if(httpResponseCode == 200){
    String payload = http.getString();
    return payload.toInt();
  }
  return 0;
}

String NetworkUtils::getParamsFromServer(String mac){
  if(WiFi.status() != WL_CONNECTED) return "";

  HTTPClient http;
  String serverPath = serverName + "/configParams?identificador=" + mac;
  http.begin(serverPath);
  int httpResponseCode = http.GET();

    if(httpResponseCode == 200){
    String payload = http.getString();
    Serial.print("Response from params : ");
    Serial.println(payload);
    return payload;
  }
  return "";
}

boolean NetworkUtils::connectWifi(String ssid, String pass){
  if(WiFi.status() == WL_CONNECTED) WiFi.disconnect();
  delay(100);
  char ssidP[100];
  char passP[100];
  ssid.toCharArray(ssidP, 100);
  pass.toCharArray(passP, 100);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssidP, passP);
  delay(10000);  
  
  if(WiFi.status() == WL_CONNECTED){
    Serial.print("Conectado a rede: ");
    Serial.println(ssid);
    return true;
  }
  Serial.println("Falha ao conectar com rede");
  return false;
}
