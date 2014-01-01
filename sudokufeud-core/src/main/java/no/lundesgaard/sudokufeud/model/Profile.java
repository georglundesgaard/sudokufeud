package no.lundesgaard.sudokufeud.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

public class Profile implements Identifiable {
    private static final long serialVersionUID = -3134336451049287093L;

    private final String id;
    private final String userId;
    private final String name;
    private final DateTime created;
    private final DateTime modified;

    public Profile(
            String id,
            String userId,
            String name,
            DateTime created,
            DateTime modified) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.created = created;
        this.modified = modified;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
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

        Profile other = (Profile) o;
        return new EqualsBuilder()
                .append(this.id, other.id)
                .append(this.userId, other.userId)
                .append(this.name, other.name)
                .append(this.created, other.created)
                .append(this.modified, other.modified)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23)
                .append(this.id)
                .append(this.userId)
                .append(this.name)
                .append(this.created)
                .append(this.modified)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id ", id)
                .append("userId", userId)
                .append("name", name)
                .append("created", created)
                .append("modified", modified)
                .toString();
    }
}
