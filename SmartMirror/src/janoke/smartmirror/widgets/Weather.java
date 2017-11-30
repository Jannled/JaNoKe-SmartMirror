package janoke.smartmirror.widgets;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Weather extends GridPane
{	
	DateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM.yy");
	Calendar calendar = Calendar.getInstance();
	
	String place;
	
	public Weather(String place)
	{
		setHgap(20);
		setVgap(3);
		
		place = place.toLowerCase();
		place = place.replace(" / ", "_");
		place = place.replace("/", "_");
		place = place.replace(" ", "_");
		this.place = place;
		
		query();
	}
	
	public void query()
	{
		int[] windData = new int[15];
		int[] weatherData = new int[15];
		int[] rainfallData = new int[15];
		int[] temperatureData = new int[15];
		
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
				if(weather[i].contains("bkn")) weatherData[i-1] = 0;
				else if(weather[i].contains("few")) weatherData[i-1] = 1;
				else if(weather[i].contains("ovc")) weatherData[i-1] = 2;
				else if(weather[i].contains("sct")) weatherData[i-1] = 3;
				else if(weather[i].contains("skc")) weatherData[i-1] = 4;
			}
			
			//Rainfall
			String[] rain = data[6].split("<td class");
			rainfallData = new int[rain.length-1];
			for(int i=1; i<rain.length; i++)
			{
				rain[i] = rain[i].substring(rain[i].indexOf(">")+1, rain[i].indexOf("<"));
				try {rainfallData[i-1] = Integer.parseInt(rain[i].split("\n")[0]);} catch (NumberFormatException e) {e.printStackTrace();}
			}
			
			//Air Temperature
			String[] temperature = data[8].split("<td class");
			temperatureData = new int[temperature.length-1];
			for(int i=1; i<temperature.length; i++)
			{
				temperature[i] = temperature[i].substring(temperature[i].indexOf(">")+1, temperature[i].indexOf("<"));
				try {temperatureData[i-1] = Integer.parseInt(temperature[i].split("\n")[0]);} catch (NumberFormatException e) {e.printStackTrace();}
			}
			System.out.println("Parsed data from WindFinder.com");

		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}			
		
		//Display the data
		update(temperatureData, weatherData, rainfallData, windData);
	}
	
	public void update(int[] temperature, int[] weather, int[] rainfall, int[] windspeed)
	{	
		Label d1 = new Label(dateFormat.format(calendar.getTime()));
		calendar.add(Calendar.DATE, 1);
		Label d2 = new Label(dateFormat.format(calendar.getTime()));
		calendar.add(Calendar.DATE, 1);
		Label d3 = new Label(dateFormat.format(calendar.getTime()));
		
		add(d1, 0, 0, 5, 1);
		add(d2, 5, 0, 5, 1);
		add(d3, 10, 0, 5, 1);
		
		populateRow(1, 0, new int[] {7, 10, 13, 16, 19, 7, 10, 13, 16, 19, 7, 10, 13, 16, 19}, ":00");
		populateRow(2, 0, weather, "");
		populateRow(3, 0, rainfall, "mm");
		populateRow(4, 0, temperature, "°C");
		populateRow(5, 0, windspeed, "bft");
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
			Label l = new Label(stuff[i] + unit);
			add(l, columnOffset + i, row);
		}
	}
}
