package unip.com.outbound.mapper;

import org.mapstruct.Mapper;
import unip.com.domain.model.SensorData;
import unip.com.outbound.adapter.mysql.entities.SensorDataEntity;

@Mapper(componentModel = "cdi")
public interface SensorDataEntityMapper {

    SensorDataEntity toEntity(SensorData sensorData);
    SensorData toModel(SensorDataEntity sensorDataEntity);

}
