package com.rogersgames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.rogersgames.principal.Game;
import com.rogersgames.tiles.Tiles;
import com.rogersgames.world.Camera;
import com.rogersgames.world.World;

public class Player extends Entities {
	
	//// Informações do player ////
	public boolean up, down, left, right; // Direções do player controladas pelo teclado
	public int dir_right = 0;
	public int dir_left = 1;
	public int dir_up = 2;
	public int dir_down = 3;
	public int dir = dir_right;
	private double speed = 1.2;
	private boolean moved;
	public boolean remedioMao; // Para ver se Rocky está com o icsizine na mão
	public boolean atirando; // Para verificar se o jogador está atirando
	public int quantidadeRem = 0;
	private int quantidadeLixo = 0;
	
	///// Temporizador /////////
	/*
	private int timer = 0;
	private int max_timer = 80;
	*/
	
	/// Sprites do player ///
	public BufferedImage dano = Game.sprt.sprite(32, 0, 16, 16);
	public BufferedImage[] andaDireita;
	public BufferedImage[] andaEsquerda;
	public BufferedImage[] andaCima;
	public BufferedImage[] andaBaixo;
	
	/// Animação do player ///
	private int frames = 0;
	private int animacoes = 0;
	private final int max_frames = 20; // Define a velocidade de nossa animação (troca de sprites)
	private final int max_animacoes = 4; // Quantidade de sprites que temos (cada direcao tem 4 sprites na animação)

	public Player(int posX, int posY, int larg, int altur, BufferedImage sprite) {
		super(posX, posY, larg, altur, sprite);
		
		/// Vendo as sprites do nosso player ///
		andaDireita = new BufferedImage[max_animacoes];
		andaEsquerda = new BufferedImage[max_animacoes];
		andaCima = new BufferedImage[max_animacoes];
		andaBaixo = new BufferedImage[max_animacoes];
			// Feito isso, hora de trabalhar cada uma para renderizar as animações
		for (int s = 0; s < andaDireita.length; s++) {
			andaDireita[s] = Game.sprt.sprite(16 * s, 16, 16, 16);
		}
		for (int s = 0; s < andaEsquerda.length; s++) {
			andaEsquerda[s] = Game.sprt.sprite(16 * s, 16 * 2, 16, 16);
		}
		for (int s = 0; s < andaCima.length; s++) {
			andaCima[s] = Game.sprt.sprite(16 * s, 16 * 3, 16, 16);
		}
		for (int s = 0; s < andaBaixo.length; s++) {
			andaBaixo[s] = Game.sprt.sprite(16 * s, 16 * 4, 16, 16);
		}
	}
	
