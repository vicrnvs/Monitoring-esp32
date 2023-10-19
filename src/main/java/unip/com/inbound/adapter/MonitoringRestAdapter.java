package unip.com.inbound.adapter;

import com.workday.insights.timeseries.arima.struct.ArimaParams;
import unip.com.inbound.adapter.dto.DataRequestEndereco;
import unip.com.domain.model.Esp32;
import unip.com.inbound.adapter.dto.ArimaForecastRequest;
import unip.com.inbound.adapter.dto.ArimaForecastResponse;
import unip.com.inbound.adapter.dto.DataDto;
import unip.com.inbound.adapter.dto.Esp32Dto;
import unip.com.inbound.adapter.mappers.DataDtoMapper;
import unip.com.inbound.adapter.mappers.Esp32DtoMapper;
import unip.com.inbound.port.MonitoringPort;
import unip.com.outbound.port.ZonedDateTimeBrPort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/monitoring")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MonitoringRestAdapter {

    @Inject
    Esp32DtoMapper esp32DtoMapper;
    @Inject
    MonitoringPort monitoringPort;
    @Inject
    DataDtoMapper dataDtoMapper;
    @Inject
    ZonedDateTimeBrPort zonedDateTimeBrPort;

    @Path("/esp32")
    @POST
    public Esp32Dto createEsp32(@Valid Esp32Dto esp32Dto){
        Esp32 esp32 = esp32DtoMapper.toModel(esp32Dto);
        return esp32DtoMapper.toDto(monitoringPort.createEsp32(esp32));
    }

    @Path("/esp32")
    @PUT
    public Esp32Dto updateEsp32(@Valid Esp32Dto esp32Dto){
        Esp32 esp32 = esp32DtoMapper.toModel(esp32Dto);
        return esp32DtoMapper.toDto(monitoringPort.updateEsp32(esp32));
    }

    @Path("/esp32")
    @GET
    public List<Esp32Dto> listAllEsp32(){
        return monitoringPort.listAllEsp32().stream().map(esp32DtoMapper::toDto).collect(Collectors.toList());
    }

    @Path("/data")
    @GET
    public List<DataDto> consultarDataPorEnderecoData(@QueryParam("estado")@NotNull String estado,
                                                     @QueryParam("cidade") String cidade,
                                                     @QueryParam("bairro") String bairro,
                                                     @QueryParam("data_inicial") @NotNull String dataInicial,
                                                     @QueryParam("data_final")@NotNull String dataFinal){
        ZonedDateTime dataIni;
        ZonedDateTime dataFin;
        try{
            dataFin = ZonedDateTime.of(LocalDateTime.parse(dataFinal), zonedDateTimeBrPort.getZoneId());
            dataIni = ZonedDateTime.of(LocalDateTime.parse(dataInicial), zonedDateTimeBrPort.getZoneId());
        }catch (Exception e){
            throw new IllegalArgumentException("Data inicial ou data final não esta em padrão valido");
        }
        DataRequestEndereco dataRequestEndereco = new DataRequestEndereco(
                estado, cidade, bairro, dataIni, dataFin);
        return monitoringPort.consultarDataPorEnderecoData(dataRequestEndereco).stream().map(dataDtoMapper::Data2DataDto)
                .collect(Collectors.toList());
    }

    @Path("/data/raw")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String consultarMoisturePorEnderecoDataRawText(@QueryParam("estado")@NotNull String estado,
                                                        @QueryParam("cidade") String cidade,
                                                        @QueryParam("bairro") String bairro,
                                                        @QueryParam("data_inicial") @NotNull String dataInicial,
                                                        @QueryParam("data_final")@NotNull String dataFinal){
        ZonedDateTime dataIni;
        ZonedDateTime dataFin;
        try{
            dataFin = ZonedDateTime.of(LocalDateTime.parse(dataFinal), zonedDateTimeBrPort.getZoneId());
            dataIni = ZonedDateTime.of(LocalDateTime.parse(dataInicial), zonedDateTimeBrPort.getZoneId());
        }catch (Exception e){
            throw new IllegalArgumentException("Data inicial ou data final não esta em padrão valido");
        }
        DataRequestEndereco dataRequestEndereco = new DataRequestEndereco(
                estado, cidade, bairro, dataIni, dataFin);
        StringBuilder builder = new StringBuilder();
        monitoringPort.consultarDataPorEnderecoData(dataRequestEndereco).stream().forEach(data ->  {
            String formatedData = String.format("%d,%s\n", data.getSensorData().getMoisture(), data.getColeta().toLocalDateTime().toString());
            builder.append(formatedData);
        });
        return builder.toString();
    }

    @Path("/esp32/consulta_proximaManutencao")
    @GET
    public List<Esp32Dto> consultarEsp32sParaProximaManutencao(){
        return monitoringPort.consultarEsp32sParaProximaManutencao().stream().map(esp32DtoMapper::toDto).collect(Collectors.toList());
    }

    @Path("/data/arima")
    @POST
    public ArimaForecastResponse arimaForecast(@Valid ArimaForecastRequest arima){
        ArimaParams arimaParams = new ArimaParams(arima.getNp(), arima.getNd(), arima.getNq(), arima.getSp(),
                arima.getSd(), arima.getSq(), arima.getM());
        return monitoringPort.timeSeriesForecast(arimaParams, arima.getTamanhoPredicao(), arima.getData());
    }


}
