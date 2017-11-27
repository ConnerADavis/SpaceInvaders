import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SIpanel extends JPanel {
    
    private boolean isPaused;
    private int score;
    private Timer timer;
	private SIinvader[][] invaders;
    
    //if destroyed, wait until next pace cycle before deleting
	public SIpanel()
	{
	    setBackground(Color.BLACK);
	    setFocusable(true);
	    SIbase base = new SIbase();
	    base.setX(250 - (base.getWidth()/2));
	    invaders = new SIinvader[10][5];
	    
	    int y = 24;
	    for(int i = 0; i < 5; i++)
	    {
	        int x = 30;
	        for(int j = 0; j < 10; j++)
	        {
	            if(i < 1)
	            {
	                invaders[j][i] = new SItop();
	                invaders[j][i].setX(x);
	                invaders[j][i].setY(y);
	                
	            }
	            else if(i < 3)
	            {
	                invaders[j][i] = new SImiddle();
	                invaders[j][i].setX(x);
	                invaders[j][i].setY(y);
	            }
	            else
	            {
	                invaders[j][i] = new SIbottom();
	                invaders[j][i].setX(x);
	                invaders[j][i].setY(y);
	            }
	            x += 30;
	        }
	        y += 24;
	    }
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
	
	protected void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	    
	    for(int i = 0; i < invaders.length; i++)
	    {
	        for(int j = 0; j < invaders[i].length; j++)
	        {
	            invaders[i][j].paint(g2);
	        }
	    }
	}
}
