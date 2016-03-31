package com.fr.ece.jbomb.model;
public class Player extends Sprite implements Move{
	 public Player(double posX, double posY,double width, double height){
		 super(posX,posY,width,height);
	 }
	 public void move(double deltaX,double deltaY) {
	        setPositionX(positionX+deltaX);
	        setPositionY(positionY+deltaY);       
	}
}