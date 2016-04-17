package com.fr.ece.jbomb.model;

/**
 * Classe d'énumération stockant les informations essentielles du plateau
 * @author Vignesh BALA && Vincent LIM
 * @version 1.0
 **/
public enum Plateau {

	LONGUEUR_PLATEAU(608),
	LARGEUR_PLATEAU(800),
	LONGUEUR_CASE(32),
	LARGEUR_CASE(32),
	SOL(0),
	PLAYER1(1),
	PLAYER2(2),
	PLAYER3(3),
	PLAYER4(4),
	PILIER(5),
	MUR(6),
	BOMBE(7),
	CRAME(8);
	private int valeur;
	
	/**
	 * Constructeur
	 * @param valeur Valeur associée à chaque objet énuméré
	 **/
	Plateau(int valeur){
		this.valeur = valeur;
	}
	/**
	 * Retourne l'objet de cette classe à partir de la valeur passée en paramètre
	 * @param valeur Valeur associée à chaque objet énuméré
	 **/
	public static Plateau getNomByValeur(int valeur){
		for (Plateau plateau : Plateau.values()) {
			if(plateau.valeur == valeur){
				return plateau;
			}
		}
			return null;
	}
	
	/**
	 * Retourne la valeur d'un objet de cette classe
	 * @return valeur Valeur associée à un objet
	 **/
	public int getValeur(){
		return this.valeur;
	}
	
}
