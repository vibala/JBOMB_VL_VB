package com.fr.ece.jbomb.controller;

import static com.fr.ece.jbomb.view.Direction.EST;
import static com.fr.ece.jbomb.view.Direction.OUEST;
import static com.fr.ece.jbomb.view.Direction.NORD;
import static com.fr.ece.jbomb.view.Direction.SUD;
import java.awt.Point;
import java.util.List;
import java.util.Map;

import com.fr.ece.jbomb.model.Decor;
import com.fr.ece.jbomb.model.Player;
import com.fr.ece.jbomb.model.Sprite;
import com.fr.ece.jbomb.view.Direction;
import com.fr.ece.jbomb.view.GUI;
import com.fr.ece.jbomb.view.KeyEventHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller implements GUIListener {


    private GUI gui;
    private Canvas canvas;
    private Canvas canvas2;
    private KeyEventHandler kev;
    
    public void setInit(GUI gui,Canvas canvas, Canvas canvas2, KeyEventHandler kev){
    	this.gui=gui;
    	this.canvas=canvas;
    	this.canvas2=canvas2;
    	this.kev=kev;
    }
  

    @FXML
    private void initialize(){}
  

    @FXML
    void onSubmitClick(ActionEvent event) {}
 

	public boolean onPressedRight(Player player1,Map<Point,Decor> murs_sprites,List<Decor> pilier_sprites) {
	  	
		Sprite tmp;
      	tmp = new Player(player1.getPositionX()+1,player1.getPositionY(),player1.getHeight(), player1.getWidth());

      	if(!check_wall_pilars_collision(EST,tmp,murs_sprites,pilier_sprites)){
      		System.out.println(player1.getPositionX()+" "+player1.getPositionY());
      		player1.move(1,0);
      		return false;
      	}
      	return true;
	}
	
	public boolean onPressedLeft(Player player1,Map<Point,Decor> murs_sprites,List<Decor> pilier_sprites) {
	  	
		Sprite tmp;
      	tmp = new Player(player1.getPositionX()-1,player1.getPositionY(),player1.getHeight(), player1.getWidth());

      	if(!check_wall_pilars_collision(OUEST,tmp,murs_sprites,pilier_sprites)){
      		System.out.println(player1.getPositionX()+" "+player1.getPositionY());
      		player1.move(-1,0);
      		return false;
      	}
      	return true;
	}
	
	
	public boolean onPressedUp(Player player1,Map<Point,Decor> murs_sprites,List<Decor> pilier_sprites) {
	  	
		Sprite tmp;
      	tmp = new Player(player1.getPositionX(),player1.getPositionY()-1,player1.getHeight(), player1.getWidth());

      	if(!check_wall_pilars_collision(NORD,tmp,murs_sprites,pilier_sprites)){
      		System.out.println(player1.getPositionX()+" "+player1.getPositionY());
      		player1.move(0,-1);
      		return false;
      	}
      	return true;
	}
	
	
	public boolean onPressedDown(Player player1,Map<Point,Decor> murs_sprites,List<Decor> pilier_sprites) {
	  	
		Sprite tmp;
      	tmp = new Player(player1.getPositionX(),player1.getPositionY()+1,player1.getHeight(), player1.getWidth());

      	if(!check_wall_pilars_collision(SUD,tmp,murs_sprites,pilier_sprites)){
      		System.out.println(player1.getPositionX()+" "+player1.getPositionY());
      		player1.move(0,1);
      		return false;
      	}
      	return true;
	}
	public boolean check_wall_pilars_collision(Direction direction,Sprite playerfictif, Map<Point,Decor> murs, List<Decor> pilliers){
		
		for (Decor pillier : pilliers) {
			if(playerfictif.intersects(pillier)){
				// Collision 
				return true;
			}	
		}
		return false;
	}


	public void start() {
		gui.setController(this);
		gui.start(canvas,canvas2,kev);
		
	}	
}
