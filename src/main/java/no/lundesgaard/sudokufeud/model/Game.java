package no.lundesgaard.sudokufeud.model;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.constants.State;
import no.lundesgaard.sudokufeud.constants.Status;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Game extends AuditedEntity {
	private static final long serialVersionUID = -3979593399708874988L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GAME_ID_SEQ")
	@SequenceGenerator(name = "GAME_ID_SEQ", sequenceName = "game_id_seq")
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "player1_id")
	private Player player1;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "player2_id")
	private Player player2;
	
	@Column(nullable = true)
	@Enumerated(EnumType.ORDINAL)
	private PlayerId currentPlayer;
	
	@OneToOne(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Board board;
	
	@Column(nullable = true)
	private int[] availablePieces;
	
	@OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Round> rounds = new ArrayList<>();

	@Column(nullable = true)
	private Date started;
	
	@Column(nullable = true)
	private Date completed;

	Game() {
	}

	Game(Player player1, Player player2, Board board) {
		this.player1 = player1;
		this.player1.setGame(this);
		this.player2 = player2;
		this.player2.setGame(this);
		this.board = board;
		this.board.setGame(this);
		this.availablePieces = board.getAvailablePieces();
	}

	public Long getId() {
		return id;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public long getInvitedPlayerId() {
		return player2.getProfile().getId();
	}

	public Board getBoard() {
		return board;
	}

	public Integer[] getBoardArray() {
		if (board != null && started != null) {
			return board.toIntegerArray();
		}
		return null;
	}

	public Difficulty getBoardDifficulty() {
		if (board != null) {
			return board.getDifficulty();
		}
		return null;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public State getState() {
		if (started == null && completed == null) {
			return State.NEW;
		}
		if (started != null && completed == null) {
			return State.RUNNING;
		}
		return State.COMPLETED;
	}

	public Status getStatusForPlayer1() {
		return getStatusFor(PlayerId.PLAYER_ONE);
	}

	public Status getStatusForPlayer2() {
		return getStatusFor(PlayerId.PLAYER_TWO);
	}

	private Status getStatusFor(PlayerId playerId) {
		switch (getState()) {
			case NEW:
				if (playerId == PlayerId.PLAYER_ONE) {
					return Status.WAITING;
				}
				return Status.INVITATION;
			case RUNNING:
				if (currentPlayer == playerId) {
					return Status.READY;
				}
				return Status.WAITING;
			default:
				if (getWinner() == null) {
					return Status.TIED;
				}
				if (getWinner() == playerId) {
					return Status.WON;
				}
				return Status.LOST;
		}
	}

	public PlayerId getCurrentPlayer() {
		return currentPlayer;
	}

	public PlayerId getWinner() {
		if (completed == null) {
			return null;
		}
		if (player1.getScore() > player2.getScore()) {
			return PlayerId.PLAYER_ONE;
		}
		if (player2.getScore() > player1.getScore()) {
			return PlayerId.PLAYER_TWO;
		}
		return rounds.get(rounds.size() - 1).getPlayerId();
	}

	public Date getLastModified() {
		if (getModified() == null) {
			return getCreated();
		}
		return getModified();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.appendSuper(super.toString())
				.append("player1", player1)
				.append("player2", player2)
				.append("currentPlayer", currentPlayer)
				.append("board", board)
				.append("availablePieces", availablePieces)
				.append("rounds", rounds)
				.append("started", started)
				.append("completed", completed)
				.toString();
	}

	public static Game create(Profile playerProfile1, Profile playerProfile2, Board board) {
		if (playerProfile1 == null) {
			throw new IllegalArgumentException("player1 required");
		}
		if (playerProfile2 == null) {
			throw new IllegalArgumentException("player2 required");
		}
		if (board == null) {
			throw new IllegalArgumentException("board required");
		}
		return new Game(new Player(playerProfile1), new Player(playerProfile2), board);
	}

	public void start(String playerUserId) {
		State state = getState();
		if (state != State.NEW) {
			throw new IllegalStateException(format("expected state <%s>, but was <%s>", State.NEW, state));
		}
		
		PlayerId currentPlayer;
		if (player1.getUserId().equals(playerUserId)) {
			currentPlayer = PlayerId.PLAYER_ONE;
		} else if (player2.getUserId().equals(playerUserId)) {
			currentPlayer = PlayerId.PLAYER_TWO;
		} else {
			throw new IllegalArgumentException(format("unknown starting player: <%s>", playerUserId));
		}
		this.currentPlayer = currentPlayer;
		
		int[] boardAvailablePieces = board.getAvailablePieces();
		int[] availablePieces = new int[7];
		System.arraycopy(boardAvailablePieces, 0, availablePieces, 0, 7);
		player1.setAvailablePieces(availablePieces);
		availablePieces = new int[7];
		System.arraycopy(boardAvailablePieces, 7, availablePieces, 0, 7);
		player2.setAvailablePieces(availablePieces);
		availablePieces = new int[boardAvailablePieces.length - 14];
		System.arraycopy(boardAvailablePieces, 14, availablePieces, 0, availablePieces.length);
		this.availablePieces = availablePieces;

		started = new Date();
	}

	public void executeRound(Move... movesInRound) {
		State state = getState();
		if (state != State.RUNNING) {
			throw new IllegalStateException(format("expected state <%s>, but was <%s>", State.RUNNING, state));
		}
		
		Board.Statistics statisticsBefore = board.getStatistics();
		List<Integer> playerPieceList = new ArrayList<>();
		if (currentPlayer == PlayerId.PLAYER_ONE) {
			for (int piece : player1.getAvailablePieces()) {
				playerPieceList.add(piece);
			}
		} else {
			for (int piece : player2.getAvailablePieces()) {
				playerPieceList.add(piece);
			}
		}

		for (Move move : movesInRound) {
			Integer piece = move.getPiece();
			if (piece <= 0 || piece >= 10) {
				throw new IllegalArgumentException(format("illegal piece: <%s>", piece));
			}
			if (!playerPieceList.contains(piece)) {
				throw new IllegalArgumentException(format("unavailable piece: <%s>", piece));
			}
			board.placePiece(move.getX(), move.getY(), piece);
			playerPieceList.remove(piece);
		}

		Board.Statistics statisticsAfter = board.getStatistics();
		int bonus = (statisticsAfter.getOccupiedColumns() - statisticsBefore.getOccupiedColumns()) * 5
				+ (statisticsAfter.getOccupiedRows() - statisticsBefore.getOccupiedRows()) * 5
				+ (statisticsAfter.getOccupiedSquares() - statisticsBefore.getOccupiedSquares()) * 5;
		if (playerPieceList.isEmpty()) {
			bonus += 10;
		}

		int score;
		if (currentPlayer == PlayerId.PLAYER_ONE) {
			score = player1.getScore();
			score += movesInRound.length + bonus;
		} else {
			score = player2.getScore();
			score += movesInRound.length + bonus;
		}

		int index = 0;
		for (; playerPieceList.size() < 7 && index < availablePieces.length; index++) {
			playerPieceList.add(availablePieces[index]);
		}
		if (index > 0 && index < availablePieces.length) {
			int[] newAvailablePieces = new int[availablePieces.length - index];
			System.arraycopy(availablePieces, index, newAvailablePieces, 0, newAvailablePieces.length);
			availablePieces = newAvailablePieces;
		} else if (index >= availablePieces.length) {
			availablePieces = new int[0];
		}

		int[] playerPieces = new int[playerPieceList.size()];
		for (int i = 0; i < playerPieceList.size(); i++) {
			playerPieces[i] = playerPieceList.get(i);
		}
		
		rounds.add(new Round(this, movesInRound));

		if (currentPlayer == PlayerId.PLAYER_ONE) {
			currentPlayer = PlayerId.PLAYER_TWO;
			player1.setScore(score);
			player1.setAvailablePieces(playerPieces);
		} else {
			currentPlayer = PlayerId.PLAYER_ONE;
			player2.setScore(score);
			player2.setAvailablePieces(playerPieces);
		}

		if (player1.getAvailablePieces().length == 0 && player2.getAvailablePieces().length == 0) {
			completed = new Date();
			currentPlayer = null;
		}
	}
}
