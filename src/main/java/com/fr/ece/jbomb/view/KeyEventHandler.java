package com.fr.ece.jbomb.view;

import java.util.ArrayList;

public class KeyEventHandler {

	private ArrayList<String> input;
	
	public KeyEventHandler(){
		input = new ArrayList<String>();
	}
	
	public void add(String code){
		if(code != null ) input.add(code);
	}
	
	public void remove(String code){
		if(code != null ) input.remove(code);
	}
	
	public ArrayList<String> getInputList(){
		return this.input;
	}
}
