package janoke.smartmirror.widgets;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Weather extends GridPane
{	
	DateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM.yy");
	Calendar calendar = Calendar.getInstance();
	
	String place;
	
	public Weather(String place)
	{
		setHgap(10);
		setVgap(5);
		
		place = place.toLowerCase();
		place = place.replace(" / ", "_");
		place = place.replace("/", "_");
		place = place.replace(" ", "_");
		this.place = place;
		
		query();
	}
	
	public void query()
	{
		int[] windData;
		int[] weatherData;
		int[] rainfallData;
		int[] temperatureData;
		
		try
		{
			//Load Website
			URL url = new URL("https://www.windfinder.com/widget/forecast/" + place + "?show_pressure=1&unit_temperature=c&show_day=1&show_rain=1&unit_rain=mm&days=3&unit_wind=bft&show_clouds=1&show_wind=1&show_temperature=1&show_waves=0&columns=3&unit_wave=m");
			InputStream stream = url.openStream();
			Scanner s = new Scanner(stream);
			s.useDelimiter("\\A");
			String website = s.hasNext() ? s.next() : "";
			stream.close();
			s.close();
			
			//Do the cutting
			website = website.split("<table class=\"weathertable last-weathertable\" cellspacing=\"1\" summary=\"forecasts\">")[1];
			website = website.split("</table>")[0];
			String[] data = website.split("<tr>");
			
			//Wind speed
			String[] wind = data[4].split("<td>");
			windData = new int[wind.length-1];
			for(int i=1; i<wind.length; i++)
			{
				try {windData[i-1] = Integer.parseInt(wind[i].split("\n")[0]);} catch (NumberFormatException e) {e.printStackTrace();}
			}
			
			//Weather
			String[] weather = data[5].split("<td>");
			weatherData = new int[weather.length-1];
			for(int i=1; i<weather.length; i++)
			{
				if(weather[i].contains("bkn")) weatherData[i+1] = i;
				else if(weather[i].contains("bkn")) weatherData[i+1] = i;
				else if(weather[i].contains("bkn")) weatherData[i+1] = i;
				else if(weather[i].contains("bkn")) weatherData[i+1] = i;
				else if(weather[i].contains("bkn")) weatherData[i+1] = i;
			}
			
			//Rainfall
			data[6].split("");
			
			//Air Temperature
			data[8].split("");
			
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void update(int[] temperature, int[] weather[], int[] rainfall, int[] windspeed)
	{	
		Text d1 = new Text(dateFormat.format(calendar.getTime()));
		calendar.add(Calendar.DATE, 1);
		Text d2 = new Text(dateFormat.format(calendar.getTime()));
		calendar.add(Calendar.DATE, 1);
		Text d3 = new Text(dateFormat.format(calendar.getTime()));
		
		d1.setFill(Color.WHITE);
		d2.setFill(Color.WHITE);
		d3.setFill(Color.WHITE);
		
		add(d1, 0, 0, 5, 1);
		add(d2, 5, 0, 5, 1);
		add(d3, 10, 0, 5, 1);
	}
	
	/**
	 * Populate a single row in the GridPane
	 * @param row The row to fill the data in.
	 * @param columnOffset The first column to start populating from.
	 * @param stuff The data to fill in.
	 * @param unit The unit of the data (for e.g. °C).
	 */
	public void populateRow(int row, int columnOffset, int[] stuff, String unit)
	{
		for(int i=0; i<stuff.length; i++)
		{
			add(new Text(stuff[i] + unit), columnOffset + i, row);
		}
	}
}
