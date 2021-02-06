package com.rogersgames.principal;

import java.applet.Applet;
import java.applet.AudioClip;

public class Audio {
	
	private AudioClip audio;
	////// Audios do game /////////
	public static final Audio fundo = new Audio("/fundo.wav");
	
	public Audio(String path) {
		audio = Applet.newAudioClip(Audio.class.getResource(path));
	}
	
	public void play() {
		new Thread() {
			public void run() {
				audio.play();
			}
		}.start();
	}
	
	public void loop() {
		new Thread() {
			public void run() {
				audio.loop();
			}
		}.start();
	}
	
}
