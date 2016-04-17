package com.fr.ece.jbomb.model;

import java.awt.Point;

public class Bomb extends Point {

	int idBomb;

	public Bomb(int id, int x, int y) {
		super(x, y);
		idBomb = id;
	}

	public Bomb(Bomb bomb) {
		super(bomb.x, bomb.y);
		idBomb = bomb.idBomb;
	}

	public int getID() {
		return idBomb;
	}

	public void setID(int id) {
		idBomb = id;
	}
}