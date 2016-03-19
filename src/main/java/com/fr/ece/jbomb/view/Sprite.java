package com.fr.ece.jbomb.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite
{
    private Image image;
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
 
   
    public void update(double time)
    {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }
 
    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, positionX, positionY );
    }
    
    public void render_to(GraphicsContext gc, double posx_pre, double posy_pre){
    	gc.drawImage(image, posx_pre, posy_pre, 32, 32, getPositionX(), getPositionY(), 32, 32);
    }
 
    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }
 
    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }
    
    public void setImage(Image image){
    	this.image = image;
    }
    
    public Image getImage(){
    	return this.image;
    }
    
    public void setPositionX(double positionX){
    	this.positionX = positionX;
    }
    
    public double getPositionX(){
    	return this.positionX;
    }
    
    public void setPositionY(double positionY){
    	this.positionY = positionY;
    }
    
    public double getPositionY(){
    	return this.positionY;
    }
    
    public void setVelocityX(double velocityX){
    	this.velocityX = velocityX;
    }
    
    public double getVelocityX(double velocityX){
    	return this.velocityX;
    }
    
    public void setVelocityY(double velocityY){
    	this.velocityY = velocityY;
    }
    
    public double getVelocityY(double velocityY){
    	return this.velocityY;
    }
    
    public void setWidthHeight(double width, double height){
    	this.width = width;
    	this.height = height;
    }
    
    public void addVelocity(double velocityX, double velocityY){
    	this.velocityX += velocityX;
    	this.velocityY += velocityY;
    }
}