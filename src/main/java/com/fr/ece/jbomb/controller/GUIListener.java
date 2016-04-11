package com.fr.ece.jbomb.controller;

import java.io.IOException;

import com.fr.ece.jbomb.model.ConfFromServer;
import com.fr.ece.jbomb.model.ConfToServer;
import com.fr.ece.jbomb.model.Player;

public interface GUIListener {
	
	public ConfFromServer readConf() throws ClassNotFoundException, IOException;
	public ConfToServer writeConf(ConfToServer conf)throws IOException;
	public Player getPlayer();
}
