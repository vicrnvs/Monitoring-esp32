package unip.com.inbound.adapter.mappers;

import org.mapstruct.Mapper;
import unip.com.domain.model.Esp32ConfigParams;
import unip.com.inbound.adapter.dto.Esp32ConfigParamsDto;

@Mapper(componentModel = "cdi")
public interface Esp32ConfigparamsDtoMapper {

    Esp32ConfigParamsDto toDto(Esp32ConfigParams esp32ConfigParams);

    Esp32ConfigParams toModel(Esp32ConfigParamsDto esp32ConfigParamsDto);
}
