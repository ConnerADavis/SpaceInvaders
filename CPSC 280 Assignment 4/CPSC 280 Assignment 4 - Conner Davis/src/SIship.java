import java.applet.AudioClip;
import java.awt.Image;

public abstract class SIship extends SIthing {
    
    private boolean isHit;
    private AudioClip hitSound;
    private Image hitImage;
    
    public SIship()
    {
        isHit = false;
        hitSound = getSound("SIshipHit.wav");
    }
    
    public boolean isHitBy(SImissile missile)
    {
        int mx = missile.getX();
        int my = missile.getY();
        if (super.getX() <= mx + missile.getWidth() && super.getX() + super.getWidth() >= mx)
        {
            if(super.getY() <= my + missile.getHeight() && super.getY() + super.getHeight() >= my)
            {
            	missile = null;
                return true;
            }
        }
        return false;
    }
    
    public void setHitImage(Image i)
    {
        hitImage = i;
    }
    
    public Image getHitImage()
    {
        return hitImage;
    }
    
    public void hit()
    {
        setIsHit(true);
        hitSound.play();
    }

    public boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
}
