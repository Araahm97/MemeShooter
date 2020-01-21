package com.tutorial.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.tutorial.main.Game.STATE;

public class KeyInput extends KeyAdapter {
	//Keyadapter har egne metoder i sin lib (keyPressed() og keyReleased())
	
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	private Game game;
	
	
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		//looper gjennom alle objectene
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			//hvis det er spiller ID...
			if(tempObject.getId() == ID.Player) {
				//key events for player 1

				if(key == KeyEvent.VK_RIGHT) {tempObject.setVelX(7);    keyDown[0] = true;}
				if(key == KeyEvent.VK_LEFT) {tempObject.setVelX(-7);   keyDown[1] = true;}
				if(key == KeyEvent.VK_SPACE && keyDown[2]!= true) {
		         handler.addObject(new Bullet((int)tempObject.getX()+3, (int)tempObject.getY(), ID.Bullet, handler));
		         keyDown[2] = true;
		        }
			}
			
		}
		
		//MUTE
		if(key == KeyEvent.VK_X){
			if (!Game.mute){ Game.music.getMusic("music").pause(); Game.mute = true; }
			else {
				Game.music.getMusic("music").resume(); Game.mute = false;
			}
		}

			
		//PAUSE THE GAME
		if(key == KeyEvent.VK_P){
			if(game.gameState == STATE.Game){
				if(Game.paused) Game.paused = false;
				else Game.paused = true;
			}
		}
		
		
		
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		//looper gjennom alle objectene
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			//hvis det er spiller ID...
			if(tempObject.getId() == ID.Player) {
				
				//key events for player 1
				
				if(key == KeyEvent.VK_RIGHT) {keyDown[0] = false;}	
				if(key == KeyEvent.VK_LEFT) {keyDown[1] = false;}
				if(key == KeyEvent.VK_SPACE) {keyDown[2] = false;}
				
				
				//GIVES BETTER CONTROL
				if(!keyDown[0] && !keyDown[1]) {
					tempObject.setVelX(0);
				}
				
				//STOP SHOOTING
				if(key == KeyEvent.VK_SPACE) {
					
				}
				
			}
		}
				
	}
}
