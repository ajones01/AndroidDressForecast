package andrew.weatherApp1;

import android.graphics.Bitmap;

public class WeatherDataWrapper 
{
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public WeatherDataWrapper(Boolean isToday) 
	{
		_weatherData = new WeatherData(isToday);
	}
	
	// ===========================================================
	// Fields
	// ===========================================================
	private WeatherData _weatherData = null;
	private Bitmap _icon = null;
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public WeatherData GetWeatherData() 
	{
		return _weatherData;
	}
	
	public Bitmap GetIcon() 
	{
		return _icon;
	}

	public void SetIcon(Bitmap bitmap) 
	{
		_icon = bitmap;
	}
}

