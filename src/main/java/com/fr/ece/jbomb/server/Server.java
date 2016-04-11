package com.fr.ece.jbomb.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.fr.ece.jbomb.model.ConfFromServer;
import com.fr.ece.jbomb.model.ConfToServer;
import com.fr.ece.jbomb.model.Player;


public class Server {
	private InetSocketAddress ipServer;
	private int nbMaxConnection;
	private ServerSocket server;

	private static List<ConnectionHandler> listConnectionHandler = new ArrayList<ConnectionHandler>(); // utile pour chat privée 
	public static boolean endOftheGame=false;
	
	private static ConfFromServer confFromServer=new ConfFromServer();
	private static Object JETON_CONF =  new Object();
	
	public Server(int nbMaxConn, InetSocketAddress ipServerArg) throws IOException{
		ipServer = ipServerArg;
		nbMaxConnection = nbMaxConn;
		server = new ServerSocket();
		System.out.println("ServerSocket créé mais non bindé: done");
		server.bind(ipServer, nbMaxConnection);
		System.out.println("Server bindé: " + server.toString());
	}

	public void start() throws InterruptedException, IOException {
		while (!endOftheGame) {
			// Accepter connexion ssi nb joueur < ou = à 4
			if (listConnectionHandler.size() <= 4) {

				// Attente de connexion
				Socket socketServerforClient = server.accept();
				System.out.println(socketServerforClient.toString());

				//Création du joueur
				Player player = null;
				if(listConnectionHandler.size()+1==1)  player=new Player(1,32, 32, 18, 24); 
				else if(listConnectionHandler.size()+1==2)  player=new Player(2,736, 32, 18, 24);
				else if(listConnectionHandler.size()+1==3)  player=new Player(3,32, 536, 18, 24);
				else if(listConnectionHandler.size()+1==4)  player=new Player(4,736, 536, 18, 24);

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

	//Appelé dans les connectionHandler pour savoir si les 4 joueurs se sont connectés
	public static List<ConnectionHandler> getListConnectionHandler(){
		return listConnectionHandler;
	}
	
	//La methode est static car pas besoin de l'instancier
	//NE PAS ACCEDER a ConfFromServer autrement que par cette fonction update (une exception a été faite dans le start)
	//Cette méthode est appelé dans les connectionHandlers pour mettre à jour la conf dur le serveur
	public static ConfFromServer updateConfFromServer(ConfToServer confToServer) {
		   synchronized(JETON_CONF) {
		return confFromServer.update(confToServer);
		   }
	}
}
