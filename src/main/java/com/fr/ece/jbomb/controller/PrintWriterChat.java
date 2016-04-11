package com.fr.ece.jbomb.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class PrintWriterChat extends PrintWriter{

	public PrintWriterChat(OutputStreamWriter arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public void envoyer(String s) throws IOException{
		//println("amorcage");
		//flush();
	
		println(s);
		flush();
	}
}
