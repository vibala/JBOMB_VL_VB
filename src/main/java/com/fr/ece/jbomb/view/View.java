package com.fr.ece.jbomb.view;

import com.fr.ece.jbomb.controller.GUIListener;
import com.fr.ece.jbomb.model.Plateau;

import javafx.scene.canvas.Canvas;

public interface View {
	void setController(GUIListener controller);
	public void start(Canvas canvas, Canvas canvas2,Plateau[][] tab_plateau,KeyEventHandler kev);
}
