package janoke.smartmirror.widgets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class Time extends Pane
{
	boolean running = true;
	
	Label clock = new Label();
	Label calen = new Label();
	Label Spruch = new Label();
	DateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM.yy");
	DateFormat timeFormat = new SimpleDateFormat("H:mm:ss"); 
	Calendar calendar = Calendar.getInstance();
	VBox box = new VBox();
	
	
	public Time()
	{
		init();
		setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
	}
	
	public void init()
	{ 
		System.out.println(dateFormat.format(calendar.getTime()));
		getChildren().add(box);
		box.getChildren().add(Spruch);
		box.getChildren().add(calen);
		box.getChildren().add(clock);
		//box.setAlignment();
		clock.setStyle("-fx-font-size: 50;");
		calen.setStyle("-fx-font-size: 50;");
		
		final Timeline timeline = new Timeline(
			new KeyFrame(
				Duration.millis(500),
				event -> 
				{	
					clock.setText(timeFormat.format(System.currentTimeMillis()));
					calen.setText(dateFormat.format(System.currentTimeMillis()));
				}
			));
		timeline.setCycleCount( Animation.INDEFINITE );
		timeline.play();
		
	}
	
	
	
}
