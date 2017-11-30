import java.applet.AudioClip;
import java.awt.Image;

public abstract class SIship extends SIthing {
    
    private boolean isHit;
    private Image hitImage;
    private AudioClip hitSound;
    
    public SIship()
    {
        isHit = false;
        hitSound = getSound("pop.wav");
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
        
        
        hitSound.play();
        //System.out.print("Whatever");
        
    }

    public boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
}
