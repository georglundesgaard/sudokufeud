package no.lundesgaard.sudokufeud.api.provider;

import no.lundesgaard.sudokufeud.api.model.JsonError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Formatter;
import java.util.UUID;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

    @Override
    public Response toResponse(RuntimeException e) {
        String logId = UUID.randomUUID().toString();
        LOGGER.error("Unexpected error (logId: {}): {}", logId, e.getMessage(), e);

        String description = new Formatter().format("Unexpected error (logId: %s)", logId).toString();
        JsonError jsonError = new JsonError(Response.Status.INTERNAL_SERVER_ERROR, description);
        return Response
                .serverError()
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(jsonError)
                .build();
    }
}
