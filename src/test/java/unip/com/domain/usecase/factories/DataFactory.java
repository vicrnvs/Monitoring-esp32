package unip.com.domain.usecase.factories;

import unip.com.domain.model.Data;
import unip.com.domain.model.Esp32;
import unip.com.domain.model.SensorData;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;
import java.util.Objects;

@ApplicationScoped
public class DataFactory {

    public Data create(Integer id){
        SensorData sensorData = createsensorData(Objects.nonNull(id)? id++ : null);
        return Data.builder().id(id).coleta(ZonedDateTime.now()).esp32(Esp32.builder().id(25).build()).sensorData(sensorData).build();
    }

    public SensorData createsensorData(Integer id){
        return SensorData.builder().id(id).moisture(456).temperatura(25.6).airHumidity(30).build();
    }
}
