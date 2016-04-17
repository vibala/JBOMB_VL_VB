package com.fr.ece.jbomb.controller;


public class FireTimer implements Runnable{
	
	private int i;
	private int j;
	private boolean burned=false;
	public FireTimer (int i, int j){
		this.i=i;
		this.j=j;
	}

		public int getI() {
		return i;
	}

	public boolean getBurned() {
		return burned;
	}
	public void setBurned(boolean bool) {
		 burned=bool;
	}
	public int getJ() {
		return j;
	}

	
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			burned=true;
		}

}

