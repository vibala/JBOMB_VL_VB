package com.fr.ece.jbomb.controller;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import com.fr.ece.jbomb.model.Decor;
import com.fr.ece.jbomb.model.Player;

public interface GUIListener {
	public boolean onPressedRight(Player player1, Map<Point, Decor> murs_sprites, List<Decor> pilier_sprites);

	public boolean onPressedLeft(Player player1, Map<Point, Decor> murs_sprites, List<Decor> pilier_sprites);

	public boolean onPressedDown(Player player1, Map<Point, Decor> murs_sprites, List<Decor> pilier_sprites);

	public boolean onPressedUp(Player player1, Map<Point, Decor> murs_sprites, List<Decor> pilier_sprites);
}
