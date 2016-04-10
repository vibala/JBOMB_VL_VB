package com.fr.ece.jbomb.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {
	protected Image image;
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

	public boolean intersects(Sprite s) {
		return s.getBoundary().intersects(this.getBoundary());
	}
	/*
	sprite: tab
	
	sprite.intersect(player)
	*/
	
	
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