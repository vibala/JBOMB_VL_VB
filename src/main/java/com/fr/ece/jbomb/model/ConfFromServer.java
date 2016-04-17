package com.fr.ece.jbomb.model;

import static com.fr.ece.jbomb.model.Plateau.BOMBE;
import static com.fr.ece.jbomb.model.Plateau.CRAME;
import static com.fr.ece.jbomb.model.Plateau.LARGEUR_PLATEAU;
import static com.fr.ece.jbomb.model.Plateau.LONGUEUR_PLATEAU;
import static com.fr.ece.jbomb.model.Plateau.MUR;
import static com.fr.ece.jbomb.model.Plateau.PILIER;
import static com.fr.ece.jbomb.model.Plateau.PLAYER1;
import static com.fr.ece.jbomb.model.Plateau.SOL;
import static com.fr.ece.jbomb.view.Direction.EST;
import static com.fr.ece.jbomb.view.Direction.NORD;
import static com.fr.ece.jbomb.view.Direction.OUEST;
import static com.fr.ece.jbomb.view.Direction.SUD;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fr.ece.jbomb.controller.BombTimer;
import com.fr.ece.jbomb.server.Server;
import com.fr.ece.jbomb.view.Direction;

public class ConfFromServer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Player> listPlayer;
	private Plateau plateau[][];
	private transient Map<Plateau, Point> playerByCoordinates;
	private transient int ID_BOMB = 0;
	private String direction;
	 private boolean recommencer=false; //Var globale dans la classe

	public ConfFromServer() {

		playerByCoordinates = new HashMap<Plateau, Point>();

		listPlayer = new ArrayList<Player>();
		// Création d'un tableau aléatoire
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

	// Construction du plateau
	private void ajout_flamme() {
		// Test
		plateau[1][0] = CRAME;
		plateau[2][0] = CRAME;
		plateau[3][0] = CRAME;
		plateau[2][1] = CRAME;
		plateau[2][2] = CRAME;
	}

	private void ajout_players() {
		// System.out.println("Ajout_Players : Entree");
		Random rand = new Random();
		List<Integer> picked_random_numbers = new ArrayList<Integer>();
		Point[] coordonnees = new Point[4];
		coordonnees[0] = new Point(0, 0);
		coordonnees[1] = new Point(0, 22);
		coordonnees[2] = new Point(16, 0);
		coordonnees[3] = new Point(16, 22);
		boolean cds = false;
		picked_random_numbers.add(0); // a
		// retirer??????????????????????????????????????????????????????????????????????????????????????????????
		plateau[16][22] = Plateau.PLAYER1; // a retirer
		// ????????????????????????????????????????????????????????????????????????????????
		plateau[0][0] = Plateau.PLAYER2;
		plateau[16][0] = Plateau.PLAYER3;
		plateau[0][22] = Plateau.PLAYER4;
		// 4 changer 3 par 4 //A CHANGER
		// ??????????????????????????????????????????????????????????????????????????????
		/*
		 * for(int i = 0; i < 3; i++){ cds = false; while(!cds){ int rd =
		 * rand.nextInt(3); // entre 0 et 3 System.out.println(rd);
		 * if(!picked_random_numbers.contains(rd)){
		 * picked_random_numbers.add(rd); int posx = coordonnees[i].x; int posy
		 * = coordonnees[i].y; plateau[posx][posy] =
		 * Plateau.getNomByValeur(rd+1); cds = true; } } }
		 */
		System.out.println("Ajout_Players : Sortie");
	}

	private void ajout_piliers_sols() {
		// Mise en forme du sol dans le tableau plateau
		// System.out.println("ajout_piliers_sols : Entree");
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
		// System.out.println("gestion_murs_particuliers : Entree");
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
				while (!cds) {
					rand_num = rand.nextInt(16); // entre 0 et 16
					if (!random_numbers.contains(rand_num)) {
						random_numbers.add(rand_num);
						this.plateau[0][3 + rand_num] = MUR;
						cds = true;
					}
				}
				break;
			case SUD:
				// System.out.println(random_numbers.size());
				while (!cds) {
					rand_num = rand.nextInt(16); // entre 0 et 16
					if (!random_numbers.contains(rand_num)) {
						random_numbers.add(rand_num);
						this.plateau[16][3 + rand_num] = MUR;
						cds = true;
					}
				}
				break;
			case EST:
				while (!cds) {
					rand_num = rand.nextInt(11); // entre 0 et 10
					if (!random_numbers.contains(rand_num)) {
						random_numbers.add(rand_num);
						this.plateau[3 + rand_num][22] = MUR;
						cds = true;
					}
				}
				break;
			case OUEST:
				while (!cds) {
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
		for (int i = 1; i < 16; i++) {
			if (i % 2 != 0) {
				nb_murs = rand.nextInt(5) + 5;
				for (int j = 0; j < nb_murs; j++) {
					cds = false;
					while (!cds) {
						rand_num = rand.nextInt(10) * 2; // rand entre 0 et 9
						if (!random_numbers.contains(rand_num)) {
							random_numbers.add(rand_num);
							plateau[i][rand_num + 2] = MUR;
							cds = true;
						}
					}
				}
				random_numbers.clear();
			} else {
				nb_murs = rand.nextInt(12) + 10; // rand entre 0 et 9 min : 10
													// murs et max : 21
				for (int j = 0; j < nb_murs; j++) {
					cds = false;
					while (!cds) {
						rand_num = rand.nextInt(21); // entre 0 et 20
						if (!random_numbers.contains(rand_num)) {
							random_numbers.add(rand_num);
							plateau[i][rand_num + 1] = MUR;
							cds = true;
						}
					}
				}
				random_numbers.clear();
			}
		}

		// System.out.println("ajout_murs : Sortie");
	}

	// -------------------Fonction appelé côté SERVER

	public void addPlayer(Player p) {
		listPlayer.add(p);
	}

	public ConfFromServer update(ConfToServer confToServer) {

		Player player = listPlayer.get(confToServer.idPlayer - 1);

		if (confToServer.getKeyList().contains("RIGHT")) {
			// System.out.println("RIGHT");
			direction = "RIGHT";
			player.setDirectionPourSavoirQuelleImageAfficher("RIGHT");
			if (!onPressedRight(player)) {
				player.move(5, 0);
			}
		}

		if (confToServer.getKeyList().contains("LEFT")) {
			// System.out.println("LEFT");
			direction = "LEFT";
			player.setDirectionPourSavoirQuelleImageAfficher("LEFT");
			if (!onPressedLeft(player)) {
				player.move(-5, 0);
			}

		}
		if (confToServer.getKeyList().contains("UP")) {
			// System.out.println("UP");
			direction = "UP";
			player.setDirectionPourSavoirQuelleImageAfficher("UP");
			if (!onPressedUp(player)) {
				player.move(0, -5);
			}
		}

		if (confToServer.getKeyList().contains("DOWN")) {
			// System.out.println("DOWN");
			direction = "DOWN";
			player.setDirectionPourSavoirQuelleImageAfficher("DOWN");
			if (!onPressedDown(player)) {
				player.move(0, 5);
			}
		}

		// Vignesh's code on explosion
		if (confToServer.getKeyList().contains("A")) {
			ajout_flamme();
		}

		// Vince's code on explosion
		if (confToServer.getKeyList().contains("B")) {
			onPressedBomb(player);
		}

		explosionDesBombes();

		return this;
	}

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

	///////////////////////////////// Check Collision

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
			neighbor = new Decor(coordinateX_neigborPixel, coordinateY_neighboPixel, 32, 32);
			break;
		default:
			neighbor = new Player(coordinateX_neigborPixel, coordinateY_neighboPixel, 32, 32);
			break;
		}

		return neighbor;
	}

	/////////////////////////////////////////////////////////////// Check
	/////////////////////////////////////////////////////////////// neighbors
	public int checkFourNeighbor(int row, int col, Sprite player, Direction direction) {
		int nb_intersections = 0;

		switch (direction) {
		case SUD:
			if (row + 1 < 17 && col - 1 >= 0) {
				Sprite neighborEast = createNeighborSprite(row + 1, col - 1);

				if (player.intersects(neighborEast)) {
					System.out.print("E ");
					nb_intersections++;
				}

				neighborEast = createNeighborSprite(row, col - 1);
				if (player.intersects(neighborEast)) {
					System.out.print("E ");
					nb_intersections++;
				}

			}

			if (row + 1 < 17 && col + 1 < 23) {
				Sprite neighborWest = createNeighborSprite(row + 1, col + 1);

				if (player.intersects(neighborWest)) {
					System.out.print("W ");
					nb_intersections++;
				}

				neighborWest = createNeighborSprite(row, col + 1);
				if (player.intersects(neighborWest)) {
					System.out.print("W");
					nb_intersections++;
				}
			}

			break;
		case NORD:
			if (row - 1 >= 0 && col - 1 >= 0) {
				Sprite neighborEast = createNeighborSprite(row - 1, col - 1);

				if (player.intersects(neighborEast)) {
					System.out.print("East Collision ");
					nb_intersections++;
				}

				neighborEast = createNeighborSprite(row, col - 1);
				if (player.intersects(neighborEast)) {
					System.out.print("East Collision ");
					nb_intersections++;
				}
			}

			if (row - 1 >= 0 && col + 1 < 23) {
				Sprite neighborWest = createNeighborSprite(row - 1, col + 1);

				if (player.intersects(neighborWest)) {
					System.out.print("West Collision");
					nb_intersections++;
				}

				neighborWest = createNeighborSprite(row, col + 1);
				if (player.intersects(neighborWest)) {
					System.out.print("West Collision");
					nb_intersections++;
				}
			}
			break;
		case EST:
			if (row - 1 >= 0 && col + 1 < 23) {
				Sprite neighborNorth = createNeighborSprite(row - 1, col + 1);

				if (player.intersects(neighborNorth)) {
					System.out.print("Nord Collision ");
					nb_intersections++;
				}

				neighborNorth = createNeighborSprite(row - 1, col);
				if (player.intersects(neighborNorth)) {
					System.out.print("Nord Collision ");
					nb_intersections++;
				}
			}

			if (row + 1 < 17 && col + 1 < 23) {
				Sprite neighborSouth = createNeighborSprite(row + 1, col + 1);
				if (player.intersects(neighborSouth)) {
					System.out.print("Sud Collision ");
					nb_intersections++;
				}

				neighborSouth = createNeighborSprite(row + 1, col);
				if (player.intersects(neighborSouth)) {
					System.out.print("Nord Collision ");
					nb_intersections++;
				}
			}

			break;
		case OUEST:
			if (row - 1 >= 0 && col - 1 >= 0) {
				Sprite neighborNorth = createNeighborSprite(row - 1, col - 1);

				if (player.intersects(neighborNorth)) {
					System.out.print("Nord Collision ");
					nb_intersections++;
				}

				neighborNorth = createNeighborSprite(row - 1, col);
				if (player.intersects(neighborNorth)) {
					System.out.print("Nord Collision ");
					nb_intersections++;
				}
			}

			if (row + 1 < 17 && col - 1 >= 0) {
				Sprite neighborSouth = createNeighborSprite(row + 1, col - 1);

				if (player.intersects(neighborSouth)) {
					System.out.print("Sud Collision ");
					nb_intersections++;
				}

				neighborSouth = createNeighborSprite(row + 1, col);
				if (player.intersects(neighborSouth)) {
					System.out.print("Nord Collision ");
					nb_intersections++;
				}
			}

			break;
		}

		System.out.println("N°inter " + nb_intersections);
		return nb_intersections;
	}

	/***************************************************************************/

	private boolean onPressedBomb(Player player) {
		if(player.getBomb()==0){
			System.out.println("Le joueur n'a plus de bombe ! "+player.getBomb());
			return true;
		}
		//System.out.println("La bombe ne peut pas être posé "+player.getBomb());
		
		//Recup case devant joueur
		int deltaX=0;
		int deltaY=0;
		 // Se fait en fonction de l'orientation de limage du player et si la case devan lui est vide
		System.out.println("direction reagrd : " +player.getDirectionPourSavoirQuelleImageAfficher());
		if(player.getDirectionPourSavoirQuelleImageAfficher().equals("UP")) deltaX--;
		if(player.getDirectionPourSavoirQuelleImageAfficher().equals("DOWN")) deltaX++;
		if(player.getDirectionPourSavoirQuelleImageAfficher().equals("RIGHT")) deltaY++;
		if(player.getDirectionPourSavoirQuelleImageAfficher().equals("LEFT")) deltaY--;
		
		//SSI caseRecup = 0
		
		int i =(int) (playerByCoordinates.get(Plateau.getNomByValeur(player.getID())).getX()+deltaX);
		int j =(int) (playerByCoordinates.get(Plateau.getNomByValeur(player.getID())).getY()+deltaY);
	
		System.out.println("bombe essaye d'etre placé en :"+i+" "+j);
		if(0<=i&& i<=16 && 0<=j && j<= 22 && plateau[i][j]==SOL)
		{
			plateau[i][j]=BOMBE;
			//player.setBomb(player.getBomb()-1);
			System.out.println("Bombe posé!");
			//Ajouter la bombe dans la liste des bombes sur le serveur
			int idBomb=ID_BOMB;
			ID_BOMB++;
			Bomb bomb=new Bomb(idBomb,i,j);
			System.out.println("pos" + i+j);
			Server.addListBomb(new Point(i,j),bomb);
			
			new Thread(new BombTimer(i,j)).start();
			
			return false;//Il n'y a rien devant toi player tu peux poser une bombe => renvoyer false
		}
		else return true;
	}
	
   

	public void removePlayer(int i, int j) {
		
			if (plateau[i][j] == Plateau.BOMBE) {
				System.out.println("plateau" + i + " " + j);
				int idBomb = Server.listBomb.get(new Point(i, j)).getID();
				System.out.println("plateau" + i + " OK " + j);
				// Les bombes qui ont pété ont un id > 50000
				Server.listBomb.get(new Point(i, j)).setID(idBomb + 50000);
				recommencer = true;
				return;
			} else {
				if (plateau[i][j] == Plateau.PLAYER1) {
					listPlayer.get(0).setPositionX(-1000);
					listPlayer.get(0).setPositionY(-1000);
				} else if (plateau[i][j] == Plateau.PLAYER2) {
					listPlayer.get(1).setPositionX(-1000);
					listPlayer.get(1).setPositionY(-1000);
				} else if (plateau[i][j] == Plateau.PLAYER3) {
					listPlayer.get(2).setPositionX(-1000);
					listPlayer.get(2).setPositionY(-1000);
				} else if (plateau[i][j] == Plateau.PLAYER4) {
					listPlayer.get(3).setPositionX(-1000);
					listPlayer.get(3).setPositionY(-1000);
				} else if (plateau[i][j] == Plateau.PLAYER4) {
					listPlayer.get(3).setPositionX(-1000);
					listPlayer.get(3).setPositionY(-1000);
				} else if (plateau[i][j] == Plateau.MUR) {
				}

				plateau[i][j] = CRAME; 
			}
	}

	public void boucleExplosion() {
		int i;
		int j;
		Iterator it = Server.listBomb.entrySet().iterator(); // Exception ou on parle getListBomb car on sait que c'est nous qui avons le  jeton
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (((Bomb) pair.getValue()).getID() >= 10000) { // car id bomb qui
																// pète >= 100
				i = (int) ((Bomb) pair.getValue()).getX();
				j = (int) ((Bomb) pair.getValue()).getY();
				if (0 <= i && i <= 16 && 0 <= j && j <= 22) {
					plateau[i][j] = CRAME;
					it.remove(); // On suprime la bombe
				}
				i = i + 1;
				j = j;
				if (0 <= i && i <= 16 && 0 <= j && j <= 22
						&& (plateau[i][j] == BOMBE | plateau[i][j] == MUR | plateau[i][j] == Plateau.PLAYER1
								| plateau[i][j] == Plateau.PLAYER2 | plateau[i][j] == Plateau.PLAYER3
								| plateau[i][j] == Plateau.PLAYER4))
					removePlayer(i, j);
				i = i - 1 - 1;
				j = j;
				if (0 <= i && i <= 16 && 0 <= j && j <= 22
						&& (plateau[i][j] == BOMBE | plateau[i][j] == MUR | plateau[i][j] == Plateau.PLAYER1
								| plateau[i][j] == Plateau.PLAYER2 | plateau[i][j] == Plateau.PLAYER3
								| plateau[i][j] == Plateau.PLAYER4))
					removePlayer(i, j);
				i = i + 1;
				j = j - 1;
				if (0 <= i && i <= 16 && 0 <= j && j <= 22
						&& (plateau[i][j] == BOMBE | plateau[i][j] == MUR | plateau[i][j] == Plateau.PLAYER1
								| plateau[i][j] == Plateau.PLAYER2 | plateau[i][j] == Plateau.PLAYER3
								| plateau[i][j] == Plateau.PLAYER4))
					removePlayer(i, j);
				i = i;
				j = j + 2;
				if (0 <= i && i <= 16 && 0 <= j && j <= 22
						&& (plateau[i][j] == BOMBE | plateau[i][j] == MUR | plateau[i][j] == Plateau.PLAYER1
								| plateau[i][j] == Plateau.PLAYER2 | plateau[i][j] == Plateau.PLAYER3
								| plateau[i][j] == Plateau.PLAYER4))
					removePlayer(i, j);

			}
		}
		if (recommencer) {
			recommencer = false;
			boucleExplosion();
		}
	}

	public void explosionDesBombes() {
		synchronized (Server.JETON_BOMB) {
			boucleExplosion();
		}
	}

	/*****************************************************************************************/
	////////////////////////////////// Check fences
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

	public boolean is_any_collision(Player player, Direction direction) {

		// Coordonnes x,y du joueur dans le tableau tab[][]
		int positionRow_player = playerByCoordinates.get(Plateau.getNomByValeur(player.getID())).x;
		int positionCol_player = playerByCoordinates.get(Plateau.getNomByValeur(player.getID())).y;

		int nb_intersections = 0;
		int positionRow_neighbor;
		int positionCol_neighbor;

		// Sprite representing the neighbor
		Sprite neighbor;
		// Simulation du déplacement du joueur
		Player simul = new Player(player.getID(), player.getPositionX(), player.getPositionY(), player.getWidth(),
				player.getHeight());
		switch (direction) {
		case NORD:

			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player - 1;
			positionCol_neighbor = positionCol_player;

			// System.out.println("Position Row Neighbor " +
			// positionRow_neighbor + " Position Col Neighbor " +
			// positionCol_neighbor);

			if (positionRow_neighbor >= 0) {

				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);
				// Simulation
				simul.move(0, -5);

				System.out.println("SIMU DONE");
				System.out.println("PLAYER1 COORDINATE IN PX : " + simul.getPositionX() + " " + simul.getPositionY());
				System.out.println("PLAYER1 COORDINATE IN ROW,COL : " + positionRow_player + " " + positionCol_player);
				System.out.println("NEIGHBOR COORDINATE IN PX : " + neighbor.getPositionX() + " " + neighbor.getPositionY());
				System.out.println("NEIGHBOR COORDINATE IN ROW,COL : " + positionRow_neighbor + " " + positionCol_neighbor);

				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, simul, NORD);
				// Conditions : Vérification des collisions
				if (simul.intersects(neighbor) && nb_intersections == 0) {
					System.out.println("Mono intersection ");
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionRow_neighbor + 1) * 32;
						int max = (positionRow_neighbor + 2) * 32;
						// System.out.println("Min : " + min + " Max : " + max);
						if (min <= simul.getPositionY() + 16 && simul.getPositionY() + 16 <= max) {
							System.out.println("Le joueur passe d'une case à une autre");
							playerByCoordinates.put(Plateau.getNomByValeur(player.getID()),new Point(positionRow_player - 1, positionCol_player));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = Plateau.getNomByValeur(player.getID());
						}
						return false;
					} else {
						System.out.println("NO SOL");
						simul.move(0, +5);
						return true;
					}
				} else if (nb_intersections != 0) {
					System.out.println("Poly collision ");
					simul.move(0, +5);
					return true;
				}

			} else if (positionRow_neighbor == -1) {
				if (player.getPositionY() >= 0) {
					return false;
				}
				simul.move(0, +5); // cancel le mov
				return true;
			}
			return false;

		/*********************************************************************************************************/
		/*********************************************************************************************************/

		case SUD:

			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player + 1;
			positionCol_neighbor = positionCol_player;
			if (positionRow_neighbor < 17) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);

				// Simulation
				simul.move(0, +5);
				System.out.println("PLAYER1 COORDINATE IN PX : " + simul.getPositionX() + " " + simul.getPositionY());
				System.out.println("PLAYER1 COORDINATE IN ROW,COL : " + positionRow_player + " " + positionCol_player);
				System.out.println(
						"NEIGHBOR COORDINATE IN PX : " + neighbor.getPositionX() + " " + neighbor.getPositionY());
				System.out.println(
						"NEIGHBOR COORDINATE IN ROW,COL : " + positionRow_neighbor + " " + positionCol_neighbor);

				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, simul, SUD);

				// Conditions : Vérification des collisions
				if (simul.intersects(neighbor) && nb_intersections == 0) {
					// System.out.println("Only one neighbor ");
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionRow_neighbor + 1) * 32;
						int max = (positionRow_neighbor + 2) * 32;

						if (min <= simul.getPositionY() + 16 && simul.getPositionY() + 16 <= max) {
							System.out.println("CHANGE  SOL ");
							playerByCoordinates.put(Plateau.getNomByValeur(player.getID()),
									new Point(positionRow_player + 1, positionCol_player));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = Plateau
									.getNomByValeur(player.getID());
						}
						return false;
					} else {
						System.out.println("NO SOL");
						simul.move(0, -5);
						return true;
					}
				} else if (nb_intersections != 0) {
					System.out.println("Poly collision ");
					simul.move(0, -5);
					return true;
				}
			} else if (positionRow_neighbor == 17) {
				if (player.getPositionY() + 32 <= Plateau.LONGUEUR_PLATEAU.getValeur() - 32) {
					return false;
				}
				simul.move(0, -5);
				return true;
			}
			return false; // s'il n'y a aucne intersection

		/*********************************************************************************************************/
		/*********************************************************************************************************/
		case EST:
			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player;
			positionCol_neighbor = positionCol_player + 1;

			if (positionCol_neighbor < 23) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);
				System.out.println("PLAYER1 COORDINATE IN PX : " + simul.getPositionX() + " " + simul.getPositionY());
				System.out.println("PLAYER1 COORDINATE IN ROW,COL : " + positionRow_player + " " + positionCol_player);
				System.out.println(
						"NEIGHBOR COORDINATE IN PX : " + neighbor.getPositionX() + " " + neighbor.getPositionY());
				System.out.println(
						"NEIGHBOR COORDINATE IN ROW,COL : " + positionRow_neighbor + " " + positionCol_neighbor);

				// Simulation
				simul.move(+5, 0);
				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, simul, EST);

				// Conditions : Vérification des collisions
				if (simul.intersects(neighbor) && nb_intersections == 0) {
					// System.out.println("Only one neighbor ");
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionCol_neighbor + 1) * 32;
						int max = (positionCol_neighbor + 2) * 32;
						// System.out.println("Min : " + min + " Max : " + max);
						if (min <= simul.getPositionX() + 16 && simul.getPositionX() + 16 <= max) {
							// System.out.println("CHANGE SOL ");
							playerByCoordinates.put(Plateau.getNomByValeur(player.getID()),
									new Point(positionRow_player, positionCol_player + 1));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = Plateau
									.getNomByValeur(player.getID());
						}
						return false;
					} else {
						System.out.println("NO SOL");
						simul.move(-5, 0);
						return true;
					}
				} else if (nb_intersections != 0) {
					simul.move(-5, 0);
					return true;
				}
			} else if (positionCol_neighbor == 23) {
				if (player.getPositionX() + 32 <= Plateau.LARGEUR_PLATEAU.getValeur()) {
					return false;
				}
				simul.move(-5, 0);
				return true;
			}
			return false; // s'il n'y a aucne intersection

		/*********************************************************************************************************/
		/*********************************************************************************************************/

		case OUEST:
			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player;
			positionCol_neighbor = positionCol_player - 1;

			if (positionCol_neighbor >= 0) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);
				System.out.println("PLAYER1 COORDINATE IN PX : " + simul.getPositionX() + " " + simul.getPositionY());
				System.out.println("PLAYER1 COORDINATE IN ROW,COL : " + positionRow_player + " " + positionCol_player);
				System.out.println(
						"NEIGHBOR COORDINATE IN PX : " + neighbor.getPositionX() + " " + neighbor.getPositionY());
				System.out.println(
						"NEIGHBOR COORDINATE IN ROW,COL : " + positionRow_neighbor + " " + positionCol_neighbor);

				// Simulation
				simul.move(-5, 0);

				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, player, OUEST);

				// Conditions : Vérification des collisions
				if (simul.intersects(neighbor) && nb_intersections == 0) {
					// System.out.println("Only one neighbor ");
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionCol_neighbor + 1) * 32;
						int max = (positionCol_neighbor + 2) * 32;
						// System.out.println("Min : " + min + " Max : " + max);
						if (min <= simul.getPositionX() + 16 && simul.getPositionX() + 16 <= max) {
							// System.out.println("CHANGE SOL ");
							playerByCoordinates.put(Plateau.getNomByValeur(player.getID()),
									new Point(positionRow_player, positionCol_player - 1));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = Plateau
									.getNomByValeur(player.getID());
							return false;

						}
					} else {
						System.out.println("NO SOL");
						simul.move(+5, 0);
						return true;
					}

				} else if (nb_intersections != 0) {
					simul.move(+5, 0);
					return true;
				}
			} else if (positionCol_neighbor == -1) {
				if (player.getPositionX() >= 0) {
					return false;
				}
				simul.move(+5, 0);
				return true;
			}
		}
		System.out.println("FFFFF");
		return false;
	}

	//////////////////////////////////////////////////////////////////// End
	//////////////////////////////////////////////////////////////////// checking
	//////////////////////////////////////////////////////////////////// Collsion

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

}
