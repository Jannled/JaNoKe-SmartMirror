package janoke.smartmirror.widgets;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Weather extends GridPane
{	
	DateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM.yy");
	Calendar calendar = Calendar.getInstance();
	
	String beautyCity;
	String place;
	
	public Weather(String place)
	{
		this.beautyCity = place;
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
			
			//Check if erroring
			if(website.contains("Error"))
			{
				final String err = "Seems like the spot \"" + beautyCity + "\" does not exist!";
				System.err.println(err);
				error(err);
			}
			
			//Do the cutting
			website = website.split("<table class=\"weathertable last-weathertable\" cellspacing=\"1\" summary=\"forecasts\">")[1];
			website = website.split("</table>")[0];
			String[] data = website.split("<tr>");
			
			//Wind speed
			String[] wind = data[4].split("<td>");
			windData = new int[wind.length-1];
			for(int i=1; i<wind.length; i++)
			{
				try {windData[i-1] = Integer.parseInt(wind[i].split("\n")[0]);} catch (NumberFormatException e) {e.printStackTrace(); error("NumberFormatException, maybe the website has been updated, please create a bugreport under \"https://github.com/Jannled/JaNoKe-SmartMirror/issues\"!"); return;}
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
				try {rainfallData[i-1] = Integer.parseInt(rain[i].split("\n")[0]);} catch (NumberFormatException e) {e.printStackTrace(); error("NumberFormatException, maybe the website has been updated, please create a bugreport under \"https://github.com/Jannled/JaNoKe-SmartMirror/issues\"!"); return;}
			}
			
			//Air Temperature
			String[] temperature = data[8].split("<td class");
			temperatureData = new int[temperature.length-1];
			for(int i=1; i<temperature.length; i++)
			{
				temperature[i] = temperature[i].substring(temperature[i].indexOf(">")+1, temperature[i].indexOf("<"));
				try {temperatureData[i-1] = Integer.parseInt(temperature[i].split("\n")[0]);} catch (NumberFormatException e) {e.printStackTrace(); error("NumberFormatException, maybe the website has been updated, please create a bugreport under \"https://github.com/Jannled/JaNoKe-SmartMirror/issues\"!"); return;}
			}
			System.out.println("Parsed data from WindFinder.com for city " + beautyCity);

		} catch (MalformedURLException e)
		{
			e.printStackTrace();
			error("The specified url to query the weather from is invalid!");
			return;
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
			error("Error while downloading the ressource from the server, please check your internet connection!");
			return;
		} catch (IOException e)
		{
			e.printStackTrace();
			error("IOException while downloading the ressource from the server, please check your internet connection!");
			return;
		} catch (ArrayIndexOutOfBoundsException e) 
		{
			e.printStackTrace();
			error("Error while parsing the ressource from the server, maybe the website has been updated, please create a bugreport under \"https://github.com/Jannled/JaNoKe-SmartMirror/issues\"!");
			return;
		}
		
		//Display the data
		WeatherDay[] wf = new WeatherDay[3];
		for(int i=0; i<3; i++)
		{
			int[] temperature1 = new int[5];
			int[] weather1 = new int[5];
			System.arraycopy(temperatureData, i*5, temperature1, 0, 5);
			System.arraycopy(weatherData, i*5, weather1, 0, 5);
			WeatherDay day = new WeatherDay(dateFormat.format(calendar.getTime()), temperature1, weather1);
			calendar.add(Calendar.DATE, 1);
			wf[i] = day;
		}
		update(wf);
	}
	
	public void error(String message)
	{
		Label error = new Label(message);
		add(error, 0, 0);
	}
	
	public void update(WeatherDay[] days)
	{	
		for(int i=0; i<days.length; i++)
		{
			Label min1 = new Label("Min: ");
			Label max1 = new Label("Max: ");
			Label min2 = new Label("Min: ");
			Label max2 = new Label("Max: ");
			Label day = new Label(days[i].date);
			Label tmin = new Label(days[i].temperatureMin + "�C");
			Label tmax = new Label(days[i].temperatureMax + "�C");
			Label wmin = new Label(days[i].weatherMin + "CL");
			Label wmax = new Label(days[i].weatherMax + "CL");
			
			add(day, 0, i*4, 4, 1);
			
			add(min1, 0, i*4+1);
			add(max1, 1, i*4+1);
			add(min2, 2, i*4+1);
			add(max2, 3, i*4+1);
			
			add(tmin, 0, i*4+2);
			add(tmax, 1, i*4+2);
			add(wmin, 2, i*4+2);
			add(wmax, 3, i*4+2);
			
			add(new Text(""), 0, i*4+3);
			add(new Text(""), 1, i*4+3);
			add(new Text(""), 2, i*4+3);
			add(new Text(""), 3, i*4+3);
		}
	}
	
	public static int[] minMax(int[] values)
	{
		int[] output = new int[] {0x11111111, 0};
		
		for(int i=0; i<values.length; i++)
		{
			if(values[i] < output[0])
			{
				output[0] = values[i];
			}
			else if(values[i] > output[1])
			{
				output[1] = values[i];
			}
		}
		return output;
	}
}

class WeatherFormat
{
	private WeatherDay[] days;
	
	public WeatherFormat(WeatherDay[] days)
	{
		this.days = days;
	}
	
	WeatherDay getWeaterDay(int day)
	{
		return days[day];
	}
}

class WeatherDay
{
	String date;
	int temperatureMin, temperatureMax, weatherMin, weatherMax;
	
	public WeatherDay(String date, int[] temperature, int[] weather)
	{
		this.date = date;
		int[] tmm = Weather.minMax(temperature);
		int[] wmm = Weather.minMax(weather);
		temperatureMin = tmm[0];
		temperatureMax = tmm[1];
		weatherMin = wmm[0];
		weatherMax = wmm[1];
	}
	
	public WeatherDay(String date, int temperatureMin, int temperatureMax, int weatherMin, int weatherMax)
	{
		this.date = date;
		this.temperatureMin = temperatureMin;
		this.temperatureMax = temperatureMax;
		this.weatherMin = weatherMin;
		this.weatherMax = weatherMax;
	}
}
