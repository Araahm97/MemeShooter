package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;



public class HUD {
	
	public static float HEALTH = 170;
	public static boolean dmg = false;
	private boolean showingDmg = false;
	private long flinchTimer = 100;
	
	private Image healthbar =  new ImageIcon(getClass().getResource("/HUD/Healthbar.png")).getImage();
	private Image dmg_notify =  new ImageIcon(getClass().getResource("/HUD/Dmg.png")).getImage();
	public static int score = 0;
	private int level = 1; 
	private Timer timer;
	
	
	public void tick() {	
		HEALTH = Game.clamp(HEALTH, 0, 170);
	
		
		if(dmg && !showingDmg) {
			showingDmg = true;
			long elapsed = (System.nanoTime() -flinchTimer) / 10000;
			if (elapsed > 1000) {
				dmg = false;
				showingDmg = false;
			}
		}
	}
	
	public void render (Graphics g) {
		
		g.setColor(new Color(120, 200, 0));
		g.fillRect(37, 24, (int)HEALTH, 23);
		
		
		
		
		g.drawImage(healthbar, 10, 15, 204, 40, null);
		g.setColor(Color.white);
		Font fnt = new Font("arcadeclassic", 1, 18);
	    g.setFont( fnt );
		g.drawString("Score: " + score, 25, 75);
		//g.drawString("Level: " + level, 25, 94);
		
	    if (dmg == true){
	     	g.drawImage(dmg_notify, 0, 0, 940, 700, null);

		}
	}
	
	public void hit () {
		if (showingDmg) return;
		
	}
	
	public void setScore (int score) {
		this.score = score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
}
