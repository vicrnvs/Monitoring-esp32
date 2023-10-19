package unip.com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArimaForecastResponse {

    private double[] forecastResult;
    private double rmse;
    private double maxNormalizedVariance;
    private String log;
}
