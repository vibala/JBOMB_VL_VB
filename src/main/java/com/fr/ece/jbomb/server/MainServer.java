package com.fr.ece.jbomb.server;

import java.io.IOException;
import java.net.InetSocketAddress;
/**
 * Classe de démarrage du serveur
 * @author Vignesh BALA
 * @version 1.0
 **/
public class MainServer {
	/**
	 * Initialise le port d'écoute et l'adresse IP du serveur ainsi que le nombre de connexions 
	 * Instancie la classe Server et démarre le serveur
	 * @param args  args
	 * @exception InterruptedException si le thread a été interrompu
	 * @exception IOException si les entrées/sorties sont corrompues
	 **/
	public static void main(String[] args) throws InterruptedException, IOException {
		Server server=null;
		int Port=1234;
		int nbConnectionMax=4;
		String ipServer="127.0.0.1";
		server = new Server (nbConnectionMax,new InetSocketAddress(ipServer,Port));
		server.start();

	}
}
