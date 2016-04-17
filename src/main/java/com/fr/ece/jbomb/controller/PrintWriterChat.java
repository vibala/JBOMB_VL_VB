package com.fr.ece.jbomb.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
/**
 * Wrap de la classe PrintWriter qui permet d'ajouter des améliorations futures
 * @author huong
 *
 */
public class PrintWriterChat extends PrintWriter{
/**
 * Constructeur
 * @param arg0 arg0
 */
	public PrintWriterChat(OutputStreamWriter arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	/**
	 * envoyer ses données envoyé dans le flux
	 * @throws IOException Exception
	 */
	/**
	 * envoyer ses données envoyé dans le flux
	 * @param s chaine de caractere
	 * @throws IOException Exception
	 */
	public void envoyer(String s) throws IOException{
		println(s);
		flush();
	}
}
