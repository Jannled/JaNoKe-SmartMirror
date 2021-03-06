package janoke.smartmirror;

import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Mainframe extends Application implements EventHandler<KeyEvent>, Runnable
{
	private Scene mirror;
	
	private BorderPane contentPane;
	
	public static Mainframe instance;
	
	Font font; 	
	Stage stage;
	
	public Mainframe()
	{
		loadFont("Futura_Md_Bt.ttf");
		instance = this;
	}
	
	public void start(Stage stage) throws Exception
	{
		this.stage = stage;
		if(font == null) font = Font.getDefault();
		
		fullScreen();
		stage.setTitle("JaNoKe SmartMirror");
		
		//Greeter Pane
		BorderPane greeterPane = new BorderPane();
		greeterPane.setStyle("-fx-font-family: \"" + font.getFamily() +"\";");
		
		//Greeter Text
		Label lbl = new Label("SmartMirror by NoahDi, ShadowFury17 and Jannled");
		lbl.setStyle("-fx-font-size: 30;");
		greeterPane.setCenter(lbl);
		
		//Set content pane style
		contentPane = new BorderPane();
		contentPane.setStyle("-fx-font-family: \"" + font.getFamily() +"\";"
				+ "-fx-border-color: rgb(0, 0, 0);");
		
		//Set Scene
		mirror = new Scene(greeterPane);
		String style = Mainframe.class.getResource("mirror.css").toExternalForm();
		mirror.getStylesheets().add(style);
		
		//Register Exit Key
		mirror.setOnKeyPressed(this);
		
		//Show Greeter
		stage.setScene(mirror);
		stage.show();
		
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
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		mirror.setRoot(contentPane);
		fullScreen();
	}
	
	public void fullScreen()
	{
		stage.setFullScreen(true);
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setFullScreenExitHint("");
	}
	
	public void loadFont(String name)
	{
		URL s = Mainframe.class.getResource(name);
		if(s!=null)
		{
			font = Font.loadFont(s.toExternalForm(), 12);
		}
		else 
		{
			System.err.println("Could not find Font " + name + ". Falling back to default one!");
		}
	}
	
	public BorderPane getContentPane()
	{
		return contentPane;
	}
}
