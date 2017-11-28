package janoke.smartmirror;

import janoke.smartmirror.widgets.Weather;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Mainframe extends Application implements EventHandler<KeyEvent>, Runnable
{
	public Scene content;
	public Scene greeter;
	
	private Pane contentPane;
	
	final Font font; 	
	Stage stage;
	
	public Mainframe(Pane contentPane)
	{
		this.contentPane = contentPane;
		font = Font.loadFont(Mainframe.class.getResource("Futura_Md_Bt.ttf").toExternalForm(), 12);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		this.stage = stage;
		
		fullScreen();
		stage.setTitle("JaNoKe SmartMirror");
		
		//Greeter Pane
		BorderPane greeterPane = new BorderPane();
		greeterPane.setStyle("-fx-background-color: #000000;"
				+ "-fx-border-color: white;"
				+ "-fx-border-width: 10 10 10 10;"
				+ "-fx-font-family: \"" + font.getFamily() +"\";");
		
		//Greeter Text
		Label lbl = new Label("SmartMirror by NoahDi, ShadowFury17 and Jannled");
		lbl.setTextFill(Color.WHITE);
		lbl.setFont(new Font("Arial", 30));
		greeterPane.setCenter(lbl);
		
		//Set content pane style
		contentPane.setStyle("-fx-background-color: #000000;"
				+ "-fx-font-family: \"" + font.getFamily() +"\";");
		
		//Set Scenes
		greeter = new Scene(greeterPane);
		content = new Scene(contentPane);
		
		//Register Exit Key
		greeter.setOnKeyPressed(this);
		content.setOnKeyPressed(this);
		
		//Show Greeter
		stage.setScene(greeter);
		stage.show();
		
		contentPane.getChildren().add(new Weather("Soest / Bad Sassendorf"));
		
		Platform.runLater(this);
		
	}

	@Override
	public void handle(KeyEvent e)
	{
		if(e.getCode() == KeyCode.ESCAPE)
		{
			System.out.println("Exit key pressed. Closing!");
			System.exit(0);
		}
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(3000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		stage.setScene(content);
		fullScreen();
	}
	
	public void fullScreen()
	{
		stage.setFullScreen(true);
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setFullScreenExitHint("");
	}
	
	public void add()
	{
		
	}
}
