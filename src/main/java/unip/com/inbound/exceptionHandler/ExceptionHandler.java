package unip.com.inbound.exceptionHandler;

import com.google.gson.JsonObject;
import io.netty.handler.codec.http.HttpResponseStatus;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        JsonObject responseBody = new JsonObject();
        JsonObject element = new JsonObject();
        responseBody.add("Requisição_inválida", element);
        element.addProperty("mensagem", e.getMessage());
        Response response = Response.status(HttpResponseStatus.BAD_REQUEST.code()).type(MediaType.APPLICATION_JSON)
                .entity(responseBody.toString())
                .build();
        return response;
    }
}
