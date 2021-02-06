package com.rogersgames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.rogersgames.principal.Game;
import com.rogersgames.world.Camera;

public class LixoLata extends Entities {
	
	// Sprite //
	public BufferedImage[] lata;
	
	/// Animações ///
	private int frames = 0;
	private int animacoes = 0;
	private final int max_frames = 20;
	private final int max_animacoes = 4;

	public LixoLata(int posX, int posY, int larg, int altur, BufferedImage sprite) {
		super(posX, posY, larg, altur, sprite);
		
		lata = new BufferedImage[max_animacoes];
		for (int i = 0; i < lata.length; i++) {
			lata[i] = Game.sprt.sprite(16 * i, 6 * 16, 16, 16);
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
		g.drawImage(lata[animacoes], getX() - Camera.x, getY() - Camera.y, getLarg(), getAlt(), null);
	}
}
