package no.lundesgaard.sudokufeud.api.model;

import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.constants.State;
import no.lundesgaard.sudokufeud.constants.Status;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

public class JsonGame {
	private String id;
	private int score;
	private int[] availablePieces;
	private String opponentUserId;
	private int opponentScore;
	private State state;
	private Status status;
	private String currentPlayer;
	private Integer[] board;
	private Difficulty difficulty;
	private DateTime created;

	public JsonGame() {
	}

	public JsonGame(
			String id,
			int score,
			int[] availablePieces,
			String opponentUserId,
			int opponentScore,
			State state,
			Status status,
			String currentPlayer,
			Integer[] board,
			Difficulty difficulty,
			DateTime created) {

		this.id = id;
		this.score = score;
		this.availablePieces = availablePieces;
		this.opponentUserId = opponentUserId;
		this.opponentScore = opponentScore;
		this.state = state;
		this.status = status;
		this.currentPlayer = currentPlayer;
		this.board = board;
		this.difficulty = difficulty;
		this.created = created;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int[] getAvailablePieces() {
		return availablePieces;
	}

	public void setAvailablePieces(int[] availablePieces) {
		this.availablePieces = availablePieces;
	}

	public String getOpponentUserId() {
		return opponentUserId;
	}

	public void setOpponentUserId(String opponentUserId) {
		this.opponentUserId = opponentUserId;
	}

	public int getOpponentScore() {
		return opponentScore;
	}

	public void setOpponentScore(int opponentScore) {
		this.opponentScore = opponentScore;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Integer[] getBoard() {
		return board;
	}

	public void setBoard(Integer[] board) {
		this.board = board;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public DateTime getCreated() {
		return created;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (o.getClass() != this.getClass())
			return false;

		JsonGame other = (JsonGame) o;
		return new EqualsBuilder()
				.append(this.id, other.id)
				.append(this.score, other.score)
				.append(this.availablePieces, other.availablePieces)
				.append(this.opponentUserId, other.opponentUserId)
				.append(this.opponentScore, other.opponentScore)
				.append(this.state, other.state)
				.append(this.status, other.status)
				.append(this.board, other.board)
				.append(this.difficulty, other.difficulty)
				.append(this.created, other.created)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23)
				.append(this.id)
				.append(this.score)
				.append(this.availablePieces)
				.append(this.opponentUserId)
				.append(this.opponentScore)
				.append(this.state)
				.append(this.status)
				.append(this.board)
				.append(this.difficulty)
				.append(this.created)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("score", score)
				.append("availablePieces", availablePieces)
				.append("opponentUserId", opponentUserId)
				.append("opponentScore", opponentScore)
				.append("state", state)
				.append("status", status)
				.append("board", board)
				.append("difficulty", difficulty)
				.append("created", created)
				.toString();
	}

}
