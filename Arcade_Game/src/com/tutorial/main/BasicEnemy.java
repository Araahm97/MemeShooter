package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BasicEnemy extends GameObject {
	
	int counter= 0;
	Handler handler;

	public BasicEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		velY = 2;
		this.velX = x;
		this.handler = handler;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}
	
	@Override
	public void tick() {
		
		 y+= velY; 
		 x =  velX + ( 50 * (float)Math.sin(y/20));
		
		//TURN AROUND WHEN REACHING CORNER OR WALL
		//if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		//if(x <= 0 || x >= Game.WIDTH - 32) velX *= -1;
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.white, 16, 16, 0.1f, handler));
	}
	
	@Override
	public void render(Graphics g ) {
			 
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, 16, 16);
		//g.drawImage(player_image, (int)x,(int) y, null);	
	}
}
