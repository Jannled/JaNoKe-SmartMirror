package janoke;

import java.util.Arrays;

import janoke.smartmirror.Mainframe;

public class Demonstration
{
	static Mainframe mf;
	
	public static void main(String[] args)
	{
		System.out.println("Starting SmartMirror with arguments: " + Arrays.toString(args));
		mf = new Mainframe();
		
		mf.openWindow(args);
	}
}
