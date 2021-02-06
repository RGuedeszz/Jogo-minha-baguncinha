package com.rogersgames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.rogersgames.entities.Carol;
import com.rogersgames.entities.Entities;
import com.rogersgames.entities.Icsizine;
import com.rogersgames.entities.LixoLata;
import com.rogersgames.entities.LixoSaco;
import com.rogersgames.entities.Player;
import com.rogersgames.entities.Tiro;
import com.rogersgames.principal.Game;
import com.rogersgames.principal.UI;
import com.rogersgames.spritesheet.Spritesheet;
import com.rogersgames.tiles.Chao;
import com.rogersgames.tiles.Parede;
import com.rogersgames.tiles.Tiles;

public class World {
	
	public BufferedImage mapa;
	public Tiles[] tile;
	public int altura, largura;
	public final int tamanhoQuadrados = 16;
	
	/// Método construtor. Dentro dele passamos como parâmetro o nome do documento do mapa
	public World(String path) {
		try {
			mapa = ImageIO.read(getClass().getResource(path)); // Vai ler e retornar o diretório para a maquina entender onde está
			altura = mapa.getHeight(); // Dimensões de nosso mapa (altura)
			largura = mapa.getWidth(); // Dimensões de nosso mapa (largura)
			
			int[] pixels = new int[altura * largura]; // A quantidade de pixels no mapa corresponde a área do mapa
			tile = new Tiles[altura * largura]; // Os tiles também correspondem a 100% da area do mapa, afinal tudo ou é chao ou é parede ou qualquer outra coisa
			mapa.getRGB(0, 0, largura, altura, pixels, 0, largura); // Vai pegar o padrao RGB de cada pixel em uma determinada posição
			
			/// Rodando o mapa todo agora ///
			for (int x = 0; x < largura; x++) { // Rodando todo mapa horizontalmente (eixo x)
				for (int y = 0; y < altura; y++) { // Rodando todo mapa verticalmente (eixo y)
					int pixelAtual = pixels[x + y * largura]; // Pega o pixel atual do mapa. O que está dentro do [] é a posição dele unidimensional
					
					// A principio, tudo por padrão é chao
					tile[x + y * largura] = new Chao(x * tamanhoQuadrados, y * tamanhoQuadrados, Tiles.tile_chao); // Ordem: x, y, largura, altura, sprite
					
					/// Agora iniciamos as condições de cores no mapa ///
					switch (pixelAtual) {
					case 0xFF000000: // Parede
						tile[x + y * largura] = new Parede(x * tamanhoQuadrados, y * tamanhoQuadrados, Tiles.tile_parede);
						break;
					
					case 0xFFB6FF00: // Rocky
						Game.player.setX(x * tamanhoQuadrados);
						Game.player.setY(y * tamanhoQuadrados);
						break;
					
					
					case 0xFFFF0000: // Poli
						Carol poli = new Carol(x * tamanhoQuadrados, y * tamanhoQuadrados, 16, 16, Carol.carolPoli); // Ordem: x, y, larg, alt, sprite
						Game.entity.add(poli);
						break;
					
					
					case 0xFF00FF21: // Saco
						LixoSaco saco = new LixoSaco(x * tamanhoQuadrados, y * tamanhoQuadrados, 16, 16, LixoSaco.saco);
						Game.entity.add(saco);
						Game.saco.add(saco);
						break;
					
					case 0xFFFF8821: // Lata
						LixoLata lata = new LixoLata(x * tamanhoQuadrados, y * tamanhoQuadrados, 16, 16, LixoLata.lixao);
						Game.entity.add(lata);
						Game.lixo.add(lata);
						break;
						
					case 0xFFFFAAC6: // Icsizine
						Icsizine remedio = new Icsizine(x * tamanhoQuadrados, y * tamanhoQuadrados, 16, 16, Icsizine.remedio);
						Game.entity.add(remedio);
						Game.rem.add(remedio);
						break;
					}
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void reinicia(String prox_lv) {
		//// Limpa ////
		Game.entity.clear();
		Game.lixo.clear();
		Game.saco.clear();
		Game.atira.clear();
		Game.rem.clear();
		//// Reaparece ////
		Game.sprt = new Spritesheet("/Spritesheet.png");
		Game.entity = new ArrayList<Entities>();
		Game.player = new Player(50, 50, 16, 16, Game.sprt.sprite(32, 0, 16, 16)); // Ordem: x, y, largura, altura, sprite
		Game.entity.add(Game.player);
		Game.rem = new ArrayList<Icsizine>();
		Game.saco = new ArrayList<LixoSaco>();
		Game.lixo = new ArrayList<LixoLata>();
		Game.atira = new ArrayList<Tiro>();
		Game.interf = new UI();
		
		///// Mudança de mapa //////
		Game.map = new World("/" + prox_lv);
		
		return;
	}
	
	public void render(Graphics g) {
		for (int x = 0; x < mapa.getWidth(); x++) { // Rodando o mapa pela horizontal (eixo x)
			for (int y = 0; y < mapa.getHeight(); y++) { // Rodando o mapa pela vertical (eixo y)
				Tiles tiles = tile[x + y * mapa.getWidth()]; // Pegamos o tile na posição
				tiles.render(g); // Renderizamos esse tile
			}
		}
	}
}
