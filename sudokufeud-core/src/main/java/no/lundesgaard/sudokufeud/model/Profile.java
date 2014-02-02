package no.lundesgaard.sudokufeud.model;

import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

public class Profile implements Identifiable {
    private static final long serialVersionUID = -3134336451049287093L;
    
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    private final String id;
    private final String userId;
    private final String password;
    private final String name;
    private final DateTime created;
    private final DateTime modified;

    public Profile(Profile profile, String userId, String password, String name) {
        this.id = profile.id;
        
        if (userId != null && userId.trim().length() > 0) {
            this.userId = userId;   
        } else {
            this.userId = profile.userId;
        }
        
        if (password != null && password.trim().length() > 0) {
            this.password = password;
        } else {
            this.password = profile.password;
        }
        
        if (name != null && name.trim().length() > 0) {
            this.name = name;
        } else {
            this.name = profile.name;
        }
        this.created = profile.created;
        this.modified = DateTime.now();
    }

    public Profile(String userId, String password, String name) {
        this.id = generateId();
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.created = DateTime.now();
        this.modified = null;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
    
    public boolean validatePassword(String password) {
        return this.password != null && this.password.equals(password); 
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
                .append(this.password, other.password)
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
                .append(this.password)
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
                .append("password", "[PASSWORD]")
                .append("name", name)
                .append("created", created)
                .append("modified", modified)
                .toString();
    }
}
