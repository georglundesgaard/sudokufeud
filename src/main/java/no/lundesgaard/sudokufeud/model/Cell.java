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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Cell implements Serializable {
	private static final long serialVersionUID = 1614866592958652878L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CELL_ID_SEQ")
	@SequenceGenerator(name = "CELL_ID_SEQ", sequenceName = "cell_id_seq")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
	
	@Column(nullable = false)
	private int x;

	@Column(nullable = false)
	private int y;
	
	@Column(nullable = false)
	private int piece;
	
	@Column(nullable = false)
	private boolean occupied;

	Cell() {
	}

	public Cell(Board board, int x, int y, int piece, boolean occupied) {
		this.board = board;
		this.x = x;
		this.y = y;
		this.piece = piece;
		this.occupied = occupied;
	}

	public Long getId() {
		return id;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
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
	
	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("boardId", board.getId())
				.append("x", x)
				.append("y", y)
				.append("piece", piece)
				.append("occupied", occupied)
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Cell rhs = (Cell) obj;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.x, rhs.x)
				.append(this.y, rhs.y)
				.append(this.piece, rhs.piece)
				.append(this.occupied, rhs.occupied)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(x)
				.append(y)
				.append(piece)
				.append(occupied)
				.toHashCode();
	}
}
