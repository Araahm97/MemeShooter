package com.tutorial.main;

import java.util.Random;

public class Spawn {
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	private Game game;

	
	private int timer = 0;

	
	
	public Spawn (Handler handler, HUD hud, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
	}
	
	
	public void tick() {
		
		timer++;
		
		//if score is less than required for the first boss
		if(!game.bossBattle){
			
			//clear all enemies out of bounds
			handler.clearEnemies();
			
			//spawn new enemies every 100 points
			if(timer % 100 == 0){
				this.enemyWave();
				this.enemyWave();
			}
		}
		
		if(hud.getScore() == 1000 ){
			game.bossBattle = true;
			 handler.clearEnemies();
			 handler.addObject(new Boss((Game.WIDTH / 2)-35, -120, ID.Boss, handler));
		}
	}
	
	
	/*
	 *  Fiks dette ved aa sette inn en ints(int randomNumberOrigin, int randomNumberBound) 
	 *  i random slik at vi kan haandtere hvor en wave spawnes med hensyn til hvor det ble spawnet 
	 *  sist gang slik at eneimes inngaar aa overlappe hverandre
	 */
	public void enemyWave () {
		int wave_x = r.nextInt(Game.WIDTH -0);
		for(int i = -100; i < 0; i += 20 ) {
			handler.addObject(new BasicEnemy(wave_x, i, ID.BasicEnemy, handler));
		}
	}
}
