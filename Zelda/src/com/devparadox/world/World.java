package com.devparadox.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.devparadox.entities.Enemy;
import com.devparadox.entities.Entity;
import com.devparadox.entities.LifePack;
import com.devparadox.entities.Potion;
import com.devparadox.main.Game;

public class World 
{
	private Tile[] tiles;
	
	public static int WIDTH;
	public static int HEIGHT;
	
	public World(String path)
	{
		try 
		{
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			
			//Create the tiles array with the map size (in pixels)
			tiles = new Tile[map.getWidth() * map.getHeight()];
			
			//Create the pixels array with the map size (in pixels)
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			
			//Fill the pixels which contains the map
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			//Scan each map pixel, detect the color of it and create the respective entity
			for(int xx=0; xx < map.getWidth(); xx++)
			{
				for(int yy = 0; yy < map.getHeight(); yy++)
				{
					int actualPixel  = pixels[xx + (yy * map.getWidth())];
					
					//Floor (Black)
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, FloorTile.TILE_FLOOR);
					
					//Check the pixel color
					if(actualPixel == 0xFF000000)
					{
						//Floor (Black)
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, FloorTile.TILE_FLOOR);
					}
					else if(actualPixel == 0xFFF7F8FF)
					{
						//Wall (White)
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, WallTile.TILE_WALL);
					}
					else if(actualPixel == 0xFF0000FF)
					{
						//Player (Blue)
						Game.player.SetX(xx * 16);
						Game.player.SetY(yy * 16);
 					}
					else if(actualPixel == 0xFFF70000)
					{
						//Enemy (Red)
						Game.entities.add(new Enemy(xx*16,yy*16,16,16,Entity.ENEMY_EN));
					}
					else if(actualPixel == 0xFFFFFF00)
					{
						//Potion (Yellow)
						Game.entities.add(new Potion(xx*16,yy*16,16,16,Entity.POTION_EN));
					}
					else if(actualPixel == 0xFF00FF00)
					{
						//Lifepack (Green)
						Game.entities.add(new LifePack(xx*16,yy*16,16,16,Entity.LIFEPACK_EN));
					}
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void Render(Graphics g)
	{
		//Update the tiles
		//Tiles -> all the static components which belongs to the environment (world)
		for(int xx=0; xx < WIDTH; xx++)
		{
			for(int yy=0; yy < HEIGHT; yy++)
			{
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.Render(g);
			}	
		}
	}
}
