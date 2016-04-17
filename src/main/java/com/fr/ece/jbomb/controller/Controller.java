package com.fr.ece.jbomb.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import com.fr.ece.jbomb.model.ConfFromServer;
import com.fr.ece.jbomb.model.ConfToServer;
import com.fr.ece.jbomb.model.Player;
import com.fr.ece.jbomb.view.GUI;
import com.fr.ece.jbomb.view.KeyEventHandler;

import javafx.scene.canvas.Canvas;
/**
 * Classe Controller : Initialisation de la connexion et des ses flux et ecriture vers le serveur et lecture depuis le serveur
 * @author huong
 *
 */
public class Controller implements GUIListener {

	private GUI gui;
	private Canvas canvas;
	private Canvas canvas2;
	private KeyEventHandler key;

	private Client moi;
	private Player player;
	private BufferedReaderChat reader = null;
	private PrintWriterChat writer = null;
	private ObjectOutputStreamChat oos = null;
	private ObjectInputStreamChat ois = null;
	private String serverIP;
	private int serverPort;
/**
 * Initialisation du controller
 * @param me client
 * @param sIP IP du server
 * @param sPort Port du server
 */
	public Controller(Client me, String sIP, int sPort) {
		this.moi = me;
		this.serverIP = sIP;
		this.serverPort = sPort;

		System.out.println("Demande de connexion");
		try {
			moi.connect(serverIP, serverPort);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Connexion établie avec le serveur: " + moi.getConnection().toString());

		// ------------------------------CREATION
		// FLUX---------------------------------------------------

		try {
			reader = new BufferedReaderChat(new InputStreamReader(moi.getConnection().getInputStream()));
			writer = new PrintWriterChat(new OutputStreamWriter(moi.getConnection().getOutputStream()));
			// Objet
			oos = new ObjectOutputStreamChat(moi.getConnection().getOutputStream(), reader);
			ois = new ObjectInputStreamChat(moi.getConnection().getInputStream(), writer);

			// Lire le message de bienvenue du serveur
			System.out.println(reader.lire());

			// Enregistrer le joueur que le serveur a assigné au client
			player = (Player) ois.lire();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * Reccup player du client
 */
	public Player getPlayer() {
		return player;
	}
/**
 * Initialiser les parametre du controller 
 * @param gui GUI
 * @param canvas canvas
 * @param canvas2 canvas2
 * @param key Objet qui réccupère les touches du joueur
 */
	public void setInit(GUI gui, Canvas canvas, Canvas canvas2, KeyEventHandler key) {
		this.gui = gui;
		this.canvas = canvas;
		this.canvas2 = canvas2;
		this.key = key;
	}
/**
 * Lire la configuration reçu par le serveur
 */
	public ConfFromServer readConf() throws ClassNotFoundException, IOException {
		ConfFromServer configServeur = (ConfFromServer) ois.lire();
		return configServeur;
	}
/**
 * Ecrire la configuration du client vers le serveur. Une fois que le serveur aura pris en compte la config du client il renverra sa config et le client mettra sa vue 
 * en fonction de la configuration du serveur
 */
	public ConfToServer writeConf(ConfToServer conf) {
		oos.envoyer(conf);
		return conf;

	}
/**
 * Démarrer le controller
 */
	public void start() {
		// ------------------------------DIALOGUE---------------------------------------------------

		/*
		 * //***********Lecture Banniere 1.1 serverAnswer=reader.readChat();
		 * System.out.println(serverAnswer);
		 * 
		 * //***********Ecriture Message 2.0 System.out.println(
		 * "Envoyer un message au server :"); myAnswer=sc.nextLine();
		 * writer.envoyer(myAnswer);
		 */

		gui.setController(this); // Initialisation du controleur cote vue
		gui.start(canvas, canvas2, key); //
	}

}
