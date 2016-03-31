package com.fr.ece.jbomb.view;

import static com.fr.ece.jbomb.view.Plateau.LONGUEUR_CASE;
import static com.fr.ece.jbomb.view.Plateau.LARGEUR_CASE;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.fr.ece.jbomb.model.Decor;
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
	private GraphicsContext gc;
	private GraphicsContext gc2;

	// Piliers sous fomes d'arbres
	final List<Decor> pilier_sprites;

	// Murs sous fomes de cloture
	final Map<Point, Decor> murs_sprites;

	public GUI() {
		pilier_sprites = new ArrayList<Decor>();
		murs_sprites = new HashMap<Point, Decor>();
	}

	private void initGraphic(Canvas canvas, Canvas canvas2) {
		// Utilisation du graphicContext
		gc = canvas.getGraphicsContext2D();
		gc2 = canvas2.getGraphicsContext2D();

		Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
		gc.setFont(theFont);
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);


		// My Example
		Image img = new Image("Decors/fence_end.png");

		// Remplissage de la barriÃ¨re en largeur
		for (int i = 0; i < 25; i++) {
			gc.drawImage(img, 32 * i, 0);
			gc.drawImage(img, 32 * i, 576);
		}

		// Remplissage de la barriÃ¨re en longueur
		for (int i = 0; i < 19; i++) {
			gc.drawImage(img, 0, 32 * i);
			gc.drawImage(img, 768, 32 * i);

		}

		// Sols
		final Image sol = new Image("Decors/sol_end.png");
		for (int i = 1; i < 18; i++) {
			for (int j = 1; j < 24; j++) {
				gc.drawImage(sol, j * 32, i * 32);
			}
		}

		Image pilier = new Image("Decors/pilier_end_v3.png");
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 12; j++) {
				Decor sprite = new Decor();
				sprite.setPositionX(64 * j);
				sprite.setPositionY(64 * i);
				sprite.setWidthHeight(31, 31);
				sprite.setImage(pilier);
				sprite.render(gc);
				pilier_sprites.add(sprite);

			}
		}

		// DÃ©finition de l'image reprÃ©sentant le mur
		Image mur = new Image("Decors/mur_end_v5.png");

		// Sprite mur
		Decor sprite_mur;

		// Random
		Random rand = new Random();

		// ArrayList contient les valeurs tirÃ©es au hasard pour Ã©viter qu'on
		// retombe sur les mÃªmes valeurs
		List<Integer> random_numbers = new ArrayList<Integer>();

		// Condition de sortie
		boolean cds = false;

		// Couloir nord et sud: 8 murs au min et 17 murs au max
		int nb_murs_nord = rand.nextInt(17 - 8) + 8; // On rÃ©cupÃ¨re le nombre
														// de murs Ã  planter
														// sur le plateau
		for (int i = 0; i <= nb_murs_nord; i++) {
			sprite_mur = new Decor();
			cds = false;
			while (!cds) {
				int nb_random = rand.nextInt(17);
				if (!random_numbers.contains(nb_random)) {
					random_numbers.add(nb_random);
					double posx = 128 + nb_random * 32;
					sprite_mur.setPositionX(posx);
					sprite_mur.setPositionY(32);
					sprite_mur.setImage(mur);
					sprite_mur.render(gc2);
					Point t = new Point((int) posx, 32);
					murs_sprites.put(t, sprite_mur);
					cds = true;
				}
			}

		}

		// Vidage de random_numbers
		random_numbers.clear();

		// Couloir sud
		int nb_murs_sud = rand.nextInt(17 - 8) + 8;
		for (int i = 0; i <= nb_murs_sud; i++) {
			sprite_mur = new Decor();
			cds = false;
			while (!cds) {
				int nb_random = rand.nextInt(17);
				if (!random_numbers.contains(nb_random)) {
					random_numbers.add(nb_random);
					double posx = 128 + nb_random * 32;
					sprite_mur.setPositionX(posx);
					sprite_mur.setPositionY(544);
					sprite_mur.setImage(mur);
					sprite_mur.render(gc2);
					Point t = new Point((int) posx, 544);
					murs_sprites.put(t, sprite_mur);
					cds = true;
				}
			}
		}

		// Vidage de random_numbers
		random_numbers.clear();

		// Couloir ouest
		int nb_murs_ouest = rand.nextInt(13 - 8) + 8;
		for (int i = 0; i <= nb_murs_ouest; i++) {
			sprite_mur = new Decor();
			cds = false;
			while (!cds) {
				int nb_random = rand.nextInt(13);
				if (!random_numbers.contains(nb_random)) {
					random_numbers.add(nb_random);
					double posy = 96 + nb_random * 32;
					sprite_mur.setPositionX(32);
					sprite_mur.setPositionY(posy);
					sprite_mur.setImage(mur);
					sprite_mur.render(gc2);
					Point t = new Point(32, (int) posy);
					murs_sprites.put(t, sprite_mur);
					cds = true;
				}
			}
		}

		// Vidage de random_numbers
		random_numbers.clear();

		// Couloir ouest
		int nb_murs_est = rand.nextInt(13 - 8) + 8;
		for (int i = 0; i <= nb_murs_est; i++) {
			sprite_mur = new Decor();
			cds = false;
			while (!cds) {
				int nb_random = rand.nextInt(13);
				if (!random_numbers.contains(nb_random)) {
					random_numbers.add(nb_random);
					double posy = 96 + nb_random * 32;
					sprite_mur.setPositionX(736);
					sprite_mur.setPositionY(posy);
					sprite_mur.setImage(mur);
					sprite_mur.render(gc2);
					Point t = new Point(736, (int) posy);
					murs_sprites.put(t, sprite_mur);
					cds = true;
				}
			}
		}

		// Positionnement des joueurs au dÃ©part
		Image image_joueur1 = new Image("Avatar/P1-1.png");
		Image image_joueur2 = new Image("Avatar/P2-1.png");
		Image image_joueur3 = new Image("Avatar/P4-4.png");
		Image image_joueur4 = new Image("Avatar/P3-4.png");

		player1 = new Player(32, 32, 18, 24);
		player1.setImage(image_joueur1);
		player1.render(gc2);

		player2 = new Player(736, 32, 18, 24);
		player2.setImage(image_joueur2);
		player2.render(gc2);

		player3 = new Player(32, 536, 18, 24);
		player3.setImage(image_joueur3);
		player3.render(gc2);

		player4 = new Player(736, 536, 18, 24);
		player4.setImage(image_joueur4);
		player4.render(gc2);

	}

	private void loop(KeyEventHandler kev) {

		final KeyEventHandler keyHandler = kev;
		new AnimationTimer() {

			@Override
			public void handle(long currentNanoTime) {

				update();

				if (keyHandler.getInputList().contains("RIGHT")) {
					System.out.println("RIGHT");
					gc2.clearRect(player1.getPositionX(), player1.getPositionY(), LONGUEUR_CASE.getValeur(),
							LARGEUR_CASE.getValeur());
					getController().onPressedRight(player1, murs_sprites, pilier_sprites);
					player1.setImage(new Image("view/avatar/P1-3.png"));
					player1.render(gc2);
				}

				if (keyHandler.getInputList().contains("LEFT")) {
					System.out.println("LEFT");
					gc2.clearRect(player1.getPositionX(), player1.getPositionY(), LONGUEUR_CASE.getValeur(),
							LARGEUR_CASE.getValeur());
					getController().onPressedLeft(player1, murs_sprites, pilier_sprites);
					player1.setImage(new Image("view/avatar/P1-2.png"));
					player1.render(gc2);
				}
				if (keyHandler.getInputList().contains("UP")) {
					System.out.println("UP");
					gc2.clearRect(player1.getPositionX(), player1.getPositionY(), LONGUEUR_CASE.getValeur(),
							LARGEUR_CASE.getValeur());
					getController().onPressedUp(player1, murs_sprites, pilier_sprites);
					player1.setImage(new Image("view/avatar/P1-4.png"));
					player1.render(gc2);
				}
				if (keyHandler.getInputList().contains("DOWN")) {
					System.out.println("DOWN");
					gc2.clearRect(player1.getPositionX(), player1.getPositionY(), LONGUEUR_CASE.getValeur(),
							LARGEUR_CASE.getValeur());
					getController().onPressedDown(player1, murs_sprites, pilier_sprites);

					player1.setImage(new Image("view/avatar/P1-1.png"));
					player1.render(gc2);
				}

			}

		}.start();

	}

	/**
	 * TODO : Récupérer les coordonnées des décors cassés par les autres puis les effacer du décor
	 * TODO : Récupérer les coordonnées des joueurs puis les actualiser
	 * */
	private void update() {
				
	}

	public void start(Canvas canvas, Canvas canvas2, KeyEventHandler kev){
				initGraphic(canvas,canvas2);
		        loop(kev);
	}
}
