import java.awt.Graphics2D;
import java.applet.AudioClip;
import java.awt.Image;


public class SIbase extends SIship {
    
    AudioClip shoot;
    Image alive;
    
    public SIbase()
    {
    	super();
    	super.setWidth(26);
    	super.setHeight(20);
        shoot = getSound("SIbaseShoot.wav");
        setIsHit(false);
        alive = getImage("SIbase.gif");
        setHitImage(getImage("SIbaseBlast.gif"));
    }
    
    public int getMiddle()
    {
    	return super.getX() + (super.getWidth()/2);
    }

    @Override
    public void paint(Graphics2D g2) {
        if(getIsHit())
        {
            g2.drawImage(getHitImage(), getX(), getY(), null);
        }
        else
        {
            g2.drawImage(alive, getX(), getY(), null);
        }
    }

	public SImissile shoot() {
		SImissile missile = new SImissile(super.getX() + (super.getWidth()/2), super.getY() - 10);
		missile.setDirection(Direction.UP);
		shoot.play();
		return missile;
	}
    
}
