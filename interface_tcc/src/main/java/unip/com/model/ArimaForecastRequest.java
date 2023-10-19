package unip.com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    private Integer tamanhoPredicao;

    private double[] data;
}
