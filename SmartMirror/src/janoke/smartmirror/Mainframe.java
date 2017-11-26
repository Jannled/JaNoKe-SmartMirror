package janoke.smartmirror;

import janoke.smartmirror.widgets.Weather;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Mainframe extends Application implements EventHandler<KeyEvent>
{
	public Scene content;
	public Scene greeter;
	
	Stage stage;
	
	public Mainframe()
	{
		
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		this.stage = stage;
		
		//stage.setFullScreen(true);
		stage.setFullScreenExitHint("");
		stage.setTitle("JaNoKe SmartMirror");
		
		//Greeter Pane
		BorderPane greeterPane = new BorderPane();
		greeterPane.setStyle("-fx-background-color: #000000;"
				+ "-fx-border-color: white;"
				+ "-fx-border-width: 10 10 10 10;");
		
		//Greeter Text
		Label lbl = new Label("SmartMirror by NoahDi, ShadowFury17 and Jannled");
		lbl.setTextFill(Color.WHITE);
		lbl.setFont(new Font("Arial", 30));
		greeterPane.setCenter(lbl);
		
		//Actual Content Pane
		VBox contentPane = new VBox();
		contentPane.setStyle("-fx-background-color: #000000;");
		
		//Set Scenes
		greeter = new Scene(greeterPane);
		content = new Scene(contentPane);
		
		//Register Exit Key
		greeter.setOnKeyPressed(this);
		content.setOnKeyPressed(this);
		
		//Show Greeter
		stage.setScene(greeter);
		stage.show();
		
		//Show actual content
		stage.setScene(content);
		//stage.setFullScreen(true);
		
		contentPane.getChildren().add(new Weather("Soest / Bad Sassendorf"));
	}
	
	public void openWindow(String[] args)
	{
		launch(args);
	}

	@Override
	public void handle(KeyEvent e)
	{
		if(e.getCode() == KeyCode.ESCAPE)
		{
			System.out.println("Exit key pressed. Closing!");
			//System.exit(0);
		}
	}
}
