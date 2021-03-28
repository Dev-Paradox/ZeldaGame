package com.devparadox.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable 
{
	private static final long serialVersionUID = 1L;
	
	//Constants
	private final int WIDTH = 240;
	private final int HEIGHT = 160;
	private final static int SCALE = 3;
	
	//Thread - Game Loop
	private Thread thread;
	private boolean isRunning;
	
	//Background
	public static JFrame frame;
	private BufferedImage image;

	public Game()
	{
		//Set the resolution
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		//Initialize the frame (window of the game)
		InitFrame();
		
		//Initialize the image to be buffered into the game
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	public void InitFrame()
	{
		//Instance a new Frame
		frame = new JFrame("Zelda");
		
		//Add the Canvas into Frame
		frame.add(this);
		
		//Packs the components within the window based on the component’s preferred sizes.
		frame.pack();
		
		//Set resize false => user cannot resize the window
		frame.setResizable(false);
		
		//Set the window at the center of the screen
		frame.setLocationRelativeTo(null);
		
		//When user click to "X" close the application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set the window visible
		frame.setVisible(true);
	}
	
	public synchronized void Start()
	{
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void Stop()
	{
		try 
		{
			thread.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.Start();
	}
	
	public void Tick()
	{

	}
	
	public void Render()
	{
		//Sequence of buffers into screen to optimize the rendering 
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics graph = image.getGraphics();
		
		//Base layer
		graph.setColor(Color.BLACK);
		graph.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		graph.dispose();
		graph = bs.getDrawGraphics();
		graph.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
	}
	
	/*
	 * Game Loop
	 */
	public void run() 
	{
		long lastTime = System.nanoTime();
		
		//Set to be 60 frames
		double amountOfTicks = 60.0;
		
		//1s in nanoSeconds divided per ticks
		//Used to calculate 60ticks per second = FPS
		double ns = 1000000000 / amountOfTicks;
		
		//To calculate the interval
		double delta = 0;
		
		int frames = 0;
		
		double timer = System.currentTimeMillis();
		
		while(isRunning)
		{
			//System.out.println("Running");
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if (delta >= 1)
			{
				Tick();
				Render();
				
				frames++;
				delta--;
			}
			
			//Prove that is 60 frames per second
			if(System.currentTimeMillis() - timer >= 1000)
			{
				System.out.println("FPS : " + frames);
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}
		
		Stop();
	}

}

