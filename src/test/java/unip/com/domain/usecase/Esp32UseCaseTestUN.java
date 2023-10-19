package unip.com.domain.usecase;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import unip.com.domain.model.Esp32ConfigParams;
import unip.com.domain.usecase.factories.DataFactory;
import unip.com.domain.usecase.factories.Esp32Factory;
import unip.com.outbound.port.DataDataPort;
import unip.com.outbound.port.MonitoringDataPort;

import javax.inject.Inject;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class Esp32UseCaseTestUN {

    @InjectMock
    MonitoringDataPort monitoringDataPort;
    @Inject
    Esp32UseCase esp32UseCase;
    @InjectMock
    DataDataPort dataDataPort;
    @Inject
    DataFactory dataFactory;
    @Inject
    Esp32Factory esp32Factory;

    @Test
    void now() {
        var now = esp32UseCase.now();
        Instant instant = Instant.ofEpochSecond(Long.parseLong(now));
    }

    @Test
    void saveData() {
        var data = dataFactory.create(null);
        data.getEsp32().setIdentificador("2646");
        var data1 = dataFactory.create(1);
        var esp32 = esp32Factory.createEsp32(2);
        esp32.setIdentificador("2646");

        when(monitoringDataPort.findEsp32ByIdentificador(anyString())).thenReturn(esp32);
        when(dataDataPort.saveData(data)).thenReturn(data1);

        var response = esp32UseCase.saveData(data);
        assertNotNull(response.getId());
    }

    @Test
    void getConfigParamsActive() {
        Esp32ConfigParams esp32ConfigParams = Esp32ConfigParams.builder().id(1).param("teste").value("valor").active(true).build();
        when(monitoringDataPort.findEsp32WithConfigActive(anyString())).thenReturn(Arrays.asList(esp32ConfigParams));
        when(monitoringDataPort.findEsp32ByIdentificador(anyString())).thenReturn(esp32Factory.createEsp32(2));

        List<Esp32ConfigParams> list = esp32UseCase.getConfigParamsActive("iden");
        assertTrue(list.contains(esp32ConfigParams));
    }
}