package unip.com.model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Esp32ConfigParamsDto {

    private Integer id;

    private String param;

    private String value;

}
