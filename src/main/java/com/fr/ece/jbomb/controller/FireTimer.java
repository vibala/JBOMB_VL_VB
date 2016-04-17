package com.fr.ece.jbomb.controller;

/**
 * Permet de faire undécompte pour savoir quand les flammes doivent s'éteindre
 * @author huong
 *
 */
public class FireTimer implements Runnable{
	
	private int i;
	private int j;
	private boolean burned=false;
	public FireTimer (int i, int j){
		this.i=i;
		this.j=j;
	}
/**
 * Réccuperer l'indice row de la flamme dans le plateau
 * @return retourne indice 
 */
		public int getI() {
		return i;
	}

		/**
		 * Permet de savoir si la flamme est présent dans le plateau
		 * @return retourne l'état de la flamme
		 */
	public boolean getBurned() {
		return burned;
	}
	/**
	 * Modifier l'état de la flamme dans le plateau
	 * @param bool modifier
	 */
	public void setBurned(boolean bool) {
		 burned=bool;
	}
	/**
	 *  Réccuperer l'indice column de la flamme dans le plateau
	 * @return retourne indice
	 */
	public int getJ() {
		return j;
	}

	/**
	 * Commence le décompte avant que la flamme disparaisse
	 */
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			burned=true;
		}

}

