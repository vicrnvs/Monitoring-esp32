package unip.com.inbound.adapter.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unip.com.domain.model.Data;
import unip.com.inbound.adapter.dto.DataDto;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Mapper(componentModel = "cdi")
@ApplicationScoped
public interface DataDtoMapper {


    @Mapping(source = "esp32.identificador", target = "identificador")
    @Mapping(source = "coleta", target = "epoch")
    DataDto Data2DataDto(Data data);
    @Mapping(source = "identificador", target = "esp32.identificador")
    @Mapping(source = "epoch", target = "coleta")
    Data DataDto2Data(DataDto dataDto);

    default ZonedDateTime toZoned(Integer epoch){
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(epoch), ZoneId.of(ZoneId.SHORT_IDS.get("BET")));
    }

    default Integer toEpoch(ZonedDateTime coleta){
        if(Objects.isNull(coleta)) return null;
        return Math.toIntExact(coleta.toEpochSecond());
    }

}
