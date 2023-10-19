package unip.com.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class SensorDataDto {

    private String erros;

    private Integer moisture;

    private Double temperatura;

    private Integer airHumidity;


	public String getErros() {
		return erros;
	}


	public void setErros(String erros) {
		this.erros = erros;
	}


	public Integer getMoisture() {
		return moisture;
	}


	public void setTvoc(Integer tvoc) {
		this.moisture = tvoc;
	}




	public Double getTemperatura() {
		return temperatura;
	}




	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}




	public Integer getUmidade() {
		return airHumidity;
	}




	public void setUmidade(Integer umidade) {
		this.airHumidity = umidade;
	}




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
