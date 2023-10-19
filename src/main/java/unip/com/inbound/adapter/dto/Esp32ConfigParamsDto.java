package unip.com.inbound.adapter.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Esp32ConfigParamsDto {

    private Integer id;

    @NotNull
    @NotEmpty
    private String param;

    @NotNull
    @NotEmpty
    private String value;

}
