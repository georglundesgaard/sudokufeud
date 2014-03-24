package no.lundesgaard.sudokufeud.model;

import static no.lundesgaard.sudokufeud.util.ArrayUtil.copyOf;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Player implements Serializable {
    private static final long serialVersionUID = -4551882781278736324L;

    private final long playerId;
    private final int score;
    private final int[] availablePieces;

    public Player(long playerId, int score, int[] availablePieces) {
        this.playerId = playerId;
        this.score = score;
        this.availablePieces = copyOf(availablePieces);
    }

    public long getPlayerId() {
        return playerId;
    }

    public int getScore() {
        return score;
    }

    public int[] getAvailablePieces() {
        return copyOf(availablePieces);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;

        Player other = (Player) o;
        return new EqualsBuilder()
                .append(this.playerId, other.playerId)
                .append(this.score, other.score)
                .append(this.availablePieces, other.availablePieces)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23)
                .append(this.playerId)
                .append(this.score)
                .append(this.availablePieces)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("playerId", playerId)
                .append("score", score)
                .append("availablePieces", availablePieces)
                .toString();
    }
}
