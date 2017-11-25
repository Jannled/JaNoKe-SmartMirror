package janoke.smartmirror;

import javafx.application.Application;
import javafx.stage.Stage;

public class Mainframe extends Application
{
	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setFullScreen(true);
		
		stage.show();
	}
	
	public void openWindow(String[] args)
	{
		launch(args);
	}
}
