package com.rogersgames.principal;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.rogersgames.entities.Entities;
import com.rogersgames.entities.Icsizine;
import com.rogersgames.entities.LixoLata;
import com.rogersgames.entities.LixoSaco;
import com.rogersgames.entities.Player;
import com.rogersgames.entities.Tiro;
import com.rogersgames.estados.Derrota;
import com.rogersgames.estados.Instruções;
import com.rogersgames.estados.Menu;
import com.rogersgames.estados.Pause;
import com.rogersgames.estados.Vitoria;
import com.rogersgames.spritesheet.Spritesheet;
import com.rogersgames.world.World;

public class Game extends Canvas implements Runnable, KeyListener {
	
	public static final int largura = 240;
	public static final int altura = 200;
	public static final int escala = 3;
	
	private boolean isRunning;
	private JFrame window;
	private Thread threads;
	private BufferedImage image;
	
	public static Spritesheet sprt;
	public static ArrayList<Entities> entity;
	public static Player player;
	public static ArrayList<Icsizine> rem;
	public static ArrayList<LixoLata> lixo;
	public static ArrayList<LixoSaco> saco;
	public static ArrayList<Tiro> atira;
	public static UI interf;
	public static World map;
	
	public Menu menu;
	public Vitoria venceu;
	public Derrota perdeu;
	public Instruções instr;
	public Pause pausa;
	
	public static final int max_lv = 5;
	public static int lv_atual = 1;
	
	public static String estadoGame = "MENU";
	public static boolean pause;
	
	public Audio som;
	
