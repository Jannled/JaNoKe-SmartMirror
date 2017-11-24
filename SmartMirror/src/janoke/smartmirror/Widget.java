package janoke.smartmirror;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class Widget extends JPanel
{
	private static final long serialVersionUID = 7974930229621046572L;
	
	public Widget()
	{
		setBackground(Color.BLACK);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		render(g);
	}
	
	public abstract void render(Graphics g);
}
