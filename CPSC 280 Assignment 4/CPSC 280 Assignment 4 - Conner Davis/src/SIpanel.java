import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SIpanel extends JPanel {
    
    private boolean isPaused;
    private int score;
    private Timer timer;
	
    
    //if destroyed, wait until next pace cycle before deleting
	public SIpanel()
	{
		
	}

	public void dialogPause() {
	    // TODO Auto-generated method stub
	}

	public void dialogUnpause() {
		if(!isPaused)
		{
		    unpause();
		}
	}

	public void startNewGame() {
		// TODO Auto-generated method stub
		
	}

	public void pause() {
		// TODO Auto-generated method stub
		
	}

	public void unpause() {
	    
	}
}
