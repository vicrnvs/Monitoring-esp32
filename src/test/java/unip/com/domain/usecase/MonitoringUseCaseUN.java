package unip.com.domain.usecase;

import com.workday.insights.timeseries.arima.struct.ArimaParams;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import unip.com.domain.model.Data;
import unip.com.inbound.adapter.dto.DataRequestEndereco;
import unip.com.domain.model.Esp32;
import unip.com.domain.usecase.factories.DataFactory;
import unip.com.domain.usecase.factories.Esp32Factory;
import unip.com.inbound.adapter.dto.ArimaForecastResponse;
import unip.com.outbound.port.DataDataPort;
import unip.com.outbound.port.MonitoringDataPort;

import javax.inject.Inject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class MonitoringUseCaseUN {

    @InjectMock
    MonitoringDataPort monitoringDataPort;
    @InjectMock
    DataDataPort dataDataPort;
    @Inject
    MonitoringUseCase monitoringUseCase;
    @Inject
    Esp32Factory esp32Factory;
    @Inject
    DataFactory dataFactory;

    @Test
    public void createEsp32() {
        var esp32 = esp32Factory.createEsp32(null);

        when(monitoringDataPort.findEsp32ByIdentificador(esp32.getIdentificador())).thenReturn(null);
        when(monitoringDataPort.save(esp32)).thenReturn(esp32);

        var esp32Registered = monitoringUseCase.createEsp32(esp32);

        assertNotNull(esp32Registered);
        assertNotNull(esp32Registered.getConfigParams());
        assertFalse(esp32Registered.getConfigParams().isEmpty());
        assertNotNull(esp32Registered.getCriadoEm());
    }

    @Test
    void updateEsp32() {
        var esp32 = esp32Factory.createEsp32(null);
        esp32.generateDefaultConfigParams();
        var exitentExp32 = esp32Factory.createEsp32(10);


        when(monitoringDataPort.findEsp32ByIdentificador(esp32.getIdentificador())).thenReturn(exitentExp32);
        when(monitoringDataPort.save(esp32)).thenReturn(exitentExp32);

        var esp32Registered = monitoringUseCase.updateEsp32(esp32);

        assertNotNull(esp32Registered);
        assertNotNull(esp32Registered.getId());
    }

    @Test
    void consultarMoisturePorEnderecoData() {
        var moistureDataRequestEndereco = DataRequestEndereco.builder().estado("estado").dataFinal(ZonedDateTime.now()).dataInicial(ZonedDateTime.now())
                .bairro("bairro").cidade("cidade").build();

        var moisture1 = dataFactory.create(2);
        var moisture2 = dataFactory.create(4);
        moisture2.getSensorData().setMoisture(560);
        var moisture3 = dataFactory.create(3);
        moisture3.getSensorData().setMoisture(800);
        var listMoisture = new ArrayList<Data>();
        listMoisture.addAll(Arrays.asList(moisture1, moisture2, moisture3));

        when(dataDataPort.buscarPorEndereco(moistureDataRequestEndereco)).thenReturn(listMoisture);

        var listMoistureR = monitoringUseCase.consultarDataPorEnderecoData(moistureDataRequestEndereco);
        assertTrue( listMoistureR.size() == 3);
        assertTrue(listMoistureR.containsAll(Arrays.asList(moisture1, moisture2, moisture3)));
    }


    @Test
    void consultarEsp32sParaProximaManutencao() {
        List<Esp32> esp32List = new ArrayList<>();
        Esp32 esp32 = esp32Factory.createEsp32(10);
        esp32List.add(esp32);

        when(monitoringDataPort.listarEsp32sParaProximaManutencao(any())).thenReturn(esp32List);

        List<Esp32> listEsp32R = monitoringUseCase.consultarEsp32sParaProximaManutencao();
        assertTrue(listEsp32R.contains(esp32));

    }

    @Test
    void timeSeriesForecast() {
        ArimaParams arimaParams = new ArimaParams(1,1,1,1,1,1,1);
        int tamanhoPred = 10;
        double[] data = new double[]{1,2,3,4,5,6,7,8,9,10};

        ArimaForecastResponse re = monitoringUseCase.timeSeriesForecast(arimaParams,tamanhoPred, data);
        var average = Arrays.stream(re.getForecastResult()).average();
        assertEquals(15.5,average.getAsDouble());
    }
}