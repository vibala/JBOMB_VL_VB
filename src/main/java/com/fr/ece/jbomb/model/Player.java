package com.fr.ece.jbomb.model;

import java.io.Serializable;

/**
 * Classe représentant le sprite associé au joueur
 * @author Vincent LIM
 * @version 1.0
 **/
public class Player extends Sprite implements Move,Serializable{
	
	private static final long serialVersionUID = -5006553028893773200L;
	private int idPlayer;
	private String directionPourSavoirQuelleImageAfficher="DOWN";
	private int bomb=1;

	/**
	 * Constructeur
	 * @param id identifiant du joueur
	 **/
	public Player(int id){
		super();
		this.idPlayer=id;
		directionPourSavoirQuelleImageAfficher="";
	}
	
/**
 * Player
 * @param id id
 * @param posX posX
 * @param posY posY
 * @param width width
 * @param height height
 */
	 public Player(int id,double posX, double posY,double width, double height){
		 super(posX,posY,width,height);
		 this.idPlayer=id;
	 }
	 
	/**
	 * Player
	 * @param posX posX
	 * @param posY posY
	 * @param width width
	 * @param height height
	 */
	public Player(double posX, double posY,double width, double height){
		 super(posX,posY,width,height);
	 }
	
	/**
	 * Retourne la direction empruntée par le joueur
	 * @return directionPourSavoirQuelleImageAfficher Direction empruntée par le joueur
	 **/
	public String getDirectionPourSavoirQuelleImageAfficher(){
		return directionPourSavoirQuelleImageAfficher;
	}
	
	/**
	 * Associe la valeur passée en paramètre à la direction empruntée par le joueur
	 * @param s Direction du joueur
	 **/
	public void setDirectionPourSavoirQuelleImageAfficher(String s){
			 directionPourSavoirQuelleImageAfficher=s;
	}
	
	/**
	 * Met à jour le déplacement du joueur
	 * @param deltaX offset en horizontal 
	 * @param deltaY offset en vertical
	 **/
	 public void move(double deltaX,double deltaY) {
	        setPositionX(positionX+deltaX);
	        setPositionY(positionY+deltaY);       
	}
	
	 /**
	 * Retourne l'id du joueur
	 * @return idPlayer identifiant du joueur
	 **/ 
	public int getID(){
		 return idPlayer;
	}

	/**
	 * Retourne le nombre de bombes possédées par le joueur
	 * @return bomb nombre de bombes
	 **/
	public int getBomb() {
		return bomb;
	}

	/**
	 * Met à jour le nombre de bombes
	 * @param i indice 
	 **/
	public void setBomb(int i) {
		bomb=i;
	}
	
}