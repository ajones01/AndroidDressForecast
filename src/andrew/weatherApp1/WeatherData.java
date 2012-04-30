package andrew.weatherApp1;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherData implements Serializable
{
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public WeatherData(Boolean isToday) 
	{
		_isToday = isToday;
	}
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Boolean _isToday = null;
	private String _condition = null;
	private String _humidity = null;
	private String _windCondition = null;
	private Integer _tempCelcius = null;
	private Integer _tempFahrenheit = null;
	private String _dayofWeek = null;
	private Integer _tempMin = null;
	private Integer _tempMax = null;
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// forecast information
	//forecast date
	
	// ===========================================================
	// Getter & Setter
	// Common to current and forecast
	// ===========================================================

	public Boolean getIsToday() 
	{
		return _isToday;
	}
	
	public String getCondition() 
	{
		return _condition;
	}

	public void setCondition(String condition) 
	{
		_condition = condition;
	}
	
	// ===========================================================
	// Getter & Setter
	// Current only
	// ===========================================================
	
	public String getHumidity() 
	{
		return _humidity;
	}

	public void setHumidity(String humidity) 
	{
		_humidity = humidity;
	}
	
	public String getWindCondition() 
	{
		return _windCondition;
	}

	public void setWindCondition(String windCondition) 
	{
		_windCondition = windCondition;
	}
	
	public Integer getTempCelcius() 
	{
		return _tempCelcius;
	}

	public void setTempCelcius(Integer temp) 
	{
		_tempCelcius = temp;
	}
	
	public Integer getTempFahrenheit() 
	{
		return _tempFahrenheit;
	}

	public void setTempFahrenheit(Integer temp) 
	{
		_tempFahrenheit = temp;
	}
	
	// ===========================================================
	// Getter & Setter
	// Forecast only
	// ===========================================================
	
	public String getDayofWeek() 
	{
		return _dayofWeek;
	}

	public void setDayofWeek(String dayofWeek) 
	{
		_dayofWeek = dayofWeek;
	}

	public Integer getTempMinCelsius() 
	{
		return _tempMin;
	}

	public void setTempMinCelsius(Integer tempMin) 
	{
		_tempMin = tempMin;
	}
	
	public Integer getTempMaxCelsius() 
	{
		return _tempMax;
	}

	public void setTempMaxCelsius(Integer tempMax) 
	{
		_tempMax = tempMax;
	}
	
	public void setTempMinFahrenheit(Integer tempMinFahrenheit) 
	{
		_tempMin = FahrenheitToCelsius(tempMinFahrenheit);
	}
	
	public void setTempMaxFahrenheit(Integer tempMaxFahrenheit) 
	{
		_tempMax = FahrenheitToCelsius(tempMaxFahrenheit);
	}
	
	// ===========================================================
	// Private Methods
	// ===========================================================
	
	private int FahrenheitToCelsius(int tFahrenheit) 
	{
		return (int) ((5.0f / 9.0f) * (tFahrenheit - 32));
	}
}
