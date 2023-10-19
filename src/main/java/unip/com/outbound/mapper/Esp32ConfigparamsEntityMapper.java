package unip.com.outbound.mapper;

import org.mapstruct.Mapper;
import unip.com.domain.model.Esp32ConfigParams;
import unip.com.inbound.adapter.dto.Esp32ConfigParamsDto;
import unip.com.outbound.adapter.mysql.entities.Esp32ConfigParamsEntity;

@Mapper(componentModel = "cdi")
public interface Esp32ConfigparamsEntityMapper {

    Esp32ConfigParamsEntity toEntity(Esp32ConfigParams esp32ConfigParams);

    Esp32ConfigParams toModel(Esp32ConfigParamsEntity esp32ConfigParamsEntity);
}
