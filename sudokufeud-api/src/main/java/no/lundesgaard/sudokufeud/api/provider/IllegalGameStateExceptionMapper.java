package no.lundesgaard.sudokufeud.api.provider;

//@Provider
public class IllegalGameStateExceptionMapper /*implements ExceptionMapper<IllegalGameStateException>*/ {/*
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
*/}
