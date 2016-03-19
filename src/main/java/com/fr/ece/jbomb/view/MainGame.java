package com.fr.ece.jbomb.view;

import static com.fr.ece.jbomb.view.Direction.EST;
import static com.fr.ece.jbomb.view.Direction.NORD;
import static com.fr.ece.jbomb.view.Direction.OUEST;
import static com.fr.ece.jbomb.view.Direction.SUD;
import static com.fr.ece.jbomb.view.Plateau.HEIGHT_CASE;
import static com.fr.ece.jbomb.view.Plateau.WIDTH_CASE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainGame extends Application {

	private Stage primaryStage;
	private AnchorPane layout;
	private Image test;
	
	
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
			
			// Construction du canvas 1
			Canvas canvas = new Canvas(800, 608);
			
			// Construction du canvas 2
			Canvas canvas2 = new Canvas(800,608);
			
			//  Ajoute les canevas dans le layout
			layout.getChildren().add(canvas);
			layout.getChildren().add(canvas2);
			
			// Utilisation du graphicContext
			final GraphicsContext gc = canvas.getGraphicsContext2D();
			final GraphicsContext gc2 = canvas2.getGraphicsContext2D();
			
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
            final Image sol = new Image("com/fr/ece/jbomb/view/sol_end.png");
            for(int i = 1; i < 18; i++ ){
            	for(int j = 1; j < 24; j++){
            		gc.drawImage(sol, j*32, i*32);
            	}
            }
            
            
            
            // Piliers sous fomes d'arbres
            final List<Sprite> pilier_sprites = new ArrayList<Sprite>(); 
            
            Image pilier = new Image("com/fr/ece/jbomb/view/pilier_end_v3.png");
            for(int i = 1; i < 9; i++ ){
            	for(int j = 1; j < 12; j++){
            		Sprite sprite = new Sprite();
            		sprite.setPositionX(64*j);
            		sprite.setPositionY(64*i);
            		sprite.setWidthHeight(31, 31);
            		sprite.setImage(pilier);
            		sprite.render(gc);
            		pilier_sprites.add(sprite);
            		
            	}
            }
            
            // Murs sous fomes de clôture
            final Map<Tuple,Sprite> murs_sprites = new HashMap<Tuple,Sprite>(); 
            
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
            			sprite_mur.render(gc2);
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
            			sprite_mur.render(gc2);
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
            			sprite_mur.render(gc2);
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
            			sprite_mur.render(gc2);
            			Tuple t = new Tuple(736,posy);
            			murs_sprites.put(t,sprite_mur);        
            			cds = true;
            		}
            	}
            }
            
            // Positionnement des joueurs au départ
            Image image_joueur1 = new Image("com/fr/ece/jbomb/view/Avatar/P1-1.png");
            Image image_joueur2 = new Image("com/fr/ece/jbomb/view/Avatar/P2-1.png");
            Image image_joueur3 = new Image("com/fr/ece/jbomb/view/Avatar/P4-4.png");
            Image image_joueur4 = new Image("com/fr/ece/jbomb/view/Avatar/P3-4.png");
            
            final Sprite player1 = new Sprite();
            player1.setPositionX(32);
            player1.setPositionY(32);
            player1.setWidthHeight(18, 24);
            player1.setImage(image_joueur1);
            player1.render(gc2);
            
            Sprite player2 = new Sprite();
            player2.setPositionX(736);
            player2.setPositionY(32);
            player2.setImage(image_joueur2);
            player2.render(gc);
            
            Sprite player3 = new Sprite();
            player3.setPositionX(32);
            player3.setPositionY(536);
            player3.setImage(image_joueur3);
            player3.render(gc2);
            
            
            Sprite player4 = new Sprite();
            player4.setPositionX(736);
            player4.setPositionY(536);
            player4.setImage(image_joueur4);
            player4.render(gc2);
           
            Scene scene = new Scene(group);	
            
            // Gérer l'utilisation des flèches directionnelles
            final KeyEventHandler kev = new KeyEventHandler();
           // Type primitif : impossible de changer leur valeur int a = 0;
                        
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				public void handle(KeyEvent event) {
					// TODO Auto-generated method stub
					
					 String code = event.getCode().toString();
                     if ( !kev.getInputList().contains(code) )
                         kev.add( code );   	 
				}
            	
			});         
        
            scene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        kev.remove( code );
                    }
            });
            
            final LongValue lastNanoTime = new LongValue(System.nanoTime());
            
            // Game Loop : Changer le statut 60 fois par seconde
            new AnimationTimer() {
				
				@Override
				public void handle(long currentNanoTime) {
					
					// TODO Auto-generated method stub
					// calculer le temps écouté depuis la dernière maj.
	                double elapsedTime = (currentNanoTime - lastNanoTime.getValue()) / 1000000000.0;
	                lastNanoTime.setValue(currentNanoTime);
	                double posx = player1.getPositionX();
	                double posy = player1.getPositionY();
	                Sprite tmp;
	                
	                // game logic
	                player1.setVelocityX(0);
	                player1.setVelocityY(0);
	               
	                if(kev.getInputList().contains("RIGHT")){
	                	gc2.clearRect(player1.getPositionX(), player1.getPositionY(),HEIGHT_CASE.getValeur(), WIDTH_CASE.getValeur());
	                	posx += (75*elapsedTime);
	                	tmp = new Sprite();
	                	tmp.setPositionX(posx);
	                	tmp.setPositionY(posy);
	                	tmp.setWidthHeight(18,24);
	                	if(!check_wall_pilars_collision(EST,tmp,murs_sprites,pilier_sprites)){
	                		System.out.println("SUIS ESST");
	                	
	                		player1.addVelocity(75, 0);
	                	}
	                	player1.setImage(new Image("com/fr/ece/jbomb/view/Avatar/P1-3.png"));
	                }
	                
	                if(kev.getInputList().contains("LEFT")){
	                	gc2.clearRect(player1.getPositionX(), player1.getPositionY(), HEIGHT_CASE.getValeur(), WIDTH_CASE.getValeur());
	                	posx += (-75) *elapsedTime;
	                	tmp = new Sprite();
	                	tmp.setPositionX(posx);
	                	tmp.setPositionY(posy);
	                	tmp.setWidthHeight(18,24);
	                	if(!check_wall_pilars_collision(OUEST,tmp,murs_sprites,pilier_sprites)){
	                		System.out.println("SUIS OUESST");
	                		player1.addVelocity(-75, 0);
	                	}
	                	player1.setImage(new Image("com/fr/ece/jbomb/view/Avatar/P1-2.png"));
	                }
	                
	                if(kev.getInputList().contains("UP")){
	                	gc2.clearRect(player1.getPositionX(), player1.getPositionY(),HEIGHT_CASE.getValeur(), WIDTH_CASE.getValeur());
	                	posy += (-75*elapsedTime);
	                	tmp = new Sprite();
	                	tmp.setPositionX(posx);
	                	tmp.setPositionY(posy);
	                	tmp.setWidthHeight(18,24);
	                	if(!check_wall_pilars_collision(NORD,tmp,murs_sprites,pilier_sprites)){
	                		System.out.println("SUIS NORD");
	                		
		                	player1.addVelocity(0, -75);
	                	}
	                	player1.setImage(new Image("com/fr/ece/jbomb/view/Avatar/P1-4.png"));
	                }
	                
	                if(kev.getInputList().contains("DOWN")){
	                
	                	gc2.clearRect(player1.getPositionX(), player1.getPositionY(),HEIGHT_CASE.getValeur(), WIDTH_CASE.getValeur());
	                	posy += (75) * elapsedTime;
	                	tmp = new Sprite();
	                	tmp.setPositionX(posx);
	                	tmp.setPositionY(posy);
	                	tmp.setWidthHeight(18,24);
	                	if(!check_wall_pilars_collision(SUD,tmp,murs_sprites,pilier_sprites)){
	                		System.out.println("SUIS SUD");
	                		
		                	player1.addVelocity(0, 75);
	                		
	                	}
	                	player1.setImage(new Image("com/fr/ece/jbomb/view/Avatar/P1-1.png"));
	                }
	                
	                player1.update(elapsedTime);
	                player1.render(gc2);
	                
				}
			}.start();
            
            
			this.primaryStage.setScene(scene);			
			this.primaryStage.show();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public boolean check_wall_pilars_collision(Direction direction,Sprite player, Map<Tuple,Sprite> murs, List<Sprite> pilliers){
		
		for (Sprite pilier : pilliers) {
			if(player.intersects(pilier)){
				System.out.println("gg");
				return true;
			}
		}
		
		// Collision 
		return false;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getPrimaryStage(){
		return this.primaryStage;
	}
}
