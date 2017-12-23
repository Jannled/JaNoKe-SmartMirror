package janoke.smartmirror.widgets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class News extends VBox
{
	
	Label newsLabel = new Label();
	
	public News()
	{
		init();

	}
	
	public String readRSSFeed(String urlAddress)
	{
		try
		{
			URL rssUrl = new URL(urlAddress);
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String sourceCode = "";
            String line;
            while((line=in.readLine())!=null)
            {
                if(line.contains("<title>"))
                {
                    System.out.println(line);
                    
                    int firstPos = line.indexOf("<title>");
                    String temp = line.substring(firstPos);
                    temp = temp.replace("<title>","");
                    
                    int lastPos = temp.indexOf("</title>");
                    temp = temp.substring(0,lastPos);
                    sourceCode += temp + "\n" ; 
                    
                }
                else if(line.contains("<description>"))
                {
                	System.out.println(line);
                    
                    int firstPos = line.indexOf("<description>");
                    String temp = line.substring(firstPos);
                    temp = temp.replace("<description>","");
                    
                    int lastPos = temp.indexOf("</description>");
                    temp = temp.substring(0,lastPos);
                    sourceCode += temp + "\n" ;   
                
                }
                else if(line.contains("<pubDate>"))
                {
                	System.out.println(line);
                	
                	int firstPos = line.indexOf("<pubDate>");
                    String temp = line.substring(firstPos);
                    temp = temp.replace("<pubDate>","");
                    
                    int lastPos = temp.indexOf("</pubDate>");
                    temp = temp.substring(0,lastPos);
                    sourceCode += temp + "\n" ;
                }   	
            }                    
            in.close();
            return sourceCode;
        } 
		catch (MalformedURLException ue)
		{
            System.out.println("Malformed URL");
            
        }
		catch (IOException ioe)
		{
            System.out.println("Something went wrong reading the contents");
        }
        return null;
}
	
	public void init()
    {
		getChildren().add(newsLabel);
    	newsLabel.setText(readRSSFeed("http://www.tagesschau.de/xml/rss2"));
    	setMaxSize(1000, 1000);
    }
	
}
	

