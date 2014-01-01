package no.lundesgaard.sudokufeud.api.filter;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import no.lundesgaard.sudokufeud.api.resource.ProfileResource;
import no.lundesgaard.sudokufeud.repository.exception.UnknownUserIdException;
import no.lundesgaard.sudokufeud.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SecurityFilter implements ContainerRequestFilter {
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
}
