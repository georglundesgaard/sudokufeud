package no.lundesgaard.sudokufeud.api.resource;

//@Path(GamesResource.PATH)
//@Component
public class GamesResource {/*
//    private static final Logger LOGGER = LoggerFactory.getLogger(GamesResource.class);

    public static final String PATH = "games";
    public static final String GAME_ID = "gameId";
    public static final String GAME_PATH = "{" + GAME_ID + "}";
    public static final String ROUNDS_PATH = GAME_PATH + "/" + RoundsResource.PATH;

    @Autowired
    private GameService gameService;

    @Autowired
    private ProfileService profileService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGames(@HeaderParam(PROFILE_ID) String profileId) {
        List<Game> games = gameService.getGames(profileId);

        
        JsonGame jsonGame = toJsonGame(game, profileId);

        Response.ResponseBuilder responseBuilder = Response.ok(toJsonGames(games, profileId));
        if (lastModified != null) {
            return responseBuilder
                    .lastModified(lastModified.toDate())
                    .build();
        }
        return responseBuilder.build();
    }

    @GET
    @Path(GAME_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(
            @HeaderParam(PROFILE_ID) String profileId,
            @PathParam(GAME_ID) String gameId)
    {
        Game game = gameService.getGame(profileId, gameId);

        return Response
                .ok(toJsonGame(game, profileId))
                .lastModified(game.getLastModified().toDate())
                .build();
    }

    @PUT
    @Path(GAME_PATH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response acceptDeclineInvitation(
            @HeaderParam(PROFILE_ID) String profileId,
            @PathParam(GAME_ID) String gameId,
            JsonGameInvitation jsonGameInvitation)
    {
        if (jsonGameInvitation.getResponse() == JsonGameInvitation.Response.ACCEPT) {
            Game game = gameService.acceptInvitation(profileId, gameId);

            return Response
                    .ok(toJsonGame(game, profileId))
                    .lastModified(game.getLastModified().toDate())
                    .build();
        } else {
            gameService.declineInvitation(profileId, gameId);

            return Response
                    .ok()
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGame(
            @HeaderParam(PROFILE_ID) String profileId,
            @Context UriInfo uriInfo,
            JsonNewGame jsonNewGame)
    {
        Board.Difficulty difficulty = Board.Difficulty.valueOf(jsonNewGame.getDifficulty());
        String opponentUserId = jsonNewGame.getOpponent();

        String opponentId = profileService.getProfileIdByUserId(opponentUserId);
        String gameId = gameService.createGame(profileId, opponentId, difficulty);

        URI location = getLocation(uriInfo, gameId);
        return Response
                .created(location)
                .build();

    }

    @Path(ROUNDS_PATH)
    public RoundsResource getRoundsResource(
            @HeaderParam(PROFILE_ID) String profileId,
            @PathParam(GAME_ID) String gameId)
    {
        return new RoundsResource(gameService, profileId, gameId);
    }

    private JsonGame[] toJsonGames(List<Game> games, String profileId) {
        JsonGame[] jsonGames = new JsonGame[games.size()];
        for (int i = 0; i < games.size(); i++) {
            jsonGames[i] = toJsonGame(games.get(i), profileId);
        }
        return jsonGames;
    }

    private JsonGame toJsonGame(Game game, String profileId) {
        int score;
        int opponentScore;
        int[] availablePieces;
        String opponentProfileId;

        if (game.getPlayer1().getPlayerId().equals(profileId)) {
            score = game.getPlayer1().getScore();
            opponentScore = game.getPlayer2().getScore();
            availablePieces = game.getPlayer1().getAvailablePieces();
            opponentProfileId = game.getPlayer2().getPlayerId();
        } else {
            score = game.getPlayer2().getScore();
            opponentScore = game.getPlayer1().getScore();
            availablePieces = game.getPlayer2().getAvailablePieces();
            opponentProfileId = game.getPlayer1().getPlayerId();
        }

        String opponentUserId = profileService.getProfile(opponentProfileId).getUserId();

        String currentPlayerUserId = null;
        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer != null) {
            String currentPlayerId = currentPlayer.getPlayerId();
            currentPlayerUserId = profileService.getProfile(currentPlayerId).getUserId();
        }

        Boolean won;
        if (game.getWinner() != null && game.getWinner().getPlayerId().equals(profileId)) {
            won = true;
        } else if (game.getLoser() != null && game.getLoser().getPlayerId().equals(profileId)) {
            won = false;
        } else {
            won = null;
        }

        JsonGame jsonGame = new JsonGame();
        jsonGame.setId(game.getId());
        jsonGame.setScore(score);
        jsonGame.setAvailablePieces(availablePieces);
        jsonGame.setOpponentUserId(opponentUserId);
        jsonGame.setOpponentScore(opponentScore);
        jsonGame.setState(toJsonGameState(game));
        jsonGame.setStatus(toJsonGameStatus(
                game.getState(),
                profileId.equals(game.getPlayer1().getPlayerId()),
                currentPlayer != null && profileId.equals(currentPlayer.getPlayerId()),
                won
        ));
        jsonGame.setCurrentPlayer(currentPlayerUserId);
        if (game.getState() != Game.State.NEW) {
            jsonGame.setBoard(game.getBoard().toIntegerArray());
        }
        jsonGame.setDifficulty(game.getBoard().getDifficulty().toString());
        jsonGame.setCreated(game.getCreated());

        return jsonGame;
    }

    private JsonGame.Status toJsonGameStatus(
            Game.State gameState,
            boolean creator,
            boolean currentPlayer,
            Boolean won)
    {
        switch (gameState) {
            case NEW:
                if (creator) {
                    return JsonGame.Status.WAITING;
                }
                return JsonGame.Status.INVITATION;
            case RUNNING:
                if (currentPlayer) {
                    return JsonGame.Status.READY;
                }
                return JsonGame.Status.WAITING;
            default:
                if (won == null) {
                    return JsonGame.Status.TIED;
                }
                if (won) {
                    return JsonGame.Status.WON;
                }
                return JsonGame.Status.LOST;
        }
    }

    private JsonGame.State toJsonGameState(Game game) {
        switch (game.getState()) {
            case NEW:
                return JsonGame.State.NEW;
            case RUNNING:
                return JsonGame.State.RUNNING;
            default:
                return JsonGame.State.COMPLETED;
        }
    }

    private URI getLocation(UriInfo uriInfo, String gameId) {
        try {
            return new URI(uriInfo.getAbsolutePath() + "/" + gameId);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to create location uri", e);
        }
    }


*/}
