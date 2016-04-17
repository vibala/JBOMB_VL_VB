package com.fr.ece.jbomb.model;

import java.awt.Point;

/**
 * Classe représentant une bombe
 * @author Vincent LIM
 * @version 1.0
 **/

@SuppressWarnings("serial")
public class Bomb extends Point {

	int idBomb;

	/**
	 * Constructeur
	 * @param id Identifiant de la bombe
	 * @param x PositionX de la bombe
	 * @param y PositionY de la bombe
	 **/
	public Bomb(int id, int x, int y) {
		super(x, y);
		idBomb = id;
	}

	/**
	 * Constructeur
	 * @param bomb Bombe
	 **/
	public Bomb(Bomb bomb) {
		super(bomb.x, bomb.y);
		idBomb = bomb.idBomb;
	}

	/**
	 * Récupère l'identifiant de la bombe
	 * @return idBomb Identifiant de la bombe
	 **/
	public int getID() {
		return idBomb;
	}

	/**
	 * Associe l'identifiant passé en paramètre à la bombe
	 * @param id Identifiant de la bombe 
	 **/
	public void setID(int id) {
		idBomb = id;
	}
}