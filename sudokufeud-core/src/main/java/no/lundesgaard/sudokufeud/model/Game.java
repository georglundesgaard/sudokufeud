package no.lundesgaard.sudokufeud.model;

import static no.lundesgaard.sudokufeud.util.ArrayUtil.copyOf;

import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

public class Game implements Identifiable {
    private static final long serialVersionUID = -3979593399708874988L;
    
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    private final String id;
    private final Player player1;
    private final Player player2;
    private final String currentPlayerId;
    private final Board board;
    private final int[] availablePieces;
    private final Round[] rounds;
    private final DateTime started;
    private final DateTime completed;
    private final DateTime created;
    private final DateTime modified;

    public Game(
            String id,
            Player player1,
            Player player2,
            String currentPlayerId,
            Board board,
            int[] availablePieces,
            Round[] rounds,
            DateTime started,
            DateTime completed,
            DateTime created,
            DateTime modified) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayerId = currentPlayerId;
        this.board = board;
        this.availablePieces = copyOf(availablePieces);
        this.rounds = copyOf(rounds);
        this.started = started;
        this.completed = completed;
        this.created = created;
        this.modified = modified;
    }

    @Override
    public String getId() {
        return id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
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

    public Player getCurrentPlayer() {
        if (currentPlayerId == null) {
            return null;
        }
        if (currentPlayerId.equals(player1.getPlayerId())) {
            return player1;
        }
        return player2;
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
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;

        Game other = (Game) o;
        return new EqualsBuilder()
                .append(this.id, other.id)
                .append(this.player1, other.player1)
                .append(this.player2, other.player2)
                .append(this.currentPlayerId, other.currentPlayerId)
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
                .append(this.currentPlayerId)
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
                .append("currentPlayer", currentPlayerId)
                .append("board", board)
                .append("availablePieces", availablePieces)
                .append("rounds", rounds)
                .append("started", started)
                .append("completed", completed)
                .append("created", created)
                .append("modified", modified)
                .toString();
    }

    public Player getPlayer(String playerId) {
        if (playerId.equals(player1.getPlayerId())) {
            return player1;
        }
        if (playerId.equals(player2.getPlayerId())) {
            return player2;
        }
        return null;
    }

    public enum State {
        NEW,
        RUNNING,
        COMPLETED
    }
}
