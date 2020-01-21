package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Random;

import javax.swing.ImageIcon;

import com.tutorial.main.Game.STATE;

public class Menu extends KeyAdapter {
	
	private HUD hud;
	private Game game; 
	private Handler handler;
	private Random r  = new Random();
	private Image background, title, button, button_1;
	private int BX = 320, BY = 300, BW = 305, BH = 64;
	private int currentChoice;
	public Boss boss;
	private int options_length = 3;
	private String [] menu_buttons = { "Play", "Options", "Exit"};
	private String [] end_buttons = { "Retry", "Back To Menu"};
	private String [] options_buttons = { "Back" };
	private boolean music_change = false;
	private boolean fx_change = false;
	private int music_volume = 8;
	private int fx_volume = 8;
	
	public Menu(Game game, Handler handler, HUD hud){
		this.game = game;
		this.handler = handler;
		this.hud = hud;

		
		
		//IMAGES
		background  = new ImageIcon(getClass().getResource("/Background/warp_space.gif")).getImage();
		title  = new ImageIcon(getClass().getResource("/Background/Title.png")).getImage();
		button  = new ImageIcon(getClass().getResource("/Background/Button.png")).getImage();
		button_1  = new ImageIcon(getClass().getResource("/Background/Button_2.png")).getImage();
		
	}
	
	private void newGame() {
		 Game.gameState = STATE.Game;
	     hud.setLevel(1);
	     hud.setScore(0);
		 handler.addObject(new Player(game.WIDTH/2-20, game.HEIGHT-80, ID.Player, handler));
		 //this.boss = new Boss((Game.WIDTH / 2)-35, -120, ID.Boss, handler);
		 //handler.addObject(boss);
		 //handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH- 50), r.nextInt(Game.HEIGHT- 50), ID.BasicEnemy, handler)); 
	}
	
	private void select(){
		
////////////////////////START MENU STATE ////////////////////////////////////////
		if(Game.gameState == STATE.Menu){
			
			//PLAY BUTTON
			if(currentChoice == 0) {
				Game.gameState = STATE.Game;
				this.newGame();
			}
			
			//OPTIONS BUTTON
			if(currentChoice == 1) {
				Game.gameState = STATE.Options;
				currentChoice = 0;
				
			}
			
			//EXIT BUTTON
			if(currentChoice == 2) {
				System.exit(1);
			}
			
		}
////////////////////////OPTIONS STATE ////////////////////////////////////////
		if(Game.gameState == STATE.Options){
			
			//BACK BUTTON
			if (currentChoice == 2) {
				Game.gameState = STATE.Menu;  
				currentChoice = 0;
			}
			
			
		}
////////////////////////GAME OVER STATE ////////////////////////////////////////
		if(Game.gameState == STATE.End){
			//RETRY BUTTON
			if(currentChoice == 0) {
				this.newGame(); 
			}
			
			//BACK TO MENU BUTTON
			if(currentChoice == 1) {
				Game.gameState = STATE.Menu;	
				currentChoice = 0;
			}
		}
		
	}
	
	public void tick(){
		/// ***NOTE THAT YOU HAVE TO ADD NEW STATES TO GAME.JAVA TICK METHOD ON LINE 126
		if(Game.gameState == STATE.Menu){
			options_length = 3;
		} 
		
		if(Game.gameState == STATE.Options){
			options_length = 3;
			
			if (currentChoice == 0){
				music_change = true;
			} else {
				music_change = false;
			}
			
			if (currentChoice == 1){
				fx_change = true;
			} else {
				fx_change = false;
			}
		} 
		
		if(Game.gameState == STATE.End){
			options_length = 2;
		}
		
	}
	
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		
		if(k == KeyEvent.VK_SPACE){
			select();
		}
		

		if(k == KeyEvent.VK_RIGHT){
			if (music_change && music_volume < 15 && music_volume >= 0){
				music_volume++;
				Game.music.getMusic("music").setVolume((float)music_volume/10);
			}
			
			if (fx_change && fx_volume < 15 && fx_volume >= 0){
				fx_volume++;
				//Game.music.getMusic("music").setVolume((float)music_volume/10);
			}
		}
				
		if(k == KeyEvent.VK_LEFT){
			if (music_change && music_volume <= 15 && music_volume > 0){
				music_volume--;
				Game.music.getMusic("music").setVolume((float)music_volume/10);
			}
			
			if (fx_change && fx_volume <= 15 && fx_volume > 0){
				fx_volume--;
				//Game.music.getMusic("music").setVolume((float)music_volume/10);
			}
		}
	
			
		if(k == KeyEvent.VK_UP){
			currentChoice--;
			if(currentChoice == -1){
				currentChoice = options_length -1;
			}
		}
		
