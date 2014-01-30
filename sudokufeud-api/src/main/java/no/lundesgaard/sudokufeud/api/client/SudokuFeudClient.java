package no.lundesgaard.sudokufeud.api.client;

import javax.ws.rs.core.MediaType;

import no.lundesgaard.sudokufeud.api.model.JsonGame;
import no.lundesgaard.sudokufeud.api.model.JsonNewGame;
import no.lundesgaard.sudokufeud.api.model.JsonProfile;
import no.lundesgaard.sudokufeud.api.model.JsonUpdatedProfile;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;

public class SudokuFeudClient {
    private static final String ROOT = "http://localhost:8080/api";
    private static final String GAMES = "games";
    private static final String PROFILE = "profile";
    private static final String ROUNDS = "rounds";
    
    private final Client client;

    public SudokuFeudClient(String userId, String password, boolean logging) {
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        Client client = Client.create(config);
        if (logging) {
            client.addFilter(new LoggingFilter(System.out));
        }
        client.addFilter(new HTTPBasicAuthFilter(userId, password));
        
        this.client = client;
    }
    
    public JsonProfile updateProfile(String name) {
        JsonUpdatedProfile jsonUpdatedProfile = new JsonUpdatedProfile();
        jsonUpdatedProfile.setName(name);
        
        return client
                .resource(ROOT)
                .path(PROFILE)
                .entity(jsonUpdatedProfile, MediaType.APPLICATION_JSON_TYPE)
                .put(JsonProfile.class);
    }

    public JsonProfile getProfile() {
        return client
                .resource(ROOT)
                .path(PROFILE)
                .get(JsonProfile.class);
    }

    public void createGame(String opponent) {
        JsonNewGame jsonNewGame = new JsonNewGame();
        jsonNewGame.setOpponent(opponent);
        jsonNewGame.setDifficulty("EASY");
        
        client
                .resource(ROOT)
                .path(GAMES)
                .entity(jsonNewGame, MediaType.APPLICATION_JSON_TYPE)
                .post();
    }

    public JsonGame[] getGames() {
        return client
                .resource(ROOT)
                .path(GAMES)
                .get(JsonGame[].class);
    }
}
