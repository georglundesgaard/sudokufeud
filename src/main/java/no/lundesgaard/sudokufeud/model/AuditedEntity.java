package no.lundesgaard.sudokufeud.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@MappedSuperclass
public abstract class AuditedEntity extends BaseEntity {
	private static final long serialVersionUID = 1609043147639625611L;
	
	@Column(nullable = false, insertable = true, updatable = false)
	private Date created;
	
	@Column(nullable = true, insertable = false, updatable = true)
	private Date modified;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getLastModified() {
		if (modified == null) {
			return created;
		}
		return created;
	}
	
	@PrePersist
	void onCreate() {
		this.created = new Date();
	}
	
	@PreUpdate
	void onUpdate() {
		this.modified = new Date();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.appendSuper(super.toString())
				.append("created", created)
				.append("modified", modified)
				.toString();
	}
}
