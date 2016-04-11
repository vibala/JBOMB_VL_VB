package com.fr.ece.jbomb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedReaderChat extends BufferedReader{

	public BufferedReaderChat(InputStreamReader in) {
		super(in);
		// TODO Auto-generated constructor stub
	}
	
	public String lire() throws IOException{
		//	this.readLine();
		return this.readLine();
	}
}
