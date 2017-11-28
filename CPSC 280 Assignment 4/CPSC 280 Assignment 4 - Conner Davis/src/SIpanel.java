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


/*
 * TO DO
 * Create  missile firing system
 * Change invader pace on wall hit
 * Add change of furthestLeft and furthestRight
 * Add mystery ship
 * Create point system.
 * Limit frequency of missiles fired
 * Add check for base destroyed
 */
@SuppressWarnings("serial")
public class SIpanel extends JPanel {
    
    private boolean isPaused;
    private int score;
    private Timer timer;
	private SIinvader[][] invaders;
	private SIbase base;
	private int increment;
	private int invaderUpdate;
	private int furthestLeft;
	private int furthestRight;
	private Direction invaderMovement;
	private ArrayList<SImissile> playerMissiles;
	private ArrayList<SIthing> toDelete;
	private ArrayList<SImissile> invaderMissiles;
    
    //if destroyed, wait until next pace cycle before deleting
	public SIpanel()
	{
		playerMissiles  = new ArrayList<>();
		toDelete        = new ArrayList<>();
		invaderMissiles = new ArrayList<>();
		invaderUpdate = 40;
	    setBackground(Color.BLACK);
	    setFocusable(true);
	    base = new SIbase();
	    base.setX(250 - (base.getWidth()/2));
	    //I was originally adding the height instead of subtracting it
	    //it was rendering off-screen
	    //I couldn't figure out the problem with my rendering code
	    base.setY(395 - base.getHeight());
	    invaders = new SIinvader[10][5];
	    furthestLeft = 0;
	    furthestRight = 9;
	    
	    instantiateInvaders();
	    
	    
	    addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_RIGHT : base.move(5, Direction.RIGHT); break;
				case KeyEvent.VK_LEFT  : base.move(5, Direction.LEFT);  break;
				}
				
				if(e.getKeyCode() == KeyEvent.VK_SPACE)
				{
				    //Check added for game balance reasons
				    if(playerMissiles.size() < 3)
				    {
				        playerMissiles.add(base.shoot());
				    }
				}
			}
		});
	    
	    
	    increment = 0;
	    timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
				removeGarbage();
				
				checkForHits();
				
				if(increment % invaderUpdate == 0)
				{ 
					updateInvaders();
				}
				//I chose 3733 because it's a prime number so it won't interfere with any other operations
				if(increment == 3733)
				{
					increment = 0;
				}
				
				for(SImissile m : playerMissiles)
				{
					m.move(5, m.getDirection());
					if(!m.canMove(5, m.getDirection()))
					{
						toDelete.add(m);
						
					}
				}

				
				
                increment++;
			}
	    });
	    
	    //REMOVE THIS BEFORE SUBMITTING FINAL
	    //SERIOUSLY THIS IS ONLY FOR BUGTESTING
	    //DON'T LEAVE THIS HERE
	    timer.start();
	}

	protected void removeGarbage() {
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
	    timer.start();
	}

	public void pause() {
		timer.stop();
		isPaused = true;
	}

	public void unpause() {
	    timer.start();
	}
	
	private void updateInvaders()
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
		if(invaderMovement == Direction.RIGHT)
		{
		    if(invaders[furthestRight][0].canMove(5, Direction.RIGHT))
		    {
		        for(int i = 0; i < invaders.length; i++)
		        {
		            for(int j = 0; j < invaders[i].length; j++)
		            {
		                if(invaders[i][j] != null)
		                {
		                    invaders[i][j].move(5, Direction.RIGHT);
		                }
		            }
		        }
		    }
		    else
		    {
		        for(int i = 0; i < invaders.length; i++)
                {
                    for(int j = 0; j < invaders[i].length; j++)
                    {
                        if(invaders[i][j] != null)
                        {
                            invaders[i][j].move(12, Direction.DOWN);
                        }
                    }
                }
		        invaderMovement = Direction.LEFT;
		        if(invaderUpdate > 1)
		        {
		            invaderUpdate = (invaderUpdate * 4)/ 5;
		        }
		    }
		}
		else if(invaderMovement == Direction.LEFT)
        {
            if(invaders[furthestLeft][0].canMove(5, Direction.LEFT))
            {
                for(int i = 0; i < invaders.length; i++)
                {
                    for(int j = 0; j < invaders[i].length; j++)
                    {
                        if(invaders[i][j] != null)
                        {
                            invaders[i][j].move(5, Direction.LEFT);
                        }
                    }
                }
            }
            else
            {
                for(int i = 0; i < invaders.length; i++)
                {
                    for(int j = 0; j < invaders[i].length; j++)
                    {
                        if(invaders[i][j] != null)
                        {
                            invaders[i][j].move(12, Direction.DOWN);
                        }
                    }
                }
                invaderMovement = Direction.RIGHT;
                if(invaderUpdate > 1)
                {
                    invaderUpdate = (invaderUpdate * 4)/ 5;
                }
            }
        }
	}
	
	private void checkForHits()
	{
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
	    
	    
	}
	
	private void instantiateInvaders()
	{
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
        invaderMovement = Direction.RIGHT;
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
