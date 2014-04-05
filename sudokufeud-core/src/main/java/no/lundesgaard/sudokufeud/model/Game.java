package no.lundesgaard.sudokufeud.model;

import static java.lang.String.format;
import static no.lundesgaard.sudokufeud.util.ArrayUtil.copyOf;

import java.util.UUID;

import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.constants.State;
import no.lundesgaard.sudokufeud.constants.Status;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

public class Game implements Identifiable {
	private static final long serialVersionUID = -3979593399708874988L;
	private final String id;
	private final Player player1;
	private final Player player2;
	private final Player currentPlayer;
	private final Board board;
	private final int[] availablePieces;
	private final Round[] rounds;
	private final DateTime started;
	private final DateTime completed;
	private final DateTime created;
	private final DateTime modified;

	public Game(String id, Player player1, Player player2, String currentPlayerId, Board board, int[] availablePieces, Round[] rounds, DateTime started,
				DateTime completed, DateTime created, DateTime modified) {
		this.id = id;
		this.player1 = player1;
		this.player2 = player2;
		if (player1.getPlayerId().equals(currentPlayerId)) {
			this.currentPlayer = player1;
		} else if (player2.getPlayerId().equals(currentPlayerId)) {
			this.currentPlayer = player2;
		} else if (currentPlayerId != null) {
			throw new IllegalArgumentException(
					format("expected current player id to be one of <%s> or <%s>, but got <%s>", player1.getPlayerId(), player2.getPlayerId(), currentPlayerId));
		} else {
			this.currentPlayer = null;
		}
		this.board = board;
		this.availablePieces = copyOf(availablePieces);
		this.rounds = copyOf(rounds);
		this.started = started;
		this.completed = completed;
		this.created = created;
		this.modified = modified;
	}

	public static String generateId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String getId() {
		return id;
	}

	public Player getPlayer1() {
		return player1;
	}

	public String getPlayerId1() {
		return player1.getPlayerId();
	}

	public String getInvitingPlayerId() {
		return player1.getPlayerId();
	}

	public Player getPlayer2() {
		return player2;
	}

	public String getPlayerId2() {
		return player2.getPlayerId();
	}

	public String getInvitedPlayerId() {
		return player2.getPlayerId();
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

	public int[] getAvailablePieces() {
		return copyOf(availablePieces);
	}

	public Round[] getRounds() {
		return copyOf(rounds);
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
		return getStatusFor(player1);
	}

	public Status getStatusForPlayer2() {
		return getStatusFor(player2);
	}

	private Status getStatusFor(Player player) {
		switch (getState()) {
			case NEW:
				if (player1 == player) {
					return Status.WAITING;
				}
				return Status.INVITATION;
			case RUNNING:
				if (currentPlayer == player) {
					return Status.READY;
				}
				return Status.WAITING;
			default:
				if (getWinner() == null) {
					return Status.TIED;
				}
				if (getWinner() == player) {
					return Status.WON;
				}
				return Status.LOST;
		}
	}

	public boolean isPlayer(Profile playerProfile) {
		if (player1.getPlayerId().equals(playerProfile.getId())) {
			return true;
		}
		if (player2.getPlayerId().equals(playerProfile.getId())) {
			return true;
		}
		return false;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public String getCurrentPlayerId() {
		return currentPlayer != null ? currentPlayer.getPlayerId() : null;
	}

	public Player getWinner() {
		if (completed == null) {
			return null;
		}
		if (player1.getScore() > player2.getScore()) {
			return player1;
		}
		if (player2.getScore() > player1.getScore()) {
			return player2;
		}
		return rounds[rounds.length - 1].getPlayer();
	}

	public Player getLoser() {
		if (completed == null) {
			return null;
		}
		if (player1.getScore() > player2.getScore()) {
			return player2;
		}
		if (player2.getScore() > player1.getScore()) {
			return player1;
		}
		return rounds[rounds.length - 2].getPlayer();
	}

	public DateTime getStarted() {
		return started;
	}

	public DateTime getCompleted() {
		return completed;
	}

	public DateTime getCreated() {
		return created;
	}

	public DateTime getModified() {
		return modified;
	}

	public DateTime getLastModified() {
		if (modified == null) {
			return created;
		}
		return created;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (o.getClass() != this.getClass())
			return false;

		Game other = (Game) o;
		return new EqualsBuilder()
				.append(this.id, other.id)
				.append(this.player1, other.player1)
				.append(this.player2, other.player2)
				.append(this.currentPlayer, other.currentPlayer)
				.append(this.board, other.board)
				.append(this.availablePieces, other.availablePieces)
				.append(this.rounds, other.rounds)
				.append(this.started, other.started)
				.append(this.completed, other.completed)
				.append(this.created, other.created)
				.append(this.modified, other.modified)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23)
				.append(this.id)
				.append(this.player1)
				.append(this.player2)
				.append(this.currentPlayer)
				.append(this.board)
				.append(this.availablePieces)
				.append(this.rounds)
				.append(this.started)
				.append(this.completed)
				.append(this.created)
				.append(this.modified)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("player1", player1)
				.append("player2", player2)
				.append("currentPlayerId", currentPlayer != null ? currentPlayer.getPlayerId() : null)
				.append("board", board)
				.append("availablePieces", availablePieces)
				.append("rounds", rounds)
				.append("started", started)
				.append("completed", completed)
				.append("created", created)
				.append("modified", modified)
				.toString();
	}

}
