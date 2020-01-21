package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Bullet extends GameObject {
	
	private Handler handler;

	public Bullet(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velY = -8;
	}
	
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 10, 10);
	}
	
	@Override
	public void tick() {
		x +=velX;
		y +=velY;
		
		
		if (y <= 0) handler.removeObject(this);
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.RED, 10, 10, 0.1f, handler));
		
		collision();
	}
	
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			
			if(tempObject.getId() == ID.BasicEnemy || 
				tempObject.getId() == ID.FastEnemy || 
				tempObject.getId() == ID.SmartEnemy
				
			  ) { 
				
				//Hvis vi kolliderer med enemy
				if(getBounds().intersects(tempObject.getBounds())){
					HUD.score+=20;
					handler.removeObject(tempObject);
					
					
				}
			}
			
		}
}
	
	@Override
	public void render(Graphics g ) {
		
		
		g.setColor(Color.RED);
		g.drawRect((int)x, (int)y, 10, 10);
	}
	
}
