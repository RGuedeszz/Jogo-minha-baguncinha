package com.rogersgames.estados;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.rogersgames.principal.Game;

public class Pause {
	
	public boolean pause;
	public boolean up, down, enter;
	private String[] pauseOptions = {"voltar", "instrucoes", "menu"};
	private final int total = pauseOptions.length;
	private int atual = 0;
	
	public void tick() {
		// System.out.println("Pause");
		// System.out.println("Opcao pausa: " + pauseOptions[atual]);
		if (up) {
			up = false;
			atual--;
			if (atual < 0) {
				atual = total - 1;
			}
			
		} else if (down) {
			down = false;
			atual++;
			if (atual >= total) {
				atual = 0;
			}
		}
		
		if (enter) {
			enter = false;
			if (pauseOptions[atual] == "voltar") {
				pause = false;
				Game.pause = false;
				atual = 0;
			} else if (pauseOptions[atual] == "instrucoes") {
				Game.estadoGame = "INSTRUCOES";
				atual = 0;
			} else if (pauseOptions[atual] == "menu") {
				Game.estadoGame = "MENU";
				atual = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D graf = (Graphics2D) g;
		graf.setColor(new Color(0, 0, 0, 100));
		graf.fillRect(0, 0, Game.largura, Game.altura);
		
		g.setColor(new Color(2, 6, 8));
		g.setFont(new Font("arial", Font.BOLD, 24));
		g.drawString("PAUSA", 90, 30);
		
		g.setColor(new Color(12, 16, 168));
		g.setFont(new Font("arial", Font.BOLD, 14));
		g.drawString("Resume", 100, 100);
		g.drawString("Instruções", 90, 120);
		g.drawString("Menu", 110, 140);
		
		if (pauseOptions[atual] == "voltar") {
			g.drawImage(Menu.selecionar, 80, 86, 16, 16, null);
		} else if (pauseOptions[atual] == "instrucoes") {
			g.drawImage(Menu.selecionar, 70, 106, 16, 16, null);
		} else if (pauseOptions[atual] == "menu") {
			g.drawImage(Menu.selecionar, 90, 126, 16, 16, null);
		}
	}
	
}