	public void tela(String titulo) {
		window = new JFrame(titulo);
		
		window.add(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		window.pack();
		window.setLocationRelativeTo(null);
	}
	
	public Game() {
		setPreferredSize(new Dimension(largura * escala, altura * escala));
		addKeyListener(this);
		
		threads = new Thread(this);
		image = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
		
		sprt = new Spritesheet("/Spritesheet.png");
		entity = new ArrayList<Entities>();
		player = new Player(50, 50, 16, 16, sprt.sprite(32, 0, 16, 16)); // Ordem: x, y, largura, altura, sprite
		entity.add(player);
		rem = new ArrayList<Icsizine>();
		saco = new ArrayList<LixoSaco>();
		lixo = new ArrayList<LixoLata>();
		atira = new ArrayList<Tiro>();
		interf = new UI();
		map = new World("/nivel" + lv_atual + ".png");
		
		menu = new Menu("/tela_inicial.png");
		venceu = new Vitoria("/venceu.png");
		perdeu = new Derrota("/perdeu.png");
		instr = new Instruções();
		pausa = new Pause();
		
		/// Audio de fundo ///
		som.fundo.loop();
		
		tela("Minha baguncinha");
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void start()  {
		if (isRunning == false)
			isRunning = true;
		
		threads.start();
	}
	
	public void tick() {
		
		////// Se o jogo estiver rodando, acontecendo //////
		if (estadoGame == "NORMAL") {
			if (pause == false) {
				// System.out.println(pausa);
				/// tick das entidades ///
				for (int i = 0; i < entity.size(); i++) {
					Entities atual = entity.get(i);
					atual.tick();
				}
				
				/// tick do tiro ///
				for (int t = 0; t < atira.size(); t++) {
					Tiro atual = atira.get(t);
					atual.tick();
				}
				
				/// tick do ui ///
				interf.tick();
			} 
			else if (pause) {
				pausa.tick();
			}
		}
		
		/////// Se o jogo estiver no menu //////////
		else if (estadoGame == "MENU") {
			menu.tick();
		}
		/////// Se o jogo estiver na tela de Game Over ////////
		else if (estadoGame == "GAME OVER") {
			perdeu.tick();
		}
		/////// Se o jogo estiver na tela de vitória ////////
		else if (estadoGame == "VENCEU") {
			venceu.tick();
		}
		//////// Se o jogo estiver na tela de instruções //////////
		else if (estadoGame == "INSTRUCOES") {
			instr.tick();
		}
		
	}
	
	public void render() {
		/////// BufferStrategy //////////
		BufferStrategy memory = getBufferStrategy();
		if (memory == null) {
			createBufferStrategy(3);
			return;
		}
		
		////////// Gráficos ////////////
		Graphics graficos = image.getGraphics();
		
		/////////// Aqui iniciam os desenhos pixel ////////////
		
		///// Estado de jogo quando estivermos jogando //////
		if (estadoGame == "NORMAL") {
			graficos.setColor(Color.BLACK);
			graficos.fillRect(0, 0, largura, altura);
			
			/// render do mapa ///
			map.render(graficos);
			
			/// render das entidades ///
			for (int i = 0; i < entity.size(); i++) {
				Entities atual = entity.get(i);
				atual.render(graficos);
			}
			
			/// render do tiro ///
			for (int t = 0; t < atira.size(); t++) {
				Tiro atual = atira.get(t);
				atual.render(graficos);
			}
			//// render da pausa ////
			if (pause) {
				pausa.render(graficos);
			}
		}
				
		/////////// Aqui se encerram os desenhos pixel ///////////
		graficos.dispose();
		requestFocus();
		graficos = memory.getDrawGraphics();
		graficos.drawImage(image, 0, 0, largura * escala, altura * escala, null);

		/// render UI ///
		// Isso tambem so é renderizado quando estamos jogando //
		if (estadoGame == "NORMAL") {
			interf.render(graficos);
		}
		
		else if (estadoGame == "MENU") {
			menu.render(graficos);
		}
		
		else if (estadoGame == "VENCEU") {
			venceu.render(graficos);
		}
		
		else if (estadoGame == "GAME OVER") {
			perdeu.render(graficos);
		}
		
		else if (estadoGame == "INSTRUCOES") {
			instr.render(graficos);
		}
		
		
		memory.show();
	}
	
	public void run() {
		long antes = System.nanoTime();
		int fps = 60;
		double ns = 1000000000 / fps;
		
		int frames = 0;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		while(isRunning) {
			long agora = System.nanoTime();
			delta += (agora - antes) / ns;
			antes = agora;
			
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if (System.currentTimeMillis() - timer >= 1000) {
				// System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		///// Controles validos quando o jogo estiver rodando //////
		if (estadoGame == "NORMAL") {
			// Movimento do player //
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				player.up = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				player.down = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player.left = true;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.right = true;
			}
			
			// Atirando //
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				player.atirando = true;
			}
			
			// Pausou o game //
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if (pause == false)
					pause = true;
				else if (pause == true)
					pause = false;
			}
			
			if (pause) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					pausa.up = true;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					pausa.down = true;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pausa.enter = true;
				}
			}
		}
		
		///// Controles validos quando o jogo estiver no menu principal ///////
		else if (estadoGame == "MENU") {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				menu.up = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.down = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				menu.left = true;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				menu.right = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				menu.enter = true;
			}
		}
		
		////// Controles validos quando estivermos nas instruções ////////
		else if (estadoGame == "INSTRUCOES") {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				instr.left = true;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				instr.right = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				instr.enter = true;
			}
		}
		
		/////// Controles validos quando estivermos no GAME OVER ///////
		if (estadoGame == "GAME OVER") {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				perdeu.menu = true;
			}
		}
		
		/////// Controles válidos quando estivermos no VENCEU ///////
		if (estadoGame == "VENCEU") {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				venceu.menu = true;
			}
		}
		
	}

	public void keyReleased(KeyEvent e) {
		if (estadoGame == "NORMAL") {
			// Movimento do player //
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				player.up = false;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				player.down = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player.left = false;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.right = false;
			}
			
			// Atirando //
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				player.atirando = false;
			}
		}
		else if (estadoGame == "MENU") {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				menu.up = false;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.down = false;
			}
		}
	}

	public void keyTyped(KeyEvent arg0) {
		
	}

}