		if(k == KeyEvent.VK_DOWN){
			currentChoice++;
			if(currentChoice == options_length){
				currentChoice = 0;
			} 
		}
		// DEBUG System.out.println("Can change volume: " + music_change + "\nCurrent choice: " + currentChoice);
	}
	
	public void keyReleased(int k) {}	

	public void render(Graphics g){
		
//////////////////////// START MENU STATE ////////////////////////////////////////
		if(game.gameState == STATE.Menu) {
			
			//draw background and title
			g.drawImage(background, 0, 0, game.WIDTH, game.HEIGHT, null);
			g.drawImage(title, 220, 30, 488, 240, null);
			
			//DRAW MENU OPTIONS
			Font fnt = new Font ("arcadeclassic", 1, 30);
			g.setFont(fnt);
			
			for(int i = 0; i < menu_buttons.length; i++){
				if(i == currentChoice){
					g.drawImage(button_1, BX, BY + i*100, BW, BH, null);
					g.setColor(Color.white);
					
				} else {
					g.drawImage(button, BX, BY + i*100, BW, BH, null);
					g.setColor(Color.BLACK);
				}
				
				if (i == 1 ) g.drawString(menu_buttons[i], BX + 95, 343 + i*100);
				else g.drawString(menu_buttons[i], BX + 115, 343 + i*100);
			}
			
			//DRAW CREDITS AND VERSION
			g.setColor(Color.white);
			Font fnt2 = new Font ("arial", Font.BOLD, 11);
			g.setFont(fnt2);
			g.drawString("MADE BY ARAN   V.0.0.2", 770, 658);
			
////////////////////////OPTIONS STATE ////////////////////////////////////////
		} else if(game.gameState == STATE.Options){
	
			//draw background and title
			g.drawImage(background, 0, 0, game.WIDTH, game.HEIGHT, null);
			
			
			//DRAW OPTIONS
			Font fnt = new Font ("arcadeclassic", 1, 30);
			g.setFont(fnt);
			
			//VOLUME SEGMENT
			g.setColor(new Color(255, 255, 255, 80));
			g.fillRoundRect(185, 208, 580, 50, 10, 10);
			g.setColor(Color.white);
			g.drawString("Music", 200, 243);
			for (int i = 0; i < (music_volume); i++){
				g.fillRect(330 + i*20, 220, 10, 25);
			}
			
			g.setColor(new Color(255, 255, 255, 80));
			g.fillRoundRect(185, 268, 580, 50, 10, 10);
			g.setColor(Color.white);
			g.drawString("FX", 200, 303);
			for (int i = 0; i < (fx_volume); i++){
				g.fillRect(330 + i*20, 280, 10, 25);
			}
			
			//MUSIC
			if(currentChoice == 0){
				g.setColor(new Color(200, 200, 200, 80));
				g.fillRoundRect(185, 208, 580, 50, 10, 10);
			}
			//FX
			if (currentChoice == 1){
				g.setColor(new Color(200, 200, 200, 80));
				g.fillRoundRect(185, 268, 580, 50, 10, 10);
			}
			//BACK
			if(currentChoice == 2){
				g.drawImage(button_1, BX, BY + 1*100, BW, BH, null);
				g.setColor(Color.white);
				g.drawString(options_buttons[0], BX + 110, 343 + 1*100);
			} else {
				g.drawImage(button, BX, BY + 1*100, BW, BH, null);
				g.setColor(Color.BLACK);
				g.drawString(options_buttons[0], BX + 110, 343 + 1*100);
				
			}	
		}
		

////////////////////////GAME OVER STATE ////////////////////////////////////////
		 else if(game.gameState == STATE.End){
			 
			 	//draw background and title
				g.drawImage(background, 0, 0, game.WIDTH, game.HEIGHT, null);
			 
				Font fnt = new Font ("arcadeclassic", 1, 30);
				Font fnt2 = new Font("arcadeclassic", 1, 35);
				Font fnt3 = new Font("arcadeclassic", 1, 100);
				
				g.setColor(Color.red);
				g.setFont(fnt3);
				g.drawString("GAME OVER", 230, 100);
				
				g.setColor(Color.white);
				g.setFont(fnt2);
				g.drawString("Your Score Was     " + hud.getScore(), 300, 160);
				g.setColor(Color.white);
					
				g.setFont(fnt);
				for(int i = 0; i < end_buttons.length; i++){
					if(i == currentChoice){
						g.drawImage(button_1, BX, BY + i*100, BW, BH, null);
						g.setColor(Color.white);
						
					} else {
						g.drawImage(button, BX, BY + i*100, BW, BH, null);
						g.setColor(Color.BLACK);
					}

					if (i == 1 ) g.drawString(end_buttons[i], BX + 65, 343 + i*100);
					else g.drawString(end_buttons[i], BX + 110, 343 + i*100);
				}
				
		}
	}
	
}
