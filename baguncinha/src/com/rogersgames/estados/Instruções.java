package com.rogersgames.estados;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.rogersgames.principal.Game;

public class Instru��es {
	
	public String[] opcoes = {"jogar", "menu"};
	private int max_opcoes = opcoes.length;
	private int opcao_atual = 0;
	public boolean left, right, enter;
	
	public void tick() {
		// System.out.println("Op��o: " + opcoes[opcao_atual]);
		if (left) {
			left = false;
			opcao_atual--;
			if (opcao_atual < 0) {
				opcao_atual = max_opcoes - 1;
			}
		} else if (right) {
			right = false;
			opcao_atual++;
			if (opcao_atual >= max_opcoes) {
				opcao_atual = 0;
			}
		}
		
		if (enter) {
			enter = false;
			if (opcoes[opcao_atual] == "jogar") {
				Game.estadoGame = "NORMAL";
			} else if (opcoes[opcao_atual] == "menu") {
				Game.estadoGame = "MENU";
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(192,192,192));
		g.fillRect(0, 0, Game.largura * Game.escala, Game.altura * Game.escala);
		
		g.setColor(Color.black);
		// T�tulo //
		g.setFont(new Font("arial", Font.BOLD, 40));
		g.drawString("INSTRU��ES", 250, 50);
		
		// Subt�tulos
		g.setFont(new Font("arial", Font.BOLD, 35));
		g.drawString("CONTROLES", 10, 130);
		g.drawString("HIST�RIA", 10, 300);
		
		// Texto //
		g.setFont(new Font("arial", Font.BOLD, 22));
		g.drawString("Setas do teclado: Controlam o personagem", 10, 180);
		g.drawString("Espa�o: Atira o hixizine", 10, 220);
		
		g.drawString("Cuide!!! A Poli vai brigar com voc� se voc� n�o cuidar.", 10, 350);
		g.drawString("Enquanto limpa a baguncinha, voc� precisa fugir da Poli", 10, 390);
		g.drawString("Use o hixizine (ele � roxo no mapa) para retardar a velocidade dela,", 10, 430);
		g.drawString("pois apesar do seu joelho doente, ela � bem mais rapida que voc�", 10, 470);
		g.drawString("quando o assunto �... CUIDAR!!! (obs: voc� precisa atirar de perto)", 10, 510);
		
		// Op��es //
		g.setColor(new Color(12, 15, 185));
		g.setFont(new Font("arial", Font.BOLD, 24));
		g.drawString("JOGAR", 100, 570);
		g.drawString("MENU PRINCIPAL", 400, 570);
		if (opcoes[opcao_atual] == "jogar") {
			g.drawImage(Menu.selecionar, 65, 548, 24, 24, null);
		} else if (opcoes[opcao_atual] == "menu") {
			g.drawImage(Menu.selecionar, 365, 548, 24, 24, null);
		}
	}
}
