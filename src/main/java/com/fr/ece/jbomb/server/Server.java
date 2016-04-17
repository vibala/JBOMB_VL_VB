package com.fr.ece.jbomb.server;

import java.awt.Point;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fr.ece.jbomb.controller.FireTimer;
import com.fr.ece.jbomb.model.Bomb;
import com.fr.ece.jbomb.model.ConfFromServer;
import com.fr.ece.jbomb.model.ConfToServer;
import com.fr.ece.jbomb.model.Player;

/**
 * Classe representant le serveur du jeu Bomberman
 * @author Vignesh BALA && Vincent LIM
 * @version 1.0
 **/
public class Server {
	private InetSocketAddress ipServer;
	private int nbMaxConnection;
	private ServerSocket server;
	public static Map<Point,Bomb> listBomb = new HashMap<Point,Bomb>();
	public static List<FireTimer> listFire = new ArrayList<FireTimer>();
	private static List<ConnectionHandler> listConnectionHandler = new ArrayList<ConnectionHandler>(); // utile pour chat privée 
	public static boolean endOftheGame=false;	
	private static ConfFromServer confFromServer=new ConfFromServer();
	private static Object JETON_CONF =  new Object();
	public static Object JETON_BOMB =  new Object();
	
	/**
	 * Constructeur
	 * @param nbMaxConn nombre maximal de connexions
	 * @param ipServerArg adresse IP du serveur
	 * @exception IOException si les entrées/sorties sont corrompues
	 **/
	public Server(int nbMaxConn, InetSocketAddress ipServerArg) throws IOException{
		ipServer = ipServerArg;
		nbMaxConnection = nbMaxConn;
		server = new ServerSocket();
		System.out.println("ServerSocket créé mais non bindé: done");
		server.bind(ipServer, nbMaxConnection);
		System.out.println("Server bindé: " + server.toString());
	}

	/**
	 * Démarre le serveur du jeu qui se charge d'accepter les connexions puis laisse la main 
	 * à la classe ConnectionHandler pour gérer chaque connexion cliente
	 * @exception InterruptedException si le thread a été interrompu
	 * @exception IOException si les entrées/sorties sont corrompues
	 **/
	public void start() throws InterruptedException, IOException {
		while (!endOftheGame) {
			// Accepter connexion ssi nb joueur < ou = à 4
			if (listConnectionHandler.size() <= 4) {

				// Attente de connexion
				Socket socketServerforClient = server.accept();
				System.out.println(socketServerforClient.toString());

				//Création du joueur
				Player player = null;
				if(listConnectionHandler.size()+1==1)  player=new Player(1,736-1, 544-1, 30, 30); 
				else if(listConnectionHandler.size()+1==2)  player=new Player(2,32-1, 32-1, 30, 30);
				else if(listConnectionHandler.size()+1==3)  player=new Player(3,736-1, 32-1, 30, 30);
				else if(listConnectionHandler.size()+1==4)  player=new Player(4,32-1, 544-1, 30, 30);

				confFromServer.addPlayer(player); // On ajoute le joueur à la liste des joueurs
				
				// Lancement d'une connexion privé avec le client
				ConnectionHandler handler = new ConnectionHandler(socketServerforClient,player);
				listConnectionHandler.add(handler); //  On ajoute la socket à la liste des sockets client du serveur
				new Thread(handler).start(); // On lance un thread
			} else {

			}
		}
		
	// La fermeture du server est innateignable pour l'instant
	server.close();
		
	}

	/**
	 * Récupère la liste des connexions clientes
	 * @return listConnectionsHandler liste des connexions clientes
	 **/
	//Appelé dans les connectionHandler pour savoir si les 4 joueurs se sont connectés
	public static List<ConnectionHandler> getListConnectionHandler(){
		return listConnectionHandler;
	}
	
	/**
	 * Ajoute une nouvelle bombe dans la liste des bombes
	 * Cette méthode statique ne pourra être accédée que par un seul thread à la fois (cf.synchronised)
	 * @param p Coordonnées de la bombe placée sur le plateau
	 * @param b Bombe 
	 **/	
	//Appelé dans les connectionHandler pour savoir si les 4 joueurs se sont connectés
	public static void addListBomb(Point p , Bomb b){
		  synchronized(JETON_BOMB) {listBomb.put(p, b);}
	}
	
	/**
	 * Met à jour la liste des bombes
	 * Cette méthode statique ne pourra être accédée que par un seul thread à la fois (cf.synchronised)
	 * @param i Coordonne ligne de la bombe sur le plateau
	 * @param j Coordonne colonne de la bombe sur le plateau
	 **/
	public static void updateListBomb(int i, int j){
		  synchronized(JETON_BOMB) {
			  Point keyBomb=new Point(i,j);
			  if(!Server.listBomb.containsKey(keyBomb)) return; // s'il ne l'a pas c'est que la bombe a explosé dans la conf par une autre bombe
			  
				Bomb bombeAFairePeter=Server.listBomb.get(keyBomb);
				bombeAFairePeter.setID(bombeAFairePeter.getID()+10000); // Les bombes qui ont pété ont un id > 10000
				   }
	}

	/**
	 * Met à jour les configurations du plateau et de la liste des joueurs envoyées par le client
	 * puis renvoie les nouvelles configurations
	 * Cette méthode statique ne pourra être accédée que par un seul thread à la fois (cf.synchronised)
	 * @param confToServer Configurations du plateau et de la liste des joueurs modifiées par le client 
	 * @return confFromServer Configuration du plateau et de la liste des joueurs actualisées par le serveur
	 **/
	//La methode est static car pas besoin de l'instancier
	//NE PAS ACCEDER a ConfFromServer autrement que par cette fonction update (une exception a été faite dans le start)
	//Cette méthode est appelé dans les connectionHandlers pour mettre à jour la conf dur le serveur
	public static ConfFromServer updateConfFromServer(ConfToServer confToServer) {
		   synchronized(JETON_CONF) {
			   	return confFromServer.update(confToServer);
		   }
	}
}