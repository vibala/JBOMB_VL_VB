package com.fr.ece.jbomb.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.fr.ece.jbomb.controller.BufferedReaderChat;
import com.fr.ece.jbomb.controller.ObjectInputStreamChat;
import com.fr.ece.jbomb.controller.ObjectOutputStreamChat;
import com.fr.ece.jbomb.controller.PrintWriterChat;
import com.fr.ece.jbomb.model.ConfToServer;
import com.fr.ece.jbomb.model.Player;

/**
 * Classe Thread gérant chaque connexion cliente
 * @author Vignesh BALA && Vincent LIM
 * @version 1.0
 **/
public class ConnectionHandler implements Runnable {

	private Player player;
	private Socket socketServerforClient;
	private ObjectInputStreamChat ois;
	private ObjectOutputStreamChat oos;
	private BufferedReaderChat reader;
	private PrintWriterChat writer;
	
	/**
	 * Constructeur initialisant le joueur et le socket d'échange du serveur
	 * @param socketServerforClient Socket d'échange
	 * @param player Joueur
	 **/
	public ConnectionHandler(Socket socketServerforClient,Player player) {
		this.socketServerforClient = socketServerforClient;
		this.player=player;
	}

	/**
	 * Implemente l'échange instantanée des données entre le client et le serveur
	 **/
	public void run() {
		try {
			//Init
			reader = new BufferedReaderChat(new InputStreamReader(socketServerforClient.getInputStream()));
		 	writer = new PrintWriterChat(new OutputStreamWriter(socketServerforClient.getOutputStream()));
		 	ois = new ObjectInputStreamChat(socketServerforClient.getInputStream(), writer);
			oos = new ObjectOutputStreamChat(socketServerforClient.getOutputStream(), reader);
			
			//Présentation
			 writer.envoyer("Bienvenue sur le Serveur BomberMan, Version 1.0"); //Envoi - Banniere 1.0
			 oos.envoyer(player); //Envoi -Informer le client sur le personnage qu'il controlle 2.0 (inutile pour le client pour l'instant car il va juste rafraichir son image graphique du jeu)
							 
			//ATTENDRE QUE TOUS LES JOUEURS soient connectés
			// while(Server.getListConnectionHandler().size()!=4){}
			 			 
			 //Boucle de réponse au client
			 while(!socketServerforClient.isClosed()){
				
				ConfToServer confToServer=(ConfToServer) ois.lire(); //Lecture confToServeur 3.0	
				if (confToServer==null) {
					Server.updateDisconnectedPlayer(player.getID());
					break;
				}
				//Traitement + Envoi de la configuration sur le tableau
				oos.envoyer(Server.updateConfFromServer(confToServer));//Envoi de la configuration actuel sur le serveur (même si d'autres joueurs l'ont modifié entre l'update et l'envoit c'est pas grv car les changement du joueur ont été pris en comtpe)
			
			 }
		

		} catch (IOException e1) {
			
		} catch (ClassNotFoundException e) {
		}finally{
			System.out.println("Le joueur s'est déconnecté");
			 System.out.println("La connexion est maintenant terminée pour le client "+player.getID());
				try {
					socketServerforClient.close();
				} catch (IOException e) {
				}
		}
	}

}
