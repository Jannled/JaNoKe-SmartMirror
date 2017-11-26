package janoke.smartmirror;

import java.lang.reflect.Field;

import org.w3c.dom.Document;

import com.sun.webkit.WebPage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Browser extends StackPane
{
	private WebView browser;
	private WebEngine engine;
	
	/**
	 * Create a new browser from the given URL or html code.
	 */
	public Browser()
	{
		this.browser = new WebView();
		this.engine = browser.getEngine();
		
		engine.documentProperty().addListener(new WebDocumentListener(engine));
		
		getChildren().add(browser);
		engine.setJavaScriptEnabled(true);
	}

	public void loadURL(String url)
	{
		engine.load(url);
	}
	
	public void loadContent(String content)
	{
		engine.loadContent(content);
	}
	
	public WebView getBrowser()
	{
		return browser;
	}

	public WebEngine getEngine()
	{
		return engine;
	}
}

class WebDocumentListener implements ChangeListener<Document> { 
    private final WebEngine webEngine; 

    public WebDocumentListener(WebEngine webEngine) { 
        this.webEngine = webEngine; 
    } 

    @Override 
    public void changed(ObservableValue<? extends Document> arg0, 
            Document arg1, Document arg2) { 
        try { 
            // Use reflection to retrieve the WebEngine's private 'page' field. 
            Field f = webEngine.getClass().getDeclaredField("page"); 
            f.setAccessible(true);
            WebPage page = (WebPage) f.get(webEngine); 
            // Set the background color of the page to be transparent. 
            page.setBackgroundColor((new java.awt.Color(0, 0, 0, 255)).getRGB()); 
        } catch (Exception e) { 
            System.out.println("Error: " + e);
        } 
    } 
}