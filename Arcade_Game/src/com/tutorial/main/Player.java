package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Player extends GameObject {

    Random r = new Random();
    Handler handler;
    private Image player_image;

    public Player(float x, float y, ID id, Handler handler){
        super(x, y, id);
        this.handler = handler;
        //player_image  = new ImageIcon(getClass().getResource("/Player/player.png")).getImage();

    }
    
    @Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 50, 50);
	}
    
    @Override
    public void tick() {
        x += velX;
        y += velY;
        
        x = Game.clamp(x, 0, Game.WIDTH -32);
        y = Game.clamp(y, 0, Game.HEIGHT-53);
        
        
    	//handler.addObject(new Trail(x, y, ID.Trail, Color.red, 32, 32, 0.1f, handler));

        
        collision();
        
    }
    
    
    private void collision() {
    		for(int i = 0; i < handler.object.size(); i++) {
    			
    			GameObject tempObject = handler.object.get(i);
    			
    			
    			if(tempObject.getId() == ID.BasicEnemy ||
    				tempObject.getId() == ID.FastEnemy ||
    				tempObject.getId() == ID.SmartEnemy||
    				tempObject.getId() == ID.BossBullet
    					) { //temp object er naa satt som basic enemy
    				//Hvis vi kolliderer med enemy
    				if(getBounds().intersects(tempObject.getBounds())){
    					handler.removeObject(tempObject);
    					
    					HUD.HEALTH -=8;
    					HUD.dmg = true;
    				}
    			}
    			
    			
    			
    		}
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int)x, (int)y, 32, 32);
    		//g.drawImage(player_image, (int)x,(int) y, 50, 75, null);
    }
 

}
