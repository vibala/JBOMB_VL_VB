package com.fr.ece.jbomb.controller;

import com.fr.ece.jbomb.model.Player;

public interface GUIListener {
	public boolean onPressedRight(Player player1);

	public boolean onPressedLeft(Player player1);

	public boolean onPressedDown(Player player1);

	public boolean onPressedUp(Player player1);
}
