package unip.com.inbound.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArimaForecastRequest {

    private int np;
    private int nd;
    private int nq;

    private int sp;
    private int sd;
    private int sq;
    private int m;

    @NotNull(message = "tamanhoPredicao não pode ser nulo")
    @Positive(message = "tamanhoPredicao não pode ser negativo")
    private Integer tamanhoPredicao;

    @Size(min = 1, message = "data não pode estar vazio")
    private double[] data;
}
