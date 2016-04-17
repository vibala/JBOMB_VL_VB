package com.fr.ece.jbomb.controller;

import com.fr.ece.jbomb.server.Server;

/**
 * Permet de configurer le temps d'attente avant que la bombe explose
 * @author Vignesh BALA && Vincent LIM
 *
 */
public class BombTimer implements Runnable {
	private int i;
	private int j;
	/**
	 * Constructeur
	 * @param i valeur row de la bombe dans le plateau 
	 * @param j valeur colomn de la bombe dans le plateau 
	 */
	public BombTimer(int i, int j) {
		this.i = i;
		this.j = j;
	}
	/**
	 * Permet de configurer le temps d'attente avant que la bombe explose
	 **/
	public void run() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Server.updateListBomb(i, j);
	}

}