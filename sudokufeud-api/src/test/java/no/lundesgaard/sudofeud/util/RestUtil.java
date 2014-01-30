package no.lundesgaard.sudofeud.util;

import javax.ws.rs.core.MediaType;

import no.lundesgaard.sudokufeud.api.client.SudokuFeudClient;
import no.lundesgaard.sudokufeud.api.model.JsonGame;
import no.lundesgaard.sudokufeud.api.model.JsonGameInvitation;
import no.lundesgaard.sudokufeud.api.model.JsonMove;
import no.lundesgaard.sudokufeud.api.model.JsonNewGame;
import no.lundesgaard.sudokufeud.api.model.JsonProfile;
import no.lundesgaard.sudokufeud.api.model.JsonRound;
import no.lundesgaard.sudokufeud.api.model.JsonUpdatedProfile;
import no.lundesgaard.sudokufeud.api.provider.ObjectMapperProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;

public class RestUtil {
    private static final String ROOT = "http://localhost:8080/api";
    private static final String GAMES = "games";
    private static final String PROFILE = "profile";
    private static final String ROUNDS = "rounds";
    private static final boolean LOGGING = false;

    public static void main(String[] args) {
//        ClientConfig config = new DefaultClientConfig(ObjectMapperProvider.class);
//        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
//        Client client1 = Client.create(config);
//        client1.addFilter(new LoggingFilter(System.out));
//
//        WebResource rootResource = client1.resource(ROOT);

        SudokuFeudClient client1 = new SudokuFeudClient("georg", "georg", LOGGING);
        SudokuFeudClient client2 = new SudokuFeudClient("ida", "ida", LOGGING);
        
        try {
//            print(client1.getProfile());
//            print(client2.getProfile());
            
//            print(client1.updateProfile("Georg Lundesgaard"));
//            client1.createGame("ida");
            print(client1.getGames());
            print(client2.getGames());
            
//            print(client2.updateProfile("Ida Sirnes"));

//            createProfile(rootResource, "georg", "Georg Lundesgaard");
//        createProfile(rootResource, "ida", "Ida Sirnes");
            
//            printProfile(rootResource, "georg");

//        createGame(rootResource, "georg", "ida");
//        createGame(rootResource, "ida", "georg");

//            printGames(rootResource, "georg");
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

    private static void print(Object json) {
        System.out.println(json);
    }

    private static void print(Object[] json) {
        System.out.println("[");
		for (Object o : json) {
            System.out.println("  " + o);	
		}
        System.out.println("]");
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
                .get(JsonGame[].class);
    }

    private static void createGame(WebResource rootResource, String userId, String opponent) {
        JsonNewGame jsonNewGame = new JsonNewGame();
        jsonNewGame.setOpponent(opponent);
        jsonNewGame.setDifficulty("EASY");
        rootResource
                .path(GAMES)
                .entity(jsonNewGame, MediaType.APPLICATION_JSON_TYPE)
                .post();
    }

    private static void createProfile(WebResource rootResource, String userId, String name) {
        JsonUpdatedProfile jsonUpdatedProfile = new JsonUpdatedProfile();
        jsonUpdatedProfile.setName(name);
        rootResource
                .path(PROFILE)
                .entity(jsonUpdatedProfile, MediaType.APPLICATION_JSON_TYPE)
                .put();
    }

    private static void printProfile(WebResource rootResource, String userId) {
        JsonProfile jsonProfile = rootResource
                .path(PROFILE)
                .get(JsonProfile.class);
        print(jsonProfile);
    }
}
