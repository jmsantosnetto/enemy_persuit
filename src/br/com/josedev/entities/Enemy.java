package br.com.josedev.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import br.com.josedev.main.Game;
import br.com.josedev.world.*;

public class Enemy extends Entity {
	private double speed = 1;
	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 1;
	private BufferedImage[] sprites;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[2];
		
		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(112+16, 16, 16, 16);
		
	}
	
	public void tick() {
		if(!isColiddingWithPlayer()) {
			if(Game.rand.nextInt(100) < 50) {
				if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
						&& !isColidding((int)(x+speed), this.getY())) {
					x+=speed;
				} else if ((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
						&& !isColidding((int)(x-speed), this.getY())) {
					x-=speed;
				} 
				
				if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
						&& !isColidding(this.getX(), (int)(y+speed))) {
					y+=speed;
				} else if ((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
						&& !isColidding(this.getX(), (int)(y-speed))) {
					y-=speed;
				}
			}
		} else {
			// Is Colliding
			if(Game.rand.nextInt(100) < 10) {
				Player.life--;
				System.out.println("Vida do player: " + Player.life);
				
				if(Player.life == 0) {
					// Game Over
					System.exit(1);
				}
			}
			
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y,  null);
	}
	
	public boolean isColidding(int xnext, int ynext) {
		Rectangle currentEnemy = new Rectangle(xnext, ynext, World.TILE_SIZE, World.TILE_SIZE);
		
		for(int i = 0; i< Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			
			Rectangle targetEmeny = new Rectangle(e.getX(), e.getY(), World.TILE_SIZE, World.TILE_SIZE);
			
			if(currentEnemy.intersects(targetEmeny)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle currentEnemy = new Rectangle(this.getX(), this.getY(), 16,16);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16,16);
		
		return currentEnemy.intersects(player);
	}
	

}
