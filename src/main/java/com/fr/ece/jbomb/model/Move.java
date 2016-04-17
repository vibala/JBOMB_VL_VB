package com.fr.ece.jbomb.model;

/**
 * Interface pour le déplacement des joueurs
 * @author Vincent LIM
 * @version 1.0
 **/
public interface Move {
	/**
	 * Méthode permettant de faire déplacer les joueurs
	 * @param deltaX offset en horizontal
	 * @param deltaY offset en vertical
	 **/
	public void move(double deltaX,double deltaY);
}
