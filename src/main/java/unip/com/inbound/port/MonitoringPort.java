package unip.com.inbound.port;

import com.workday.insights.timeseries.arima.struct.ArimaParams;
import unip.com.domain.model.Data;
import unip.com.inbound.adapter.dto.DataRequestEndereco;
import unip.com.domain.model.Esp32;
import unip.com.inbound.adapter.dto.ArimaForecastResponse;

import java.util.List;

public interface MonitoringPort {

    Esp32 createEsp32(Esp32 esp32);
    Esp32 updateEsp32(Esp32 esp32);

    List<Esp32> listAllEsp32();

    List<Data> consultarDataPorEnderecoData(DataRequestEndereco dataRequestEndereco);
    List<Esp32> consultarEsp32sParaProximaManutencao();
    ArimaForecastResponse timeSeriesForecast(ArimaParams arimaParams, int tamanhoPredicao, double[] data);
}
