package no.lundesgaard.sudokufeud.model;

import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import no.lundesgaard.sudokufeud.repository.exception.GameNotFoundException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.NaturalId;

@Entity
public class Profile extends AuditedEntity {
	private static final long serialVersionUID = -3134336451049287093L;

	@Column(nullable = false)
	@NaturalId
	private String userId;

	@Column(nullable = false)
	private String password;

	@Column(nullable = true)
	private String name;
	
	@OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
	private Set<Player> gamePlayers = new HashSet<>();

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
	
	public List<Game> findGames() {
		return gamePlayers
				.stream()
				.map(Player::getGame)
				.collect(toList());
	}
	
	public Game findGame(long id) {
		Optional<Game> gameOptional = gamePlayers
				.stream()
				.map(Player::getGame)
				.filter(g -> g.getId() == id)
				.findFirst();
		
		return gameOptional.orElseThrow(() -> new GameNotFoundException(id, userId));
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
