package com.fr.ece.jbomb.model;

import static com.fr.ece.jbomb.model.Plateau.LARGEUR_PLATEAU;
import static com.fr.ece.jbomb.model.Plateau.LONGUEUR_PLATEAU;
import static com.fr.ece.jbomb.model.Plateau.MUR;
import static com.fr.ece.jbomb.model.Plateau.PILIER;
import static com.fr.ece.jbomb.model.Plateau.PLAYER1;
import static com.fr.ece.jbomb.model.Plateau.PLAYER2;
import static com.fr.ece.jbomb.model.Plateau.PLAYER3;
import static com.fr.ece.jbomb.model.Plateau.PLAYER4;
import static com.fr.ece.jbomb.model.Plateau.SOL;
import static com.fr.ece.jbomb.view.Direction.EST;
import static com.fr.ece.jbomb.view.Direction.NORD;
import static com.fr.ece.jbomb.view.Direction.OUEST;
import static com.fr.ece.jbomb.view.Direction.SUD;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fr.ece.jbomb.model.Plateau;
import com.fr.ece.jbomb.view.Direction;

public class ConfFromServer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Player> listPlayer;
	private Plateau plateau[][];
	private transient Map<Plateau, Point> playerByCoordinates;

	public ConfFromServer() {
		
		playerByCoordinates = new HashMap<Plateau, Point>();
		
		listPlayer = new ArrayList<Player>();
		//Création d'un tableau aléatoire
		this.plateau = new Plateau[17][23];
		ajout_piliers_sols();
		ajout_murs();
		ajout_players();
		
		update_array_plateau();
	}
	
	// -------------------Fonction appelé côté CLIENT

	public Plateau[][] getPlateau() {
		return plateau;
	}

	public List<Player> getListPlayer() {
		return listPlayer;
	}
	//Construction du tableau
	
	
	private void ajout_players() {
		System.out.println("Ajout_Players : Entree");
		Random rand = new Random();
		List<Integer> picked_random_numbers = new ArrayList<Integer>();
		Point[] coordonnees = new Point[4];
		coordonnees[0] = new Point(0, 0);
		coordonnees[1] = new Point(0, 22);
		coordonnees[2] = new Point(16, 0);
		coordonnees[3] = new Point(16, 22);
		boolean cds = false;
		
		picked_random_numbers.add(0); // a retirer??????????????????????????????????????????????????????????????????????????????????????????????
		plateau[16][22] = Plateau.PLAYER1; //  a retirer ????????????????????????????????????????????????????????????????????????????????
		// 4 changer 3 par 4 //A CHANGER ??????????????????????????????????????????????????????????????????????????????
		for(int i = 0; i < 3; i++){
			cds = false;
			while(!cds){
				int rd = rand.nextInt(4); // entre 0 et 3
				System.out.println(rd);
				if(!picked_random_numbers.contains(rd)){
					picked_random_numbers.add(rd);
					int posx = coordonnees[i].x;
					int posy = coordonnees[i].y;
					plateau[posx][posy] = Plateau.getNomByValeur(rd+1);
					cds = true;
				}
			}
		}
		System.out.println("Ajout_Players : Sortie");
	}

	private void ajout_piliers_sols() {
		// Mise en forme du sol dans le tableau plateau
		System.out.println("ajout_piliers_sols : Entree");
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 23; j++) {
				if (i % 2 != 0 && j % 2 != 0) {
					// Initialisation des piliers
					plateau[i][j] = PILIER;
				} else {
					plateau[i][j] = SOL;
				}
			}
		}
		System.out.println("ajout_piliers_sols : Sortie");
	}

	private void gestion_murs_particuliers(int nb_murs, Direction direction) {
		System.out.println("gestion_murs_particuliers : Entree");
		// Random
		Random rand = new Random();

		// ArrayList contient les valeurs tiréees au hasard pour éviter qu'on
		// retombe sur les memes valeurs
		List<Integer> random_numbers = new ArrayList<Integer>();
		int rand_num;
		// Condition de sortie
		boolean cds = false;
		for (int i = 0; i < nb_murs; i++) {
			cds = false;
			switch (direction) {
					case NORD:
						while(!cds){
							rand_num = rand.nextInt(16); // entre 0 et 16
							if (!random_numbers.contains(rand_num)) {
								random_numbers.add(rand_num);
								this.plateau[0][3 + rand_num] = MUR;
								cds = true;
							}
						}
						break;
					case SUD:
						System.out.println(random_numbers.size());
						while(!cds){
							rand_num = rand.nextInt(16); // entre 0 et 16
							if (!random_numbers.contains(rand_num)) {
								random_numbers.add(rand_num);
								this.plateau[16][3 + rand_num] = MUR;
								cds = true;
							}
						}
						break;
					case EST:
						while(!cds){
							rand_num = rand.nextInt(11); // entre 0 et 10
							if (!random_numbers.contains(rand_num)) {
								random_numbers.add(rand_num);
								this.plateau[3 + rand_num][22] = MUR;
								cds = true;
							}
						}
						break;
					case OUEST:
						while(!cds){
							rand_num = rand.nextInt(11); // entre 0 et 10
							if (!random_numbers.contains(rand_num)) {
								random_numbers.add(rand_num);
								this.plateau[3 + rand_num][0] = MUR;
								cds = true;
							}
						}
						break;
			}
		}
		
		random_numbers.clear();
		System.out.println("gestion_murs_particuliers : Sortie");
		
	}

	private void ajout_murs() {
		System.out.println("ajout_murs : Entree");
		// Random
		Random rand = new Random();
		int nb_murs = 0, rand_num = 0;
		boolean cds = false;
		// ArrayList contient les valeurs tirÃ©es au hasard pour Ã©viter qu'on
		// retombe sur les mÃªmes valeurs
		List<Integer> random_numbers = new ArrayList<Integer>();

		// Couloir nord et sud: 8 murs au min et 17 murs au max
		/* Murs Nord + Sud */
		nb_murs = rand.nextInt(17 - 8) + 8;
		gestion_murs_particuliers(nb_murs, NORD);
		nb_murs = rand.nextInt(17 - 8) + 8;
		gestion_murs_particuliers(nb_murs, SUD);
		/* Mur Est + Ouest */
		gestion_murs_particuliers(rand.nextInt(11 - 8) + 8, EST);
		gestion_murs_particuliers(rand.nextInt(11 - 8) + 8, OUEST);
		
		
		// Gestion des autres murs
		for(int i = 1; i < 16; i++){
			if(i%2!=0){
				nb_murs = rand.nextInt(5) + 5;
				for(int j = 0; j < nb_murs; j++){
					cds = false;
					while(!cds){
						rand_num = rand.nextInt(10)* 2; // rand entre 0 et 9
						if(!random_numbers.contains(rand_num)){
							random_numbers.add(rand_num);
							plateau[i][rand_num + 2] = MUR;
							cds = true;
						}	
					}
				} 
				random_numbers.clear();
			}else{
				nb_murs = rand.nextInt(12) + 10;  // rand entre 0 et 9 min : 10 murs et max : 21
				for(int j = 0; j < nb_murs; j++){
					cds = false;
					while(!cds){
						rand_num = rand.nextInt(21); // entre 0 et 20
						if(!random_numbers.contains(rand_num)){
							random_numbers.add(rand_num);
							plateau[i][rand_num+1] = MUR;
							cds = true;
						}	
					}
				} 
				random_numbers.clear();	
			}
		}
		
		System.out.println("ajout_murs : Sortie");
	}
	
	// -------------------Fonction appelé côté SERVER

	public void addPlayer(Player p) {
		listPlayer.add(p);
	}

	public ConfFromServer update(ConfToServer confToServer) {

		Player player = listPlayer.get(confToServer.idPlayer - 1);

		if (confToServer.getKeyList().contains("RIGHT")) {
			System.out.println("RIGHT");
			player.setDirectionPourSavoirQuelleImageAfficher("RIGHT");
			if (!onPressedRight(player)) {
				player.move(1, 0);
				listPlayer.add(confToServer.idPlayer - 1, player);
			}
		}

		if (confToServer.getKeyList().contains("LEFT")) {
			System.out.println("LEFT");
			player.setDirectionPourSavoirQuelleImageAfficher("LEFT");
			if (!onPressedLeft(player)) {
				player.move(-1, 0);
				listPlayer.add(confToServer.idPlayer - 1, player);
			}

		}
		if (confToServer.getKeyList().contains("UP")) {
			System.out.println("UP");
			player.setDirectionPourSavoirQuelleImageAfficher("UP");
			if (!onPressedUp(player)) {
				player.move(0, -1);
				listPlayer.add(confToServer.idPlayer - 1, player);
			}
		}

		if (confToServer.getKeyList().contains("DOWN")) {
			System.out.println("DOWN");
			player.setDirectionPourSavoirQuelleImageAfficher("DOWN");
			if (!onPressedDown(player)) {
				player.move(0, 1);
				listPlayer.add(confToServer.idPlayer - 1, player);
			}

		}
		return this;
	}

	//CHANGER LE NOM il c'est pas une fonction qui fait que printer !!!! example update
	private void update_array_plateau() {
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 23; j++) {
				switch (plateau[i][j]) {
				case PLAYER1:
				case PLAYER2:
				case PLAYER3:
				case PLAYER4:
					playerByCoordinates.put(plateau[i][j], new Point(i, j));
				default:
					break;
				}
				System.out.print(plateau[i][j].getValeur() + " ");
			}
			System.out.println();
		}
	}

	private boolean onPressedRight(Player player) {

		System.out.println(player.getPositionX() + " " + player.getPositionY());
		if (exceed_fences(player, EST)) {
			return true;
		}
		return is_any_collision(player, EST);
	}

	private boolean onPressedLeft(Player player) {
		System.out.println(player.getPositionX() + " " + player.getPositionY());
		// Si la méthode check_pillars_collisions renvoie false
		if (exceed_fences(player, OUEST)) {
			// Il existe une collision donc pas de déplacement
			return true;
		}
		return is_any_collision(player, OUEST);

	}

	private boolean onPressedUp(Player player) {
		System.out.println(player.getPositionX() + " " + player.getPositionY());
		if (exceed_fences(player, NORD)) {
			return true;
		}
		return is_any_collision(player, NORD);
	}

	private boolean onPressedDown(Player player) {
		System.out.println(player.getPositionX() + " " + player.getPositionY());
		if (exceed_fences(player, SUD)) {
			return true;
		}
		return is_any_collision(player, SUD);
	}

	private boolean exceed_fences(Player player, Direction direction) {
		switch (direction) {
		case EST:
			if ((player.getPositionX() + 32) + 1 < (LARGEUR_PLATEAU.getValeur() - 32)) {
				return false;
			}
			break;
		case OUEST:

			if ((player.getPositionX()) - 1 > 32) {
				return false;
			}
			break;
		case NORD:

			if ((player.getPositionY()) - 1 > 32) {
				return false;
			}
			break;

		case SUD:
			if ((player.getPositionY() + 32) + 1 < (LONGUEUR_PLATEAU.getValeur() - 32)) {
				return false;
			}
			break;
		}
		return true;
	}

	public Sprite createNeighborSprite(int positionRow_neighbor, int positionCol_neighbor) {

		// Coordonnes en pixels du voisin
		int coordinateX_neigborPixel = (positionCol_neighbor + 1) * 32;
		int coordinateY_neighboPixel = (positionRow_neighbor + 1) * 32;

		// Sprite Voisin
		Sprite neighbor;

		switch (plateau[positionRow_neighbor][positionCol_neighbor]) {
		case SOL:
		case MUR:
		case PILIER:
			neighbor = new Decor(coordinateX_neigborPixel, coordinateY_neighboPixel, 32, 32);
			break;
		case BOMBE:
			neighbor = new Decor(coordinateX_neigborPixel, coordinateY_neighboPixel, 16, 16);
			break;
		default:
			neighbor = new Player(coordinateX_neigborPixel, coordinateY_neighboPixel, 16, 16);
			break;
		}

		return neighbor;
	}

	private boolean is_any_collision(Player player, Direction direction) {

		// Coordonnes x,y du joueur dans le tableau tab[][]
		int positionRow_player = playerByCoordinates.get(PLAYER1).x;
		int positionCol_player = playerByCoordinates.get(PLAYER1).y;

		//
		int positionRow_neighbor;
		int positionCol_neighbor;

		// Sprite representing the neighbor
		Sprite neighbor;

		switch (direction) {
		case NORD:

			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player - 1;
			positionCol_neighbor = positionCol_player;

			// Bombe : (16pixels,16pixels) en (longueur,largeur)
			// Sol ou Pilier (32pixels,32pixels) en
			// (longpositionRow_neighborueur,largeur)
			System.out.println(
					"Position Row Neighbor " + positionRow_neighbor + " Position Col Neighbor " + positionCol_neighbor);

			if (positionRow_neighbor >= 0) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);

				// Simulation
				player.move(0, -1);

				System.out.println("SIMU DONE");
				System.out.println("PLAYER1 COORDINATE IN PX : " + player.getPositionX() + " " + player.getPositionY());
				System.out.println(
						"NEIHBOR COORDINATE IN PX : " + neighbor.getPositionX() + " " + neighbor.getPositionY());
				// Conditions : Vérification des collisions
				if (neighbor.intersects(player)) {
					System.out.println("INTERSECTION");
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionRow_neighbor + 1) * 32;
						int max = (positionRow_neighbor + 2) * 32;
						System.out.println("Min : " + min + " Max : " + max);
						if (min <= player.getPositionY() + 16 && player.getPositionY() + 16 <= max) {
							System.out.println("CHANGE  SOL ");
							playerByCoordinates.put(PLAYER1, new Point(positionRow_player - 1, positionCol_player));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = PLAYER1;
						}

						return false;
					} else {
						System.out.println("NO SOL");
						player.move(0, +1);

						return true;
					}
				} else {
					System.out.println("NO INTERSECTION");
					return false; // Le joueur avance d'un pixel dans sa case de
									// départ
				}
			}

		case SUD:
			// Coordonnes x,y du voisin dans le tableau plateau[][]
			positionRow_neighbor = positionRow_player + 1;
			positionCol_neighbor = positionCol_player;

			if (positionRow_neighbor < 17) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);

				// Simulation
				player.move(0, +1);

				// Conditions : Vérification des collisions
				if (neighbor.intersects(player)) {
					System.out.println("INTERSECTION");
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionRow_neighbor + 1) * 32;
						int max = (positionRow_neighbor + 2) * 32;
						System.out.println("Min : " + min + " Max : " + max);
						if (min <= player.getPositionY() + 16 && player.getPositionY() + 16 <= max) {
							System.out.println("CHANGE  SOL ");
							playerByCoordinates.put(PLAYER1, new Point(positionRow_player + 1, positionCol_player));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = PLAYER1;
						}

						return false;
					} else {
						System.out.println("NO SOL");
						player.move(0, -1);
						return true;
					}
				} else {
					System.out.println("NO INTERSECTION");
					return false; // Le joueur avance d'un pixel dans sa case de
									// départ
				}
			} else if (positionRow_neighbor == 17) {
				player.move(0, +1);
				if (player.getPositionY() + 16 <= Plateau.LONGUEUR_PLATEAU.getValeur()) {
					return false;
				}
				player.move(0, -1); // cancel le mov
				return true;
			}
		case EST:
			// Coordonnes x,y du voisin dans le tableau plateau[][]
			positionRow_neighbor = positionRow_player;
			positionCol_neighbor = positionCol_player + 1;

			if (positionCol_neighbor < 23) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);

				// Simulation
				player.move(+1, 0);

				// Conditions : Vérification des collisions
				if (neighbor.intersects(player)) {
					System.out.println("INTERSECTION");
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionCol_neighbor + 1) * 32;
						int max = (positionCol_neighbor + 2) * 32;
						System.out.println("Min : " + min + " Max : " + max);
						if (min <= player.getPositionX() + 16 && player.getPositionX() + 16 <= max) {
							System.out.println("CHANGE  SOL ");
							playerByCoordinates.put(PLAYER1, new Point(positionRow_player, positionCol_player + 1));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = PLAYER1;
						}

						return false;
					} else {
						System.out.println("NO SOL");
						player.move(-1, 0);
						return true;
					}
				} else {
					System.out.println("NO INTERSECTION");
					return false; // Le joueur avance d'un pixel dans sa case de
									// départ
				}
			} else if (positionCol_neighbor == 23) {
				player.move(+1, 0);
				if (player.getPositionX() + 16 <= Plateau.LARGEUR_PLATEAU.getValeur()) {
					return false;
				}
				player.move(-1, 0); // cancel le mov
				return true;
			}

		case OUEST:
			// Coordonnes x,y du voisin dans le tableau plateau[][]
			positionRow_neighbor = positionRow_player;
			positionCol_neighbor = positionCol_player - 1;

			if (positionCol_neighbor >= 0) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);

				// Simulation
				player.move(-1, 0);

				// Conditions : Vérification des collisions
				if (neighbor.intersects(player)) {
					System.out.println("INTERSECTION");
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionCol_neighbor + 1) * 32;
						int max = (positionCol_neighbor + 2) * 32;
						System.out.println("Min : " + min + " Max : " + max);
						if (min <= player.getPositionX() + 16 && player.getPositionX() + 16 <= max) {
							System.out.println("CHANGE  SOL ");
							playerByCoordinates.put(PLAYER1, new Point(positionRow_player, positionCol_player - 1));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = PLAYER1;
						}

						return false;
					} else {
						System.out.println("NO SOL");
						player.move(+1, 0);
						return true;
					}
				} else {
					System.out.println("NO INTERSECTION");
					return false; // Le joueur avance d'un pixel dans sa case de
									// départ
				}
			}
		}

		return true;

	}


}
