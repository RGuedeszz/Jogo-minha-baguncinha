package com.rogersgames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.rogersgames.principal.Game;
import com.rogersgames.tiles.Tiles;
import com.rogersgames.world.Camera;

public class Carol extends Entities {
	
	/// Sprites de Carol ///
	public BufferedImage[] poli;
	
	// Infiormações da Poli //
	private double speed = 1.4; // velocidade da Poli
	private int vida = 3;
	
	/// Animações da sprite ///
	private int frames = 0;
	private int animacoes = 0;
	private final int max_frames = 20;
	private final int max_animacoes = 4;
	
	private Random rand = new Random(); // Para gerar uma aleatoriedade no movimento

	public Carol(int posX, int posY, int larg, int altur, BufferedImage sprite) {
		super(posX, posY, larg, altur, sprite);
		
		poli = new BufferedImage[max_animacoes];
		for (int i = 0; i < poli.length; i++) {
			poli[i] = Game.sprt.sprite(16* i, 8 * 16, 16, 16);
		}
	}
	
	public void tick() {
		/// Animação da Poli ///
		frames++;
		if (frames > max_frames) {
			frames = 0;
			animacoes++;
			if (animacoes >= max_animacoes) {
				animacoes = 0;
			}
		}
		
		/// IA da Poli ///
		for (int i = 0; i < Game.atira.size(); i++) {
			Tiro atirar = Game.atira.get(i);
			if (Entities.isCollidingEntity(atirar, this)) {
				// System.out.println("Bateu a bala nela");
				Game.atira.remove(atirar);
				Game.rem.remove(atirar);
				// vida--; // Não quero que ela suma no mapa pois ainda n sei como fazer p ela voltar. Quando souber, ai troco
				speed -= 0.15; // Ao tomar um tiro, ela vai perder velocidade
				/// Quando ela tomar o tiro vai rolar o temporizador e ela só vai andar depois de 2 segundos
			}
		}
		
		
		// System.out.println(Entities.isCollidingEntity(this, Game.player));
		int chance = rand.nextInt(100); // Vai gerar um numero pra que tenha uma chance de movimento de Carol, gerando uma aleatoriedade
		if (Entities.isCollidingEntity(this, Game.player) == false && chance < 70) { // Só anda se não estiver colidindo com o player e 70% de chance de se mover
			if (getX() > Game.player.getX() && Tiles.isCollidingTiles((int)(x - speed), getY()) == false) {
				x -= speed;
			} else if (getX() < Game.player.getX() && Tiles.isCollidingTiles((int)(x + speed), getY()) == false) {
				x += speed;
			}
			if (getY() > Game.player.getY() && Tiles.isCollidingTiles(getX(), (int)(y - speed)) == false) {
				y -= speed;
			} else if (getY() < Game.player.getY() && Tiles.isCollidingTiles(getX(), (int)(y + speed)) == false) {
				y += speed;
			}
		}
		
		///// Condição de GAME OVER: Carol colidir com Rocky //////////
		if (Entities.isCollidingEntity(this, Game.player)) {
			Game.estadoGame = "GAME OVER";
		}
		
		/*
		///// Carol parou de correr atrás de você e foi dormir ////////
		if (vida <= 0) {
			// System.out.println("Carol deu uma pausa e foi dormir");
			Game.entity.remove(this);
		}
		*/
		
	}
	
	
	public void render(Graphics g) {
		g.drawImage(poli[animacoes], getX() - Camera.x, getY() - Camera.y, getLarg(), getAlt(), null);
	} 
	
}
