package com.rogersgames.world;

public class Camera {
	
	public static int x, y; // Posi��o de nossa c�mera
	
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
