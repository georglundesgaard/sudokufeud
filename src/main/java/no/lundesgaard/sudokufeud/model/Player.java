package no.lundesgaard.sudokufeud.model;

import static no.lundesgaard.sudokufeud.util.ArrayUtil.copyOf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.NaturalId;

@Entity
public class Player extends BaseEntity {
	private static final long serialVersionUID = -4551882781278736324L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "game_id")
	private Game game;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private Profile profile;

	@Column(nullable = false)
	private int score;

	@Column(nullable = true)
	private int[] availablePieces;

	public Player() {
	}

	public Player(Profile profile) {
		this.profile = profile;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int[] getAvailablePieces() {
		return copyOf(availablePieces);
	}

	public void setAvailablePieces(int[] availablePieces) {
		this.availablePieces = availablePieces;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.appendSuper(super.toString())
				.append("gameId", game.getId())
				.append("profileId", profile.getId())
				.append("score", score)
				.append("availablePieces", availablePieces)
				.toString();
	}
}
