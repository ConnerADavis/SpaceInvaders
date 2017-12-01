import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;


/*
 * TODO
 * --Create invader missile firing system
 * --Change invader pace on wall hit
 * --Add change of furthestLeft and furthestRight
 * --Add mystery ship
 * --Create point system.
 * --Limit frequency of missiles fired
 * --Add check for base destroyed
 * --Create lose condition
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
	private boolean ongoingGame;
	private Font scoreFont;
	private boolean gameLost;
	private boolean won;
	private int fireDelay;
	private SImystery mystery;
    private Random rand;
	
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
	    ongoingGame = false;
	    gameLost = false;
	    scoreFont = new Font("Arial", Font.PLAIN, 20);
	    won = false;
        fireDelay = 50;
        rand = new Random();
	    
	    instantiateInvaders();
	    
	    
	    addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_RIGHT : base.move(5, Direction.RIGHT); break;
				case KeyEvent.VK_LEFT  : base.move(5, Direction.LEFT);  break;
				}
				
				if(e.getKeyCode() == KeyEvent.VK_SPACE && ongoingGame)
				{
				    //Check added for game balance reasons
				    if(playerMissiles.size() < 3 && fireDelay == 50)
				    {
				        playerMissiles.add(base.shoot());
				        fireDelay = 0;
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
				if(increment == 200000)
				{
					increment = 1;
				}
				
				if(invaderMissiles.size() < 3)
				{
	                attemptToFire();
				}
				
				for(SImissile m : playerMissiles)
				{
					m.move(5, m.getDirection());
					if(!m.canMove(5, m.getDirection()))
					{
						toDelete.add(m);
						
					}
				}
				
				for(SImissile m : invaderMissiles)
				{
					m.move(2, m.getDirection());
					if(!m.canMove(2, m.getDirection()))
					{
						toDelete.add(m);
					}
				}
				
				if(furthestLeft <= furthestRight && isColumnEmpty(furthestLeft) && isColumnEmpty(furthestRight))
	            {
				    win();
	            }
				
				if(fireDelay < 50)
				{
				    fireDelay++;
				}
				
				if(mystery == null)
				{
				    if(increment % 100 == 0)
				    {
				        if(rand.nextInt(100) > 95)
				        {
				            mystery = new SImystery();
				            mystery.setX(500);
				            mystery.setY(0);
				            mystery.playSound();
				        }
				    }
				}
				if(mystery != null)
		        {
		            mystery.move(1, Direction.LEFT);
		            for(SImissile m : playerMissiles)
		            {
		                if(mystery.isHitBy(m))
		                {
		                    mystery.hit();
		                    score += mystery.getPointValue();
		                    toDelete.add(mystery);
		                }
		            }
		            if(mystery.getX() + mystery.getWidth() < 0)
		            {
		                mystery = null;
		            }
		        }
                increment++;
                
                checkForLoss();
			}
	    });
	    
	    //REMOVE THIS BEFORE SUBMITTING FINAL
	    //SERIOUSLY THIS IS ONLY FOR BUGTESTING
	    //DON'T LEAVE THIS HERE
	    //timer.start();
	}

	private void attemptToFire() {
		for(int i = 0; i < 10; i++)
		{
			attemptToFire(i);
		}
		
	}
	
	private void attemptToFire(int i)
	{
	    if(invaderMissiles.size() < 3)
	    {
	        for(int j = 4; j >= 0; j--)
	        {
	            if(invaders[i][j] != null)
	            {
	                //I changed it from an 80% chance to 50% so the missiles wouldn't all be clustered on the left
	                if(rand.nextInt(100)  < 50)
	                {
	                    invaderMissiles.add(invaders[i][j].shoot());
	                }
	                return;
	            }
	        }
	    }
	}
	
	private void checkForLoss()
	{
	    for(int i = 4; i > 0; i--)
	    {
	        if(!isRowEmpty(i))
	        {
	            for(int j = 0; j < 10; j++)
	            {
	                if(invaders[j][i] != null)
	                {
	                    if(invaders[j][i].getY() >= 350)
	                    {
	                        gameOver();
	                    }
	                }
	            }
	        }
	    }
	}

	private void removeGarbage() {
	    for(SIthing t : toDelete)
        {
            playerMissiles.removeAll(toDelete);
            invaderMissiles.removeAll(toDelete);
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
		if(!isPaused && ongoingGame)
		{
		    unpause();
		}
	}

	public void startNewGame() {
	    timer.stop();
	    
	    
	    playerMissiles  = new ArrayList<>();
        toDelete        = new ArrayList<>();
        invaderMissiles = new ArrayList<>();
        invaderUpdate = 40;
	    isPaused = false;
	    score = 0;
	    instantiateInvaders();
	    invaderMovement = Direction.RIGHT;
	    base = new SIbase();
	    base.setX(250 - (base.getWidth()/2));
        base.setY(395 - base.getHeight());
	    increment = 0;
	    furthestLeft = 0;
        furthestRight = 9;
        ongoingGame = true;
        gameLost = false;
        won = false;
        mystery = null;
	    
	    timer.restart();
	}

	public void pause() {
		timer.stop();
		isPaused = true;
	}

	public void unpause() {
		if(ongoingGame)
		{
			timer.start();
		}
	}
	
	public void win()
	{
	    ongoingGame = false;
		timer.stop();
		won = true;
		repaint();
	}
	
	private void updateInvaders()
	{
		if(isColumnEmpty(furthestLeft))
		{
			furthestLeft++;
		}
		if(isColumnEmpty(furthestRight))
		{
			furthestRight--;
		}
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
			for(int l = 0; l < 5; l++)
			{
				if(invaders[furthestRight][l] != null)
				{
				    if(invaders[furthestRight][l].canMove(5, Direction.RIGHT))
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
				    break;
				}
			}
		}
		else if(invaderMovement == Direction.LEFT)
        {
			for(int l = 0; l < 5; l++)
			{
				if(invaders[furthestLeft][l] != null)
				{
		            if(invaders[furthestLeft][l].canMove(5, Direction.LEFT))
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
		            break;
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
                        	score += invaders[i][j].getPointValue();
                            invaders[i][j].hit();
                            toDelete.add(invaders[i][j]);
                            toDelete.add(m);
                        }
                    }
                }
            }
        }
	    for(SImissile m : invaderMissiles)
	    {
	        if(base.isHitBy(m))
	        {
	            base.hit();
	            gameOver();
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
	
	private void gameOver()
	{
		timer.stop();
		ongoingGame = false;
		gameLost = true;
	}
	
	private boolean isColumnEmpty(int colNum)
	{
		for(int i = 0; i < 5; i++) 
		{
			if(invaders[colNum][i] != null)
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean isRowEmpty(int row)
	{
	    for(int i = 0; i < 10; i++)
	    {
	        if(invaders[i][row] != null)
	        {
	            return false;
	        }
	    }
	    return true;
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
	    
	    if(mystery != null)
	    {
	        mystery.paint(g2);
	    }
	    
	    for(SImissile m : playerMissiles)
	    {
	    	m.paint(g2);
	    }
	    
	    for(SImissile m : invaderMissiles)
	    {
	        m.paint(g2);
	    }
	    g2.setColor(Color.CYAN);
	    g2.setFont(scoreFont);
	    String scoreDisplay = "Score:" + score;
	    int x = getWidth() - g2.getFontMetrics(scoreFont).stringWidth(scoreDisplay) - 2;
	    
	    g2.drawString(scoreDisplay, x, 20);
	    
	    if(gameLost)
	    {
	    	String gameOver = "Game Over";
	    	int x2 = (getWidth() / 2) - (g2.getFontMetrics(scoreFont).stringWidth(gameOver)/2);
	    	int y2 = (getHeight() / 2) - (g2.getFontMetrics(scoreFont).getHeight()/2);
	    	
	    	g2.drawString(gameOver, x2, y2);
	    }
	    
	    if(won)
	    {
	        String youWin = "YOU WIN!";
	        int x2 = (getWidth() / 2) - (g2.getFontMetrics(scoreFont).stringWidth(youWin)/2);
            int y2 = (getHeight() / 2) - (g2.getFontMetrics(scoreFont).getHeight()/2);
            
            g2.drawString(youWin, x2, y2);
	    }
	    
	}
}
