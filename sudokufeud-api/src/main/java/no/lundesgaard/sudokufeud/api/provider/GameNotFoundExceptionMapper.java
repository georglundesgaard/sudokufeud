package no.lundesgaard.sudokufeud.api.provider;

//@Provider
public class GameNotFoundExceptionMapper /*implements ExceptionMapper<GameNotFoundException>*/ {/*
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
*/}
