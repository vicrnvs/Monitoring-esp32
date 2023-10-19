package unip.com.domain.usecase;

import unip.com.domain.model.Data;
import unip.com.domain.model.Esp32;
import unip.com.domain.model.Esp32ConfigParams;
import unip.com.domain.scripts.PopulateDatabase;
import unip.com.inbound.port.Esp32Port;
import unip.com.outbound.port.DataDataPort;
import unip.com.outbound.port.MonitoringDataPort;
import unip.com.outbound.port.ZonedDateTimeBrPort;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RequestScoped
public class Esp32UseCase implements Esp32Port {

    @Inject
    DataDataPort dataDataPort;
    @Inject
    MonitoringDataPort monitoringDataPort;
    @Inject
    PopulateDatabase populateDatabase;
    @Inject
    ZonedDateTimeBrPort zonedDateTimeBrPort;

    @Override
    public String now() {
        long epochSeconds = zonedDateTimeBrPort.now().toEpochSecond();
        return Long.toString(epochSeconds);
    }

    @Override
    @Transactional
    public Data saveData(Data data) {
        Esp32 esp32 = monitoringDataPort.findEsp32ByIdentificador(data.getEsp32().getIdentificador());
        if(Objects.isNull(esp32)){
            throw new IllegalArgumentException("Esp32 não cadastrado");
        }

        data.setSensorData(dataDataPort.saveSensorData(data.getSensorData()));
        data.setEsp32(esp32);
        return dataDataPort.saveData(data);
    }

    @Override
    public List<Esp32ConfigParams> getConfigParamsActive(String identificador) {
        var config = monitoringDataPort.findEsp32WithConfigActive(identificador);

        if(Objects.isNull(config) || config.isEmpty()){
            throw new IllegalArgumentException("Nenhuma configuração ativa encontrada");
        }

        Esp32 esp32 = monitoringDataPort.findEsp32ByIdentificador(identificador);
        if(Objects.isNull(esp32)) throw new IllegalArgumentException("Esp32 não encontrado");

        return config;
    }


}
