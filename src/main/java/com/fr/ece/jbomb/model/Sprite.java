package com.fr.ece.jbomb.model;

import java.io.Serializable;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe abstraite sérialisée représentant un sprite
 * @author Vincent LIM
 * @version 1.0
 **/
public abstract class Sprite implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected transient Image image;
	protected double positionX;
	protected double positionY;
	protected double width;
	protected double height;

	/**
	 * Constructeur
	 **/
	public Sprite() {

	}

	/**
	 * Sprite 
	 * @param posX posX
	 * @param posY posY
	 * @param width width
	 * @param height height
	 */
	public Sprite(double posX, double posY, double width, double height) {
		this.positionX = posX;
		this.positionY = posY;
		this.width = width;
		this.height = height;
	}

	/**
	 * Dessine l'image sur le canvas
	 * @param gc Composant graphique
	 **/
	public void render(GraphicsContext gc) {
		gc.drawImage(image, positionX, positionY);
	}

	/**
	 * Dessine le rectangle délimitant un sprite
	 * @return rectangle Un rectangle
	 **/
	public Rectangle2D getBoundary() {
		return new Rectangle2D(positionX, positionY, width, height);
	}

	/**
	 * Retourne le rectangle délimitant les sprites associées aux objets décors
	 * @return rectangle Un rectangle
	 **/
	public Rectangle2D getBoundaryDecor(){
		return new Rectangle2D(positionX+4, positionY+4, 24, 24);
	}
	
	/**
	 * Verifie la collision avec le sprite passé en paramètre
	 * @param s Sprite 
	 * @return true/false Collision avec le sprite passé en paramètre
	 **/
	public boolean intersects(Sprite s) {
		return s.getBoundaryDecor().intersects(this.getBoundary());
	}

	/**
	 * Associe une image au sprite
	 * @param image Image
	 **/
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Retourne l'image du sprite
	 * @return image Image du sprite
	 **/
	public Image getImage() {
		return this.image;
	}

	/**
	 * Associe la positionX du sprite
	 * @param positionX PositionX
	 **/
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	/**
	 * Retourne la positionX du sprite
	 * @return positionX PositionX du sprite
	 **/
	public double getPositionX() {
		return this.positionX;
	}

	/**
	 * Associe la positionY du sprite
	 * @param positionY PositionY
	 **/
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	/**
	 * Retourne la positionY du sprite
	 * @return positionY PositionY du sprite
	 **/
	public double getPositionY() {
		return this.positionY;
	}

	/**
	 * Associe la longueur et la largeur
	 * @param width Largeur
	 * @param height Hauteur
	 **/
	public void setWidthHeight(double width, double height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Retourne la largeur du sprite
	 * @return largeur Largeur du sprite
	 **/
	public double getWidth() {
		return width;
	}

	/**
	 * Associe la largeur du sprite
	 * @param width Largeur
	 **/
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * Retourne l'hauteur du sprite
	 * @return hauteur Hauteur du sprite
	 **/
	public double getHeight() {
		return height;
	}

	/**
	 * Associe la longueur du sprite
	 * @param height Hauteur
	 **/
	public void setHeight(double height) {
		this.height = height;
	}
}