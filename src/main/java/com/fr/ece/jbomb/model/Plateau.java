package com.fr.ece.jbomb.model;

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
	
	Plateau(int valeur){
		this.valeur = valeur;
	}
	
	public static Plateau getNomByValeur(int valeur){
		for (Plateau plateau : Plateau.values()) {
			if(plateau.valeur == valeur){
				return plateau;
			}
		}
			return null;
	}
	
	public int getValeur(){
		return this.valeur;
	}
	
}
