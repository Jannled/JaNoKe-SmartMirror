package janoke.smartmirror.widgets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import janoke.smartmirror.Config;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
		readRSSFeed();
	}
	
	public void readRSSFeed()
	{
		String url = Config.getProperty("news");
		SyndFeed feed = null;
		try
		{
			feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(feed.getTitle());
		
		for(SyndEntry e : feed.getEntries())
		{
			e.getTitle();
			e.getPublishedDate();
			System.out.println(e.getDescription());
		}
	}


	
	public void init()
	{
		getChildren().add(newsLabel);
		//newsLabel.setText(readRSSFeed(Config.getProperty("news")));
		
		//Region bliblablup = ((Region) Mainframe.instance.getContentPane().getRight());
		//double width = ((Region) Mainframe.instance.getContentPane().getRight()).getWidth();
		//setMaxSize(((Region) Mainframe.instance.getContentPane().getRight()).getWidth(), Label.USE_PREF_SIZE);
	}
	
}
	

