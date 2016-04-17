package com.fr.ece.jbomb.view;

import static com.fr.ece.jbomb.model.Plateau.LARGEUR_PLATEAU;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.fr.ece.jbomb.model.ConfFromServer;
import com.fr.ece.jbomb.model.ConfToServer;
import com.fr.ece.jbomb.model.Plateau;
import com.fr.ece.jbomb.model.Player;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe de la représentation du GUI 
 * @author Vignesh BALA
 * @version 1.0
 **/
public class GUI extends AbstractView {
	private GraphicsContext gc1;
	private GraphicsContext gc2;
	private Canvas canvas;
	private Canvas canvas2;
	private ConfFromServer configServeur;
	private ConfToServer configClient;
	public static boolean endOfGame=false;
	/**
	 * Retourne l'identifiant de l'image du joueur en fonction de la direction 
	 * @param direction Direction du joueur
	 * @return id Identifiant du joueur
	 **/
	public int getIdFromDirection(String direction) {
		if (direction.contains("RIGHT")) {
			return 3;
		} else if (direction.contains("LEFT")) {
			return 2;
		} else if (direction.contains("UP")) {
			return 4;
		}
		return 1;
	}

	
	/**
	 * Place uniquement les joueurs sur le plateau 
	 * @param plateau Plateau de jeu
	 * @param listPlayer Liste des joueurs
	 **/
	private void drawPlayerPlateau(Plateau[][] plateau, List<Player> listPlayer){
		StringBuilder image_joueur1 = new StringBuilder("com/fr/ece/jbomb/view/Avatar/P1-"); // com/fr/ece/jbomb/view/Avatar/P1-1.png
		StringBuilder image_joueur2 = new StringBuilder("com/fr/ece/jbomb/view/Avatar/P2-");
		StringBuilder image_joueur3 = new StringBuilder("com/fr/ece/jbomb/view/Avatar/P3-");
		StringBuilder image_joueur4 = new StringBuilder("com/fr/ece/jbomb/view/Avatar/P4-");
		
		for (Player player : listPlayer) {

			switch(player.getID()){
					case 1:
						image_joueur1.append(""+getIdFromDirection(player.getDirectionPourSavoirQuelleImageAfficher())+".png");
						gc2.drawImage(new Image(image_joueur1.toString()), player.getPositionX(), player.getPositionY());
						break;
					case 2:
						image_joueur2.append(""+getIdFromDirection(player.getDirectionPourSavoirQuelleImageAfficher())+".png");
						gc2.drawImage(new Image(image_joueur2.toString()), player.getPositionX(), player.getPositionY());
						break;
					case 3:
						image_joueur3.append(""+getIdFromDirection(player.getDirectionPourSavoirQuelleImageAfficher())+".png");
						gc2.drawImage(new Image(image_joueur3.toString()), player.getPositionX(), player.getPositionY());
						break;
					case 4:
						image_joueur4.append(""+getIdFromDirection(player.getDirectionPourSavoirQuelleImageAfficher())+".png");
						gc2.drawImage(new Image(image_joueur4.toString()), player.getPositionX(), player.getPositionY());
						break;
				
					}
		}
	}
	
	
	/**
	 * Place uniquement le décor(mur,pilier,bombe,feu) sur le plateau 
	 * @param plateau Plateau de jeu	 *
	 **/
	private void drawDecorPlateau(Plateau[][] plateau){
		
		/* Initialisation des images */
		// Pilier
		Image pilier = new Image("com/fr/ece/jbomb/view/Decors/pilier_end_v3.png");
		// Mur
		Image mur = new Image("com/fr/ece/jbomb/view/Decors/mur_end_v5.png");
			
		// Bombe
		Image bombe = new Image("com/fr/ece/jbomb/view/Avatar/bomb.png");
		
		// Crame
		Image crame = new Image("com/fr/ece/jbomb/view/Avatar/Fire.gif");
				
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 23; j++) {
				switch (plateau[i][j]) {
					case PILIER:
						gc1.drawImage(pilier, (j + 1) * 32, (i + 1) * 32);
						break;
					
					case MUR:
						gc1.drawImage(mur, (j + 1) * 32, (i + 1) * 32);
						break;
					case BOMBE:
						gc1.drawImage(bombe, (j + 1) * 32, (i + 1) * 32);
						break;
					case CRAME:
						gc1.drawImage(crame, (j+1)*32, (i+1)*32);
						break;
					default:
						break;
				}
			}
		}
	}

	/**
	 * Coordonne les étapes de construction du plateau de jeu 
	 * @param plateau Plateau de jeu
	 * @param listPlayer Liste des joueurs
	 **/
	private void initPlateauSprite(Plateau[][] plateau, List<Player> listPlayer) {
		
		/* Initialisation des canvas */
		gc1 = canvas2.getGraphicsContext2D();
		gc2 = canvas2.getGraphicsContext2D();

		gc1.clearRect(0, 0, LARGEUR_PLATEAU.getValeur(), Plateau.LONGUEUR_PLATEAU.getValeur());
		gc2.clearRect(0, 0, LARGEUR_PLATEAU.getValeur(), Plateau.LONGUEUR_PLATEAU.getValeur());

		// le canvas où les frontières ont été dessinées
		initFrontierePlateauSprite();

		// Dessiner les décors sur le plateau
		drawDecorPlateau(plateau);
		
		// Dessiner les joueurs sur le plateau
		drawPlayerPlateau(plateau,listPlayer);
		
}
	
	/**
	 * Place les barrières et le sol sur le plateau 
	 * 
	 **/
	private void initFrontierePlateauSprite() {

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

	/**
	 * Anime le jeu côté client et rafraichit l'interface graphique tous les 60 fois par seconde
	 * @param key Touches appuyées par le joueur	 * 
	 **/
	private void loop(KeyEventHandler key) {

		new AnimationTimer() {

			public void handle(long currentNanoTime) {
			
				try {	
					int t=Calendar.getInstance().get(Calendar.MILLISECOND);
				if(GUI.endOfGame) {
					getController().getClient().close(); 
					System.exit(1);
				}
			
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
	 * Met à jour l'interface graphique à partir des données contenues dans l'objet configServeur
	 * @param canvas Premier Canvas du GUI contenant les objets décors
	 * @param canvas2 Deuxième Canvas du  GUI contenant les objets player
	 * @param configServeur Elements de configuration du serveur
	 **/
	private void updateFromserver(Canvas canvas, Canvas canvas2, ConfFromServer configServeur) {
		initPlateauSprite(configServeur.getPlateau(), configServeur.getListPlayer());
	}
	
	/**
	 * Démarre le GUI
	 * @param canvas Premier Canvas du GUI contenant les objets décors
	 * @param canvas2 Deuxième Canvas du  GUI contenant les objets player
	 * @param key Touches appuyées par le joueur
	 **/
	public void start(Canvas canvas, Canvas canvas2, KeyEventHandler key) {
	
		/* Initialisation des canvas */
		this.canvas=canvas;
		this.canvas2=canvas2;
		
		/* Initialisation du graphical Component */
		gc1 = canvas2.getGraphicsContext2D();
		gc2 = canvas2.getGraphicsContext2D();
		
		// Comme les frontières seront toujours fixes, on ne touchera jamais
		// HORS DE LA BOUCLE on bind la config client avec le KeyEventHandler
		// qui est liée à la scene
		configClient = new ConfToServer(getController().getPlayer().getID(), key);

		loop(key);
	}
}
