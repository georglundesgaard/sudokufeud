package no.lundesgaard.sudokufeud.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.NaturalId;

@Entity
public class Profile extends BaseEntity {
	private static final long serialVersionUID = -3134336451049287093L;

	@Column(nullable = false)
	@NaturalId
	private String userId;

	@Column(nullable = false)
	private String password;

	@Column(nullable = true)
	private String name;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o == this) return true;
		if (o.getClass() != this.getClass()) return false;

		Profile other = (Profile) o;
		return new EqualsBuilder()
				.appendSuper(super.equals(o))
				.append(this.userId, other.userId)
				.append(this.password, other.password)
				.append(this.name, other.name)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23)
				.appendSuper(super.hashCode())
				.append(this.userId)
				.append(this.password)
				.append(this.name)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.appendSuper(super.toString())
				.append("userId", userId)
				.append("password", "[HIDDEN]")
				.append("name", name)
				.toString();
	}
}
