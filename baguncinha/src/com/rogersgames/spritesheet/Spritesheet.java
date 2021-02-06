package com.rogersgames.spritesheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	public BufferedImage image;
	
	/// M�todo construtor com o par�metro para ser o nome do arquivo onde est�o as sprites
	public Spritesheet(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path)); // Vai ler o diret�rio do aquivo do spritesheet
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/// M�todo para pegar a sprite dentro do spritesheet, isto �, a sub imagem
	public BufferedImage sprite(int x, int y, int largura, int altura) {
		return image.getSubimage(x, y, largura, altura);
	}
	
}
