package no.lundesgaard.sudokufeud.api.provider;

import no.lundesgaard.sudokufeud.api.model.JsonError;
import no.lundesgaard.sudokufeud.repository.exception.GameNotFoundException;
import no.lundesgaard.sudokufeud.service.IllegalGameStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalGameStateExceptionMapper implements ExceptionMapper<IllegalGameStateException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IllegalGameStateExceptionMapper.class);

    @Override
    public Response toResponse(IllegalGameStateException e) {
        LOGGER.debug("{}", e.getMessage());

        String description = e.getMessage();
        JsonError jsonError = new JsonError(Response.Status.BAD_REQUEST, description);
        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(jsonError)
                .build();
    }
}
