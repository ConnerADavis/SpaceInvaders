import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class SI extends JFrame {
	
	SIpanel panel = new SIpanel();
	
	public SI()
	{
		
		JMenuBar menubar = new JMenuBar();
		JMenu game = new JMenu("Game");
		
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem pause = new JMenuItem("Pause");
		JMenuItem resume = new JMenuItem("Resume");
		JMenuItem quit = new JMenuItem("Quit");
		
		newGame.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.startNewGame();
			}
			
		});
		pause.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.pause();
			}
			
		});
		resume.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.unpause();
				
			}
			
		});
		quit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayExitDialog();
			}
		});
		
		game.add(newGame);
		game.add(pause);
		game.add(resume);
		game.add(quit);
		
		JMenu help = new JMenu("Help");
		
		JMenuItem about = new JMenuItem("About");
		
		about.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAboutDialog();
			}
		});
		
		help.add(about);
		
		menubar.add(game);
		menubar.add(help);
		add(panel);
		
		setJMenuBar(menubar);
		
		setSize(500, 450);
		setTitle("Space Invaders");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() 
        {
			// TODO Auto-generated method stub
        	@Override
        	public void windowClosing(WindowEvent arg0)
        	{
        		displayExitDialog();
        	}
        });
	}
	
	private void displayAboutDialog()
    {
	    panel.dialogPause();
        JOptionPane.showMessageDialog(this, new JLabel("<html><hr><b>Space Invaders</b><br>by Conner A. Davis</hr></html>"), 
                "Message",JOptionPane.INFORMATION_MESSAGE);
        panel.dialogUnpause();
    }
	
	private void displayExitDialog()
    {
		panel.dialogPause();
    	int result = JOptionPane.showConfirmDialog(SI.this, "Dare to quit?");
        if(result == JOptionPane.YES_OPTION)
            dispose();
        else
        	panel.dialogUnpause();
    }

    public static void main(String[] args) {
        JFrame f = new SI();
        f.setVisible(true);
    }

}
