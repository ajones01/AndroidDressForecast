package andrew.weatherApp1;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import andrew.weatherApp1.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * SAXHandler capable of extracting information out of the xml-data returned by
 * the Google Weather API.
 */
public class GoogleWeatherHandler extends DefaultHandler 
{
	// ===========================================================
	// Fields
	// ===========================================================

	private ArrayList<WeatherDataWrapper> _weatherDataWrapper = null;
	private boolean _inForecastInformation = false;
	private boolean _inForecastConditions = false;
	private boolean _usingSITemperature = false; // true means Fahrenheit
	private int _currentWeatherData = 0;
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public ArrayList<WeatherDataWrapper> GetWeatherDataWrapper() 
	{
		return _weatherDataWrapper;
	}
	
	// ===========================================================
	// Public Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException 
	{
		_weatherDataWrapper = new ArrayList<WeatherDataWrapper>();
	}

	@Override
	public void endDocument() throws SAXException 
	{
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException 
	{
		// 'Outer' Tags
		if (localName.equals("current_conditions")) 
		{
			_weatherDataWrapper.add(new WeatherDataWrapper(true));
		} 
		else if (localName.equals("forecast_conditions")) 
		{
			if (_inForecastConditions) // first loop we want to stick with current weather data as the first forecast is also today
			{
				_weatherDataWrapper.add(new WeatherDataWrapper(false));
				_currentWeatherData++;
			}
			_inForecastConditions = true;
		} 
		else 
		{
			String dataAttribute = atts.getValue("data");
			
			if (localName.equals("unit_system")) 
			{
				if (dataAttribute.equals("SI"))
				{
					_usingSITemperature = true;
				}
			}
			else if (localName.equals("day_of_week")) 
			{
				_weatherDataWrapper.get(_currentWeatherData).GetWeatherData().setDayofWeek(dataAttribute);
			} 
			else if (localName.equals("icon")) 
			{
				Bitmap bitmap = null;
				
				try
				{
					URL imgURL = new URL("http://www.google.com" + dataAttribute);
					URLConnection connection = imgURL.openConnection();
					connection.connect();
					InputStream inputStream = connection.getInputStream();
					BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
					bitmap = BitmapFactory.decodeStream(bufferedInputStream);
					bufferedInputStream.close();
					inputStream.close();
				}
				catch (Exception e)
				{
					//bitmap = getResources().getDrawable(R.drawable.dunno);
				}	
					
				_weatherDataWrapper.get(_currentWeatherData).SetIcon(bitmap);
			} 
			else if (localName.equals("condition")) 
			{
				_weatherDataWrapper.get(_currentWeatherData).GetWeatherData().setCondition(dataAttribute);
			}
			else if (localName.equals("temp_f")) 
			{
				_weatherDataWrapper.get(_currentWeatherData).GetWeatherData().setTempFahrenheit(Integer.parseInt(dataAttribute));
			} 
			else if (localName.equals("temp_c")) 
			{
				_weatherDataWrapper.get(_currentWeatherData).GetWeatherData().setTempCelcius(Integer.parseInt(dataAttribute));
			} 
			else if (localName.equals("humidity")) 
			{
				_weatherDataWrapper.get(_currentWeatherData).GetWeatherData().setHumidity(dataAttribute);
			} 
			else if (localName.equals("wind_condition")) 
			{
				_weatherDataWrapper.get(_currentWeatherData).GetWeatherData().setWindCondition(dataAttribute);
			}
			else if (localName.equals("low"))
			{
				int temp = Integer.parseInt(dataAttribute);
				if (_usingSITemperature) 
				{
					_weatherDataWrapper.get(_currentWeatherData).GetWeatherData().setTempMinCelsius(temp);
				} 
				else 
				{
					_weatherDataWrapper.get(_currentWeatherData).GetWeatherData().setTempMinFahrenheit(temp);
				}
			} 
			else if (localName.equals("high")) 
			{
				int temp = Integer.parseInt(dataAttribute);
				if (_usingSITemperature) 
				{
					_weatherDataWrapper.get(_currentWeatherData).GetWeatherData().setTempMaxCelsius(temp);
				} 
				else 
				{
					_weatherDataWrapper.get(_currentWeatherData).GetWeatherData().setTempMaxFahrenheit(temp);
				}
			}
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException 
	{
		if (localName.equals("forecast_information")) 
		{
			_inForecastInformation = false;
		}
	}
}
