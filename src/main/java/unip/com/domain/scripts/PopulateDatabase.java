package unip.com.domain.scripts;

import unip.com.domain.model.Data;
import unip.com.domain.model.Esp32;
import unip.com.domain.model.SensorData;
import unip.com.inbound.port.Esp32Port;
import unip.com.outbound.adapter.mysql.MonitoringDataAdapter;
import unip.com.outbound.port.ZonedDateTimeBrPort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class PopulateDatabase {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Inject
    Esp32Port esp32Port;
    @Inject
    MonitoringDataAdapter monitoringDataAdapter;
    @Inject
    ZonedDateTimeBrPort zonedDateTimeBrPort;

    public void populateDatabaseFromUCICo() {
        try {

            BufferedReader bf = new BufferedReader(new FileReader("AirQualityUCI.csv"));
            String line;
            Esp32 esp32 = monitoringDataAdapter.findEsp32ByIdentificador("1000000001");
            for(int i = 0; i < 10000; i++) {
                line = bf.readLine();
                if(i > 4999){

                    String[] splitLine = line.split(";");

                    var time = LocalTime.parse(splitLine[1].replace(".", ":"));
                    var date = LocalDate.parse(splitLine[0], formatter);
                    var coleta = ZonedDateTime.of(date, time, zonedDateTimeBrPort.getZoneId());
                    SensorData sensorData = SensorData.builder().moisture(Integer.parseInt(splitLine[3]))
                            .temperatura(Double.parseDouble(splitLine[12].replace(",", ".")))
                            .airHumidity((int) Double.parseDouble(splitLine[13].replace(",", ".")))
                            .erros("").build();

                    if(sensorData.getMoisture().equals(-200)){
                        sensorData.setMoisture(0);
                        sensorData.setErros(sensorData.getErros().concat("[CCS811] Falha ao detectar sensor CCS811 : "));
                    }
                    if (sensorData.getTemperatura().equals(-200.0)) {
                        sensorData.setTemperatura(0.0);
                        sensorData.setAirHumidity(0);
                        sensorData.setErros(sensorData.getErros().concat("[AHT10] Falha ao detectar sensor AHT10 : "));
                    }

                    Data data = Data.builder().coleta(coleta).sensorData(sensorData).esp32(esp32)
                            .build();
                    data.getSensorData();
                    esp32Port.saveData(data);

                }
                }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
