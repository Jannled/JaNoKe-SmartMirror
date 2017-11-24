package janoke;

import java.util.Arrays;

import janoke.smartmirror.Mainframe;
import janoke.smartmirror.widgets.Weather;

public class Demonstration
{
	static Mainframe mf;
	
	public static void main(String[] args)
	{
		System.out.println("Starting SmartMirror with arguments: " + Arrays.toString(args));
		mf = new Mainframe();
		
		mf.addComponent(new Weather());
	}
}
