package com.rogersgames.spritesheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	public BufferedImage image;
	
	/// Método construtor com o parâmetro para ser o nome do arquivo onde estão as sprites
	public Spritesheet(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path)); // Vai ler o diretório do aquivo do spritesheet
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/// Método para pegar a sprite dentro do spritesheet, isto é, a sub imagem
	public BufferedImage sprite(int x, int y, int largura, int altura) {
		return image.getSubimage(x, y, largura, altura);
	}
	
}
