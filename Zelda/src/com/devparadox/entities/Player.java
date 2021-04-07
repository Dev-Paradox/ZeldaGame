package com.devparadox.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.devparadox.main.Game;

public class Player extends Entity
{
	//Movement
	public boolean up;
	public boolean down;
	public boolean right;
	public boolean left;
	
	//Speed
	public double speed = 1;
	
	//Player's frames
	private int frames = 0;
	private int maxFrames = 12;
	private int index = 0;
	private int maxIndex = 2;
	private boolean moved;
	
	//Sprite
	private BufferedImage[] downPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	private BufferedImage lastPlayerMove;
	
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) 
	{
		super(x, y, width, height, sprite);
		
		downPlayer = new BufferedImage[3];
		for(int i=0; i<3; i++)
		{
			downPlayer[i] = Game.spritesheet.GetSprite(32 + (i*16), 0, 16, 16);
		}		
		upPlayer = new BufferedImage[3];
		for(int i=0; i<2; i++)
		{
			upPlayer[i] = Game.spritesheet.GetSprite(80 + (i*16), 0, 16, 16);
		}
		upPlayer[2] = Game.spritesheet.GetSprite(80, 0, 16, 16);
		
		rightPlayer = new BufferedImage[3];
		for(int i=0; i<3; i++)
		{
			rightPlayer[i] = Game.spritesheet.GetSprite(112 + (i*16), 0, 16, 16);
		}	
		leftPlayer = new BufferedImage[3];
		for(int i=0; i<3; i++)
		{
			leftPlayer[i] = Game.spritesheet.GetSprite((i*16), 16, 16, 16);
		}
		
		lastPlayerMove = downPlayer[0];
		
	}
	
	/*
	 * Tick the actions
	 */
	public void Tick()
	{
		moved = false;
		
		if(up)
		{
			moved = true;
			y -= speed;
		}
		else if(down)
		{
			moved = true;
			y += speed;
		}
		
		if(right)
		{
			moved = true;
			x += speed;
		}
		else if(left)
		{
			moved = true;
			x -= speed;
		}
		
		if(moved)
		{
			frames++;
			
			if(frames == maxFrames)
			{
				frames = 0;
				index++;
				if(index > maxIndex)
				{
					index = 0;
				}
			}
		}
	}
	
	public void Render(Graphics g)
	{
		if(up)
		{
			g.drawImage(upPlayer[index], this.GetX(), this.GetY(), null);
			lastPlayerMove = upPlayer[index];
		}
		else if(down)
		{
			g.drawImage(downPlayer[index], this.GetX(), this.GetY(), null);
			lastPlayerMove = downPlayer[index];
		}
		
		if(right)
		{
			g.drawImage(rightPlayer[index], this.GetX(), this.GetY(), null);
			lastPlayerMove = rightPlayer[index];
		}
		else if(left)
		{
			g.drawImage(leftPlayer[index], this.GetX(), this.GetY(), null);
			lastPlayerMove = leftPlayer[index];
		}
		else
		{
			g.drawImage(lastPlayerMove, this.GetX(), this.GetY(), null);
		}
	}

}
