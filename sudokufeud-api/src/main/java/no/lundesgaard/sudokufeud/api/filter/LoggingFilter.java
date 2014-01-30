package no.lundesgaard.sudokufeud.api.filter;

public class LoggingFilter /*implements ContainerRequestFilter, ContainerResponseFilter*/ {/*
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    private static final String STOP_WATCH = "stopWatch";

    @InjectParam
    private ProfileService profileService;

    @Override
    public ContainerRequest filter(ContainerRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        request.getProperties().put(STOP_WATCH, stopWatch);
        return request;
    }

    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        StopWatch stopWatch = (StopWatch) request.getProperties().get(STOP_WATCH);
        stopWatch.stop();
        LOGGER.debug(
                "Request: {}. Response: {}. Duration: {} ms.",
                toString(request),
                toString(response),
                stopWatch.getTotalTimeMillis());
        return response;
    }

    private Object toString(ContainerResponse response) {
        int status = response.getStatus();
        String reason = response.getStatusType().getReasonPhrase();
        return new Formatter().format("[status: %d; reason: %s]", status, reason).toString();
    }

    private Object toString(ContainerRequest request) {
        String method = request.getMethod();
        String path = request.getPath();
        List<String> headers = request.getRequestHeader(ProfileResource.PROFILE_ID);
        String user = null;
        if (headers != null) {
            String profileId = headers.get(0);
            user = profileService.getProfile(profileId).getUserId();
        }
        return new Formatter().format("[method: %s; path: %s; user: %s]", method, path, user).toString();
    }
*/}
