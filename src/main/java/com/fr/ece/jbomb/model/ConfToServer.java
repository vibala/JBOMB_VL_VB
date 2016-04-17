package com.fr.ece.jbomb.model;


import java.io.Serializable;
import java.util.List;

import com.fr.ece.jbomb.view.KeyEventHandler;

/**
 * Classe pour stocker les informations (config plateau + liste des joueurs) à transmettre au serveur
 * @author Vignesh BALA && Vincent LIM
 * @version 1.0
 **/
public class ConfToServer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private final transient KeyEventHandler keyHandler;
	int idPlayer;
	private List<String> keyList;
	
	/**
	 * Constructeur
	 * @param idPlayer identifiant du joueur
	 * @param keyHandler Objet de gestion des touches saisies
	 **/
	public ConfToServer(int idPlayer , KeyEventHandler keyHandler){
		this.keyHandler=keyHandler;
		this.idPlayer=idPlayer;
	}
	
	/**
	 * Met à jour la liste des touches saisies
	 **/
	public void update(){
		keyList=keyHandler.getInputList();
	}
	
	/**
	 * Retourne la liste des touches saisies
	 * @return  keyList liste des touches saisies
	 **/
	public List<String> getKeyList() {
		return keyList;
	}

}
