package unip.com.outbound.mapper;

import org.mapstruct.Mapper;
import unip.com.domain.model.Data;
import unip.com.outbound.adapter.mysql.entities.DataEntity;

@Mapper(componentModel = "cdi")
public interface DataEntityMapper {

    DataEntity toEntity(Data data);
    Data toModel(DataEntity dataEntity);
}
