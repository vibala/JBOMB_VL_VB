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

public class ConnectionHandler implements Runnable {

	private Player player;

	private Socket socketServerforClient;
	private ObjectInputStreamChat ois;
	private ObjectOutputStreamChat oos;
	private BufferedReaderChat reader;
	private PrintWriterChat writer;
	
	public ConnectionHandler(Socket socketServerforClient,Player player) {
		this.socketServerforClient = socketServerforClient;
		this.player=player;
	}

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
			 while(!Server.endOftheGame){
				ConfToServer confToServer=(ConfToServer) ois.lire(); //Lecture confToServeur 3.0
				 //Traitement + Envoi de la configuration sur le tableau
				oos.envoyer(Server.updateConfFromServer(confToServer));//Envoi de la configuration actuel sur le serveur (même si d'autres joueurs l'ont modifié entre l'update et l'envoit c'est pas grv car les changement du joueur ont été pris en comtpe)
			 }
			 
			socketServerforClient.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
