package no.lundesgaard.sudofeud.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import no.lundesgaard.sudokufeud.api.model.*;
import no.lundesgaard.sudokufeud.api.provider.ObjectMapperProvider;

import javax.ws.rs.core.MediaType;

public class RestUtil {
    private static final String ROOT = "http://localhost:8080/api";
    private static final String GAMES = "games";
    private static final String PROFILE = "profile";
    private static final String ROUNDS = "rounds";
    public static final String HEADER_USER_ID = "User-Id";

    public static void main(String[] args) {
        ClientConfig config = new DefaultClientConfig(ObjectMapperProvider.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        Client client = Client.create(config);
        client.addFilter(new LoggingFilter(System.out));

        WebResource rootResource = client.resource(ROOT);

        try {
//        createProfile(rootResource, "georg", "Georg Lundesgaard");
//        createProfile(rootResource, "ida", "Ida Sirnes");

//        createGame(rootResource, "georg", "ida");
//        createGame(rootResource, "ida", "georg");

            printGames(rootResource, "georg");
//        printGames(rootResource, "ida");

//        JsonGame[] games = getGames(rootResource, "ida");
//        String gameId = games[0].getId();
//        acceptInvitation(rootResource, "ida", gameId);

//        JsonGame[] games = getGames(rootResource, "georg");
//        String gameId = games[0].getId();
//        sampleGame(rootResource, gameId, "georg", "ida");

//        JsonGame[] games = getGames(rootResource, "georg");
//        String gameId = games[0].getId();
//        acceptInvitation(rootResource, "georg", gameId);

//        JsonGame[] games = getGames(rootResource, "ida");
//        String gameId = games[1].getId();
//        sampleGame(rootResource, gameId, "ida", "georg");

//            JsonGame[] games = getGames(rootResource, "georg");
//            executeRound(
//                    rootResource,
//                    "georg",
//                    games[0].getId(),
//                    jsonMove(6, 5, 2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void sampleGame(WebResource rootResource, String gameId, String playerUserId1, String playerUserId2) {
        // round 1
        executeRound(
                rootResource,
                playerUserId1,
                gameId,
                jsonMove(6, 5, 2),
                jsonMove(0, 1, 3),
                jsonMove(4, 1, 8));

        executeRound(
                rootResource,
                playerUserId2,
                gameId,
                jsonMove(3, 3, 5),
                jsonMove(5, 5, 6),
                jsonMove(3, 2, 7),
                jsonMove(3, 0, 2),
                jsonMove(3, 1, 1));

        // round 2
        executeRound(
                rootResource,
                playerUserId1,
                gameId,
                jsonMove(6, 0, 1),
                jsonMove(8, 7, 3),
                jsonMove(6, 1, 4),
                jsonMove(7, 7, 4),
                jsonMove(8, 3, 4),
                jsonMove(5, 7, 5));

        executeRound(
                rootResource,
                playerUserId2,
                gameId,
                jsonMove(8, 2, 6),
                jsonMove(2, 2, 9),
                jsonMove(0, 3, 9),
                jsonMove(6, 3, 8),
                jsonMove(1, 5, 8),
                jsonMove(4, 5, 1),
                jsonMove(4, 3, 7));

        // round 3
        executeRound(
                rootResource,
                playerUserId1,
                gameId,
                jsonMove(1, 2, 2),
                jsonMove(3, 4, 4),
                jsonMove(7, 3, 6),
                jsonMove(7, 4, 1),
                jsonMove(5, 8, 7),
                jsonMove(0, 0, 8));

        executeRound(
                rootResource,
                playerUserId2,
                gameId,
                jsonMove(1, 4, 7),
                jsonMove(4, 6, 4),
                jsonMove(1, 7, 6),
                jsonMove(1, 3, 1),
                jsonMove(2, 3, 3),
                jsonMove(0, 7, 2),
                jsonMove(3, 6, 3));

        // round 4
        executeRound(
                rootResource,
                playerUserId1,
                gameId,
                jsonMove(1, 0, 4),
                jsonMove(1, 1, 5),
                jsonMove(2, 7, 1),
                jsonMove(0, 6, 7),
                jsonMove(2, 8, 8));

        executeRound(
                rootResource,
                playerUserId2,
                gameId,
                jsonMove(6, 7, 7),
                jsonMove(3, 7, 8),
                jsonMove(6, 8, 9));

        // round 5
        executeRound(
                rootResource,
                playerUserId1,
                gameId,
                jsonMove(6, 6, 6),
                jsonMove(3, 8, 6));
    }

    private static JsonMove jsonMove(int x, int y, int piece) {
        return new JsonMove(x, y, piece);
    }

    private static void executeRound(WebResource rootResource, String userId, String gameId, JsonMove... jsonMoves) {
        JsonRound jsonRound = new JsonRound();
        jsonRound.setMoves(jsonMoves);

        rootResource
                .path(GAMES)
                .path(gameId)
                .path(ROUNDS)
                .header(HEADER_USER_ID, userId)
                .entity(jsonRound, MediaType.APPLICATION_JSON_TYPE)
                .post();
    }

    private static void acceptInvitation(WebResource rootResource, String userId, String gameId) {
        updateInvitation(rootResource, userId, gameId, JsonGameInvitation.Response.ACCEPT);
    }

    private static void declineInvitation(WebResource rootResource, String userId, String gameId) {
        updateInvitation(rootResource, userId, gameId, JsonGameInvitation.Response.DECLINE);
    }

    private static void updateInvitation(
            WebResource rootResource,
            String userId,
            String gameId,
            JsonGameInvitation.Response response) {

        JsonGameInvitation jsonGameInvitation = new JsonGameInvitation();
        jsonGameInvitation.setResponse(response);
        rootResource
                .path(GAMES)
                .path(gameId)
                .header(HEADER_USER_ID, userId)
                .entity(jsonGameInvitation, MediaType.APPLICATION_JSON)
                .put();
    }

    private static void printGames(WebResource rootResource, String userId) {
        JsonGame[] games = getGames(rootResource, userId);
        for (JsonGame game : games) {
            System.out.println(game);
        }
    }

    private static JsonGame[] getGames(WebResource rootResource, String userId) {
        return rootResource
                .path(GAMES)
                .header(HEADER_USER_ID, userId)
                .get(JsonGame[].class);
    }

    private static void createGame(WebResource rootResource, String userId, String opponent) {
        JsonNewGame jsonNewGame = new JsonNewGame();
        jsonNewGame.setOpponent(opponent);
        jsonNewGame.setDifficulty("EASY");
        rootResource
                .path(GAMES)
                .header(HEADER_USER_ID, userId)
                .entity(jsonNewGame, MediaType.APPLICATION_JSON_TYPE)
                .post();
    }

    private static void createProfile(WebResource rootResource, String userId, String name) {
        JsonProfile jsonProfile = new JsonProfile();
        jsonProfile.setUserId(userId);
        jsonProfile.setName(name);
        rootResource
                .path(PROFILE)
                .header(HEADER_USER_ID, userId)
                .entity(jsonProfile, MediaType.APPLICATION_JSON_TYPE)
                .put();
    }
}
