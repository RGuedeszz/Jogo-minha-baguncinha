package com.rogersgames.principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UI {
	
	private int resultLixo;
	private int remedio;
	
	public void tick() {
		resultLixo = Game.lixo.size() + Game.saco.size();
		remedio = Game.player.quantidadeRem;
	}
	
	public void render(Graphics g) {
		/// Mostrar quantidade de lixo ///
			// Retangulo de fundo
		g.setColor(new Color(200, 120, 26));
		if (resultLixo >= 10)
			g.fillRoundRect(10, Game.altura * Game.escala - 52, 205, 40, 10, 10);
		else
			g.fillRoundRect(10, Game.altura * Game.escala - 52, 184, 40, 10, 10);
			// Texto
		g.setColor(new Color(66, 85, 48));
		g.setFont(new Font("arial", Font.BOLD, 34));
		g.drawString("Bagunça: " + resultLixo, 10, Game.altura * Game.escala - 20);
		
		/// Mostrar quantidade de balas (icsizine) ///
			// Retangulo de fundo
		g.setColor(Color.LIGHT_GRAY);
		g.fillRoundRect(Game.escala * Game.largura - 154, 15, 150, 30, 10, 10);
			// Texto
		g.setColor(new Color(81, 12, 255));
		g.setFont(new Font("arial", Font.BOLD, 28));
		g.drawString("Hixizine: " + remedio, Game.escala * Game.largura - 150, 40);
		
		/// Mostrar level ///
		g.setColor(new Color(12, 65, 122));
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Nivel: " + Game.lv_atual + "/" + Game.max_lv, 40, 40);
	}
	
}
