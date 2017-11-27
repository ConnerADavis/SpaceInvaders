import java.awt.Graphics2D;
import java.awt.Image;


public abstract class SIinvader extends SIship {
    
	private Image alive1;
	private Image alive2;
	private int pointValue;
	private boolean swap;
	
	public SIinvader()
	{
		swap = false;
		setHitImage(getImage("SIinvaderBlast.gif"));
	}
	
	public Image getAlive1() {
		return alive1;
	}
	
	public void setAlive1(Image alive1) {
		this.alive1 = alive1;
	}
	
	public Image getAlive2() {
		return alive2;
	}
	
	public void setAlive2(Image alive2) {
		this.alive2 = alive2;
	}
	
	public int getPointValue() {
		return pointValue;
	}
	
	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}
	
	public boolean isSwap() {
		return swap;
	}
	
	public void setSwap(boolean swap) {
		this.swap = swap;
	}
	
	//This uses methods instead of references to the variables because
	//I originally wrote this for one of the subclasses
	@Override
    public void paint(Graphics2D g2) {
        if(!getIsHit())
        {
        	if(isSwap())
        	{
        		setSwap(false);
        		g2.drawImage(getAlive2(), getX(), getY(), null);
        	}
        	else
        	{
        		setSwap(true);
        		g2.drawImage(getAlive1(), getX(), getY(), null);
        	}
        }
        else
        {
        	g2.drawImage(getHitImage(), getX(), getY(), null);
        }
    }
}
