package unip.com.inbound.factories;

import unip.com.inbound.adapter.dto.DataDto;
import unip.com.inbound.adapter.dto.SensorDataDto;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;

@ApplicationScoped
public class DataDtoFactory {


    public DataDto create(Integer identificador){
        SensorDataDto sensorData = createsensorData();
        return DataDto.builder().identificador(identificador).epoch((int) ZonedDateTime.now().toEpochSecond()).sensorData(sensorData).build();
    }

    public SensorDataDto createsensorData(){
        return SensorDataDto.builder().moisture(456).temperatura(25.6).airHumidity(30).build();
    }
}
