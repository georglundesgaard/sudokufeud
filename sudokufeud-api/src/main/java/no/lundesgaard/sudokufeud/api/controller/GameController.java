package no.lundesgaard.sudokufeud.api.controller;

import static java.util.Arrays.asList;
import static no.lundesgaard.sudokufeud.api.controller.ProfileController.PROFILE_ID;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(GameController.GAMES_PATH)
public class GameController {
    
    public static final String GAMES_PATH = SudokuFeudApiConfiguration.ROOT_PATH + "/games";
    public static final String GAME_ID = "gameId";
    public static final String GAME_PATH = "{" + GAME_ID + "}";
    public static final String ROUNDS_PATH = GAME_PATH + "/rounds";
    
    @Autowired
    private GameService gameService;

    @Autowired
    private ProfileService profileService;
    
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<JsonGame> getGames(@RequestHeader(PROFILE_ID) String profileId) {
        List<Game> games = gameService.getGames(profileId);
        return toJsonGames(games, profileId);
    }
    
    @RequestMapping(value = GAME_PATH, method = RequestMethod.GET)
    public @ResponseBody JsonGame getGame(@RequestHeader(ProfileController.PROFILE_ID) String profileId, @PathVariable(GAME_ID) String gameId) {
        Game game = gameService.getGame(profileId, gameId);
        return toJsonGame(game, profileId);
    }

    @RequestMapping(value = GAME_PATH, method = RequestMethod.PUT)
    public JsonGame acceptDeclineInvitation(
            @RequestHeader(PROFILE_ID) String profileId,
            @PathVariable(GAME_ID) String gameId,
            @RequestBody JsonGameInvitation jsonGameInvitation)
    {
        if (jsonGameInvitation.getResponse() == JsonGameInvitation.Response.ACCEPT) {
            Game game = gameService.acceptInvitation(profileId, gameId);

            return toJsonGame(game, profileId);
        } else {
            gameService.declineInvitation(profileId, gameId);

            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createGame(
            @RequestHeader(PROFILE_ID) String profileId,
            @RequestBody JsonNewGame jsonNewGame)
    {
        Board.Difficulty difficulty = Board.Difficulty.valueOf(jsonNewGame.getDifficulty());
        String opponentUserId = jsonNewGame.getOpponent();

        String opponentId = profileService.getProfileIdByUserId(opponentUserId);
        gameService.createGame(profileId, opponentId, difficulty);
    }

    @RequestMapping(value = ROUNDS_PATH, method = RequestMethod.POST)
    public void executeRound(
            @RequestHeader(PROFILE_ID) String profileId,
            @PathVariable(GAME_ID) String gameId,
            @RequestBody JsonRound jsonRound) {
        
        Move[] moves = toMoves(jsonRound);
        gameService.executeRound(profileId, gameId, moves);
    }

    @RequestMapping(value = ROUNDS_PATH, method = RequestMethod.GET)
    public List<JsonRound> getRounds() {
        return asList();
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
