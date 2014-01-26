package no.lundesgaard.sudokufeud.api.provider;

//@Provider
public class WebApplicationExceptionMapper /*implements ExceptionMapper<WebApplicationException>*/ {/*
    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationExceptionMapper.class);

    @Override
    public Response toResponse(WebApplicationException e) {
        Response response = e.getResponse();
        Response.Status status = Response.Status.fromStatusCode(response.getStatus());
        if (status == null) {
            return response;
        }

        JsonError jsonError = new JsonError(status, null);
        return Response
                .fromResponse(response)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(jsonError)
                .build();
    }
*/}
