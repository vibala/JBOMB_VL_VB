package com.fr.ece.jbomb.view;

/**
 * Classe abstraite pour la partie GUI du projet
 * @author Vignesh BALA && Vincent LIM
 * @version 1.0 
 */

import com.fr.ece.jbomb.controller.Controller;
import com.fr.ece.jbomb.controller.GUIListener;

public abstract class AbstractView implements View {

	public Controller controller;

	/**
	 * Constructeur permettant de récupérer le controleur associé au GUI
	 * 
	 * @return controller
	 */
	protected final GUIListener getController() {
		return controller;
	}

	/**
	 * Constructeur permettant d'initialiser le controleur associé au GUI
	 */
	public void setController(GUIListener ctrl) {
		// TODO Auto-generated method stub
		this.controller = (Controller) ctrl;
	}

}
