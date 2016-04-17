package com.fr.ece.jbomb.view;

import java.util.ArrayList;

/**
 * Classe permettant de gérer les touches de clavier saisies
 * @author Vignesh BALA && Vincent LIM
 * @version 1.0
 **/

public class KeyEventHandler {

	private ArrayList<String> input;

	/**
	 * Constructeur initialisant la liste des touches entrées	 *
	 **/
	public KeyEventHandler(){
		input = new ArrayList<String>();
	}
	
	/**
	 * Ajoute la touche saisie dans la liste des touches entrées
	 * @param code Code associé à la touche saisie	 * 
	 **/
	public void add(String code){
		if(code != null ) input.add(code);
	}
	
	/**
	 * Retire la touche saisie de la liste des touches entrées 
	 * @param code Code associé à la touche saisie	 * 
	 **/
	public void remove(String code){
		if(code != null ) input.remove(code);
	}
	
	/**
	 * Retourne la liste des touches entrées 
	 * @return input liste des touches saisies	 * 
	 **/
	public ArrayList<String> getInputList(){
		return this.input;
	}
}
