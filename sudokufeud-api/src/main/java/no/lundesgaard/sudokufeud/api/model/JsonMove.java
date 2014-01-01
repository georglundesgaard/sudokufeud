package no.lundesgaard.sudokufeud.api.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JsonMove {
    private int x;
    private int y;
    private int piece;

    public JsonMove() {
    }

    public JsonMove(int x, int y, int piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;

        JsonMove other = (JsonMove) o;
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
