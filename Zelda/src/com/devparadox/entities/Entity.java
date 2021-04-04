package com.devparadox.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity 
{
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public Entity(double x, double y, int width, int height, BufferedImage sprite)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}

	//Render sprite
	public void Render(Graphics g)
	{
		g.drawImage(sprite, this.GetX(), this.GetY(), null);
	}
	
	//Entity action
	public void Tick()
	{
		
	}
	
	//Getters and Setters
	public int GetX() {
		return (int)x;
	}

	public void SetX(int x) {
		this.x = x;
	}

	public int GetY() {
		return (int)y;
	}

	public void SetY(int y) {
		this.y = y;
	}

	public int GetWidth() {
		return width;
	}

	public void SetWidth(int width) {
		this.width = width;
	}

	public int GetHeight() {
		return height;
	}

	public void SetHeight(int height) {
		this.height = height;
	}
}
