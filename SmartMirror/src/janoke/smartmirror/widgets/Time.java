package janoke.smartmirror.widgets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.control.Label;

public class Time extends Label
{
	DateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM.yy");
	Calendar calendar = Calendar.getInstance();
	GregorianCalendar now = new GregorianCalendar(); 
	
	public Time()
	{
		init();
	}
	
	public void init()
	{
		dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG); // 14.04.12 21:34:07 MESZ 
        System.out.println(dateFormat.format(now.getTime()));
	}
}
