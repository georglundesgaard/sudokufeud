package no.lundesgaard.sudokufeud.api.controller;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import no.lundesgaard.sudokufeud.SudokuFeudConfiguration;
import no.lundesgaard.sudokufeud.api.mapper.JsonGameMapper;
import no.lundesgaard.sudokufeud.api.model.JsonError;
import no.lundesgaard.sudokufeud.api.model.JsonGame;
import no.lundesgaard.sudokufeud.api.model.JsonGameInvitation;
import no.lundesgaard.sudokufeud.api.model.JsonMove;
import no.lundesgaard.sudokufeud.api.model.JsonNewGame;
import no.lundesgaard.sudokufeud.api.model.JsonRound;
import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.model.BoardException;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Move;
import no.lundesgaard.sudokufeud.repository.exception.GameNotFoundException;
import no.lundesgaard.sudokufeud.service.GameService;
import no.lundesgaard.sudokufeud.service.IllegalGameStateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(GameController.PATH)
public class GameController {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
	
	public static final String GAMES_PATH = "games";
	public static final String PATH = SudokuFeudConfiguration.ROOT_PATH + "/" + GAMES_PATH;
	public static final String GAME_ID = "gameId";
	public static final String GAME_PATH = "{" + GAME_ID + "}";
	public static final String ROUNDS_PATH = GAME_PATH + "/rounds";
	public static final String ROUND_ID = "roundId";
	public static final String ROUND_PATH = "{" + ROUND_ID + "}";
	
	@Autowired
	private GameService gameService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<JsonGame>> getGames(@AuthenticationPrincipal String userId) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		List<Game> games = gameService.getGames(userId);
		if (games.size() == 0) {
			return new ResponseEntity<>(Collections.emptyList(), httpHeaders, HttpStatus.OK);
		}

		Optional<Date> optionalLastModified = games
				.stream()
				.map(Game::getLastModified)
				.collect(maxBy((d1, d2) -> d1.compareTo(d2)));
		optionalLastModified.ifPresent(lastModified -> httpHeaders.setLastModified(lastModified.getTime()));

		JsonGameMapper mapper = new JsonGameMapper(userId);
		List<JsonGame> jsonGames = games
				.stream()
				.map(mapper::from)
				.collect(toList());

		return new ResponseEntity<>(jsonGames, httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = GAME_PATH, method = RequestMethod.GET)
	public ResponseEntity<JsonGame> getGame(@AuthenticationPrincipal String userId, @PathVariable(GAME_ID) long gameId) {
		Game game = gameService.getGame(userId, gameId);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setLastModified(game.getLastModified().getTime());

		JsonGame jsonGame = new JsonGameMapper(userId).from(game);
		return new ResponseEntity<>(jsonGame, httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = GAME_PATH, method = RequestMethod.PUT)
	public ResponseEntity<?> acceptDeclineInvitation(
			@AuthenticationPrincipal String userId,
			@PathVariable(GAME_ID) long gameId,
			@RequestBody JsonGameInvitation jsonGameInvitation) {

		if (jsonGameInvitation.getResponse() == JsonGameInvitation.Response.ACCEPT) {
			Game game = gameService.acceptInvitation(userId, gameId);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			JsonGame jsonGame = new JsonGameMapper(userId).from(game);
			return new ResponseEntity<>(jsonGame, httpHeaders, HttpStatus.OK);
		} else {
			gameService.declineInvitation(userId, gameId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createGame(@AuthenticationPrincipal String userId, @RequestBody JsonNewGame jsonNewGame, UriComponentsBuilder uriComponentsBuilder) {
		Difficulty difficulty = Difficulty.valueOf(jsonNewGame.getDifficulty());
		String opponentUserId = jsonNewGame.getOpponent();
		long gameId = gameService.createGame(userId, opponentUserId, difficulty);
		URI gameLoction = uriComponentsBuilder
				.pathSegment(SudokuFeudConfiguration.ROOT_PATH, GAMES_PATH, GAME_PATH)
				.buildAndExpand(gameId)
				.toUri();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(gameLoction);
		return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = ROUNDS_PATH, method = RequestMethod.POST)
	public ResponseEntity<?> executeRound(
			@AuthenticationPrincipal String userId,
			@PathVariable(GAME_ID) long gameId, 
			@RequestBody JsonRound jsonRound,
			UriComponentsBuilder uriComponentsBuilder) {

		Move[] moves = toMoves(jsonRound);
		int roundId = gameService.executeRound(userId, gameId, moves);
		URI roundLocation = uriComponentsBuilder.path(ROUND_PATH).buildAndExpand(roundId).toUri();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(roundLocation);
		return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = ROUNDS_PATH, method = RequestMethod.GET)
	public List<JsonRound> getRounds(@AuthenticationPrincipal String userId, @PathVariable(GAME_ID) String gameId) {
		// TODO: implement
		return asList();
	}

	@RequestMapping(value = ROUNDS_PATH + "/" + ROUND_PATH, method = RequestMethod.GET)
	public JsonRound getRound(@AuthenticationPrincipal String userId, @PathVariable(GAME_ID) String gameId, @PathVariable(ROUND_ID) int roundId) {
		// TODO: implement
		return new JsonRound();
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(GameNotFoundException.class)
	@ResponseBody
	public JsonError handleGameNotFoundException(GameNotFoundException e) {
		return jsonError(e, HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalGameStateException.class)
	@ResponseBody
	public JsonError handleIllegalGameStateException(IllegalGameStateException e) {
		return jsonError(e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BoardException.class)
	@ResponseBody
	public JsonError handleBoardException(BoardException e) {
		return jsonError(e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public JsonError handleRuntimeException(RuntimeException e) {
		return jsonError(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private JsonError jsonError(Exception e, HttpStatus status) {
		if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
			LOGGER.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
		} else {
			LOGGER.debug("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
		}
		return new JsonError(status, e.getMessage());
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
