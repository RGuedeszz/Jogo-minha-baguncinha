package com.rogersgames.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.rogersgames.principal.Game;

public class Entities {
	
	protected double x, y; // Posições de nossas entidades
	public int altura, largura; // Dimensões de nossas entidades
	public static BufferedImage imageEntity; // Sprite de nossa entidade
	
	/// Sprites iniciais de entidades ///
	public static BufferedImage saco = Game.sprt.sprite(0, 5 * 16, 16, 16);
	public static BufferedImage lixao = Game.sprt.sprite(0, 6 * 16, 16, 16);
	public static BufferedImage remedio = Game.sprt.sprite(0, 7 * 16, 16, 16);
	public static BufferedImage carolPoli = Game.sprt.sprite(0, 8 * 16, 16, 16);
	public static BufferedImage icsizinNaMao = Game.sprt.sprite(3 * 16, 0, 16, 16);
	
	////////// Métodos acessores (getters) ////////////////
	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}
	public int getLarg() {
		return largura;
	}
	public int getAlt() {
		return altura;
	}
	
	/////////// Métodos modificadores (setters) ///////////////
	public void setX(int newX) {
		x = newX;
	}
	public void setY(int newY) {
		y = newY;
	}
	public void setLarg(int newWidth) {
		largura = newWidth;
	}
	public void setAlt(int newHeight) {
		altura = newHeight;
	}
	
	//////////// Método construtor (construct) /////////////////
	public Entities(int posX, int posY, int larg, int altur, BufferedImage sprite) {
		x = posX;
		y = posY;
		setLarg(larg);
		setAlt(altur);
		imageEntity = sprite;
	}
	
	/////////// Método para avaliar colisão entre entidades ///////////////
	public static boolean isCollidingEntity(Entities e1, Entities e2) {
		Rectangle ent1 = new Rectangle(e1.getX(), e1.getY(), e1.getLarg(), e1.getAlt());
		Rectangle ent2 = new Rectangle(e2.getX(), e2.getY(), e2.getLarg(), e2.getAlt());
		
		if (ent1.intersects(ent2)) { // Estao colidindo se um retangulo interceptar outro
			return true;
		} else {
			return false;
		}
	}
	
	///////////// Métodos do game /////////////////
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(imageEntity, getX(), getY(), getLarg(), getAlt(), null);
	}
	
	
}
















