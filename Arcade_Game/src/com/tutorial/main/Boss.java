package com.tutorial.main;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Boss extends GameObject {
	
	private Handler handler;
	Random r = new Random();
	public int BOSS_HEALTH = 50;
	private Image boss;
	
	private int timer = 60;
	private int timer2 = 50;
	
	public Boss(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velX = 0;
		velY = 2;
		boss  = new ImageIcon(getClass().getResource("/Bosses/Salt_bae.png")).getImage();

	}
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			
			if(tempObject.getId() == ID.Bullet && timer2 <= -30) { //temp object er naa satt som Bullet
				//Hvis vi kolliderer med bullet
				if(getBounds().intersects(tempObject.getBounds())){
					if(BOSS_HEALTH <= 1){
						handler.removeObject(this);
						Game.bossBattle = false;
					} else {
						handler.removeObject(tempObject);
						BOSS_HEALTH -= 1;
					}
				}
			}
			
		}
	}
	
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 215, 200);
	}
	
	@Override
	public void tick() {
		
		collision();
		x +=velX;
		y +=velY;
		
		//TIMER FOR BOSS COMING INTO SCREEN
		if(timer <= 0)velY = 0;
		else timer--;
		
		//TIMER UNTIL BOSS STARTS SHOOTING
		if(timer <=0) timer2--;
		if(timer2 <=0){
			
			if(velX == 0) velX = 2;
			
			//BOSS GETS FASTER WITH TIME
			if(velX >0)
				velX += 0.005f;
			else if (velX < 0)
				velX -= 0.005f;
			
			velX = Game.clamp(velX, -10, 100);
			
			//SPAWN BULLETS RANDOMLY
			int spawn = r.nextInt(10);
			if(spawn == 0) handler.addObject(new BossBullet((int)x+22, (int)y+12, ID.BossBullet, handler));
		}
		
		//BOSS STOPS AT THE EDGES AND TURN
		if(x <= 0 || x >= Game.WIDTH -180) velX *= -1;
		
	
	}
	
	@Override
	public void render(Graphics g ) {
		

		g.drawImage(boss, (int)x, (int)y, 200, 200, null);
		
	}
	
}
