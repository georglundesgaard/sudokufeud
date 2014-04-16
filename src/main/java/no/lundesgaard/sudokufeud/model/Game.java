package no.lundesgaard.sudokufeud.model;

import static no.lundesgaard.sudokufeud.util.ArrayUtil.copyOf;

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

	public Game() {
	}

	public Game(Player player1, Player player2, Board board) {
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

	public void setBoard(Board board) {
		this.board = board;
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

	public int[] getAvailablePieces() {
		return copyOf(availablePieces);
	}

	public void setAvailablePieces(int[] availablePieces) {
		this.availablePieces = availablePieces;
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

	public void setCurrentPlayer(PlayerId currentPlayer) {
		this.currentPlayer = currentPlayer;
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

	public void setStarted(Date started) {
		this.started = started;
	}

	public void setCompleted(Date completed) {
		this.completed = completed;
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

}
