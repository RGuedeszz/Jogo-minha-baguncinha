package com.rogersgames.estados;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.rogersgames.principal.Audio;
import com.rogersgames.principal.Game;
import com.rogersgames.world.World;

public class Derrota {
	
	private BufferedImage image;
	public boolean menu;
	private int currTime = 0;
	private final int time = 70;
	private boolean troca;
	
	private Audio som;
	
	public Derrota(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if (menu) {
			menu = false;
			Game.estadoGame = "MENU";
		}
		////// Efeito pisca ////////
		currTime++;
		if (currTime >= time) {
			currTime = 0;
			if (troca == false) {
				troca = true;
			} else if (troca == true) {
				troca = false;
			}
		}
		
		/////// Reiniciando ////////
		Game.lv_atual = 1;
		String reiniciar = "nivel" + Game.lv_atual + ".png";
		World.reinicia(reiniciar);
	}
	
	public void render(Graphics g) {
		g.drawImage(image, 0, 0, Game.largura * Game.escala, Game.altura * Game.escala, null);
		if (troca) {
			g.setColor(Color.black);
		} else {
			g.setColor(Color.blue);
		}
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("PRESSIONE ENTER PARA VOLTAR AO MENU", 20, 575);
	}
	
}
