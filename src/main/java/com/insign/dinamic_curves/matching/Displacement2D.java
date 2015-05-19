package com.insign.dinamic_curves.matching;

/**
 * Created by ilion on 02.05.2015.
 */
public class Displacement2D {
	private int dx, dy;
	public Displacement2D(int dx, int dy) {
		setDx(dx);
		setDy(dy);
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}
}
