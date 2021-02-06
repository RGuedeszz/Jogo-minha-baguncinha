package com.rogersgames.estados;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.rogersgames.principal.Game;

public class Vitoria {
	
	public BufferedImage telaVenceu;
	public boolean menu, troca;
	private int currTimer = 0;
	private final int timer = 70;
	
	public Vitoria(String path) {
		try {
			telaVenceu = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if (menu) {
			menu = false;
			Game.estadoGame = "MENU";
		}
		
		currTimer++;
		if (currTimer >= timer) {
			currTimer = 0;
			if (troca == false) {
				troca = true;
			} else if (troca == true) {
				troca = false;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(telaVenceu, 0, 0, Game.largura * Game.escala, Game.altura * Game.escala, null);
		
		if (troca) {
			g.setColor(Color.black);
			g.drawImage(Menu.selecionar, 550, 540, 24, 24, null);
		} else {
			g.setColor(Color.BLUE);
			g.drawImage(Menu.selecionar, 210, 540, 24, 24, null);
		}
		g.setFont(new Font("arial", Font.BOLD, 26));
		g.drawString("ENTER PARA VOLTAR", 250, 550);
		g.drawString("AO MENU PRINCIPAL", 255, 580);
	}
	
}
