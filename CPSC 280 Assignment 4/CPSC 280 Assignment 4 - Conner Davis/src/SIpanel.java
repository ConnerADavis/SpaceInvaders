import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SIpanel extends JPanel {
    
    private boolean isPaused;
    private int score;
    private Timer timer;
	private SIinvader[][] invaders;
	private SIbase base;
	private int timerThingy;
	private ArrayList<SImissile> playerMissiles;
	private ArrayList<SIthing> toDelete;
    
    //if destroyed, wait until next pace cycle before deleting
	public SIpanel()
	{
		playerMissiles = new ArrayList<>();
		toDelete = new ArrayList<>();
	    setBackground(Color.BLACK);
	    setFocusable(true);
	    base = new SIbase();
	    base.setX(250 - (base.getWidth()/2));
	    //I was originally adding the height instead of subtracting it
	    //it was rendering off-screen
	    //I couldn't figure out the problem with my rendering code
	    base.setY(395 - base.getHeight());
	    invaders = new SIinvader[10][5];
	    
	    int y = 24;
	    for(int i = 0; i < 5; i++)
	    {
	        int x = 100;
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
	    
	    
	    addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_RIGHT : base.move(5,SIthing.Direction.RIGHT); break;
				case KeyEvent.VK_LEFT  : base.move(5,SIthing.Direction.LEFT);  break;
				case KeyEvent.VK_SPACE : playerMissiles.add(base.shoot());     break;
				}
			}
			
			/*@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_UP : base.move(false, SIthing.Direction.UP); break;
				case KeyEvent.VK_DOWN : base.move(false, SIthing.Direction.DOWN); break;
				case KeyEvent.VK_RIGHT : base.move(false, SIthing.Direction.RIGHT); break;
				case KeyEvent.VK_LEFT : base.move(false, SIthing.Direction.LEFT); break;
				}
			}*/
		});
	    timerThingy = 0;
	    timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
				for(SIthing t : toDelete)
				{
					playerMissiles.removeAll(toDelete);
					for(int i = 0; i < invaders.length; i++)
					{
						for(int j = 0; j < invaders[i].length; j++)
						{
							if(invaders[i][j] != null)
							{
								if(invaders[i][j] == t)
								{
									invaders[i][j] = null;
								}
							}
						}
					}
					t = null;
				}
				toDelete = new ArrayList<>();
				timerThingy++;
				
				for(int i = 0; i < invaders.length; i++)
				{
					for(int j = 0; j < invaders[i].length; j++)
					{
						if(invaders[i][j] != null)
						{
							for(SImissile m : playerMissiles)
							{
								if(invaders[i][j].isHitBy(m))
								{
									invaders[i][j].hit();
									toDelete.add(invaders[i][j]);
									toDelete.add(m);
								}
							}
						}
					}
				}
				
				if(timerThingy%100==0)
				{ 
					swapInvaders();
				}
				if(timerThingy == 3001)
				{
					timerThingy = 0;
				}
				
				for(SImissile m : playerMissiles)
				{
					m.move(5, m.getDirection());
					if(!m.canMove(5, m.getDirection()))
					{
						toDelete.add(m);
					}
				}
				
			}
	    	
	    });
	    
	    //REMOVE THIS BEFORE SUBMITTING FINAL
	    //SERIOUSLY THIS IS ONLY FOR BUGTESTING
	    //DON'T LEAVE THIS HERE
	    timer.start();
	}

	public void dialogPause() {
		timer.stop();
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
		timer.stop();
		isPaused = true;
	}

	public void unpause() {
	    timer.start();
	}
	
	private void swapInvaders()
	{
		for(int i = 0; i < invaders.length; i++)
		{
			for(int j = 0; j < invaders[i].length; j++)
			{
				if(invaders[i][j] != null)
				{
					invaders[i][j].setSwap(!invaders[i][j].isSwap());
				}
			}
		}
	}
	
	protected void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	    

	    base.paint(g2);
	    
	    for(int i = 0; i < invaders.length; i++)
	    {
	        for(int j = 0; j < invaders[i].length; j++)
	        {
	        	if(invaders[i][j] != null)
	        	{
	        		invaders[i][j].paint(g2);
	        	}
	        }
	    }
	    
	    for(SImissile m : playerMissiles)
	    {
	    	m.paint(g2);
	    }
	    
	}
}
