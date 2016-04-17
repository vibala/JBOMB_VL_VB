package com.fr.ece.jbomb.model;

import static com.fr.ece.jbomb.model.Plateau.BOMBE;
import static com.fr.ece.jbomb.model.Plateau.CRAME;
import static com.fr.ece.jbomb.model.Plateau.LARGEUR_PLATEAU;
import static com.fr.ece.jbomb.model.Plateau.LONGUEUR_PLATEAU;
import static com.fr.ece.jbomb.model.Plateau.MUR;
import static com.fr.ece.jbomb.model.Plateau.PILIER;
import static com.fr.ece.jbomb.model.Plateau.SOL;
import static com.fr.ece.jbomb.view.Direction.EST;
import static com.fr.ece.jbomb.view.Direction.NORD;
import static com.fr.ece.jbomb.view.Direction.OUEST;
import static com.fr.ece.jbomb.view.Direction.SUD;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fr.ece.jbomb.controller.BombTimer;
import com.fr.ece.jbomb.controller.FireTimer;
import com.fr.ece.jbomb.server.Server;
import com.fr.ece.jbomb.view.Direction;
/**
 * Classe permettant de stocker les informations (config plateau + liste des joueurs) à transmettre au client
 * @author Vignesh BALA && Vincent LIM
 * @version 1.0
 **/
