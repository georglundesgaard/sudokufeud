package no.lundesgaard.sudokufeud.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Round extends AuditedEntity {
	private static final long serialVersionUID = -3949281744347864061L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private Game game;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private PlayerId playerId;
	
	@OneToMany(mappedBy = "round", fetch = FetchType.LAZY)
	private List<Move> moves = new ArrayList<>();

	public Round(PlayerId playerId, Move... moves) {
		this.playerId = playerId;
		Collections.addAll(this.moves, moves);
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public PlayerId getPlayerId() {
		return playerId;
	}

	public void setPlayerId(PlayerId playerId) {
		this.playerId = playerId;
	}

	public List<Move> getMoves() {
		return moves;
	}

	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.appendSuper(super.toString())
				.append("playerId", playerId)
				.append("moves", moves)
				.toString();
	}
}
