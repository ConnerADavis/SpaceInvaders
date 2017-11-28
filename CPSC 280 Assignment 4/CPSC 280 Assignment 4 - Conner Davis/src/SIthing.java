import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public abstract class SIthing {
    
	private int x;
	private int y;
	private int width;
	private int height;
	

    public abstract void paint(Graphics2D g2);
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void move(int distance, Direction d)
	{
		if(canMove(distance, d))
		{
		    switch(d)
		    {
		        case UP    : y -= distance; break;
		        case DOWN  : y += distance; break;
		        case LEFT  : x -= distance; break;
		        case RIGHT : x += distance; break;
		    }
		}
	    //I figured this would be sufficient given nothing travels in multiple directions at once
	}
	
	public boolean canMove(int distance, Direction d)
	{
		if(d == Direction.UP)
		{
			if(y - distance > 0)
			{
				return true;
			}
			return false;
		}
		if(d == Direction.DOWN)
		{
			if(y + height + distance  < 450)
			{
				return true;
			}
			return false;
		}
		if(d == Direction.LEFT)
		{
			if(x - distance > 0)
			{
				return true;
			}
			return false;
		}
		if(d == Direction.RIGHT)
		{
			if(x + width + distance < 500)
			{
				return true;
			}
			return false;
		}
		return false;
	}
	
	public Image getImage(String fileName)
    {
        URL url = getClass().getResource(fileName);
        ImageIcon icon = new ImageIcon(url);
        return icon.getImage();
    }
	
	public AudioClip getSound(String fileName) {
        URL url = getClass().getResource(fileName);
        return Applet.newAudioClip(url);
    }
	
}
