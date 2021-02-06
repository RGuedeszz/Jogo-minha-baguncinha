package com.rogersgames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.rogersgames.principal.Game;
import com.rogersgames.world.Camera;

public class Icsizine extends Entities {
	
	// Sprite //
	public BufferedImage[] ics;
	
	/// Animações ///
	private int frames = 0;
	private int animacoes = 0;
	private final int max_frames = 20;
	private final int max_animacoes = 4;
	
	public Icsizine(int posX, int posY, int larg, int altur, BufferedImage sprite) {
		super(posX, posY, larg, altur, sprite);
		
		ics = new BufferedImage[max_animacoes];
		for (int i = 0; i < ics.length; i++) {
			ics[i] = Game.sprt.sprite(16 * i, 7 * 16, 16, 16);
		}
	}

	public void tick() {
		frames++;
		if (frames > max_frames) {
			frames = 0;
			animacoes++;
			if (animacoes >= max_animacoes) {
				animacoes = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(ics[animacoes], getX() - Camera.x, getY() - Camera.y, getLarg(), getAlt(), null);
	}
}
