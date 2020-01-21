package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BossBullet extends GameObject {
	
	private Handler handler;
	Random r = new Random();

	public BossBullet(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velX = r.nextInt((5- -5) + -5);
		velY = 5;
	}
	
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 8, 8);
	}
	
	@Override
	public void tick() {
		x +=velX;
		y +=velY;
		
		
		//Bytter vei i 90 grader naar det kommer til en vegg
//		if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
//		if(x <= 0 || x >= Game.WIDTH - 32) velX *= -1;
		
		if (y >= Game.HEIGHT) handler.removeObject(this);
		
		
		//handler.addObject(new Trail(x, y, ID.Trail, Color.white, 8, 8, 0.1f, handler));
	}
	
	@Override
	public void render(Graphics g ) {
		
		
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, 9, 9);

	}
	
}
