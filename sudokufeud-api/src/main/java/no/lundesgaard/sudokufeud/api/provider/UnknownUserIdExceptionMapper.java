package no.lundesgaard.sudokufeud.api.provider;

//@Provider
public class UnknownUserIdExceptionMapper /*implements ExceptionMapper<UnknownUserIdException>*/ {/*
    private static final Logger LOGGER = LoggerFactory.getLogger(UnknownUserIdExceptionMapper.class);

    @Override
    public Response toResponse(UnknownUserIdException e) {
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
