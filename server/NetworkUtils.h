#ifndef NetworkUtils_h
#define NetworkUtils_h
#include <ArduinoJson.h>
#include "Arduino.h"
#include "Adafruit_CCS811.h"
#include <Adafruit_AHTX0.h>


class NetworkUtils{

  public:
    
    String serverName;
    boolean connectWifi(String ssid, String pass);
    boolean sendDataRequest(String body);
    String getParamsFromServer(String mac);
    int getDateTimeFromServer();
};

class Sensors{

    
  public:
    Adafruit_CCS811 ccs;
    boolean ignoreWarmupCC811 = false;
    Adafruit_AHTX0 aht;
    String startSensors();
    void getCo2DataFromSensors(JsonObject sensorData, int ammount, int dela);
    void getTempUmidadeFromSensors(JsonObject sensorData);
    void setCcs811DriveMode(int driveMode);
};

#endif
