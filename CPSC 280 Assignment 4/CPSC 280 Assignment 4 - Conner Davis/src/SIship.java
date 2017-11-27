import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.net.URL;

public abstract class SIship extends SIthing {
    
    private boolean isHit;
    private AudioClip hitSound = getSound("SIshipHit.wav");
    private Image hitImage;
    
    public boolean isHitBy(SImissle missle)
    {
        int mx = missle.getX();
        int my = missle.getY();
        if (super.getX() <= mx + missle.getWidth() && super.getX() + super.getWidth() >= mx)
        {
            if(super.getY() <= my + missle.getHeight() && super.getY() + super.getHeight() >= my)
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
    }
    
    private AudioClip getSound(String fileName) {
        URL url = getClass().getResource(fileName);
        return Applet.newAudioClip(url);
    }

    public boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
}
