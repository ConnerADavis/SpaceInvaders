import java.awt.Graphics2D;

public abstract class SIthing {
	
	enum Direction{UP, DOWN, LEFT, RIGHT}
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
	    switch(d)
	    {
	        case UP    : y -= distance;
	        case DOWN  : y += distance;
	        case LEFT  : x -= distance;
	        case RIGHT : x += distance;
	    }
	    //I figured this would be sufficient given nothing travels in multiple directions at once
	}
	
}
