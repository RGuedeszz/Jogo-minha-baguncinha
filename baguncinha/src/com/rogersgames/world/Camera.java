package com.rogersgames.world;

public class Camera {
	
	public static int x, y; // Posição de nossa câmera
	
	public static int Clamp(int atual, int max, int min) {
		if (atual > max) {
			atual = max;
		}
		if (atual < min) {
			atual = min;
		}
		
		return atual;
	}
	
	
}