	///// Métodos de game ////////
	public void tick() {
		moved = false;
		// Movimentos do player //
		if (up && Tiles.isCollidingTiles(getX(), (int)(y - speed)) == false) {
			moved = true;
			y -= speed;
			dir = dir_up;
		} else if (down && Tiles.isCollidingTiles(getX(), (int)(y + speed)) == false) {
			moved = true;
			y += speed;
			dir = dir_down;
		}
		if (left && Tiles.isCollidingTiles((int)(x - speed), getY()) == false) {
			moved = true;
			x -= speed;
			dir = dir_left;
		} else if (right && Tiles.isCollidingTiles((int)(x + speed), getY()) == false) {
			moved = true;
			x += speed;
			dir = dir_right;
		}
		
		// Animação do player para renderizar //
		if (moved == true) {
			frames++;
			if (frames > max_frames) {
				frames = 0;
				animacoes++;
				if (animacoes >= max_animacoes) {
					animacoes = 0;
				}
			}
		}
		
		////////// Câmera para deixar o jogador no centro da tela //////////////
		Camera.x = Camera.Clamp(getX() - (Game.largura / 2), (Game.map.mapa.getWidth() * Game.map.tamanhoQuadrados) - Game.largura, 0);
		Camera.y = Camera.Clamp(getY() - (Game.altura / 2), (Game.map.mapa.getHeight() * Game.map.tamanhoQuadrados) - Game.altura, 0);
		
		///// Atirando remédio //////
		if (atirando && quantidadeRem > 0) {
			atirando = false;
			quantidadeRem--;
			
			int posX = 0; // Posição x inicial de onde vamos jogar a bala
			int posY = 8; // Posição y inicial de onde vamos jogar a bala
			int dirX = 0; // direcao x de onde vamos jogar a bala
			int dirY = 0; // direcao y de onde vamos jogar a bala
			
			if (dir == dir_up) {
				dirY = -1;
			} else if (dir == dir_down) {
				dirY = 1;
				posX = 9;
			}
			if (dir == dir_left) {
				dirX = -1;
				posX = 3;
			} else if (dir == dir_right) {
				dirX = 1;
				posX = 8;
			}
			
			Tiro bala = new Tiro(getX() + posX, getY() + posY, 3,3, null, dirX, dirY);
			Game.atira.add(bala);
		}
		
		
		//// Vitoria ////
		if (Game.lixo.size() == 0 && Game.saco.size() == 0) {
			// System.out.println("Vitoria");
			Game.lv_atual++;
			if (Game.lv_atual > Game.max_lv) {
				Game.lv_atual = Game.max_lv;
				Game.estadoGame = "VENCEU";
			}
			
			/// Trocar de mapa ///
			String proxMapa = "nivel" + Game.lv_atual + ".png";
			World.reinicia(proxMapa);
		}
		
		checkColisionWithRemedio();
		checkColisionWithLata();
		checkColisionWithSaco();
		
	}
	
	public void render(Graphics g) {
		if (dir == dir_left) {
			g.drawImage(andaEsquerda[animacoes], getX() - Camera.x, getY() - Camera.y, getLarg(), getAlt(), null);
		} else if (dir == dir_right) {
			g.drawImage(andaDireita[animacoes], getX() - Camera.x, getY() - Camera.y, getLarg(), getAlt(), null);
		}
		if (dir == dir_up) {
			g.drawImage(andaCima[animacoes], getX() - Camera.x, getY() - Camera.y, getLarg(), getAlt(), null);
		} else if (dir == dir_down) {
			g.drawImage(andaBaixo[animacoes], getX() - Camera.x, getY() - Camera.y, getLarg(), getAlt(), null);
		}
		
		////// Renderizando o remedio na mao  //////
		if (quantidadeRem > 0) {
			if (dir == dir_right || dir == dir_up)
				g.drawImage(icsizinNaMao, getX() - Camera.x, getY() - Camera.y, getLarg(), getAlt(), null);
			else if (dir == dir_left || dir == dir_down)
				g.drawImage(icsizinNaMao, getX() + 2 - Camera.x, getY() + 1 - Camera.y, getLarg(), getAlt(), null);
		}
	}
	
	////// Métodos de checagem de colisão com outros itens //////
	public void checkColisionWithRemedio() { // Checa se pegou o icsizine
		for (int i = 0; i < Game.rem.size(); i++) {
			Icsizine atual = Game.rem.get(i);
			if (Entities.isCollidingEntity(atual, this)) {
				Game.entity.remove(atual);
				remedioMao = true;
				quantidadeRem += 2;
				Game.rem.remove(atual);
				// System.out.println("Pegou icsizine");
			}
		}
	}
	public void checkColisionWithLata() { // Checa se pegou o icsizine
		for (int i = 0; i < Game.lixo.size(); i++) {
			LixoLata atual = Game.lixo.get(i);
			if (Entities.isCollidingEntity(atual, this)) {
				Game.lixo.remove(atual);
				quantidadeLixo++;
				Game.entity.remove(atual);
				// System.out.println("Pegou lata");
			} 
		}
	}
	public void checkColisionWithSaco() { // Checa se pegou o icsizine
		for (int i = 0; i < Game.saco.size(); i++) {
			LixoSaco atual = Game.saco.get(i);
			if (Entities.isCollidingEntity(atual, this)) {
				Game.saco.remove(atual);
				quantidadeLixo++;
				Game.entity.remove(atual);
				// System.out.println("Pegou saco");
			} 
		}
	}

}










