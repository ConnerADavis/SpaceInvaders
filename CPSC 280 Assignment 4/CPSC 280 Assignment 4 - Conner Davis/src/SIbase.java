import java.awt.Graphics2D;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;


public class SIbase extends SIship {
    
    AudioClip shoot;
    Image alive;
    
    public SIbase()
    {
        shoot = getSound("SIbaseShoot.wav");
        setIsHit(false);
        alive = getImage("SIbase.gif");
        setHitImage(getImage("SIbaseBlast.gif"));
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
    
    private AudioClip getSound(String fileName) {
        URL url = getClass().getResource(fileName);
        return Applet.newAudioClip(url);
    }
    
    private Image getImage(String fileName)
    {
        URL url = getClass().getResource(fileName);
        ImageIcon icon = new ImageIcon(url);
        return icon.getImage();
    }
}
