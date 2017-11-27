public class SImystery extends SIinvader {

	public SImystery()
	{
		super();
		super.setAlive1(getImage("SImystery.gif"));
		super.setAlive2(getImage("SImystery.gif"));
		int rand = (int) (System.currentTimeMillis() % 4);
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
	}
    @Override
    public void move(int distance, Direction d)
    {
    	//overriding so that it can go off the edge of the screen before disappearing
    }
}
