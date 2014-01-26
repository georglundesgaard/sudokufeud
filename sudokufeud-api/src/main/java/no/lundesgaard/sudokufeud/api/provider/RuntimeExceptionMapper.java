package no.lundesgaard.sudokufeud.api.provider;

//@Provider
public class RuntimeExceptionMapper /*implements ExceptionMapper<RuntimeException>*/ {/*
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
*/}
