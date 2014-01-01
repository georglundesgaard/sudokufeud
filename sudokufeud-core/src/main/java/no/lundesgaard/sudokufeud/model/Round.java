package no.lundesgaard.sudokufeud.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

import java.io.Serializable;

import static no.lundesgaard.sudokufeud.util.ArrayUtil.copyOf;

public class Round implements Serializable {
    private static final long serialVersionUID = -9161769701283945758L;

    private final Player player;
    private final Move[] moves;
    private final DateTime timestamp;

    public Round(Player player, Move[] moves) {
        this.player = player;
        this.moves = copyOf(moves);
        this.timestamp = DateTime.now();
    }

    public Player getPlayer() {
        return player;
    }

    public Move[] getMoves() {
        return copyOf(moves);
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;

        Round other = (Round) o;
        return new EqualsBuilder()
                .append(this.player, other.player)
                .append(this.moves, other.moves)
                .append(this.timestamp, other.timestamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23)
                .append(this.player)
                .append(this.moves)
                .append(this.timestamp)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("player", player)
                .append("moves", moves)
                .append("timestamp", timestamp)
                .toString();
    }
}
