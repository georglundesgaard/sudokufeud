package no.lundesgaard.sudokufeud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Cell extends BaseEntity {
	private static final long serialVersionUID = 1614866592958652878L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
	
	@Column(nullable = false)
	private int x;

	@Column(nullable = false)
	private int y;
	
	@Column(nullable = true)
	private Integer piece;

	public Cell() {
	}

	public Cell(int x, int y, Integer piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
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

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Integer getPiece() {
		return piece;
	}

	public void setPiece(Integer piece) {
		this.piece = piece;
	}
	
	public boolean isOccupied() {
		return piece != null;
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
