package com.fr.ece.jbomb.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainGame extends Application {

	private Stage primaryStage;
	private AnchorPane layout;
	
	
	//  Classe interne
	public class Tuple{
		
		public double posx;
		public double posy;
		
		public Tuple(double posx, double posy){
			this.posx = posx;
			this.posy = posy;
		}
		
		@Override
		public boolean equals(Object o){
			 	if (o == null) return false;

	            if( ! (o instanceof Tuple) ) return false;

	            Tuple other = (Tuple) o;
	            
	            if(this.posx == other.posx && this.posy == other.posy)  return true; 

	            if( this == other) return true;
	            
	            return false;
		}
		
		// http://www.infoq.com/fr/articles/retour-sur-les-bases-equals-et-hashcode
		@Override
		public int hashCode(){
			return (int) (this.posx + this.posy);
		}
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Client Window");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("JbombApp.fxml"));
		try {
			// Chargement du layout
			layout = loader.load();
			
			// Definit le group comme il se doit
			Group group = new Group(layout);
			
			// Construction du canvas
			Canvas canvas = new Canvas(800, 608);
			
			//  Ajoute le canevas dans le layout
			layout.getChildren().add(canvas);
			
			// Utilisation du graphicContext
			GraphicsContext gc = canvas.getGraphicsContext2D();
	        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
	        gc.setFont( theFont );
	        gc.setFill( Color.GREEN );
	        gc.setStroke( Color.BLACK );
	        gc.setLineWidth(1);
			
	        
	        String pointsText = "Cash: $" + (100 * 10);
            gc.fillText( pointsText, 360, 36 );
            gc.strokeText( pointsText, 360, 36);
            
            
            // My Example
            Image img = new Image("com/fr/ece/jbomb/view/fence_end.png");
            
            // Remplissage de la barrière en largeur
            for (int i = 0; i < 25; i++) {
            	gc.drawImage(img, 32 * i, 0);
            	gc.drawImage(img, 32 * i, 576);
			}
            
            // Remplissage de la barrière en longueur
            for (int i = 0; i < 19; i++) {
            	gc.drawImage(img, 0, 32*i);
         		gc.drawImage(img, 768, 32*i);
			}
            
            // Sols
            Image sol = new Image("com/fr/ece/jbomb/view/sol_end.png");
            for(int i = 1; i < 18; i++ ){
            	for(int j = 1; j < 24; j++){
            		gc.drawImage(sol, j*32, i*32);
            	}
            }
            
            
            
            // Piliers sous fomes d'arbres
            ArrayList<Sprite> pilier_sprites = new ArrayList<Sprite>(); 
            
            Image pilier = new Image("com/fr/ece/jbomb/view/pilier_end_v3.png");
            for(int i = 1; i < 9; i++ ){
            	for(int j = 1; j < 12; j++){
            		Sprite sprite = new Sprite();
            		sprite.setPositionX(64*j);
            		sprite.setPositionY(64*i);
            		sprite.setImage(pilier);
            		sprite.render(gc);
            		pilier_sprites.add(sprite);
            		
            	}
            }
            
            // Murs sous fomes de clôture
            Map<Tuple,Sprite> murs_sprites = new HashMap<Tuple,Sprite>(); 
            
            // Définition de l'image représentant le mur
            Image mur =  new Image("com/fr/ece/jbomb/view/mur_end_v5.png");
                        
            // Sprite mur
            Sprite sprite_mur;
            
            // Random
            Random rand = new Random();
            
            // ArrayList contient les valeurs tirées au hasard pour éviter qu'on retombe sur les mêmes valeurs
            List<Integer> random_numbers = new ArrayList<Integer>();
            
            // Condition de sortie
            boolean cds = false;
            
            // Couloir nord et sud: 8 murs au min et 17 murs au max           
            int nb_murs_nord = rand.nextInt(17-8) + 8; // On récupère le nombre de murs à planter sur le plateau
            for(int i = 0; i <= nb_murs_nord; i++){
            	sprite_mur = new Sprite();            	
                cds = false;     	
            	while(!cds){
            		int nb_random =  rand.nextInt(17);
            		if(!random_numbers.contains(nb_random)){
            			random_numbers.add(nb_random);
            			double posx = 128 + nb_random * 32;
            			sprite_mur.setPositionX(posx);
            			sprite_mur.setPositionY(32);
            			sprite_mur.setImage(mur);
            			sprite_mur.render(gc);
            			Tuple t = new Tuple(posx,32);
            			murs_sprites.put(t,sprite_mur);       
            			cds = true;
            		}
            	}
            	
            }
           
            //Vidage de random_numbers
            random_numbers.clear();
            
            // Couloir sud
            int nb_murs_sud = rand.nextInt(17-8) + 8;
            for(int i = 0; i <= nb_murs_sud; i++){
            	sprite_mur = new Sprite();            	
                cds = false;     	
            	while(!cds){
            		int nb_random =  rand.nextInt(17);
            		if(!random_numbers.contains(nb_random)){
            			random_numbers.add(nb_random);
            			double posx = 128 + nb_random * 32;
            			sprite_mur.setPositionX(posx);
            			sprite_mur.setPositionY(544);
            			sprite_mur.setImage(mur);
            			sprite_mur.render(gc);
            			Tuple t = new Tuple(posx,544);
            			murs_sprites.put(t,sprite_mur);
            			cds = true;
            		}
            	}
            }
            
            //Vidage de random_numbers
            random_numbers.clear();
            
            // Couloir ouest
            int nb_murs_ouest = rand.nextInt(13-8) + 8;
            for(int i = 0; i <= nb_murs_ouest; i++){
            	sprite_mur = new Sprite();            	
                cds = false;     	
            	while(!cds){
            		int nb_random =  rand.nextInt(13);
            		if(!random_numbers.contains(nb_random)){
            			random_numbers.add(nb_random);
            			double posy = 96 + nb_random * 32;
            			sprite_mur.setPositionX(32);
            			sprite_mur.setPositionY(posy);
            			sprite_mur.setImage(mur);
            			sprite_mur.render(gc);
            			Tuple t = new Tuple(32,posy);
            			murs_sprites.put(t,sprite_mur);      
            			cds = true;
            		}
            	}
            }
            
            //Vidage de random_numbers
            random_numbers.clear();
            
            // Couloir ouest
            int nb_murs_est = rand.nextInt(13-8) + 8;
            for(int i = 0; i <= nb_murs_est; i++){
            	sprite_mur = new Sprite();            	
                cds = false;     	
            	while(!cds){
            		int nb_random =  rand.nextInt(13);
            		if(!random_numbers.contains(nb_random)){
            			random_numbers.add(nb_random);
            			double posy = 96 + nb_random * 32;
            			sprite_mur.setPositionX(736);
            			sprite_mur.setPositionY(posy);
            			sprite_mur.setImage(mur);
            			sprite_mur.render(gc);
            			Tuple t = new Tuple(736,posy);
            			murs_sprites.put(t,sprite_mur);        
            			cds = true;
            		}
            	}
            }
            
            Scene scene = new Scene(group);			
			this.primaryStage.setScene(scene);			
			this.primaryStage.show();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getPrimaryStage(){
		return this.primaryStage;
	}
}
