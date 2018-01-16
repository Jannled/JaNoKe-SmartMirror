package janoke.smartmirror.widgets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import janoke.smartmirror.Config;
import janoke.smartmirror.Mainframe;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;

public class News extends VBox
{
	
	Label newsLabel = new Label();
	
	int counter = 0;
	
	public News()
	{
		init();
		setMaxSize(500,1000);
		setMaxWidth(500);
		newsLabel.setWrapText(true);
	}
	
	public String readRSSFeed(String urlAddress)
	{
		try
		{
			URL rssUrl = new URL(urlAddress);
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream(), "UTF-8"));
			String sourceCode = "";
			String line;
			
		    while((line=in.readLine())!=null && counter <= 4)
			{
				if(line.contains("<title>"))
				{
					int firstPos = line.indexOf("<title>");
					String temp = line.substring(firstPos);
					temp = temp.replace("<title>","");
					
					int lastPos = temp.indexOf("</title>");
					temp = temp.substring(0,lastPos);
					
					sourceCode += temp + "\n" ; 
				}
				
				else if(line.contains("<description>"))
				{
					int firstPos = line.indexOf("<description>");
					String temp = line.substring(firstPos);
					temp = temp.replace("<description>","");
					
					int lastPos = temp.indexOf("</description>");
					temp = temp.substring(0,lastPos);
					
					sourceCode += temp + "\n" ;
					sourceCode += "\n";
					counter++;
				}
				
				else if(line.contains("<pubDate>"))
				{
					int firstPos = line.indexOf("<pubDate>");
					String temp = line.substring(firstPos);
					temp = temp.replace("<pubDate>","");
					int lastPos = temp.indexOf("</pubDate>");
					temp = temp.substring(0,lastPos);
					
					temp = temp.replace("+0100", "");
					temp = temp.trim();
					
					sourceCode += temp + "\n" ;
				}
				
			}	
			in.close();
			System.out.println("Pulled Newsfeed from: " + rssUrl);
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
		newsLabel.setText(readRSSFeed(Config.getProperty("news")));
		
		//Region bliblablup = ((Region) Mainframe.instance.getContentPane().getRight());
		//double width = ((Region) Mainframe.instance.getContentPane().getRight()).getWidth();
		//setMaxSize(((Region) Mainframe.instance.getContentPane().getRight()).getWidth(), Label.USE_PREF_SIZE);
	}
	
}
	

