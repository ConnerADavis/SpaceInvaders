import java.applet.AudioClip;
import java.awt.Image;

public abstract class SIship extends SIthing {
    
    private boolean isHit;
    private Image hitImage;
    
    public SIship()
    {
        isHit = false;
    }
    
    public boolean isHitBy(SImissile missile)
    {
        int mx = missile.getX();
        int my = missile.getY();
        if (super.getX() <= mx + missile.getWidth() && super.getX() + super.getWidth() >= mx)
        {
            if(super.getY() <= my + missile.getHeight() && super.getY() + super.getHeight() >= my)
            {
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
        
        //I don't understand why, but this doesn't work on linux.
        //It crashes on the second destroyed ship.
        //Works fine on windows though, and hopefully macOS as well
        getSound("SIshipHit.wav").play();
        
    }

    public boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
}
