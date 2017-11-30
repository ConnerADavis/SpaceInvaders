import java.applet.AudioClip;
import java.util.Random;

public class SImystery extends SIinvader {

    private AudioClip sound;
	public SImystery()
	{
		super();
		super.setAlive1(getImage("SImystery.gif"));
		super.setAlive2(getImage("SImystery.gif"));
		Random r = new Random();
		int rand = r.nextInt(4);
		if(rand == 0)
			super.setPointValue(50);
		else if(rand == 1)
			super.setPointValue(100);
		else if(rand == 2)
			super.setPointValue(150);
		else if(rand == 3)
			super.setPointValue(300);
		super.setWidth(36);
		super.setHeight(18);
		
		sound = getSound("SImystery.wav");
	}
	
	@Override
    public void move(int distance, Direction d)
    {
        switch(d)
        {
            case UP    : super.setY(super.getY() - distance); break;
            case DOWN  : super.setY(super.getY() + distance); break;
            case LEFT  : super.setX(super.getX() - distance); break;
            case RIGHT : super.setX(super.getX() + distance); break;
        }
    }
	
	public void playSound()
	{
	    sound.play();
	}
}
