package unip.com.outbound.adapter.mysql;

import unip.com.domain.model.Esp32;
import unip.com.domain.model.Esp32ConfigParams;
import unip.com.outbound.adapter.mysql.entities.Esp32Entity;
import unip.com.outbound.mapper.Esp32ConfigparamsEntityMapper;
import unip.com.outbound.mapper.Esp32EntityMapper;
import unip.com.outbound.port.MonitoringDataPort;
import unip.com.outbound.repository.Esp32ConfigRepository;
import unip.com.outbound.repository.Esp32Repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class MonitoringDataAdapter implements MonitoringDataPort {

    @Inject
    Esp32Repository esp32Repository;
    @Inject
    Esp32EntityMapper esp32EntityMapper;
    @Inject
    Esp32ConfigRepository esp32ConfigRepository;
    @Inject
    Esp32ConfigparamsEntityMapper esp32ConfigparamsEntityMapper;

    @Override
    public Esp32 save(Esp32 esp32) {
        Esp32Entity esp32Entity = esp32EntityMapper.toEntity(esp32);
        var c = esp32Entity.getConfigParams();
        esp32Entity.setConfigParams(null);

        c.forEach(esp32ConfigParamsEntity -> esp32Entity.addConfig(esp32ConfigParamsEntity));

        Esp32Entity finalEnti = esp32Repository.save(esp32Entity);

        return esp32EntityMapper.toModel(finalEnti);
    }

    @Override
    public Esp32 findEsp32ById(Integer id) {
        Esp32Entity esp32Entity = esp32Repository.findById(id).get();
        if(Objects.isNull(esp32Entity)) return null;
        return esp32EntityMapper.toModel(esp32Entity);
    }

    @Override
    public Esp32 findEsp32ByIdentificador(String identificador) {
        Esp32Entity esp32Entity = esp32Repository.findByIdentificador(identificador);
        if(Objects.isNull(esp32Entity)) return null;
        return esp32EntityMapper.toModel(esp32Entity);
    }

    @Override
    public List<Esp32> listAllEsp32(){
        return esp32Repository.findAll().stream().map(esp32EntityMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Esp32ConfigParams> findEsp32WithConfigActive(String identificador){
        return esp32ConfigRepository.findByIdentificadorConfigParamsActive(identificador, true).stream()
                .map(esp32ConfigparamsEntityMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Esp32> listarEsp32sParaProximaManutencao(LocalDate diasAntesManutecao){
        return esp32Repository.listarEsp32sComManutencao(diasAntesManutecao).stream()
                .map(esp32EntityMapper::toModel).collect(Collectors.toList());
    }
}
