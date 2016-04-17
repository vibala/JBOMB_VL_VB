package com.fr.ece.jbomb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Wrap de la classe BufferedReader qui permet d'ajouter des améliorations futures
 * @author huong
 *
 */
public class BufferedReaderChat extends BufferedReader{
/**
 * Constructeur
 * @param in lecteur
 */
	public BufferedReaderChat(InputStreamReader in) {
		super(in);
		// TODO Auto-generated constructor stub
	}
	/**
	 * lire les données envoyé par le serveur dans le flux
	 * @return retourne les données
	 * @throws IOException Exception
	 */
	public String lire() throws IOException{
		return this.readLine();
	}
}
