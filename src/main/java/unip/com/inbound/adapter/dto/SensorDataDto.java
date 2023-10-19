package unip.com.inbound.adapter.dto;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SensorDataDto {

    private String erros;

    @NotNull(message = "moisture não encontrada!")
    private Integer moisture;

    @NotNull(message = "temperatura não encontrada!")
    private Double temperatura;

    @NotNull(message = "airHumidity não encontrada!")
    private Integer airHumidity;


    @JsonSetter("erros")
    public void setErros(Object erros){
        if(Objects.nonNull(erros)){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                this.erros = objectMapper.writeValueAsString(erros);
            } catch (Exception e) {
                this.erros = null;
            }
        }else this.erros = null;

    }
}
