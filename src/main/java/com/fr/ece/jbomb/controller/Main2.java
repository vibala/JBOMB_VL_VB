package com.fr.ece.jbomb.controller;

import com.fr.ece.jbomb.view.GUI;
import com.fr.ece.jbomb.view.KeyEventHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * Main du client 2
 * @author huong
 *
 */
public class Main2 extends Application {
	@FXML
	ImageView image;
	/**
	 * Démarre le jeu
	 * @param args args
	 */
	public static void main(String[] args)  {
	
		//Lancement de l'interface graphique
	      Application.launch(args);
	}
/**
 * Démarrage du jeu
 */
	@Override
public void start(Stage primaryStage) throws Exception {
		
		Pane layout = (Pane) FXMLLoader.load(Main.class.getResource("/com/fr/ece/jbomb/view/vueGraphique.fxml"));
		Group group = new Group(layout);
		
		// Construction des canvas
		Canvas canvas = (Canvas) layout.getChildren().get(0);
		Canvas canvas2 = (Canvas) layout.getChildren().get(1);
		image = (ImageView) layout.getChildren().get(3);
		image.setImage(new Image("com/fr/ece/jbomb/view/Decors/Panel_GAME.png"));
		// Ajoute les canevas dans le layout
		Scene scene = new Scene(group);
		
		// Gérer l'utilisation des flèches directionnelles
		final KeyEventHandler kev = new KeyEventHandler();
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				String code = event.getCode().toString();
				if (!kev.getInputList().contains(code))
					kev.add(code);
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				kev.remove(code);
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();

		
		//Connexion
		String serverIP="127.0.0.1";
		int serverPort=1234;
		
		String clientIP="127.0.0.1";
		int clientPort=1236;
				
		Client moi=new Client(clientIP,clientPort);
		Controller controller = new Controller(moi,serverIP,serverPort);

		//Vue
		GUI gui = new GUI();
		controller.setInit(gui, canvas, canvas2, kev);
		
		//Lancement
		controller.start();

	}

}
