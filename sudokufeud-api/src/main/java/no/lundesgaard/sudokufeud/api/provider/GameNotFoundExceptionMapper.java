package no.lundesgaard.sudokufeud.api.provider;

import no.lundesgaard.sudokufeud.api.model.JsonError;
import no.lundesgaard.sudokufeud.repository.exception.GameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GameNotFoundExceptionMapper implements ExceptionMapper<GameNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(GameNotFoundException e) {
        LOGGER.debug("{}", e.getMessage());

        String description = e.getMessage();
        JsonError jsonError = new JsonError(Response.Status.NOT_FOUND, description);
        return Response
                .status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(jsonError)
                .build();
    }
}
