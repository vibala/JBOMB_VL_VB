package com.fr.ece.jbomb.view;

import com.fr.ece.jbomb.controller.Controller;
import com.fr.ece.jbomb.controller.GUIListener;

public abstract class AbstractView implements View {
	public Controller controller;


	protected final GUIListener getController(){
		return controller;
	}
	
	public void setController(GUIListener ctrl) {
		// TODO Auto-generated method stub
		this.controller=(Controller) ctrl;
	}
	

}
