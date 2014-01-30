package no.lundesgaard.sudokufeud.api.controller;

import static java.util.Arrays.asList;

import java.net.URI;
import java.util.List;

import no.lundesgaard.sudokufeud.api.SudokuFeudApiConfiguration;
import no.lundesgaard.sudokufeud.api.model.JsonGame;
import no.lundesgaard.sudokufeud.api.model.JsonGameInvitation;
import no.lundesgaard.sudokufeud.api.model.JsonMove;
import no.lundesgaard.sudokufeud.api.model.JsonNewGame;
import no.lundesgaard.sudokufeud.api.model.JsonRound;
import no.lundesgaard.sudokufeud.model.Board;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Move;
import no.lundesgaard.sudokufeud.model.Player;
import no.lundesgaard.sudokufeud.service.GameService;
import no.lundesgaard.sudokufeud.service.ProfileService;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(GameController.GAMES_PATH)
public class GameController {
    
    public static final String GAMES_PATH = SudokuFeudApiConfiguration.ROOT_PATH + "/games";
    public static final String GAME_ID = "gameId";
    public static final String GAME_PATH = "{" + GAME_ID + "}";
    public static final String ROUNDS_PATH = GAME_PATH + "/rounds";
    public static final String ROUND_ID = "roundId";
    public static final String ROUND_PATH = "{" + ROUND_ID + "}";

    @Autowired
    private GameService gameService;

    @Autowired
    private ProfileService profileService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<JsonGame>> getGames(@AuthenticationPrincipal String userId) {
        String profileId = profileService.getProfileIdByUserId(userId);
        List<Game> games = gameService.getGames(profileId);

        DateTime lastModified = null;
        for (Game game : games) {
            if (lastModified == null || lastModified.isBefore(game.getLastModified())) {
                lastModified = game.getLastModified();
            }
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (lastModified != null) {
            httpHeaders.setLastModified(lastModified.getMillis());
        }
        
        List<JsonGame> jsonGames = toJsonGames(games, profileId);
        return new ResponseEntity<>(jsonGames, httpHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = GAME_PATH, method = RequestMethod.GET)
    public ResponseEntity<JsonGame> getGame(
            @AuthenticationPrincipal String userId, 
            @PathVariable(GAME_ID) String gameId) {

        String profileId = profileService.getProfileIdByUserId(userId);
        Game game = gameService.getGame(profileId, gameId);
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setLastModified(game.getLastModified().getMillis());
        
        JsonGame jsonGame = toJsonGame(game, profileId);
        return new ResponseEntity<>(jsonGame, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = GAME_PATH, method = RequestMethod.PUT)
    public ResponseEntity<?> acceptDeclineInvitation(
            @AuthenticationPrincipal String userId,
            @PathVariable(GAME_ID) String gameId,
            @RequestBody JsonGameInvitation jsonGameInvitation) {

        String profileId = profileService.getProfileIdByUserId(userId);
        if (jsonGameInvitation.getResponse() == JsonGameInvitation.Response.ACCEPT) {
            Game game = gameService.acceptInvitation(profileId, gameId);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            JsonGame jsonGame = toJsonGame(game, profileId);
            return new ResponseEntity<>(jsonGame, httpHeaders, HttpStatus.OK);
        } else {
            gameService.declineInvitation(profileId, gameId);

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createGame(
            @AuthenticationPrincipal String userId,
            @RequestBody JsonNewGame jsonNewGame, 
            UriComponentsBuilder uriComponentsBuilder) {

        String profileId = profileService.getProfileIdByUserId(userId);
        Board.Difficulty difficulty = Board.Difficulty.valueOf(jsonNewGame.getDifficulty());
        String opponentUserId = jsonNewGame.getOpponent();
        String opponentId = profileService.getProfileIdByUserId(opponentUserId);
        String gameId = gameService.createGame(profileId, opponentId, difficulty);

        URI gameLoction = uriComponentsBuilder
                .path(GAME_PATH)
                .buildAndExpand(gameId)
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(gameLoction);
        
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = ROUNDS_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> executeRound(
            @AuthenticationPrincipal String userId,
            @PathVariable(GAME_ID) String gameId,
            @RequestBody JsonRound jsonRound,
            UriComponentsBuilder uriComponentsBuilder) {

        String profileId = profileService.getProfileIdByUserId(userId);
        Move[] moves = toMoves(jsonRound);
        int roundId = gameService.executeRound(profileId, gameId, moves);

        URI roundLocation = uriComponentsBuilder
                .path(ROUND_PATH)
                .buildAndExpand(roundId)
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(roundLocation);
        
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = ROUNDS_PATH, method = RequestMethod.GET)
    public List<JsonRound> getRounds(
            @AuthenticationPrincipal String userId,
            @PathVariable(GAME_ID) String gameId) {
        // TODO: implement
        return asList();
    }
    
    @RequestMapping(value = ROUNDS_PATH + "/" + ROUND_PATH, method = RequestMethod.GET)
    public JsonRound getRound(
            @AuthenticationPrincipal String userId,
            @PathVariable(GAME_ID) String gameId,
            @PathVariable(ROUND_ID) int roundId) {
        // TODO: implement
        return new JsonRound();
    }

    private List<JsonGame> toJsonGames(List<Game> games, String profileId) {
        JsonGame[] jsonGames = new JsonGame[games.size()];
        for (int i = 0; i < games.size(); i++) {
            jsonGames[i] = toJsonGame(games.get(i), profileId);
        }
        return asList(jsonGames);
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
            Boolean won) {
        
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

    private Move[] toMoves(JsonRound jsonRound) {
        JsonMove[] jsonMoves = jsonRound.getMoves();
        if (jsonMoves == null || jsonMoves.length == 0) {
            return new Move[0];
        }

        Move[] moves = new Move[jsonMoves.length];
        for (int i = 0; i < jsonMoves.length; i++) {
            moves[i] = toMove(jsonMoves[i]);
        }

        return moves;
    }

    private Move toMove(JsonMove jsonMove) {
        return new Move(jsonMove.getX(), jsonMove.getY(), jsonMove.getPiece());
    }
}
