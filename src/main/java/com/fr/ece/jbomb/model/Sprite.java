package com.fr.ece.jbomb.model;

import java.io.Serializable;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected transient Image image;
	protected double positionX;
	protected double positionY;
	protected double width;
	protected double height;

	public Sprite() {

	}

	public Sprite(double posX, double posY, double width, double height) {
		this.positionX = posX;
		this.positionY = posY;
		this.width = width;
		this.height = height;
	}

	public void render(GraphicsContext gc) {
		gc.drawImage(image, positionX, positionY);
	}

	public void render_to(GraphicsContext gc, double posx_pre, double posy_pre) {
		gc.drawImage(image, posx_pre, posy_pre, 32, 32, getPositionX(), getPositionY(), 32, 32);
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(positionX, positionY, width, height);
	}

	public Rectangle2D getBoundaryDecor(){
		return new Rectangle2D(positionX+4, positionY+4, 24, 24);
	}
	
	public boolean intersects(Sprite s) {
		return s.getBoundaryDecor().intersects(this.getBoundary());
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return this.image;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionX() {
		return this.positionX;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public double getPositionY() {
		return this.positionY;
	}

	public void setWidthHeight(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
}