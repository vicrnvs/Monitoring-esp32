package unip.com.inbound.adapter;

import unip.com.domain.model.Data;
import unip.com.inbound.adapter.dto.DataDto;
import unip.com.inbound.adapter.dto.Esp32ConfigParamsDto;
import unip.com.inbound.adapter.mappers.DataDtoMapper;
import unip.com.inbound.adapter.mappers.Esp32ConfigparamsDtoMapper;
import unip.com.inbound.port.Esp32Port;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("esp32/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Esp32RestAdapter {

    @Inject
    Esp32Port esp32Port;

    @Inject
    DataDtoMapper dataDtoMapper;
    @Inject
    Esp32ConfigparamsDtoMapper esp32ConfigparamsDtoMapper;

    @Path("now/")
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String now(){
        return esp32Port.now();
    }

    @Path("data/")
    @POST
    public DataDto createData(@Valid DataDto dataDto) {
        Data data = dataDtoMapper.DataDto2Data(dataDto);
        DataDto dataDto1 = dataDtoMapper.Data2DataDto(esp32Port.saveData(data));
        return dataDto1;
    }

    @Path("configParams/")
    @GET
    public List<Esp32ConfigParamsDto> getConfigParamsActive(@QueryParam("identificador") @NotNull String identificador){
        return esp32Port.getConfigParamsActive(identificador).stream().map(esp32ConfigparamsDtoMapper::toDto).collect(Collectors.toList());
    }

}
