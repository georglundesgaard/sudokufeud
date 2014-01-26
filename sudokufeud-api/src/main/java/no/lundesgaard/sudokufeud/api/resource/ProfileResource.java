package no.lundesgaard.sudokufeud.api.resource;

//@Path(ProfileResource.PATH)
//@Component
public class ProfileResource {/*
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileResource.class);

    public static final String PATH = "profile";

    public static final String PROFILE_ID = "Profile-Id";

    @Autowired
    private ProfileService profileService;

    public ProfileResource() {
        LOGGER.debug("ProfileResource: init");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfile(@HeaderParam(PROFILE_ID) String profileId) {
        Profile profile = profileService.getProfile(profileId);
        return Response
                .ok(toJsonProfile(profile))
                .lastModified(profile.getLastModified().toDate())
                .build();
    }

    private JsonProfile toJsonProfile(Profile profile) {
        JsonProfile jsonProfile = new JsonProfile();
        jsonProfile.setUserId(profile.getUserId());
        jsonProfile.setName(profile.getName());
        return jsonProfile;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(@HeaderParam(PROFILE_ID) String profileId, JsonProfile jsonProfile) {
        Profile profile;
        if (profileId == null) {
            Profile newProfile = toProfile(jsonProfile);
            String newProfileId = profileService.createProfile(newProfile);
            profile = profileService.getProfile(newProfileId);
        } else {
            profile = profileService.getProfile(profileId);
            profile = toUpdatedProfile(jsonProfile, profile);
            profileService.updateProfile(profileId, profile);
        }

        return Response
                .ok(toJsonProfile(profile))
                .lastModified(profile.getLastModified().toDate())
                .build();
    }

    private Profile toUpdatedProfile(JsonProfile jsonProfile, Profile profile) {
        return new Profile(
                profile.getId(),
                jsonProfile.getUserId(),
                jsonProfile.getName(),
                profile.getCreated(),
                DateTime.now());
    }

    private Profile toProfile(JsonProfile jsonProfile) {
        return new Profile(
                UUID.randomUUID().toString(),
                jsonProfile.getUserId(),
                jsonProfile.getName(),
                DateTime.now(),
                null);
    }
*/}
