package unip.com.inbound.adapter.mappers;

import org.mapstruct.Mapper;
import unip.com.domain.model.Esp32;
import unip.com.inbound.adapter.dto.Esp32Dto;

@Mapper(componentModel = "cdi")
public interface Esp32DtoMapper {

    Esp32Dto toDto(Esp32 esp32);
    Esp32 toModel(Esp32Dto esp32Dto);
}
