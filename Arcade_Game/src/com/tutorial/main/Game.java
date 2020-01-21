package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


import javax.swing.ImageIcon;

public class Game extends Canvas implements Runnable {
	
	//Inneholder selve spillet

    private static final long serialVersionUID = -240840600533154354L;
    
    //setter variable for storrelse  paa vindu
    public static final int WIDTH = 940, HEIGHT = WIDTH / 12*9;

    private Thread thread;
    private boolean running = false;
    public static boolean paused = false;
    public static AudioPlayer music;
    public static float volume = 0.5f; 
    public static boolean mute;
    public static boolean bossBattle = false;
    public static int scoreKeep;
    

    private Handler handler;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;
    private Image background;
    
    public enum STATE {
    	Menu,
    	Options,
    	Game,
    	End
    	
    };
    
    public static STATE gameState = STATE.Menu;
    
    public static BufferedImage sprite_sheet;
    
    //Constructor lager handler, window og legger inn Gameobjects i spillet
    public Game() {
    		
    	handler = new Handler();
    	hud = new HUD();
    	menu = new Menu(this, handler, hud);
    	
    	//INPUT
    	this.addKeyListener(new KeyInput(handler, this));
    	this.addKeyListener(menu);
    	//this.addMouseListener(menu);
    	
    	//MUSIC
    	//music.init();
    	//music.getMusic("music").loop(1, 0.8f);
    
    	new Window (WIDTH, HEIGHT, "First Game!", this);
    	
    	background = new ImageIcon(getClass().getResource("/Background/background.gif")).getImage();
    	
	    spawner = new Spawn(handler, hud, this);
	    
	    
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
    		//Dette lar oss vaare aktive i vinduet naar det aapner
    		this.requestFocus();
    		
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long  timer = System.currentTimeMillis();
        int frames = 0;
        
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            while(delta >= 1){
                tick();
                delta--;
            }
            
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer >1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        
        stop();
    }

    private void tick(){
    
        
        if (gameState == STATE.Game){
        	
        	if(!paused){
        		hud.tick();
     	        spawner.tick();
     	        handler.tick();
     	        
     	        if(HUD.HEALTH <= 0){
     	        	HUD.HEALTH = 170;
     	        	gameState = STATE.End;
     	        	handler.clearEnemies();	
     	        }
        	}
        	
        } else if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Options){
         	menu.tick();
            handler.tick();
        }       
    }

    private void render(){
    	
        BufferStrategy bs = this.getBufferStrategy();
         if(bs == null) {
             this.createBufferStrategy(3);
             return;
         }

        Graphics g = bs.getDrawGraphics();

        //g.setColor(Color.black);
        //g.fillRect(0, 0, WIDTH, HEIGHT);
        
        if(gameState == STATE.Game) {
        	g.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);
        }
        handler.render(g);
        
        if(gameState == STATE.Game) {

        	hud.render(g);
        	   	
        	if(paused){
        		g.setColor(Color.white);
        		Font fnt = new Font("arial", 1, 50);
        		g.setFont(fnt);
        		g.drawString("PAUSED", 230, 230);
        	}
        } else if (gameState == STATE.Menu || gameState == STATE.Options || gameState == STATE.End){
        	menu.render(g);
        }
        
        g.dispose();
        bs.show();
    }
    
    public static float clamp(float var, float min, float max) {
    		
    	if(var >= max) {
    			return var = max;
    			
    		} else if (var <= min) {
    			return var = min;
    			
    		} else {
    			return var;
    		}
    }

    public static void main(String[] args) {
        new Game();
    }
}

