package com.fr.ece.jbomb.controller;
import com.fr.ece.jbomb.view.GUI;
import com.fr.ece.jbomb.view.KeyEventHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main4 extends Application {
	
	public static void main(String[] args)  {
	
		//Lancement de l'interface graphique
	      Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Pane layout = (Pane) FXMLLoader.load(Main4.class.getResource("/com/fr/ece/jbomb/view/vueGraphique.fxml"));
		Group group = new Group(layout);
		
		// Construction des canvas
		Canvas canvas = (Canvas) layout.getChildren().get(0);
		Canvas canvas2 = (Canvas) layout.getChildren().get(1);
		
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
		int clientPort=1238;
				
		Client moi=new Client(clientIP,clientPort);
		Controller controller = new Controller(moi,serverIP,serverPort);

		//Vue
		GUI gui = new GUI();
		controller.setInit(gui, canvas, canvas2, kev);
		
		//Lancement
		controller.start();

	}

}
