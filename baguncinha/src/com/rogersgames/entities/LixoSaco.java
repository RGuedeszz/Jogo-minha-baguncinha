package com.rogersgames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.rogersgames.principal.Game;
import com.rogersgames.world.Camera;

public class LixoSaco extends Entities {
	
	// Sprite //
	public BufferedImage[] lixo;
	
	/// Animações ///
	private int frames = 0;
	private int animacoes = 0;
	private final int max_frames = 20;
	private final int max_animacoes = 4;
	
	public LixoSaco(int posX, int posY, int larg, int altur, BufferedImage sprite) {
		super(posX, posY, larg, altur, sprite);
		
		lixo = new BufferedImage[max_animacoes];
		for (int i = 0; i < lixo.length; i++) {
			lixo[i] = Game.sprt.sprite(16 * i, 5 * 16, 16, 16);
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
		g.drawImage(lixo[animacoes], getX() - Camera.x, getY() - Camera.y, getLarg(), getAlt(), null);
	}

}
