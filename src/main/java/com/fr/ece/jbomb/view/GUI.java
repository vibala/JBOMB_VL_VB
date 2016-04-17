package com.fr.ece.jbomb.view;

import static com.fr.ece.jbomb.model.Plateau.LARGEUR_PLATEAU;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.fr.ece.jbomb.model.ConfFromServer;
import com.fr.ece.jbomb.model.ConfToServer;
import com.fr.ece.jbomb.model.Decor;
import com.fr.ece.jbomb.model.Plateau;
import com.fr.ece.jbomb.model.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GUI extends AbstractView {
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private GraphicsContext gc1;
	private GraphicsContext gc2;

	private Canvas canvas;
	private Canvas canvas2;

	private ConfFromServer configServeur;
	private ConfToServer configClient;

	public int getIdFromDirection(String direction) {
		if (direction == "RIGHT") {
			return 3;
		} else if (direction == "LEFT") {
			return 2;
		} else if (direction == "UP") {
			return 4;
		}
		return 1;
	}

	
	
	private void drawPlayerPlateau(Plateau[][] plateau, List<Player> listPlayer){
		StringBuilder image_joueur1 = new StringBuilder("com/fr/ece/jbomb/view/Avatar/P1-"); // com/fr/ece/jbomb/view/Avatar/P1-1.png
		StringBuilder image_joueur2 = new StringBuilder("com/fr/ece/jbomb/view/Avatar/P2-");
		StringBuilder image_joueur3 = new StringBuilder("com/fr/ece/jbomb/view/Avatar/P3-");
		StringBuilder image_joueur4 = new StringBuilder("com/fr/ece/jbomb/view/Avatar/P4-");
		
		for (Player player : listPlayer) {

			switch(player.getID()){
					case 1:
						player1 = new Player(1, player.getPositionX(),player.getPositionY(), 32,32);
						image_joueur1.append(""+getIdFromDirection(player1.getDirectionPourSavoirQuelleImageAfficher())+".png");
						player1.setImage(new Image(image_joueur1.toString()));
						gc2.drawImage(player1.getImage(), player1.getPositionX(), player1.getPositionY());
						break;
					case 2:
						player2 = new Player(2, player.getPositionX(),player.getPositionY(), 32,32);
						image_joueur2.append(""+getIdFromDirection(player2.getDirectionPourSavoirQuelleImageAfficher())+".png");
						player2.setImage(new Image(image_joueur2.toString()));
						gc2.drawImage(player2.getImage(), player2.getPositionX(), player2.getPositionY());
						break;
					case 3:
						player3 = new Player(3, player.getPositionX(),player.getPositionY(), 32,32);
						image_joueur3.append(""+getIdFromDirection(player3.getDirectionPourSavoirQuelleImageAfficher())+".png");
						player3.setImage(new Image(image_joueur3.toString()));
						gc2.drawImage(player3.getImage(), player3.getPositionX(), player3.getPositionY());
						break;
					case 4:
						player4 = new Player(4,  player.getPositionX(),player.getPositionY(), 32,32);
						image_joueur4.append(""+getIdFromDirection(player4.getDirectionPourSavoirQuelleImageAfficher())+".png");
						player4.setImage(new Image(image_joueur4.toString()));
						gc2.drawImage(player4.getImage(), player4.getPositionX(), player4.getPositionY());
						break;
				
					}
		}
	}

	private void drawDecorPlateau(Plateau[][] plateau){
		
		/* Initialisation des images */
		// Pilier
		Image pilier = new Image("com/fr/ece/jbomb/view/Decors/pilier_end_v3.png");
		// Mur
		Image mur = new Image("com/fr/ece/jbomb/view/Decors/mur_end_v5.png");
			
		// Instanciation d'un objet decor, des objets player
		Decor decor;
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 23; j++) {
				switch (plateau[i][j]) {
					case PILIER:
						decor = new Decor();
						decor.setImage(pilier);
						decor.setPositionX((j + 1) * 32);
						decor.setPositionY((i + 1) * 32);
						decor.setWidth(32);
						decor.setHeight(32);
						gc1.drawImage(decor.getImage(), decor.getPositionX(), decor.getPositionY());
						
						break;
					
					case MUR:
						decor = new Decor();
						decor.setImage(mur);
						decor.setPositionX((j + 1) * 32);
						decor.setPositionY((i + 1) * 32);
						decor.setWidth(32);
						decor.setHeight(32);
						gc1.drawImage(decor.getImage(), decor.getPositionX(), decor.getPositionY());
						break;
					
					default:
						break;
				}
			}
		}
	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!A MODIFIER POUR prendre
	// en compte la listePlayer !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private void initPlateauSprite(Plateau[][] plateau, List<Player> listPlayer) {
		System.out.println("initPlateauSprite : debut");

		/* Initialisation des canvas */
		gc1 = canvas2.getGraphicsContext2D();
		gc2 = canvas2.getGraphicsContext2D();

		gc1.clearRect(0, 0, LARGEUR_PLATEAU.getValeur(), Plateau.LONGUEUR_PLATEAU.getValeur());
		gc2.clearRect(0, 0, LARGEUR_PLATEAU.getValeur(), Plateau.LONGUEUR_PLATEAU.getValeur());

		// le canvas où les frontières ont été dessinées
		initFrontierePlateauSprite();

		// Dessiner les joueurs sur le plateau
		drawPlayerPlateau(plateau,listPlayer);
		
		// Dessiner les décors sur le plateau
		drawDecorPlateau(plateau);
		
		System.out.println("initPlateauSprite : fin");
	}
	
	private void initFrontierePlateauSprite() {

		//System.out.println("initFrontierePlateauSprite");
		/**
		 * TODO : Check the font text
		 */
		Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
		gc1.setFont(theFont);
		gc1.setFill(Color.GREEN);
		gc1.setStroke(Color.BLACK);
		gc1.setLineWidth(1);

		// Barrière
		final Image img = new Image("com/fr/ece/jbomb/view/Decors/fence_end.png");

		// Remplissage de la barrière en largeur
		for (int i = 0; i < 25; i++) {
			gc1.drawImage(img, 32 * i, 0);
			gc1.drawImage(img, 32 * i, 576);
		}

		// Remplissage de la barrière en longueur
		for (int i = 0; i < 19; i++) {
			gc1.drawImage(img, 0, 32 * i);
			gc1.drawImage(img, 768, 32 * i);

		}

		// Sols
		Image sol = new Image("com/fr/ece/jbomb/view/Decors/sol_end.png");
		for (int i = 1; i < 18; i++) {
			for (int j = 1; j < 24; j++) {
				gc1.drawImage(sol, j * 32, i * 32);
			}
		}
	}

	private void loop(KeyEventHandler kev) {

		new AnimationTimer() {

			public void handle(long currentNanoTime) {
			
				int t=Calendar.getInstance().get(Calendar.MILLISECOND);
			
				// Dans la boucle
				try {
					// On met a jour la config avec les touches saisies par
					// l'utilisateur
					configClient.update();
					// On envoit la config au serveur
					getController().writeConf(configClient);

					// Réccupération de la ConfigGraphique du serveur
					configServeur = (ConfFromServer) getController().readConf();
					
					// -----------------------UPDATE // GRAPHIQUE à partir de la
					// configuration du serveur
					updateFromserver(canvas, canvas2, configServeur);
					System.out.println(Calendar.getInstance().get(Calendar.MILLISECOND)-t);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}.start();

	}

	/**
	 * TODO : Récupérer les coordonnées des décors cassés par les autres puis
	 * les effacer du décor TODO : Récupérer les coordonnées des joueurs puis
	 * les actualiser
	 */
	private void updateFromserver(Canvas canvas, Canvas canvas2, ConfFromServer configServeur) {
		initPlateauSprite(configServeur.getPlateau(), configServeur.getListPlayer());
	}

	public void start(Canvas canvas, Canvas canvas2, KeyEventHandler kev) {

		this.canvas=canvas;
		this.canvas2=canvas2;
		/* Initialisation des canvas */
		gc1 = canvas2.getGraphicsContext2D();
		gc2 = canvas2.getGraphicsContext2D();
		// Comme les frontières seront toujours fixes, on ne touchera jamais
		// HORS DE LA BOUCLE on bind la config client avec le KeyEventHandler
		// qui est liée à la scene
		configClient = new ConfToServer(getController().getPlayer().getID(), kev);

		loop(kev);
	}
}
