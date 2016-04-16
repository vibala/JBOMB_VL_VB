package com.fr.ece.jbomb.model;

import static com.fr.ece.jbomb.model.Plateau.MUR;
import static com.fr.ece.jbomb.model.Plateau.PILIER;
import static com.fr.ece.jbomb.model.Plateau.SOL;
import static com.fr.ece.jbomb.view.Direction.EST;
import static com.fr.ece.jbomb.view.Direction.NORD;
import static com.fr.ece.jbomb.view.Direction.OUEST;
import static com.fr.ece.jbomb.view.Direction.SUD;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fr.ece.jbomb.view.Direction;

public class ServerTest {

	private Plateau plateau[][];

	public ServerTest() {
		this.plateau = new Plateau[17][23];
		ajout_piliers_sols();
		//ajout_murs();
		ajout_players();
	}

	public Plateau[][] getPlateau(){
		return this.plateau;
	}
	private void ajout_players() {
		System.out.println("Ajout_Players : Entree");
		// TODO Auto-generated method stub
		Random rand = new Random();
		List<Integer> picked_random_numbers = new ArrayList<Integer>();
		Point[] coordonnees = new Point[4];
		coordonnees[0] = new Point(0, 0);
		coordonnees[1] = new Point(0, 22);
		coordonnees[2] = new Point(16, 0);
		coordonnees[3] = new Point(16, 22);
		boolean cds = false;
		
		picked_random_numbers.add(0); // a retirer
		plateau[16][22] = Plateau.PLAYER1; //  a retirer
		// 4 changer 3 par 4
		for(int i = 0; i < 3; i++){
			cds = false;
			while(!cds){
				int rd = rand.nextInt(4); // entre 0 et 3
				System.out.println(rd);
				if(!picked_random_numbers.contains(rd)){
					picked_random_numbers.add(rd);
					int posx = coordonnees[i].x;
					int posy = coordonnees[i].y;
					plateau[posx][posy] = Plateau.getNomByValeur(rd+1);
					cds = true;
				}
			}
		}
		System.out.println("Ajout_Players : Sortie");
	}

	private void ajout_piliers_sols() {
		// Mise en forme du sol dans le tableau plateau
		System.out.println("ajout_piliers_sols : Entree");
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
		System.out.println("ajout_piliers_sols : Sortie");
	}

	private void gestion_murs_particuliers(int nb_murs, Direction direction) {
		System.out.println("gestion_murs_particuliers : Entree");
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
						while(!cds){
							rand_num = rand.nextInt(16); // entre 0 et 16
							if (!random_numbers.contains(rand_num)) {
								random_numbers.add(rand_num);
								this.plateau[0][3 + rand_num] = MUR;
								cds = true;
							}
						}
						break;
					case SUD:
						System.out.println(random_numbers.size());
						while(!cds){
							rand_num = rand.nextInt(16); // entre 0 et 16
							if (!random_numbers.contains(rand_num)) {
								random_numbers.add(rand_num);
								this.plateau[16][3 + rand_num] = MUR;
								cds = true;
							}
						}
						break;
					case EST:
						while(!cds){
							rand_num = rand.nextInt(11); // entre 0 et 10
							if (!random_numbers.contains(rand_num)) {
								random_numbers.add(rand_num);
								this.plateau[3 + rand_num][22] = MUR;
								cds = true;
							}
						}
						break;
					case OUEST:
						while(!cds){
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
		System.out.println("gestion_murs_particuliers : Sortie");
		
	}

	private void ajout_murs() {
		System.out.println("ajout_murs : Entree");
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
		for(int i = 1; i < 16; i++){
			if(i%2!=0){
				nb_murs = rand.nextInt(5) + 5;
				for(int j = 0; j < nb_murs; j++){
					cds = false;
					while(!cds){
						rand_num = rand.nextInt(10)* 2; // rand entre 0 et 9
						if(!random_numbers.contains(rand_num)){
							random_numbers.add(rand_num);
							plateau[i][rand_num + 2] = MUR;
							cds = true;
						}	
					}
				} 
				random_numbers.clear();
			}else{
				nb_murs = rand.nextInt(12) + 10;  // rand entre 0 et 9 min : 10 murs et max : 21
				for(int j = 0; j < nb_murs; j++){
					cds = false;
					while(!cds){
						rand_num = rand.nextInt(21); // entre 0 et 20
						if(!random_numbers.contains(rand_num)){
							random_numbers.add(rand_num);
							plateau[i][rand_num+1] = MUR;
							cds = true;
						}	
					}
				} 
				random_numbers.clear();	
			}
		}
		
		System.out.println("ajout_murs : Sortie");
	}

}
