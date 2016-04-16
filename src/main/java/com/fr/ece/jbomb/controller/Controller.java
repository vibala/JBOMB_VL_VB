package com.fr.ece.jbomb.controller;

import static com.fr.ece.jbomb.model.Plateau.LARGEUR_PLATEAU;
import static com.fr.ece.jbomb.model.Plateau.LONGUEUR_PLATEAU;
import static com.fr.ece.jbomb.model.Plateau.PLAYER1;
import static com.fr.ece.jbomb.model.Plateau.SOL;
import static com.fr.ece.jbomb.view.Direction.EST;
import static com.fr.ece.jbomb.view.Direction.NORD;
import static com.fr.ece.jbomb.view.Direction.OUEST;
import static com.fr.ece.jbomb.view.Direction.SUD;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.fr.ece.jbomb.model.Decor;
import com.fr.ece.jbomb.model.Plateau;
import com.fr.ece.jbomb.model.Player;
import com.fr.ece.jbomb.model.ServerTest;
import com.fr.ece.jbomb.model.Sprite;
import com.fr.ece.jbomb.view.Direction;
import com.fr.ece.jbomb.view.GUI;
import com.fr.ece.jbomb.view.KeyEventHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class Controller implements GUIListener {

	private GUI gui;
	private Canvas canvas;
	private Canvas canvas2;
	private KeyEventHandler key;
	private Plateau tab[][];
	private Map<Plateau, Point> playerByCoordinates;
	private ServerTest model;

	// Le tableau représentant le plateau de jeu est envoyé au début de la
	// partie à chacun
	// des joueurs (le controlleur côté client le recevra et l'initialisera)

	public void setInit(GUI gui, ServerTest model, Canvas canvas, Canvas canvas2, KeyEventHandler key) {
		this.gui = gui;
		this.canvas = canvas;
		this.canvas2 = canvas2;
		this.key = key;
		this.model = model;
		this.tab = model.getPlateau();
		this.playerByCoordinates = new HashMap<Plateau, Point>();
		print_array_plateau();
	}

	public void print_array_plateau() {
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 23; j++) {
				switch (tab[i][j]) {
				case PLAYER1:
				case PLAYER2:
				case PLAYER3:
				case PLAYER4:
					playerByCoordinates.put(tab[i][j], new Point(i, j));
				default:
					break;
				}
				System.out.print(tab[i][j].getValeur() + " ");
			}
			System.out.println();
		}
	}

	@FXML
	private void initialize() {
	}

	@FXML
	void onSubmitClick(ActionEvent event) {
	}

	public boolean exceed_fences(Player player1, Direction direction) {
		switch (direction) {
		case EST:
			if ((player1.getPositionX() + 32) + 1 < (LARGEUR_PLATEAU.getValeur() - 32)) {
				return false;
			}
			break;
		case OUEST:

			if ((player1.getPositionX()) - 1 > 32) {
				return false;
			}
			break;
		case NORD:

			if ((player1.getPositionY()) - 1 > 32) {
				return false;
			}
			break;

		case SUD:
			if ((player1.getPositionY() + 32) + 1 < (LONGUEUR_PLATEAU.getValeur() - 32)) {
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

		switch (tab[positionRow_neighbor][positionCol_neighbor]) {
		case SOL:
		case MUR:
		case PILIER:
			neighbor = new Decor(null, coordinateX_neigborPixel, coordinateY_neighboPixel, 32, 32);
			break;
		case BOMBE:
			neighbor = new Decor(null, coordinateX_neigborPixel, coordinateY_neighboPixel, 32, 32);
			break;
		default:
			neighbor = new Player(coordinateX_neigborPixel, coordinateY_neighboPixel, 32,32);
			break;
		}

		return neighbor;
	}

	
	public int checkFourNeighbor(int row, int col, Sprite player,Direction direction){
		int nb_intersections = 0;
		
		switch(direction){
			case SUD:
				if(row+1 < 17 && col-1 >= 0){
					Sprite neighborEast = createNeighborSprite(row+1, col-1);
					
					if(player.intersects(neighborEast)){
						System.out.print("E ");
						nb_intersections++;
					}
					
					neighborEast = createNeighborSprite(row, col-1);
					if(player.intersects(neighborEast)){
						System.out.print("E ");
						nb_intersections++;
					}
							
				}
				
				if(row+1 < 17 && col+1 < 23){
					Sprite neighborWest = createNeighborSprite(row+1,col+1);
					
					if(player.intersects(neighborWest)){
						System.out.print("W ");
						nb_intersections++;
					}
					
					neighborWest = createNeighborSprite(row, col+1);
					if(player.intersects(neighborWest)){
						System.out.print("W");
						nb_intersections++;
					}
				}
				
				
				break;
			case NORD:
				if(row-1 >= 0 && col-1 >= 0){
					Sprite neighborEast = createNeighborSprite(row-1, col-1);
					
					if(player.intersects(neighborEast)){
						System.out.print("East Collision ");
						nb_intersections++;
					}
					
					neighborEast = createNeighborSprite(row, col-1);
					if(player.intersects(neighborEast)){
						System.out.print("East Collision ");
						nb_intersections++;
					}
				}
				
				if(row-1 >= 0 && col+1 < 23){
					Sprite neighborWest = createNeighborSprite(row-1,col+1);
					
					if(player.intersects(neighborWest)){
						System.out.print("West Collision");
						nb_intersections++;
					}
					
					neighborWest = createNeighborSprite(row, col+1);
					if(player.intersects(neighborWest)){
						System.out.print("West Collision");
						nb_intersections++;
					}
				}
				break;
			case EST:
				if(row-1 >= 0 && col+1 < 23){
					Sprite neighborNorth = createNeighborSprite(row-1,col+1);
					
					if(player.intersects(neighborNorth)){
						System.out.print("Nord Collision ");
						nb_intersections++;
					}
					
					neighborNorth = createNeighborSprite(row-1, col);
					if(player.intersects(neighborNorth)){
						System.out.print("Nord Collision ");
						nb_intersections++;
					}
				}
				
				if(row+1 < 17 && col+1 < 23){
					Sprite neighborSouth = createNeighborSprite(row+1, col+1);
					if(player.intersects(neighborSouth)){
						System.out.print("Sud Collision ");
						nb_intersections++;
					}
					
					neighborSouth = createNeighborSprite(row+1, col);
					if(player.intersects(neighborSouth)){
						System.out.print("Nord Collision ");
						nb_intersections++;
					}
				}
				
				break;
			case OUEST:
				if(row-1 >= 0 && col-1 >= 0){
					Sprite neighborNorth = createNeighborSprite(row-1,col-1);
					
					if(player.intersects(neighborNorth)){
						System.out.print("Nord Collision ");
						nb_intersections++;
					}
					
					neighborNorth = createNeighborSprite(row-1, col);
					if(player.intersects(neighborNorth)){
						System.out.print("Nord Collision ");
						nb_intersections++;
					}
				}
				
				if(row+1 < 17 && col-1 >=0 ){
					Sprite neighborSouth = createNeighborSprite(row+1, col-1);
					
					if(player.intersects(neighborSouth)){
						System.out.print("Sud Collision ");
						nb_intersections++;
					}
					
					neighborSouth = createNeighborSprite(row+1, col);
					if(player.intersects(neighborSouth)){
						System.out.print("Nord Collision ");
						nb_intersections++;
					}
				}
				
				break;
		}
		
		System.out.println("N°inter " + nb_intersections);
		return nb_intersections;
	}
	
	public boolean is_any_collision(Player player1, Direction direction) {

		// Coordonnes x,y du joueur dans le tableau tab[][]
		int positionRow_player = playerByCoordinates.get(PLAYER1).x;
		int positionCol_player = playerByCoordinates.get(PLAYER1).y;

		int nb_intersections = 0;
		int positionRow_neighbor;
		int positionCol_neighbor;
		
		// Sprite representing the neighbor
		Sprite neighbor;

		switch (direction) {
		case NORD:

			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player - 1;
			positionCol_neighbor = positionCol_player;

			System.out.println("Position Row Neighbor " + positionRow_neighbor + " Position Col Neighbor " + positionCol_neighbor);

			if (positionRow_neighbor >= 0) {
				
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);

				// Simulation
				player1.move(0, -1);

				System.out.println("SIMU DONE");
				System.out.println("PLAYER1 COORDINATE IN PX : " + player1.getPositionX() + " " + player1.getPositionY());
				System.out.println("NEIHBOR COORDINATE IN PX : " + neighbor.getPositionX() + " " + neighbor.getPositionY());
				
				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, player1,NORD);
				// conditions : Vérification des collisions
				if (player1.intersects(neighbor) && nb_intersections == 0){
					System.out.println("Mono collision ");
					if (tab[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionRow_neighbor + 1) * 32;
						int max = (positionRow_neighbor + 2) * 32;
						System.out.println("Min : " + min + " Max : " + max);
						if (min <= player1.getPositionY() + 16 && player1.getPositionY() + 16 <= max) {
							System.out.println("CHANGE  SOL ");
							playerByCoordinates.put(PLAYER1, new Point(positionRow_player - 1, positionCol_player));
							tab[positionRow_player][positionCol_player] = SOL;
							tab[positionRow_neighbor][positionCol_neighbor] = PLAYER1;
						}

						return false;
					} else {
						System.out.println("NO SOL");
						player1.move(0, +1);
						return true;
					}
				}else if(player1.intersects(neighbor) && nb_intersections != 0){
					System.out.println("Poly collision ");
					player1.move(0, +1);
					return true;
				}
			}else if(positionRow_neighbor == -1){
				if (player1.getPositionY() >= 0){
					return false;
				}
					player1.move(0, +1); // cancel le mov
					return true;
			}
			System.out.println("NNNNN");
			return false; 
		case SUD:
			
			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player + 1;
			positionCol_neighbor = positionCol_player;

			if (positionRow_neighbor < 17) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);

				// Simulation
				player1.move(0, +1);
				System.out.println("SIMU DONE");
				System.out.println("PLAYER1 COORDINATE IN PX : " + player1.getPositionX() + " " + player1.getPositionY());
				System.out.println("NEIHBOR COORDINATE IN PX : " + neighbor.getPositionX() + " " + neighbor.getPositionY());
				
				//nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, player1,SUD);
				
				// Conditions : Vérification des collisions
				if (player1.intersects(neighbor) && nb_intersections == 0){
					System.out.println("Mono collision ");
					if (tab[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionRow_neighbor + 1) * 32;
						int max = (positionRow_neighbor + 2) * 32;
						System.out.println("Min : " + min + " Max : " + max);
						if (min <= player1.getPositionY() + 16 && player1.getPositionY() + 16 <= max) {
							System.out.println("CHANGE  SOL ");
							playerByCoordinates.put(PLAYER1, new Point(positionRow_player + 1, positionCol_player));
							tab[positionRow_player][positionCol_player] = SOL;
							tab[positionRow_neighbor][positionCol_neighbor] = PLAYER1;
						}
	
						return false;
						} else {
							System.out.println("NO SOL");
							player1.move(0, -1);
							return true;
						}
				}else if(player1.intersects(neighbor) && nb_intersections != 0){
					System.out.println("Poly collision ");
					player1.move(0, -1);
					return true;
				}
			}else if(positionRow_neighbor == 17){
				if (player1.getPositionY() + 32 <= Plateau.LONGUEUR_PLATEAU.getValeur()-32) {
					return false;
				}
					player1.move(0, -1); // cancel le mov
					return true;
			}
			System.out.println("SSSSS");
			return false; // s'il n'y a aucne intersection
			
		/*********************************************************************************************************/
		/*********************************************************************************************************/
			
		case EST:
			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player;
			positionCol_neighbor = positionCol_player + 1;

			if (positionCol_neighbor < 23 && positionRow_player + 1 < 17) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);
				System.out.println("SIMU DONE");
				System.out.println("PLAYER1 COORDINATE IN PX : " + player1.getPositionX() + " " + player1.getPositionY());
				System.out.println("NEIHBOR COORDINATE IN PX : " + neighbor.getPositionX() + " " + neighbor.getPositionY());
				
				// Simulation
				player1.move(+1, 0);

				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, player1,EST);

				// Conditions : Vérification des collisions
				if (player1.intersects(neighbor) && nb_intersections == 0){
					System.out.println("Mono collision");
					if (tab[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min =  (positionCol_neighbor + 1) * 32;
						int max =  (positionCol_neighbor + 2) * 32;
						System.out.println("Min : " + min + " Max : " + max);
						if (min <= player1.getPositionX() + 16 && player1.getPositionX() + 16 <= max ) {
							System.out.println("Le joueur change de position dans le plateau");
							playerByCoordinates.put(PLAYER1, new Point(positionRow_player, positionCol_player + 1));
							tab[positionRow_player][positionCol_player] = SOL;
							tab[positionRow_neighbor][positionCol_neighbor] = PLAYER1;
						}							
					} else {
						System.out.println("Pas de sol détéecté");
						player1.move(-1, 0); // cancel le mouvement
						return true;
					}
				// Le joueur rencontre multiple collisions (supposons tonneau + arbre)
				}else if(player1.intersects(neighbor) && nb_intersections != 0){
					System.out.println("Poly collisions");
					player1.move(-1, 0);
					return true;
				}
			}else if (positionCol_neighbor == 23) {
				if (player1.getPositionX() + 32 <= Plateau.LARGEUR_PLATEAU.getValeur()) {
					return false;
				}
				player1.move(-1, 0); // cancel le mouvement
				return true;
			}
			System.out.println("EEEEEE");
			return false; // Si le joueur n'a rencontré aucune collision
			
		/***********************************************************************************************************/
		/***********************************************************************************************************/
			
		case OUEST:
			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player;
			positionCol_neighbor = positionCol_player - 1;

			if (positionCol_neighbor >= 0) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);
				System.out.println("SIMU DONE");
				System.out.println("PLAYER1 COORDINATE IN PX : " + player1.getPositionX() + " " + player1.getPositionY());
				System.out.println("NEIHBOR COORDINATE IN PX : " + neighbor.getPositionX() + " " + neighbor.getPositionY());
				
				// Simulation
				player1.move(-1, 0);

				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, player1,OUEST);

				// Conditions : Vérification des collisions
				if (player1.intersects(neighbor) && nb_intersections == 0) {
					System.out.println("Mono collision");
					if (tab[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionCol_neighbor + 1) * 32;
						int max = (positionCol_neighbor + 2) * 32;
						System.out.println("Min : " + min + " Max : " + max);
						if (min <= player1.getPositionX() + 16 && player1.getPositionX() + 16 <= max) {
							System.out.println("CHANGE  SOL ");
							playerByCoordinates.put(PLAYER1, new Point(positionRow_player, positionCol_player - 1));
							tab[positionRow_player][positionCol_player] = SOL;
							tab[positionRow_neighbor][positionCol_neighbor] = PLAYER1;
						
						}
					} else {
							System.out.println("NO SOL");
							player1.move(+1, 0);
							return true;
					}
					
				 // Le joueur rencontre multiple collisions (supposons tonneau + arbre)
				}else if(player1.intersects(neighbor) && nb_intersections != 0){
						System.out.println("Poly collisions");
						player1.move(+1, 0);
						return true;
				}
			}else if (positionCol_neighbor == -1) {
				if (player1.getPositionX() >= 0){return false;}
				player1.move(+1, 0); // cancel le mov
				return true;
			}
		}	
		System.out.println("FFFFF");
		return false;
	}
			
	public void start() {
		gui.setController(this); // Initialisation du controleur cote vue
		gui.start(canvas, canvas2, tab, key); //
	}

	public boolean onPressedRight(Player player1) {

		System.out.println(player1.getPositionX() + " " + player1.getPositionY());
		if (exceed_fences(player1, EST)) {
			return true;
		}
		return is_any_collision(player1, EST);
	}

	public boolean onPressedLeft(Player player1) {
		System.out.println(player1.getPositionX() + " " + player1.getPositionY());
		// Si la méthode check_pillars_collisions renvoie false
		if (exceed_fences(player1, OUEST)) {
			// Il existe une collision donc pas de déplacement
			return true;
		}
		return is_any_collision(player1, OUEST);

	}

	public boolean onPressedUp(Player player1) {
		System.out.println(player1.getPositionX() + " " + player1.getPositionY());
		if (exceed_fences(player1, NORD)) {
			return true;
		}
		return is_any_collision(player1, NORD);
	}

	public boolean onPressedDown(Player player1) {
		System.out.println(player1.getPositionX() + " " + player1.getPositionY());
		
		if (exceed_fences(player1, SUD)) {
			return true;
		}
		System.out.println("hhh");
		return is_any_collision(player1, SUD);
	}
}
