package com.fr.ece.jbomb.view;

public enum Plateau {

	HEIGHT_PLATEAU(608),
	WIDTH_PLATEAU(800),
	HEIGHT_CASE(32),
	WIDTH_CASE(32);
	
	private int valeur;
	
	Plateau(int valeur){
		this.valeur = valeur;
	}
	
	public int getValeur(){
		return this.valeur;
	}
	
}
