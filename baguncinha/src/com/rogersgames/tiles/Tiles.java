package com.rogersgames.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.rogersgames.principal.Game;
import com.rogersgames.world.Camera;

public class Tiles {
	
	protected int x, y; // Posições dos nossos tiles
	public BufferedImage sprite;
	
	//// Sprites dos tiles ////
	public static BufferedImage tile_chao = Game.sprt.sprite(0, 0, 16, 16);
	public static BufferedImage tile_parede = Game.sprt.sprite(16, 0, 16, 16);
	
	//// Método construtor (construct) //////
	public Tiles(int posX, int posY, BufferedImage tileSprite) {
		x = posX;
		y = posY;
		sprite = tileSprite;
	}
	
	///////// Método de colisão com os tiles ///////////
	public static boolean isCollidingTiles(int nextX, int nextY) {
		int x1 = nextX / Game.map.tamanhoQuadrados;
		int y1 = nextY / Game.map.tamanhoQuadrados;
		
		int x2 = nextX / Game.map.tamanhoQuadrados;
		int y2 = (nextY + Game.map.tamanhoQuadrados - 1) / Game.map.tamanhoQuadrados;
		
		int x3 = (nextX + Game.map.tamanhoQuadrados - 1) / Game.map.tamanhoQuadrados;
		int y3 = nextY / Game.map.tamanhoQuadrados;
		
		int x4 = (nextX + Game.map.tamanhoQuadrados - 1) / Game.map.tamanhoQuadrados;
		int y4 = (nextY + Game.map.tamanhoQuadrados - 1) / Game.map.tamanhoQuadrados;
		
		return (Game.map.tile[x1 + y1 * Game.map.mapa.getWidth()] instanceof Parede ||
				Game.map.tile[x2 + y2 * Game.map.mapa.getWidth()] instanceof Parede ||
				Game.map.tile[x3 + y3 * Game.map.mapa.getWidth()] instanceof Parede ||
				Game.map.tile[x4 + y4 * Game.map.mapa.getWidth()] instanceof Parede);
		
	}
	
	/// Método de game ///
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
