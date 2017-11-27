public class SImystery extends SIinvader {

	public SImystery()
	{
		super();
		super.setAlive1(getImage("SImystery.gif"));
		super.setAlive2(getImage("SImystery.gif"));
		int rand = (int) (System.currentTimeMillis() % 4);
		if(rand == 0)
			setPointValue(50);
		else if(rand == 1)
			setPointValue(100);
		else if(rand == 2)
			setPointValue(150);
		else if(rand == 3)
			setPointValue(300);
	}
    @Override
    public void move(int distance, Direction d)
    {
    	//overriding so that it can go off the edge of the screen before disappearing
    }
}
