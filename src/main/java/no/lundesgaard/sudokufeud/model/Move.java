package no.lundesgaard.sudokufeud.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Move implements Serializable {
    private static final long serialVersionUID = -1070998890759246050L;

    private int x;
    private int y;
    private int piece;

    public Move(int x, int y, int piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPiece() {
        return piece;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;

        Move other = (Move) o;
        return new EqualsBuilder()
                .append(this.x, other.x)
                .append(this.y, other.y)
                .append(this.piece, other.piece)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23)
                .append(this.x)
                .append(this.y)
                .append(this.piece)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("x", x)
                .append("y", y)
                .append("piece", piece)
                .toString();
    }
}
