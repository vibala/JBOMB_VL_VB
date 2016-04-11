package com.fr.ece.jbomb.model;


import java.io.Serializable;
import java.util.List;

import com.fr.ece.jbomb.view.KeyEventHandler;

public class ConfToServer implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final transient KeyEventHandler keyHandler;
	int idPlayer;
	
	 private List<String> keyList;
	//AUTRE ?
	
	 //------------------------------------Fonction appelé chez le client
	 
	 
	public ConfToServer(int idPlayer , KeyEventHandler keyHandler){
		this.keyHandler=keyHandler;
		this.idPlayer=idPlayer;
	}
	
	public void update(){
		keyList=keyHandler.getInputList();
	}
	
	//------------------------------------Fonction appelé chez le Server
	
	
	public List<String> getKeyList() {
		return keyList;
	}

}
