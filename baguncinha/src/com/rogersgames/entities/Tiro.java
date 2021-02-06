package com.rogersgames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.rogersgames.principal.Game;
import com.rogersgames.world.Camera;

public class Tiro extends Icsizine {
	
	public int dx; // Dire��o x da bala
	public int dy; // Dire��o y da bala
	public double speedBala = 2.4; // Velocidade da bala
	
	///// Para o jogo n�o ficar muito pesado vamos adicionar uma vida �til as nossas balas //////
	private int atual_bala = 0;
	private final int life_bala = 40;
	
	public Tiro(int posX, int posY, int larg, int altur, BufferedImage sprite, int direcX, int direcY) {
		super(posX, posY, larg, altur, sprite);
		dx = direcX;
		dy = direcY;
	}
	
	public void tick() {
		// Anima��o do tiro //
		x += (dx * speedBala);
		y += (dy * speedBala);
		
		atual_bala++;
		if (atual_bala >= life_bala) {
			Game.atira.remove(this);
			Game.rem.remove(this);
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(getX() - Camera.x, getY() - Camera.y, getLarg(), getAlt());
	}
	
}
