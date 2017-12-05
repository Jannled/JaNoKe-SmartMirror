package janoke;

import java.util.Arrays;

import janoke.smartmirror.Mainframe;
import janoke.smartmirror.widgets.News;
import janoke.smartmirror.widgets.Time;
import janoke.smartmirror.widgets.Weather;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

public class Demonstration implements Runnable
{
	private static Thread bypass;
	
	public Demonstration(String[] args)
	{
		bypass = new Thread(this, "Logicthread");
		bypass.start();
		
		Thread.currentThread().setName("Renderthread");
		Application.launch(Mainframe.class, args);
	}
	
	public static void main(String[] args)
	{
		System.out.println("Starting SmartMirror with arguments: " + Arrays.toString(args));
		new Demonstration(args);
	}

	@Override
	public void run()
	{
		System.out.println("Launched second thread.");
		
		Time t = new Time();
		Weather w = new Weather("Soest / Bad Sassendorf");
		News n = new News();
		
		Mainframe.instance.getContentPane().setLeft(n);
		Mainframe.instance.getContentPane().setRight(w);
		Mainframe.instance.getContentPane().setBottom(t);
		BorderPane.setAlignment(t, Pos.CENTER);
	}
}
