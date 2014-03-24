package no.lundesgaard.sudokufeud.api.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JsonRound {
    private JsonMove[] moves;

    public JsonMove[] getMoves() {
        return moves;
    }

    public void setMoves(JsonMove[] moves) {
        this.moves = moves;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;

        JsonRound other = (JsonRound) o;
        return new EqualsBuilder()
                .append(this.moves, other.moves)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23)
                .append(this.moves)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("moves", moves)
                .toString();
    }
}
