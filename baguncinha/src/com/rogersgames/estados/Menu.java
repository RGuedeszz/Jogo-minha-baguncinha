package com.rogersgames.estados;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.rogersgames.principal.Game;
import com.rogersgames.world.World;

public class Menu {
	/// Opções de menu ///
	public String[] opcoes = {"jogar", "instrucoes", "sair"};
	public boolean up, down, left, right, enter;
	private int opcoes_totais = opcoes.length;
	private int opcao_atual = 0;
	/// Opções confirmação saida ///
	public String[] saida = {"sim", "nao"};
	private final int currentSaida = saida.length;
	private int current = 0;
	public BufferedImage image;
	public static BufferedImage selecionar;
	public boolean sair;
	
	public Menu(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		selecionar = Game.sprt.sprite(0, 7 * 16, 16, 16);
	}
	
	public void tick() {
		if (sair == false) {
			// System.out.println("Opção: " + opcoes[opcao_atual]);
			if (up) {
				up = false;
				opcao_atual--;
				if (opcao_atual < 0) {
					opcao_atual = opcoes_totais - 1;
				}
			} else if (down) {
				down = false;
				opcao_atual++;
				if (opcao_atual >= opcoes_totais) {
					opcao_atual = 0;
				}
			}
			//// Ao confirmar ////
			if (enter) {
				enter = false;
				if (opcoes[opcao_atual] == "jogar") {
					Game.estadoGame = "NORMAL";
					Game.pause = false;
					Game.lv_atual = 1;
					String novoGame = "nivel" + Game.lv_atual + ".png";
					World.reinicia(novoGame);
					opcao_atual = 0; 
				} else if (opcoes[opcao_atual] == "instrucoes") {
					Game.estadoGame = "INSTRUCOES";
					opcao_atual = 0;
				} else if (opcoes[opcao_atual] == "sair") {
					sair = true;
					opcao_atual = 0;
				}
			}
		}
		else if (sair) {
			System.out.println("Opção: " + saida[current] + ". Numero: " + current);
			if (left) {
				left = false;
				current--;
				if (current < 0) {
					current = currentSaida - 1;
				}
				
			} else if (right) {
				right = false;
				current++;
				if (current >= currentSaida) {
					current = 0;
				}
			}
			
			if (enter) {
				enter = false;
				if (saida[current] == "sim") {
					System.exit(1);
				} else if (saida[current] == "nao") {
					sair = false;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(image, 0, 0, Game.largura * Game.escala, Game.altura * Game.escala, null);
		
		// Selecionar //
		if (opcoes[opcao_atual] == "jogar") {
			g.drawImage(selecionar, 220, 190, 20, 20, null);
			g.drawImage(selecionar, 505, 190, 20, 20, null);
		} else if (opcoes[opcao_atual] == "instrucoes") {
			g.drawImage(selecionar, 220, 250, 20, 20, null);
			g.drawImage(selecionar, 510, 250, 20, 20, null);
		} else if (opcoes[opcao_atual] == "sair") {
			g.drawImage(selecionar, 290, 310, 20, 20, null);
			g.drawImage(selecionar, 425, 310, 20, 20, null);
		}
		
		/// Texto ///
		g.setColor(new Color(196, 24, 55));
		g.setFont(new Font("arial", Font.BOLD, 50));
		g.drawString("Novo jogo", 250, 220);
		g.drawString("Instruções", 250, 280);
		g.drawString("Sair", 320, 340);
		
		if (sair) {
			Graphics2D graf = (Graphics2D) g;
			graf.setColor(new Color(10, 10, 30, 220));
			g.fillRoundRect(200, 170, 350, 200, 20, 20);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Deseja mesmo sair?", 230, 210);
			/// Opções de saida ///
			g.setFont(new Font("arial", Font.BOLD, 24));
			g.drawString("Sim :(", 230, 300);
			g.drawString("Não :)", 450, 300);
			/// Seletor ///
			if (saida[current] == "sim") {
				g.drawImage(selecionar, 250, 305, 20, 20, null);
			} else if (saida[current] == "nao") {
				g.drawImage(selecionar, 470, 305, 20, 20, null);
			}
		}
	}
	
}
