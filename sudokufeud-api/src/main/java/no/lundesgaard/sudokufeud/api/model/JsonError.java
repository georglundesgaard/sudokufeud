package no.lundesgaard.sudokufeud.api.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.ws.rs.core.Response;

public class JsonError {
    private int code;
    private String reason;
    private String description;

    public JsonError(int code, String reason, String description) {
        this.code = code;
        this.reason = reason;
        this.description = description;
    }

    public JsonError(Response.Status status, String description) {
        this(status.getStatusCode(), status.getReasonPhrase(), description);
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;

        JsonError other = (JsonError) o;
        return new EqualsBuilder()
                .append(this.code, other.code)
                .append(this.reason, other.reason)
                .append(this.description, other.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23)
                .append(this.code)
                .append(this.reason)
                .append(this.description)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("code", code)
                .append("reason", reason)
                .append("description", description)
                .toString();
    }
}
