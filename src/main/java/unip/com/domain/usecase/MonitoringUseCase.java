package unip.com.domain.usecase;

import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;
import unip.com.domain.model.Data;
import unip.com.inbound.adapter.dto.DataRequestEndereco;
import unip.com.domain.model.Esp32;
import unip.com.inbound.adapter.dto.ArimaForecastResponse;
import unip.com.inbound.port.MonitoringPort;
import unip.com.outbound.port.DataDataPort;
import unip.com.outbound.port.MonitoringDataPort;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@RequestScoped
public class MonitoringUseCase implements MonitoringPort {

    @Inject
    DataDataPort dataDataPort;
    @Inject
    MonitoringDataPort monitoringDataPort;

    @Override
    @Transactional
    public Esp32 createEsp32(Esp32 esp32) {
        Esp32 esp32R = monitoringDataPort.findEsp32ByIdentificador(esp32.getIdentificador());
        if(Objects.nonNull(esp32R)){
            throw new IllegalArgumentException("Esp32 Já registrado com identificador");
        }

        esp32.setCriadoEm(ZonedDateTime.now());
        return monitoringDataPort.save(esp32);
    }

    @Override
    public Esp32 updateEsp32(Esp32 esp32) {
        Esp32 esp32R = monitoringDataPort.findEsp32ByIdentificador(esp32.getIdentificador());

        if(Objects.isNull(esp32R)){
            throw new IllegalArgumentException("Esp32 não encontrado com identificador");
        }
        esp32.setId(esp32R.getId());
        esp32.setCriadoEm(esp32R.getCriadoEm());
        esp32.getConfigParams().forEach(c -> c.setActive(true));
        return monitoringDataPort.save(esp32);
    }

    @Override
    public List<Esp32> listAllEsp32(){

        List<Esp32> esps = monitoringDataPort.listAllEsp32();

        if(esps.isEmpty()){
            throw new IllegalArgumentException("Nenhuma unidade registrada");
        }
        return esps;
    }

    @Override
    public List<Data> consultarDataPorEnderecoData(DataRequestEndereco dataRequestEndereco) {

        if(Objects.isNull(dataRequestEndereco.getCidade())  && Objects.nonNull(dataRequestEndereco.getBairro())){
            throw new IllegalArgumentException("Não é possível realizar busca: bairro informado mas cidade não informado");
        }

        List<Data> data = dataDataPort.buscarPorEndereco(dataRequestEndereco);

        if(Objects.isNull(data) || data.isEmpty()){
            throw new IllegalArgumentException("Nenhum dado encontrado com parametros");
        }

        return data;
    }

    @Override
    public List<Esp32> consultarEsp32sParaProximaManutencao(){
        var dataPrevista = LocalDate.now();
        List<Esp32> esp32s = monitoringDataPort.listarEsp32sParaProximaManutencao(dataPrevista);

        if(Objects.isNull(esp32s) || esp32s.isEmpty()){
            throw new IllegalArgumentException("Nenhum dado encontrado com parametros");
        }
        return esp32s;
    }

    @Override
    public ArimaForecastResponse timeSeriesForecast(ArimaParams arimaParams, int tamanhoPredicao, double[] data){
        ForecastResult result = Arima.forecast_arima(data, tamanhoPredicao, arimaParams);
        double[] forecastResult = result.getForecast();
        double rmse = result.getRMSE();
        double maxNormalizedVariance = result.getMaxNormalizedVariance();
        String log = result.getLog();

        return new ArimaForecastResponse(forecastResult, rmse, maxNormalizedVariance, log);
    }

}
