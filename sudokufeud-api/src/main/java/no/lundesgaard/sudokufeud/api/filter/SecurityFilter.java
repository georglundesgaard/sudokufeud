package no.lundesgaard.sudokufeud.api.filter;

public class SecurityFilter /*implements ContainerRequestFilter*/ {/*
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);

    @InjectParam
    private ProfileService profileService;

    @Override
    public ContainerRequest filter(ContainerRequest request) {
        List<String> headers = request.getRequestHeader("User-Id");
        String userId;
        if (headers != null) {
            userId = headers.get(0);
        } else {
            userId = null;
        }
        LOGGER.debug("User id: {}", userId);

        try {
            String profileId = profileService.getProfileIdByUserId(userId);
            request.getRequestHeaders().putSingle(ProfileResource.PROFILE_ID, profileId);
        } catch (UnknownUserIdException e) {
            if (!"profile".equals(request.getPath()) || !"PUT".equals(request.getMethod())) {
                throw e;
            }
        }

        return request;
    }
*/}
