import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class SI extends JFrame {
	
	SIpanel panel = new SIpanel();
	
	public SI()
	{
		
		setSize(500, 450);
		setTitle("Space Invaders");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() 
        {
        	@Override
        	public void windowClosing(WindowEvent arg0)
        	{
        		displayExitDialog();
        	}
        });
	}
	
	private void displayAboutDialog()
    {
        JOptionPane.showMessageDialog(this, new JLabel("<html><hr><b>Space Invaders</b><br>by Conner A. Davis</hr></html>"), 
                "Message",JOptionPane.INFORMATION_MESSAGE);
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
