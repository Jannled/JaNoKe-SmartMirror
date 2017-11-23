package janoke.smartmirror;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class Mainframe implements KeyListener
{
	JFrame frame = new JFrame("SmartMirror - JaNoKe");
	JPanel panel = new JPanel();
	
	LineBorder whiteBoarder;
	
	private Mainframe()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setVisible(true);
		frame.addKeyListener(this);
		
		frame.getContentPane().add(panel);
		panel.setBackground(Color.BLACK);
		whiteBoarder = new LineBorder(Color.WHITE, 10);
		panel.setBorder(whiteBoarder);
		panel.setLayout(new BorderLayout());
		JLabel greeter = new JLabel("<html><h1 style=\"text-align: center;\">SmartMirror by Jannled, NoahDi and ShadowFury</h1></html>");
		greeter.setForeground(Color.WHITE);
		panel.add(greeter, BorderLayout.CENTER);
		
		Window[] windows = Window.getWindows();
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(windows[0]);
	}
	
	public static void main(String[] args)
	{
		System.out.println("Starting SmartMirror with arguments: " + Arrays.toString(args));
		new Mainframe();
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.out.println("Exit key pressed, closing!");
			System.exit(0); 
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
	
	public void addComponent(Component comp)
	{
		frame.getContentPane().add(comp);
	}
	
	public void addComponent(Component comp, Object constraints)
	{
		frame.getContentPane().add(comp, constraints);
	}
}