public class ConfFromServer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Player> listPlayer;
	private Plateau plateau[][];
	private transient Map<Plateau, Point> playerByCoordinates;
	private transient int ID_BOMB = 0;
	private boolean recommencer=false; //Var globale dans la classe

	/**
	 * Constructeur
	 **/
	public ConfFromServer() {
		playerByCoordinates = new HashMap<Plateau, Point>();
		listPlayer = new ArrayList<Player>();
		this.plateau = new Plateau[17][23];
		ajout_piliers_sols();
		ajout_murs();
		ajout_players();
		update_array_plateau();
	}

	/**
	 * Retourne le plateau 
	 * @return plateau Plateau de jeu
	 **/
	public Plateau[][] getPlateau() {
		return plateau;
	}

	/**
	 * Retourne la liste des joueurs 
	 * @return listPlayer Liste des joueurs
	 **/
	public List<Player> getListPlayer() {
		return listPlayer;
	}

	/**
	 * Ajoute les joueurs dans le tableau chargé en mémoire 
	 **/
	private void ajout_players() {
		plateau[16][22] = Plateau.PLAYER1;		
		plateau[0][0] = Plateau.PLAYER2;
		plateau[16][0] = Plateau.PLAYER3;
		plateau[0][22] = Plateau.PLAYER4;		
	}

	/**
	 * Ajoute les piliers et le sol dans le tableau chargé en mémoire 
	 **/
	private void ajout_piliers_sols() {
		// Mise en forme du sol dans le tableau plateau
		// //System.out.println("ajout_piliers_sols : Entree");
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
		
	}

	/**
	 * Ajoute les murs particuliers (murs présents sur le premier et le dernier rang en horizontal et en vertical)
	 * dans le tableau chargé en mémoire 
	 **/
	private void gestion_murs_particuliers(int nb_murs, Direction direction) {
		// //System.out.println("gestion_murs_particuliers : Entree");
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
				// //System.out.println(random_numbers.size());
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
		//System.out.println("gestion_murs_particuliers : Sortie");

	}

	/**
	 * Ajoute les autres murs dans le tableau chargé en mémoire 
	 **/
	private void ajout_murs() {
		//System.out.println("ajout_murs : Entree");
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
						rand_num = rand.nextInt(10) * 2;
						if (!random_numbers.contains(rand_num)) {
							random_numbers.add(rand_num);
							plateau[i][rand_num + 2] = MUR;
							cds = true;
						}
					}
				}
				random_numbers.clear();
			} else {
				nb_murs = rand.nextInt(12) + 10; 
				for (int j = 0; j < nb_murs; j++) {
					cds = false;
					while (!cds) {
						rand_num = rand.nextInt(21); 
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
	}

	
	/**
	 * Ajoute le joueur passé en paramètre dans la liste des joueurs
	 * @param p Player 
	 **/
	public void addPlayer(Player p) {
		listPlayer.add(p);
	}

	/**
	 * Met à jour les configurations du plateau et de la liste des joueurs souhaitées par le client
	 * et retourne les nouvelles configurations 
	 * @param p Player 
	 * @return confFromServer Configuration du plateau et de la liste des joueurs
	 **/
	public ConfFromServer update(ConfToServer confToServer) {

		Player player = listPlayer.get(confToServer.idPlayer - 1);

		if (confToServer.getKeyList().contains("RIGHT")) {
			player.setDirectionPourSavoirQuelleImageAfficher("RIGHT");
			if (!onPressedRight(player)) {
				player.move(5, 0);
			}
		}

		if (confToServer.getKeyList().contains("LEFT")) {
			player.setDirectionPourSavoirQuelleImageAfficher("LEFT");
			if (!onPressedLeft(player)) {
				player.move(-5, 0);
			}
		}
		
		if (confToServer.getKeyList().contains("UP")) {
			player.setDirectionPourSavoirQuelleImageAfficher("UP");
			if (!onPressedUp(player)) {
				player.move(0, -5);
			}
		}

		if (confToServer.getKeyList().contains("DOWN")) {
			player.setDirectionPourSavoirQuelleImageAfficher("DOWN");
			if (!onPressedDown(player)) {
				player.move(0, 5);
			}
		}

		if (confToServer.getKeyList().contains("B")) {
			onPressedBomb(player);
		}
		
		explosionDesBombes();
		cleanFire();
		return this;
	}
	
	/**
	 * Actualise les endroits impactés par l'explosition de la bombe 
	 * 
	 **/
	public void cleanFire(){
		for(FireTimer f:Server.listFire){
			if(f.getBurned()){
				plateau[f.getI()][f.getJ()]=SOL;
				f.setBurned(false); // Il ne brule plus
			}
		}
		
	}

	/**
	 * Met à jour le tableau (chargé en mémoire) representant le plateau de jeu 
	 **/
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
			}
		}
	}

	/**
	 * Crée le sprite associé au voisin du joueur
	 * @return  neighbor Sprite voisin
	 * @param positionRow_neighbor Position ligne du voisin dans le tableau chargé en mémoire
 	 * @param positionCol_neighbor Position colonne du voisin dans le tableau chargé en mémoire
	 **/
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

	/**
	 * Vérifie les collisions du joueur avec les voisins de proximité
	 * @return  nb_intersections Nombre de collisions
	 * @param row Position ligne du joueur dans le tableau chargé en mémoire
 	 * @param col Position colonne du joueur dans le tableau chargé en mémoire
 	 * @param player Sprite associé au joueur
 	 * @param direction Direction empruntée par le joueur
	 **/
	public int checkFourNeighbor(int row, int col, Sprite player, Direction direction) {
		int nb_intersections = 0;

		switch (direction) {
		case SUD:
			if (row + 1 < 17 && col - 1 >= 0) {
				Sprite neighborEast = createNeighborSprite(row + 1, col - 1);

				if (player.intersects(neighborEast)) {
					nb_intersections++;
				}

				neighborEast = createNeighborSprite(row, col - 1);
				if (player.intersects(neighborEast)) {
					nb_intersections++;
				}

			}

			if (row + 1 < 17 && col + 1 < 23) {
				Sprite neighborWest = createNeighborSprite(row + 1, col + 1);

				if (player.intersects(neighborWest)) {
					nb_intersections++;
				}

				neighborWest = createNeighborSprite(row, col + 1);
				if (player.intersects(neighborWest)) {
					nb_intersections++;
				}
			}

			break;
		case NORD:
			if (row - 1 >= 0 && col - 1 >= 0) {
				Sprite neighborEast = createNeighborSprite(row - 1, col - 1);

				if (player.intersects(neighborEast)) {
					nb_intersections++;
				}

				neighborEast = createNeighborSprite(row, col - 1);
				if (player.intersects(neighborEast)) {
						nb_intersections++;
				}
			}

			if (row - 1 >= 0 && col + 1 < 23) {
				Sprite neighborWest = createNeighborSprite(row - 1, col + 1);

				if (player.intersects(neighborWest)) {				
					nb_intersections++;
				}

				neighborWest = createNeighborSprite(row, col + 1);
				if (player.intersects(neighborWest)) {					
					nb_intersections++;
				}
			}
			break;
		case EST:
			if (row - 1 >= 0 && col + 1 < 23) {
				Sprite neighborNorth = createNeighborSprite(row - 1, col + 1);

				if (player.intersects(neighborNorth)) {
					nb_intersections++;
				}

				neighborNorth = createNeighborSprite(row - 1, col);
				if (player.intersects(neighborNorth)) {
					nb_intersections++;
				}
			}

			if (row + 1 < 17 && col + 1 < 23) {
				Sprite neighborSouth = createNeighborSprite(row + 1, col + 1);
				if (player.intersects(neighborSouth)) {
					nb_intersections++;
				}

				neighborSouth = createNeighborSprite(row + 1, col);
				if (player.intersects(neighborSouth)) {
					nb_intersections++;
				}
			}

			break;
		case OUEST:
			if (row - 1 >= 0 && col - 1 >= 0) {
				Sprite neighborNorth = createNeighborSprite(row - 1, col - 1);

				if (player.intersects(neighborNorth)) {
					nb_intersections++;
				}

				neighborNorth = createNeighborSprite(row - 1, col);
				if (player.intersects(neighborNorth)) {
					nb_intersections++;
				}
			}

			if (row + 1 < 17 && col - 1 >= 0) {
				Sprite neighborSouth = createNeighborSprite(row + 1, col - 1);

				if (player.intersects(neighborSouth)) {
					nb_intersections++;
				}

				neighborSouth = createNeighborSprite(row + 1, col);
				if (player.intersects(neighborSouth)) {
					nb_intersections++;
				}
			}

			break;
		}

		return nb_intersections;
	}

	/**
	 * Methode invoquée lorsque le joueur dépose une bombe
	 * @return true/false Autorisation du dépôt de la bombe
	 **/
	private boolean onPressedBomb(Player player) {
		if(player.getBomb()==0){
			return true;
		}
		
		//Recup case devant joueur
		int deltaX=0;
		int deltaY=0;
		// Se fait en fonction de l'orientation de limage du player et si la case devan lui est vide
		
		if(player.getDirectionPourSavoirQuelleImageAfficher().equals("UP")) deltaX--;
		if(player.getDirectionPourSavoirQuelleImageAfficher().equals("DOWN")) deltaX++;
		if(player.getDirectionPourSavoirQuelleImageAfficher().equals("RIGHT")) deltaY++;
		if(player.getDirectionPourSavoirQuelleImageAfficher().equals("LEFT")) deltaY--;
		
			
		int i =(int) (playerByCoordinates.get(Plateau.getNomByValeur(player.getID())).getX()+deltaX);
		int j =(int) (playerByCoordinates.get(Plateau.getNomByValeur(player.getID())).getY()+deltaY);
	
		if(0<=i&& i<=16 && 0<=j && j<= 22 && plateau[i][j]==SOL)
		{
			plateau[i][j]=BOMBE;
			//player.setBomb(player.getBomb()-1);
			//System.out.println("Bombe posé!");
			//Ajouter la bombe dans la liste des bombes sur le serveur
			int idBomb=ID_BOMB;
			ID_BOMB++;
			Bomb bomb=new Bomb(idBomb,i,j);
			//System.out.println("pos" + i+j);
			Server.addListBomb(new Point(i,j),bomb);
			
			new Thread(new BombTimer(i,j)).start();
			
			return false;//Il n'y a rien devant toi player tu peux poser une bombe => renvoyer false
		}
		else return true;
	}
	
   
	/**
	 * Retire le joueur du plateau une fois qu'il est touché par une bombe
	 **/
	public void removePlayer(int i, int j){
		{
			if(plateau[i][j]==Plateau.BOMBE){
				System.out.println("plateau"+i+ " "+j);
				 int idBomb=Server.listBomb.get(new Point(i,j)).getID();
				 System.out.println("plateau"+i+ " OK "+j);
					Server.listBomb.get(new Point(i,j)).setID(idBomb+50000);// Les bombes qui ont pété ont un id > 50000
			recommencer =true;
			return;
			}else {
			if(plateau[i][j]==Plateau.PLAYER1){
				listPlayer.get(0).setPositionX(-1000);
			listPlayer.get(0).setPositionY(-1000);
			}
			else if(plateau[i][j]==Plateau.PLAYER2){
				listPlayer.get(1).setPositionX(-1000);
			listPlayer.get(1).setPositionY(-1000);
			}	else if(plateau[i][j]==Plateau.PLAYER3){
				listPlayer.get(2).setPositionX(-1000);
			listPlayer.get(2).setPositionY(-1000);
			}
			else if(plateau[i][j]==Plateau.PLAYER4){
				listPlayer.get(3).setPositionX(-1000);
			listPlayer.get(3).setPositionY(-1000);
			}
			else if(plateau[i][j]==Plateau.PLAYER4){
				listPlayer.get(3).setPositionX(-1000);
			listPlayer.get(3).setPositionY(-1000);
			}
			else if(plateau[i][j]==Plateau.MUR|plateau[i][j]==CRAME|plateau[i][j]==Plateau.SOL){
			}
			
			plateau[i][j]=CRAME;//CRAME
			FireTimer fire =new FireTimer(i,j);
			Server.listFire.add(fire);
			new Thread(fire).start();
			}	
		}
	}
	
	/**
	 * Implemente l'explosion de la bombe
	 **/
	public void boucleExplosion(){
		int i;
		int j;
		   Iterator<?> it = Server.listBomb.entrySet().iterator(); //Exception ou on ne passe pas par le getListBomb car on sait que c'est nous qui avons le jeton
		    while (it.hasNext()) {
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();
		    	if(((Bomb)pair.getValue()).getID()>=10000){ //car id bomb qui pète >= 100
		    		i=(int) ((Bomb)pair.getValue()).getX();
		    		j=(int) ((Bomb)pair.getValue()).getY();
					if(0<=i&& i<=16 && 0<=j && j<= 22)
					{	
					plateau[i][j]=CRAME;//CRAME
					FireTimer fire =new FireTimer(i,j);
					Server.listFire.add(fire);
					new Thread(fire).start();
					it.remove(); //On suprime la bombe
					}
					i=i+1;				
					if(0<=i&& i<=16 && 0<=j && j<= 22  && (plateau[i][j]==CRAME|plateau[i][j]==SOL|plateau[i][j]==BOMBE|plateau[i][j]==MUR|plateau[i][j]==Plateau.PLAYER1|plateau[i][j]==Plateau.PLAYER2|plateau[i][j]==Plateau.PLAYER3|plateau[i][j]==Plateau.PLAYER4))
						removePlayer(i,j);
					i=i-1-1;
					if(0<=i&& i<=16 && 0<=j && j<= 22  && (plateau[i][j]==CRAME|plateau[i][j]==SOL|plateau[i][j]==BOMBE|plateau[i][j]==MUR|plateau[i][j]==Plateau.PLAYER1|plateau[i][j]==Plateau.PLAYER2|plateau[i][j]==Plateau.PLAYER3|plateau[i][j]==Plateau.PLAYER4))
						removePlayer(i,j);
					i=i+1;
					j=j-1;
					if(0<=i&& i<=16 && 0<=j && j<= 22  && (plateau[i][j]==CRAME|plateau[i][j]==SOL|plateau[i][j]==BOMBE|plateau[i][j]==MUR|plateau[i][j]==Plateau.PLAYER1|plateau[i][j]==Plateau.PLAYER2|plateau[i][j]==Plateau.PLAYER3|plateau[i][j]==Plateau.PLAYER4))
						removePlayer(i,j);
					j=j+2;
					if(0<=i&& i<=16 && 0<=j && j<= 22  && (plateau[i][j]==CRAME|plateau[i][j]==SOL|plateau[i][j]==BOMBE|plateau[i][j]==MUR|plateau[i][j]==Plateau.PLAYER1|plateau[i][j]==Plateau.PLAYER2|plateau[i][j]==Plateau.PLAYER3|plateau[i][j]==Plateau.PLAYER4))
						removePlayer(i,j);
					
				}
		    }
		  	if(recommencer) {
	    		recommencer=false;
	    		boucleExplosion();
	    	}
	}
	/**
	 * Invoque la méthode boucleExplosion en mode synchrone (un seul thread ne peut
	 * accéder à cette méthode à la fois)
	 *  
	 **/
	public void explosionDesBombes() {
		synchronized (Server.JETON_BOMB) {
			boucleExplosion();
		}
	}

	
	/**
	 * Vérifie les collisions avec les barrières délimitant le plateau
	 * @param player Sprite associé au joueur
 	 * @param direction Direction empruntée par le joueur
 	 * @return true/false Collision avec les barrières
	 **/
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

	/**
	 * Vérifie les collisions du joueur avec les voisins de proximité et valide le passage
	 * du joueur d'une case à une autre
	 * @return true/false Autorisation du déplacement du joueur
 	 * @param player Sprite associé au joueur
 	 * @param direction Direction empruntée par le joueur
	 **/
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

			if (positionRow_neighbor >= 0) {

				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);
				// Simulation
				simul.move(0, -5);
				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, simul, NORD);
				// Conditions : Vérification des collisions
				if (simul.intersects(neighbor) && nb_intersections == 0) {
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionRow_neighbor + 1) * 32;
						int max = (positionRow_neighbor + 2) * 32;
						if (min <= simul.getPositionY() + 16 && simul.getPositionY() + 16 <= max) {
							playerByCoordinates.put(Plateau.getNomByValeur(player.getID()),new Point(positionRow_player - 1, positionCol_player));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = Plateau.getNomByValeur(player.getID());
						}
					
					} else {
						simul.move(0, +5);
						return true;
					}
				} else if (nb_intersections != 0) {
					simul.move(0, +5);
					return true;
				}

			} else if (positionRow_neighbor == -1) {
				if (player.getPositionY() < 0) {
					simul.move(0, +5); // cancel le mov
					return true;
				}
			}
			return false;

		case SUD:

			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player + 1;
			positionCol_neighbor = positionCol_player;
			if (positionRow_neighbor < 17) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);

				// Simulation
				simul.move(0, +5);
				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, simul, SUD);

				// Conditions : Vérification des collisions
				if (simul.intersects(neighbor) && nb_intersections == 0) {
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionRow_neighbor + 1) * 32;
						int max = (positionRow_neighbor + 2) * 32;

						if (min <= simul.getPositionY() + 16 && simul.getPositionY() + 16 <= max) {
							playerByCoordinates.put(Plateau.getNomByValeur(player.getID()),
									new Point(positionRow_player + 1, positionCol_player));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = Plateau
									.getNomByValeur(player.getID());
						}
						return false;
					} else {
						simul.move(0, -5);
						return true;
					}
				} else if (nb_intersections != 0) {
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

		case EST:
			// Coordonnes x,y du voisin dans le tableau tab[][]
			positionRow_neighbor = positionRow_player;
			positionCol_neighbor = positionCol_player + 1;

			if (positionCol_neighbor < 23) {
				neighbor = createNeighborSprite(positionRow_neighbor, positionCol_neighbor);
				// Simulation
				simul.move(+5, 0);
				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, simul, EST);

				// Conditions : Vérification des collisions
				if (simul.intersects(neighbor) && nb_intersections == 0) {
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionCol_neighbor + 1) * 32;
						int max = (positionCol_neighbor + 2) * 32;
						if (min <= simul.getPositionX() + 16 && simul.getPositionX() + 16 <= max) {
							playerByCoordinates.put(Plateau.getNomByValeur(player.getID()),
									new Point(positionRow_player, positionCol_player + 1));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = Plateau
									.getNomByValeur(player.getID());
						}
						return false;
					} else {
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
				// Simulation
				simul.move(-5, 0);

				nb_intersections = checkFourNeighbor(positionRow_player, positionCol_player, player, OUEST);

				// Conditions : Vérification des collisions
				if (simul.intersects(neighbor) && nb_intersections == 0) {
					if (plateau[positionRow_neighbor][positionCol_neighbor] == SOL) {
						int min = (positionCol_neighbor + 1) * 32;
						int max = (positionCol_neighbor + 2) * 32;
						if (min <= simul.getPositionX() + 16 && simul.getPositionX() + 16 <= max) {
							playerByCoordinates.put(Plateau.getNomByValeur(player.getID()),
									new Point(positionRow_player, positionCol_player - 1));
							plateau[positionRow_player][positionCol_player] = SOL;
							plateau[positionRow_neighbor][positionCol_neighbor] = Plateau
									.getNomByValeur(player.getID());
							return false;

						}
					} else {
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
		return false;
	}

	/**
	 * Méthode invoqué lorsque le joueur appuie sur la touche droite du pavé directionnel
	 * @param player Sprite associé au joueur
	 * @return  true/false Droit du déplacement
	 **/
	private boolean onPressedRight(Player player) {

		if (exceed_fences(player, EST)) {
			return true;
		}
		return is_any_collision(player, EST);
	}

	/**
	 * Méthode invoqué lorsque le joueur appuie sur la touche gauche du pavé directionnel
	 * @param player Sprite associé au joueur
	 * @return  true/false Droit du déplacement
	 **/
	private boolean onPressedLeft(Player player) {
		if (exceed_fences(player, OUEST)) {
			return true;
		}
		return is_any_collision(player, OUEST);

	}

	/**
	 * Méthode invoqué lorsque le joueur appuie sur la touche haut du pavé directionnel
	 * @param player Sprite associé au joueur
	 * @return  true/false Droit du déplacement
	 **/
	private boolean onPressedUp(Player player) {
		if (exceed_fences(player, NORD)) {
			return true;
		}
		return is_any_collision(player, NORD);
	}

	/**
	 * Méthode invoqué lorsque le joueur appuie sur la touche bas du pavé directionnel
	 * @param player Sprite associé au joueur
	 * @return  true/false Droit du déplacement
	 **/
	private boolean onPressedDown(Player player) {
		if (exceed_fences(player, SUD)) {
			return true;
		}
		return is_any_collision(player, SUD);
	}
	/**
	 * Mettre a jour les joueurs qui se sont déconnectés
	 * @param idPlayer
	 */
	public void updateDisconnectedPlayer(int idPlayer) {
		listPlayer.get(idPlayer-1).setPositionX(-1000);
		listPlayer.get(idPlayer-1).setPositionY(-1000);
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 23; j++) {
			
					if(plateau[i][j] == Plateau.getNomByValeur(idPlayer))
						{
						plateau[i][j]=SOL;
						return;
						}
			}
		}
	}

}
