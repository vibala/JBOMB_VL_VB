package com.fr.ece.jbomb.view;

public enum Plateau {

	LONGUEUR_PLATEAU(608),
	LARGEUR_PLATEAU(800),
	LONGUEUR_CASE(32),
	LARGEUR_CASE(32);
	
	
	private int valeur;
	
	Plateau(int valeur){
		this.valeur = valeur;
	}
	
	public int getValeur(){
		return this.valeur;
	}
	
}
