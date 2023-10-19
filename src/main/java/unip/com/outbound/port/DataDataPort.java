package unip.com.outbound.port;

import unip.com.domain.model.Data;
import unip.com.inbound.adapter.dto.DataRequestEndereco;
import unip.com.domain.model.SensorData;

import java.util.List;

public interface DataDataPort {

    Data saveData(Data dataEntity);
    SensorData saveSensorData(SensorData sensorData);
    List<Data> buscarPorEndereco(DataRequestEndereco dataRequestEndereco);

}
