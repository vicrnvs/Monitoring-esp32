package unip.com.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unip.com.domain.model.Esp32;
import unip.com.outbound.adapter.mysql.entities.Esp32Entity;

@Mapper(componentModel = "cdi")
public interface Esp32EntityMapper {

    Esp32Entity toEntity(Esp32 esp32);
    Esp32 toModel(Esp32Entity esp32Entity);
}
