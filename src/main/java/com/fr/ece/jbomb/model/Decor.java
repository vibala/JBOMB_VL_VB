package com.fr.ece.jbomb.model;

/**
 * Classe représentant un objet décor (mur et pilier)
 * @author Vignesh BALA && Vincent LIM
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class Decor extends Sprite{

	/**
	 * Constructeur
	 * @param posX Position X en px de l'objet
	 * @param posY Position Y en px de l'objet
	 * @param width Largeur de l'objet décor
	 * @param height Hauteur de l'objet décor
	 **/
	public Decor( double posX, double posY, double width, double height) {
		super(posX, posY, width, height);
	}
	
	/**
	 * Constructeur 
	 **/
	 public Decor() {
		super();
	}

}
