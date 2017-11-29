package janoke;

import java.util.Arrays;

import janoke.smartmirror.Mainframe;
import javafx.application.Application;

public class Demonstration
{
	static Mainframe mf;
	
	public static void main(String[] args)
	{
		System.out.println("Starting SmartMirror with arguments: " + Arrays.toString(args));
		Application.launch(Mainframe.class, args);
	}
}
