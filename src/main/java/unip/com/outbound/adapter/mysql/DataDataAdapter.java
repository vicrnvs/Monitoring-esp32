package unip.com.outbound.adapter.mysql;

import unip.com.domain.model.Data;
import unip.com.inbound.adapter.dto.DataRequestEndereco;
import unip.com.domain.model.SensorData;
import unip.com.outbound.adapter.mysql.entities.DataEntity;
import unip.com.outbound.adapter.mysql.entities.SensorDataEntity;
import unip.com.outbound.mapper.DataEntityMapper;
import unip.com.outbound.mapper.SensorDataEntityMapper;
import unip.com.outbound.port.DataDataPort;
import unip.com.outbound.repository.DataRepository;
import unip.com.outbound.repository.SensorDataRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class DataDataAdapter implements DataDataPort {

    @Inject
    DataRepository dataRepository;
    @Inject
    SensorDataRepository sensorDataRepository;
    @Inject
    SensorDataEntityMapper sensorDataEntityMapper;
    @Inject
    DataEntityMapper dataEntityMapper;


    @Override
    public Data saveData(Data data) {
        DataEntity dataEntity = dataEntityMapper.toEntity(data);
        return dataEntityMapper.toModel(dataRepository.save(dataEntity));
    }

    @Override
    public SensorData saveSensorData(SensorData sensorData) {
        SensorDataEntity sensorDataEntity = sensorDataEntityMapper.toEntity(sensorData);
        return sensorDataEntityMapper.toModel(sensorDataRepository.save(sensorDataEntity));
    }

    @Override
    public List<Data> buscarPorEndereco(DataRequestEndereco dataRequestEndereco) {
        Stream<DataEntity> list;

        if(Objects.nonNull(dataRequestEndereco.getBairro())){
            list = dataRepository.findByEsp32CidadeBairro(dataRequestEndereco.getEstado(),
                    dataRequestEndereco.getCidade(), dataRequestEndereco.getBairro(),
                    dataRequestEndereco.getDataInicial(), dataRequestEndereco.getDataFinal());

        } else if (Objects.nonNull(dataRequestEndereco.getCidade())) {
            list = dataRepository.findByEsp32Cidade(dataRequestEndereco.getEstado(), dataRequestEndereco.getCidade(), dataRequestEndereco.getDataInicial(),
                    dataRequestEndereco.getDataFinal());

        }else{
            list = dataRepository.findByEsp32Estado(dataRequestEndereco.getEstado(), dataRequestEndereco.getDataInicial(),
                    dataRequestEndereco.getDataFinal());
        }

        return list.map(dataEntityMapper::toModel).collect(Collectors.toList());
    }


}
