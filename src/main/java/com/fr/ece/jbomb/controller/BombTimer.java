package com.fr.ece.jbomb.controller;

import com.fr.ece.jbomb.server.Server;

public class BombTimer implements Runnable {
	private int i;
	private int j;

	public BombTimer(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public void run() {
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Server.updateListBomb(i, j);
	}

}