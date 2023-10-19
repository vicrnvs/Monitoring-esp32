package unip.com.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import unip.com.control.exceptions.RequestException;

public class Requests {
	
	private String baseUrl;
	private OkHttpClient client;
	private ObjectMapper ob;
	
	public Requests(String baseUrl) {
		this.baseUrl = baseUrl;
		this.client = new OkHttpClient();
		this.ob = new ObjectMapper();
		this.ob.registerModule(new JavaTimeModule());
	}
	
	@SuppressWarnings("unchecked")
	public List<?> getRequest(@SuppressWarnings("rawtypes") TypeReference targetClass, HashMap<String, String> params, String additionalPath) throws RequestException {
		
		String url = baseUrl;
		List<?> responseObject = new ArrayList<>();
		url += additionalPath;
		
		url = concatParams(url, params);
		
		try {
			Request request = new Request.Builder().url(url).get().build();
			Call call = client.newCall(request);
			Response response = call.execute();
			
			String respBody = response.body().string();
			System.out.println(respBody);
			if(response.isSuccessful()) {
				responseObject = (List<?>) this.ob.readValue(respBody, targetClass);
			}else {
				throw new RequestException(String.format("Falha ao buscar dados: %s", respBody));
			}
			
			
		} catch (StreamReadException e1) {
			e1.printStackTrace();
			throw new RequestException("Falha ao realizar mapeamento de entidade");
		} catch (DatabindException e1) {
			e1.printStackTrace();
			throw new RequestException("Falha ao realizar mapeamento de entidade");
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new RequestException("Falha ao realizar requisição");
		}
		
		return responseObject;
	}
	
	public List<?> postRequest(TypeReference targetClass, HashMap<String, String> params, String additionalPath, Object body) throws RequestException {
		String url = baseUrl;
		List<Object> responseObject = new ArrayList<>();
		url += additionalPath;
		
		url = concatParams(url, params);
		
		try {
			RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), this.ob.writeValueAsString(body));
			Request request = new Request.Builder().url(url).post(requestBody).post(requestBody).build();
			Call call = client.newCall(request);
			Response response = call.execute();
			
			String respBody = response.body().string();
			System.out.println(respBody);
			
			if(response.isSuccessful()) {
				Object obje = this.ob.readValue(respBody, targetClass);
				if(obje instanceof Collection) {
					responseObject = (List<Object>) obje;
				}else {
					responseObject.add(obje);
				}
			}else {
				throw new RequestException(String.format("Falha ao buscar dados: %s", respBody));
			}
			
		} catch (StreamReadException e1) {
			e1.printStackTrace();
			throw new RequestException("Falha ao realizar mapeamento de entidade");
		} catch (DatabindException e1) {
			e1.printStackTrace();
			throw new RequestException("Falha ao realizar mapeamento de entidade");
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new RequestException("Falha ao realizar requisição");
		}
		
		return responseObject;
	}

	
	public List<?> putRequest(TypeReference targetClass, HashMap<String, String> params, String additionalPath, Object body) throws RequestException{
		String url = baseUrl;
		List<Object> responseObject = new ArrayList<>();
		url += additionalPath;
		
		url = concatParams(url, params);
		
		try {
			System.out.println(this.ob.writeValueAsString(body));
			RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), this.ob.writeValueAsString(body));
			Request request = new Request.Builder().url(url).post(requestBody).put(requestBody).build();
			Call call = client.newCall(request);
			Response response = call.execute();
			
			String respBody = response.body().string();
			System.out.println(respBody);
			
			if(response.isSuccessful()) {
				Object obje = this.ob.readValue(respBody, targetClass);
				if(obje instanceof Collection) {
					responseObject = (List<Object>) obje;
				}else {
					responseObject.add(obje);
				}
			}else {
				throw new RequestException(String.format("Falha ao buscar dados: %s", respBody));
			}
			
		} catch (StreamReadException e1) {
			e1.printStackTrace();
			throw new RequestException("Falha ao realizar mapeamento de entidade");
		} catch (DatabindException e1) {
			e1.printStackTrace();
			throw new RequestException("Falha ao realizar mapeamento de entidade");
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new RequestException("Falha ao realizar requisição");
		}
		
		return responseObject;
	}
	
	private String concatParams(String url, HashMap<String, String> params) {
		String localUrl = url;
		if(Objects.nonNull(params)) {
			if(!params.isEmpty()) localUrl = localUrl.concat("?");
			
			
			for(Map.Entry<String, String> e : params.entrySet()) {
				localUrl += String.format("%s=%s&", e.getKey(), e.getValue());
			}
		}

		return localUrl;
	}
}
