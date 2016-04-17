package com.fr.ece.jbomb.view;

import com.fr.ece.jbomb.controller.GUIListener;

import javafx.scene.canvas.Canvas;

/**
 * Classe de la représentation du GUI 
 * @author Vignesh BALA && Vincent LIM
 * @version 1.0
 **/

public interface View {
	/**
	 * Associe le controleur au GUI
	 * @param controller Controleur
	 */
	void setController(GUIListener controller);
	/**
	 * Démarre le GUI
	 * @param canvas Premier Canvas du GUI contenant les objets décors
	 * @param canvas2 Deuxième Canvas du  GUI contenant les objets player
	 * @param key Touches appuyées par le joueur
	 **/
	public void start(Canvas canvas, Canvas canvas2,KeyEventHandler kev);
}
