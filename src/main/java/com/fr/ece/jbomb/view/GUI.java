package com.fr.ece.jbomb.view;

import static com.fr.ece.jbomb.model.Plateau.LARGEUR_CASE;
import static com.fr.ece.jbomb.model.Plateau.LONGUEUR_CASE;
import static com.fr.ece.jbomb.model.Plateau.PLAYER1;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fr.ece.jbomb.model.Decor;
import com.fr.ece.jbomb.model.Plateau;
import com.fr.ece.jbomb.model.Player;
import com.fr.ece.jbomb.model.Sprite;

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
	private Sprite[][] plateau_sprites;

	public GUI() {
		plateau_sprites = new Sprite[17][23];
	}

	private void initPlateauSprite(Canvas canvas, Canvas canvas2, Plateau[][] plateau) {
		System.out.println("initPlateauSprite : debut");
		/* Initialisation des canvas */
		gc1 = canvas.getGraphicsContext2D();
		gc2 = canvas2.getGraphicsContext2D();

		/* Initialisation des images */
		// Pilier
		Image pilier = new Image("com/fr/ece/jbomb/view/Decors/pilier_end_v3.png");
		// Mur
		Image mur = new Image("com/fr/ece/jbomb/view/Decors/mur_end_v5.png");
		// Player 1
		Image image_joueur1 = new Image("com/fr/ece/jbomb/view/Avatar/P1-1.png");
		// Player 2
		Image image_joueur2 = new Image("com/fr/ece/jbomb/view/Avatar/P2-1.png");
		// Player 3
		Image image_joueur3 = new Image("com/fr/ece/jbomb/view/Avatar/P4-4.png");
		// Player 4
		Image image_joueur4 = new Image("com/fr/ece/jbomb/view/Avatar/P3-4.png");

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
					decor.setWidth(24);
					decor.setHeight(32);
					plateau_sprites[i][j] = decor;
					gc1.drawImage(decor.getImage(), decor.getPositionX(), decor.getPositionY());
				case SOL:
					break;
				case MUR:
					decor = new Decor();
					decor.setImage(mur);
					plateau_sprites[i][j] = decor;
					decor.setPositionX((j + 1) * 32);
					decor.setPositionY((i + 1) * 32);
					decor.setWidth(32);
					decor.setHeight(32);
					gc1.drawImage(decor.getImage(), decor.getPositionX(), decor.getPositionY());
					break;
				case PLAYER1:
					player1 = new Player((j + 1) * 32, (i + 1) * 32, 32, 32);
					player1.setPositionX((j + 1) * 32);
					player1.setPositionY((i + 1) * 32);
					player1.setImage(image_joueur1);
					plateau_sprites[i][j] = player1;
					gc2.drawImage(player1.getImage(), player1.getPositionX(), player1.getPositionY());
					break;
				case PLAYER2:
					player2 = new Player((j + 1) * 32, (i + 1) * 32, 32, 32);
					player2.setImage(image_joueur2);
					plateau_sprites[i][j] = player2;
					gc2.drawImage(player2.getImage(), player2.getPositionX(), player2.getPositionY());
					break;
				case PLAYER3:
					player3 = new Player((j + 1) * 32, (i + 1) * 32, 32, 32);
					player3.setImage(image_joueur3);
					plateau_sprites[i][j] = player3;
					gc2.drawImage(player3.getImage(), player3.getPositionX(), player3.getPositionY());
					break;
				case PLAYER4:
					player4 = new Player((j + 1) * 32, (i + 1) * 32, 32, 32);
					player4.setImage(image_joueur4);
					plateau_sprites[i][j] = player4;
					gc2.drawImage(player4.getImage(), player4.getPositionX(), player4.getPositionY());
					break;
				default:
					break;
				}
			}
		}
		System.out.println("initPlateauSprite : fin");
	}

	private void initFrontierePlateauSprite(Canvas canvas, Canvas canvas2) {

		System.out.println("initFrontierePlateauSprite");
		/* Initialisation des canvas */
		gc1 = canvas.getGraphicsContext2D();
		gc2 = canvas2.getGraphicsContext2D();

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
		final KeyEventHandler keyHandler = kev;
		new AnimationTimer() {

			public void handle(long currentNanoTime) {

				update();

				if (keyHandler.getInputList().contains("RIGHT")) {
					System.out.println("RIGHT");
					if(!getController().onPressedRight(player1)){
						gc2.clearRect(player1.getPositionX(), player1.getPositionY(),LONGUEUR_CASE.getValeur() ,LARGEUR_CASE.getValeur());
						player1.move(1, 0);
						player1.setImage(new Image("com/fr/ece/jbomb/view/Avatar/P1-3.png"));
						player1.render(gc2);
					}					

				}

				if (keyHandler.getInputList().contains("LEFT")) {
					System.out.println("LEFT");
					
					if(!getController().onPressedLeft(player1)){
						gc2.clearRect(player1.getPositionX(), player1.getPositionY(), LONGUEUR_CASE.getValeur(),LARGEUR_CASE.getValeur());
						player1.move(-1,0);
						player1.setImage(new Image("com/fr/ece/jbomb/view/Avatar/P1-2.png"));
						player1.render(gc2);
					}
					
					
				}
				if (keyHandler.getInputList().contains("UP")) {
					System.out.println("UP");					
					if(!getController().onPressedUp(player1)){
						gc2.clearRect(player1.getPositionX(), player1.getPositionY()+1, LONGUEUR_CASE.getValeur(),LARGEUR_CASE.getValeur());
						// ecrasé en dessinant herbe sur perso
						player1.move(0, -1);
						// dessiner sprite sur novezlle pos en dessinant herbe sur perso
						player1.setImage(new Image("com/fr/ece/jbomb/view/Avatar/P1-4.png"));
						player1.render(gc2);
					}
				}
				
				if (keyHandler.getInputList().contains("DOWN")) {
					System.out.println("DOWN");
					
					if(!getController().onPressedDown(player1)){
						gc2.clearRect(player1.getPositionX(), player1.getPositionY()-1, LONGUEUR_CASE.getValeur(),LARGEUR_CASE.getValeur());
						player1.move(0, 1);
						player1.setImage(new Image("com/fr/ece/jbomb/view/Avatar/P1-1.png"));
						player1.render(gc2);
					}
					
					
				}

			}

		}.start();

	}

	/**
	 * TODO : Récupérer les coordonnées des décors cassés par les autres puis
	 * les effacer du décor TODO : Récupérer les coordonnées des joueurs puis
	 * les actualiser
	 */
	private void update() {

	}

	public void start(Canvas canvas, Canvas canvas2, Plateau[][] tab_plateau, KeyEventHandler kev) {
		initFrontierePlateauSprite(canvas, canvas2);
		initPlateauSprite(canvas, canvas2, tab_plateau);
		loop(kev);
	}
}
