package janoke.smartmirror;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Config
{
	private static List<String> content = Arrays.asList(
			"#Specify the rss file for your news feed:",
			"news: http://www.tagesschau.de/xml/rss2",
			"",
			"#Specify the city you want to recieve weather news from, look it up on WindFinder.com",
			"city: Dortmund"
			);
	
	private static HashMap<String, String> config = new HashMap<String, String>();
	
	public static void parse(File f)
	{
		try
		{
			if(Files.exists(f.toPath()))
			{
				//Try to read the file
				content = Files.readAllLines(f.toPath());
				System.out.println("Opening config file: " + f.getAbsolutePath() + ".");
			}
			else
			{
				//File not found creating default one.
				File def = new File("SmartMirror.config");
				System.out.println("Config file not found, creating default one at " + def.getAbsolutePath() + "!");
				Files.write(def.toPath(), content);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		
		//Load config file
		config = new HashMap<String, String>();
		int lineCounter = 0;
		for(String s : content)
		{
			lineCounter++;
			s.trim();
			if(s.startsWith("#") || s.isEmpty()) continue;
			
			String[] line = s.split(":", 2);
			if(line.length == 2)
				config.put(line[0].trim(), line[1].trim());
			else
				System.err.println("Error while parsing line " + lineCounter + " from file " + f.getAbsolutePath() + "!");
		}
	}
	
	public static String getProperty(String key)
	{
		return config.get(key);
	}
}
