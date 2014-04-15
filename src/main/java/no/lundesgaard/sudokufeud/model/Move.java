package no.lundesgaard.sudokufeud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Move implements Serializable {
	private static final long serialVersionUID = -1070998890759246050L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVE_ID_SEQ")
	@SequenceGenerator(name = "MOVE_ID_SEQ", sequenceName = "move_id_seq")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "round_id")
	private Round round;

	@Column(nullable = false)
	private int x;

	@Column(nullable = false)
	private int y;

	@Column(nullable = false)
	private int piece;

	public Move() {
	}

	public Move(int x, int y, int piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
	}

	public Long getId() {
		return id;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getPiece() {
		return piece;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.appendSuper(super.toString())
				.append("x", x)
				.append("y", y)
				.append("piece", piece)
				.toString();
	}
}
