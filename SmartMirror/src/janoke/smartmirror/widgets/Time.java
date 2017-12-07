package janoke.smartmirror.widgets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Time extends Pane
{
	boolean running = true;
	
	Label noah = new Label();
	DateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM.yy");
	DateFormat timeFormat = new SimpleDateFormat("H:mm:ss"); 
	Calendar calendar = Calendar.getInstance();
	
	public Time()
	{
		init();
	}
	
	public void init()
	{ 
		System.out.println(dateFormat.format(calendar.getTime()));
		getChildren().add(noah);
		noah.setStyle("-fx-font-size: 30;");
		
		final Timeline timeline = new Timeline(
			new KeyFrame(
				Duration.millis(500),
				event -> 
				{	
					noah.setText(timeFormat.format(System.currentTimeMillis()));
				}
			));
		timeline.setCycleCount( Animation.INDEFINITE );
		timeline.play();
		
	}
	
	
	
}
