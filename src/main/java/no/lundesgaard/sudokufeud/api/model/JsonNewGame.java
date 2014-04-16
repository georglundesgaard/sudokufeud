package no.lundesgaard.sudokufeud.api.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JsonNewGame {
    private String opponent;
    private String difficulty;

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;

        JsonNewGame other = (JsonNewGame) o;
        return new EqualsBuilder()
                .append(this.opponent, other.opponent)
                .append(this.difficulty, other.difficulty)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23)
                .append(this.opponent)
                .append(this.difficulty)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("opponent", opponent)
                .append("difficulty", difficulty)
                .toString();
    }
}
