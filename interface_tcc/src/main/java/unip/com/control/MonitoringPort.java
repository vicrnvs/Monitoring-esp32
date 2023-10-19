package unip.com.control;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;

import unip.com.control.exceptions.RequestException;
import unip.com.model.ArimaForecastRequest;
import unip.com.model.ArimaForecastResponse;
import unip.com.model.Co2DataDto;
import unip.com.model.Esp32Dto;

public class MonitoringPort {

	private Requests request;

	public MonitoringPort() {
		this.request = new Requests("https://monitoringmoisture-4d5fd27c6347.herokuapp.com");
	}

	@SuppressWarnings("unchecked")
	public List<Co2DataDto> listCo2ByDateAndFullAddress(LocalDateTime dataInicial, LocalDateTime dataFinal,
			String estado, String cidade, String bairro) throws RequestException {
		HashMap<String, String> params = new HashMap<String, String>();
		
		if(Objects.nonNull(dataInicial)) params.put("data_inicial", dataInicial.toString());
		if(Objects.nonNull(dataFinal))params.put("data_final", dataFinal.toString());
		if(Objects.nonNull(estado) && !estado.isEmpty())params.put("estado", estado);
		if(Objects.nonNull(cidade) && !cidade.isEmpty())params.put("cidade", cidade);
		if(Objects.nonNull(bairro) && !bairro.isEmpty())params.put("bairro", bairro);
		List<Co2DataDto> data = (List<Co2DataDto>) request.getRequest(new TypeReference<List<Co2DataDto>>() {}, params, "/monitoring/data");
		ZoneId zoneId = ZoneId.systemDefault(); // Use the system default time zone

		data.forEach(t -> {
			Instant instant = Instant.ofEpochSecond(t.getEpoch());
			t.setDateTime(instant.atZone(zoneId).toLocalDateTime());
		});
		return data;

	}
	
	public ArimaForecastResponse getArimaPrediction(ArimaForecastRequest arimaParams)throws RequestException {
		
		List<ArimaForecastResponse> re = (List<ArimaForecastResponse>) request.postRequest(new TypeReference<ArimaForecastResponse>() {}, null, "/monitoring/data/arima", arimaParams);
		if(!re.isEmpty()) return re.get(0);
		return null;
	}
	
	public List<Esp32Dto> listAllEsp32() throws RequestException{
		return (List<Esp32Dto>) request.getRequest(new TypeReference<List<Esp32Dto>>() {}, null, "/monitoring/esp32");
	}
	
	public void registerNewEsp32(Esp32Dto esp32) throws RequestException {
		request.postRequest(new TypeReference<Esp32Dto>() {}, null, "/monitoring/esp32", esp32);
	}
	
	public void updateEsp32(Esp32Dto esp32) throws RequestException {
		request.putRequest(new TypeReference<Esp32Dto>() {}, null, "/monitoring/esp32", esp32);
	}
}
