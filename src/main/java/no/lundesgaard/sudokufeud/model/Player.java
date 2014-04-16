package no.lundesgaard.sudokufeud.model;

import static no.lundesgaard.sudokufeud.util.ArrayUtil.copyOf;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Player implements Serializable {
	private static final long serialVersionUID = -4551882781278736324L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAYER_ID_SEQ")
	@SequenceGenerator(name = "PLAYER_ID_SEQ", sequenceName = "player_id_seq")
	private Long id;

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

	public Long getId() {
		return id;
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

	public String getUserId() {
		return profile.getUserId();
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
