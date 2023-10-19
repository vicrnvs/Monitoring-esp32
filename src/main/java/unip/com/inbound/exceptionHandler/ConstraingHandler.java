package unip.com.inbound.exceptionHandler;

import com.google.gson.JsonObject;
import io.netty.handler.codec.http.HttpResponseStatus;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraingHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        JsonObject responseJson = new JsonObject();
        JsonObject body = new JsonObject();
        String[] cause = e.getMessage().split(":");
        body.addProperty("campo", cause[0]);
        body.addProperty("mensagem", cause[1]);
        responseJson.add("Erro de validação de dados de entrada", body);

        Response response = Response.status(HttpResponseStatus.BAD_REQUEST.code()).type(MediaType.APPLICATION_JSON)
                .entity(responseJson.toString())
                .build();
        return response;
    }
}
