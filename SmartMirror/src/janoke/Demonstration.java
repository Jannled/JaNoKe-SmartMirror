package janoke;

import java.util.Arrays;

import janoke.smartmirror.Mainframe;
import javafx.application.Application;
import javafx.scene.layout.VBox;

public class Demonstration
{
	static Mainframe mf;
	
	public static void main(String[] args)
	{
		System.out.println("Starting SmartMirror with arguments: " + Arrays.toString(args));
		
		mf = new Mainframe(new VBox());
		Application.launch(Mainframe.class, args); //TODO App startet nicht!
	}
}
