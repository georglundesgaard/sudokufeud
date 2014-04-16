package no.lundesgaard.sudokufeud.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Round extends AuditedEntity {
	private static final long serialVersionUID = -3949281744347864061L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROUND_ID_SEQ")
	@SequenceGenerator(name = "ROUND_ID_SEQ", sequenceName = "round_id_seq")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private Game game;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private PlayerId playerId;
	
	@OneToMany(mappedBy = "round", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Move> moves = new ArrayList<>();

	public Round() {
	}

	public Round(Game game, Move... moves) {
		this.game = game;
		this.playerId = game.getCurrentPlayer();
		for (Move move : moves) {
			move.setRound(this);
			this.moves.add(move);
		}
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

	public PlayerId getPlayerId() {
		return playerId;
	}

	public List<Move> getMoves() {
		return moves;
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
