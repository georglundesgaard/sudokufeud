package no.lundesgaard.sudokufeud.api.client;

import javax.ws.rs.core.MediaType;

import no.lundesgaard.sudokufeud.api.model.JsonGame;
import no.lundesgaard.sudokufeud.api.model.JsonGameInvitation;
import no.lundesgaard.sudokufeud.api.model.JsonMove;
import no.lundesgaard.sudokufeud.api.model.JsonNewGame;
import no.lundesgaard.sudokufeud.api.model.JsonProfile;
import no.lundesgaard.sudokufeud.api.model.JsonRound;
import no.lundesgaard.sudokufeud.api.model.JsonUpdatedProfile;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;

public class SudokuFeudClient {
    private static final String GAMES = "games";
    private static final String PROFILE = "profile";
    private static final String ROUNDS = "rounds";
    
    private final Client client;
	private final String root;

	public SudokuFeudClient(String root, String userId, String password, boolean logging) {
		ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        Client client = Client.create(config);
        if (logging) {
            client.addFilter(new LoggingFilter(System.out));
        }
        client.addFilter(new HTTPBasicAuthFilter(userId, password));
        
        this.client = client;
		this.root = root;
	}

 public JsonProfile updateProfile(String name) {
        JsonUpdatedProfile jsonUpdatedProfile = new JsonUpdatedProfile();
        jsonUpdatedProfile.setName(name);
        
        return client
.resource(root).path(PROFILE)         .entity(jsonUpdatedProfile, MediaType.APPLICATION_JSON_TYPE)
                .put(JsonProfile.class);
    }

    public JsonProfile getProfile() {
        return client
.resource(root).path(PROFILE).get(JsonProfile.class);
	}

	public void createGame(String opponent) {
        JsonNewGame jsonNewGame = new JsonNewGame();
        jsonNewGame.setOpponent(opponent);
        jsonNewGame.setDifficulty("EASY");
        
        client
                .resource(root)
.path(GAMES).entity(jsonNewGame, MediaType.APPLICATION_JSON_TYPE)
.post();
	}

	public JsonGame[] getGames() {
        return client
                .resource(root)
                .path(GAMES)
.get(JsonGame[].class);
	}

	public JsonGame acceptInvitation(String gameId) {
		JsonGameInvitation jsonGameInvitation = new JsonGameInvitation();
		jsonGameInvitation.setResponse(JsonGameInvitation.Response.ACCEPT);

		return client.resource(root).path(GAMES).path(gameId).entity(jsonGameInvitation, MediaType.APPLICATION_JSON_TYPE).put(JsonGame.class);
	}

	public void declineInvitation(String gameId) {
		JsonGameInvitation jsonGameInvitation = new JsonGameInvitation();
		jsonGameInvitation.setResponse(JsonGameInvitation.Response.DECLINE);

		client.resource(root).path(GAMES).path(gameId).entity(jsonGameInvitation, MediaType.APPLICATION_JSON_TYPE).put();
	}

	public void executeRound(String gameId, JsonMove... jsonMoves) {
		JsonRound jsonRound = new JsonRound();
		jsonRound.setMoves(jsonMoves);

		client.resource(root).path(GAMES).path(gameId).path(ROUNDS).entity(jsonRound, MediaType.APPLICATION_JSON_TYPE).post();
	}
}
