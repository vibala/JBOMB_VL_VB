package com.fr.ece.jbomb.model;

import java.io.Serializable;

public class Player extends Sprite implements Move,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idPlayer;
	private String directionPourSavoirQuelleImageAfficher;
	//private List<Bonus> BonusAcquis=new ArrayList<Bonus>();
	
	public Player(int id){
		super();
		this.idPlayer=id;
		directionPourSavoirQuelleImageAfficher="";
	}
	
	 public Player(int id,double posX, double posY,double width, double height){
		 super(posX,posY,width,height);
		 this.idPlayer=id;
	 }
	 
	 //Pour similuer un déplacement
	public Player(double posX, double posY,double width, double height){
		 super(posX,posY,width,height);
	 }
	 
	public String getDirectionPourSavoirQuelleImageAfficher(){
		return directionPourSavoirQuelleImageAfficher;
	}
	public void setDirectionPourSavoirQuelleImageAfficher(String s){
	 directionPourSavoirQuelleImageAfficher=s;
	}
	 //Que Côté SERVER
	 public void move(double deltaX,double deltaY) {
	        setPositionX(positionX+deltaX);
	        setPositionY(positionY+deltaY);       
	}
	 
	 public int getID(){
		 return idPlayer;
	 }

	
}