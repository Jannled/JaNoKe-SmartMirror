package janoke.smartmirror;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Mainframe implements KeyListener, Runnable, FocusListener
{
	JFrame frame = new JFrame("SmartMirror - JaNoKe");
	JPanel greeterPanel = new JPanel();
	JPanel contentPanel = new JPanel();
	Thread anim = new Thread();
	
	private boolean init = false;
	
	LineBorder whiteBoarder;
	
	public Mainframe()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.addKeyListener(this);
		frame.addFocusListener(this);
		
		frame.getContentPane().add(greeterPanel);
		greeterPanel.setBackground(Color.BLACK);
		whiteBoarder = new LineBorder(Color.WHITE, 10);
		greeterPanel.setBorder(whiteBoarder);
		greeterPanel.setLayout(new BorderLayout());
		JLabel greeter = new JLabel("<html><h1>SmartMirror by Jannled, NoahDi and ShadowFury</h1></html>");
		greeter.setForeground(Color.WHITE);
		greeterPanel.add(greeter, BorderLayout.CENTER);
		
		frame.setVisible(true);
		
		anim = new Thread(this);
		anim.run();
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
	
	public void addComponent(Widget widget)
	{
		if(!init)
		{
			init = true;
			frame.getContentPane().removeAll();
		}
		contentPanel.add(widget);
		frame.revalidate();
		frame.repaint();
	}
	
	public void addComponent(Widget widget, Object constraints)
	{
		if(!init)
		{
			init = true;
			frame.getContentPane().removeAll();
		}
		contentPanel.add(widget, constraints);
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(3000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("Closing greeter!");
		frame.getContentPane().removeAll();
		frame.getContentPane().add(contentPanel);
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		System.out.println("Focus gained.");
		setFullscreen(true);
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		System.out.println("Focus lost.");
		setFullscreen(false);
	}
	
	public void setFullscreen(boolean fullscreen)
	{
		Window[] windows = Window.getWindows();
		if(fullscreen) 
		{
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(windows[0]);
		}
		else
		{
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
		}
	}
}
