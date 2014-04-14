package no.lundesgaard.sudokufeud.api.client;

import static java.lang.String.valueOf;

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

	private Client client;
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

	private void validateState() {
		if (client == null) {
			throw new IllegalStateException("client is closed");
		}
	}

	public JsonProfile updateProfile(String name) {
		validateState();

		JsonUpdatedProfile jsonUpdatedProfile = new JsonUpdatedProfile();
		jsonUpdatedProfile.setName(name);

		return client.resource(root).path(PROFILE).entity(jsonUpdatedProfile, MediaType.APPLICATION_JSON_TYPE).put(JsonProfile.class);
	}

	public JsonProfile getProfile() {
		validateState();

		return client.resource(root).path(PROFILE).get(JsonProfile.class);
	}

	public void createGame(String opponent) {
		validateState();

		JsonNewGame jsonNewGame = new JsonNewGame();
		jsonNewGame.setOpponent(opponent);
		jsonNewGame.setDifficulty("EASY");

		client.resource(root).path(GAMES).entity(jsonNewGame, MediaType.APPLICATION_JSON_TYPE).post();
	}

	public JsonGame[] getGames() {
		validateState();

		return client.resource(root).path(GAMES).get(JsonGame[].class);
	}

	public JsonGame acceptInvitation(long gameId) {
		validateState();

		JsonGameInvitation jsonGameInvitation = new JsonGameInvitation();
		jsonGameInvitation.setResponse(JsonGameInvitation.Response.ACCEPT);

		return client.resource(root).path(GAMES).path(valueOf(gameId)).entity(jsonGameInvitation, MediaType.APPLICATION_JSON_TYPE).put(JsonGame.class);
	}

	public void declineInvitation(String gameId) {
		validateState();

		JsonGameInvitation jsonGameInvitation = new JsonGameInvitation();
		jsonGameInvitation.setResponse(JsonGameInvitation.Response.DECLINE);

		client.resource(root).path(GAMES).path(gameId).entity(jsonGameInvitation, MediaType.APPLICATION_JSON_TYPE).put();
	}

	public void executeRound(String gameId, JsonMove... jsonMoves) {
		validateState();

		JsonRound jsonRound = new JsonRound();
		jsonRound.setMoves(jsonMoves);

		client.resource(root).path(GAMES).path(gameId).path(ROUNDS).entity(jsonRound, MediaType.APPLICATION_JSON_TYPE).post();
	}

	public void destroy() {
		if (client != null) {
			client.destroy();
			client = null;
		}
	}
}
