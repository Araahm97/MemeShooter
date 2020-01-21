package com.tutorial.main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class BufferedImageLoader {
	BufferedImage image;
	Image image2;
	
	public BufferedImage loadImage(String path){
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {

			e.printStackTrace();
		}
		return image;
	}
	
	
	public Image loadIconImage(String path){
			image2 = new ImageIcon(getClass().getResource(path)).getImage();
		return image2;
	}
}
