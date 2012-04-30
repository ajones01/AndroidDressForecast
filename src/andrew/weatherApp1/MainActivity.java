package andrew.weatherApp1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import andrew.weatherApp1.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.os.AsyncTask;

public class MainActivity extends Activity 
{
	private ProgressDialog _progressDialog;
	private String _saveFileName = "appData";
	ArrayList<WeatherDataWrapper> _weatherDataWrapper = null;
	
	// ===========================================================
	// Public Methods
	// ===========================================================
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button cmd_submit = (Button) findViewById(R.id.cmd_submit);
        EditText editInput = ((EditText) findViewById(R.id.edit_input));
        
        try
        {
	        FileInputStream fileInputStream = openFileInput(_saveFileName);
	        
	        int ch;
	        StringBuffer strContent = new StringBuffer("");
	        while( (ch = fileInputStream.read()) != -1)
	        {
	            strContent.append((char)ch);
	        }
	        
	        fileInputStream.close();
	        
	        editInput.setHint(strContent);
        }
        catch(Exception e)
        {
        	editInput.setHint(getString(R.string.defaultlocation));
        }
        
        GetWeather(this);
                
        cmd_submit.setOnClickListener(
        	new OnClickListener() 
	        {
				@Override
				public void onClick(View arg0) 
				{
					GetWeather(arg0.getContext());
				}
			}
        );
        
        WeatherInfoView weatherTodayInfo = (WeatherInfoView) findViewById(R.id.weatherToday);
        WeatherInfoView weather1Info = (WeatherInfoView) findViewById(R.id.weather1);
        WeatherInfoView weather2Info = (WeatherInfoView) findViewById(R.id.weather2);
        WeatherInfoView weather3Info = (WeatherInfoView) findViewById(R.id.weather3);
     
        weatherTodayInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ClothingInfoActivity.class);
               	intent.putExtra("weatherData", _weatherDataWrapper.get(0).GetWeatherData());
                startActivityForResult(intent, 0);
            }

        });
        
        weather1Info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ClothingInfoFutureActivity.class);
               	intent.putExtra("weatherData", _weatherDataWrapper.get(1).GetWeatherData());
                startActivityForResult(intent, 0);
            }

        });
        
        weather2Info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ClothingInfoFutureActivity.class);
               	intent.putExtra("weatherData", _weatherDataWrapper.get(2).GetWeatherData());
                startActivityForResult(intent, 0);
            }

        });
        
        weather3Info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ClothingInfoFutureActivity.class);
               	intent.putExtra("weatherData", _weatherDataWrapper.get(3).GetWeatherData());
                startActivityForResult(intent, 0);
            }

        });
    }
    
    // ===========================================================
 	// Private Methods
 	// ===========================================================
    
    private void GetWeather(Context context)
    {
    	URL url;
		try 
		{
			_progressDialog = ProgressDialog.show(context, "", getString(R.string.getweather), true,
	                false);
			
			EditText editInput = ((EditText) findViewById(R.id.edit_input));
			
			String cityParamString = editInput.getText().toString();
			
			if (cityParamString.length() == 0)
			{
				cityParamString = (String) editInput.getHint();
			}
			String queryString = "http://www.google.com/ig/api?weather="
					+ cityParamString + "&oe=utf-8";
			/* Replace blanks with HTML-Equivalent. */
			url = new URL(queryString.replace(" ", "%20"));						

			FileOutputStream fileOutputStream = openFileOutput(_saveFileName, context.MODE_PRIVATE);
			fileOutputStream.write(cityParamString.getBytes());
			fileOutputStream.close();
			
			GetWeatherAsyncTask task = new GetWeatherAsyncTask();
			
			task.execute(url);
			
		} 
		catch (Exception e) 
		{
			ResetWeatherInfoViews();
		}
	}
    
    private class GetWeatherAsyncTask extends AsyncTask<URL, Void, ArrayList<WeatherDataWrapper>> 
    {
    	@Override
		protected ArrayList<WeatherDataWrapper> doInBackground(URL... urls) 
    	{
    		try 
    		{
    			_weatherDataWrapper = null;
    			
    			boolean connected = false;
    		    ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    		    NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
    		    connected = networkinfo != null && networkinfo.isAvailable() && networkinfo.isConnected();
    			
    		    if (connected)
    		    {
					SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
					SAXParser saxParser = saxParserFactory.newSAXParser();
					XMLReader xmlReader = saxParser.getXMLReader();
		
					GoogleWeatherHandler googleWeatherHandler = new GoogleWeatherHandler();
					xmlReader.setContentHandler(googleWeatherHandler);
					
					/* Parse the xml-data our URL-call returned. */
					InputSource is = new InputSource(urls[0].openStream());
		  	      	is.setEncoding("UTF-8");
		  	      	xmlReader.parse(is);
		  	      	
		  	      	_weatherDataWrapper = googleWeatherHandler.GetWeatherDataWrapper();
    		    }
    		} 
    		catch (Exception e) 
    		{
    			// _weatherDataWrapper should be null if a problem, so just ignore this as we cannot access UI thread from here.
    		}
		
    		return _weatherDataWrapper;
    	}

		@Override
		protected void onPostExecute(ArrayList<WeatherDataWrapper> weatherDataWrapper) 
		{
			try 
    		{
				if (_weatherDataWrapper == null)
				{
					ResetWeatherInfoViews();
				}
				_progressDialog.dismiss();
				UpdateCurrentWeatherInfoView(R.id.weatherToday, weatherDataWrapper.get(0));
				UpdateForecastWeatherInfoView(R.id.weather1, weatherDataWrapper.get(1));
				UpdateForecastWeatherInfoView(R.id.weather2, weatherDataWrapper.get(2));
				UpdateForecastWeatherInfoView(R.id.weather3, weatherDataWrapper.get(3));
    		} 
    		catch (Exception e) 
    		{
    			ResetWeatherInfoViews();
    		}
		}
    }
    
    private void UpdateCurrentWeatherInfoView(int aResourceID, WeatherDataWrapper weatherDataWrapper) throws MalformedURLException 
	{
    	int tempMin = weatherDataWrapper.GetWeatherData().getTempMinCelsius();
		int tempMax = weatherDataWrapper.GetWeatherData().getTempMaxCelsius();
		
    	WeatherInfoView weatherInfoView = (WeatherInfoView)findViewById(aResourceID);
    	weatherInfoView.SetImage(weatherDataWrapper.GetIcon());
    	weatherInfoView.SetDay(getString(R.string.today));
		weatherInfoView.SetTempCelcius(weatherDataWrapper.GetWeatherData().getTempCelcius());
		weatherInfoView.SetCondition(weatherDataWrapper.GetWeatherData().getCondition());
		//weatherInfoView.SetTempCelciusMinMax(tempMin, tempMax);
		
		
		
		/* Convert from Celsius to Fahrenheit if necessary. */
		
		/*if (this.chk_usecelsius.isChecked())
		{
			((WeatherInfoView) findViewById(aResourceID)).setTempCelcius(weatherDataWrapper.getTempCelcius());
		}
		else
		{
			((WeatherInfoView) findViewById(aResourceID)).setTempFahrenheit(weatherDataWrapper.getTempFahrenheit());
		}*/
	}
    
    private void UpdateForecastWeatherInfoView(int aResourceID, WeatherDataWrapper weatherDataWrapper) throws MalformedURLException 
    {
		int tempMin = weatherDataWrapper.GetWeatherData().getTempMinCelsius();
		int tempMax = weatherDataWrapper.GetWeatherData().getTempMaxCelsius();

		WeatherInfoView weatherInfoView = (WeatherInfoView) findViewById(aResourceID);
		weatherInfoView.SetImage(weatherDataWrapper.GetIcon());
		weatherInfoView.SetDay(weatherDataWrapper.GetWeatherData().getDayofWeek());
		weatherInfoView.SetTempCelciusMinMax(tempMin, tempMax);
		weatherInfoView.SetCondition(weatherDataWrapper.GetWeatherData().getCondition());
		
		/*
		if (this.chk_usecelsius.isChecked()) 
		{
			((WeatherInfoView) findViewById(aResourceID)).setTempCelciusMinMax(tempMin, tempMax);
		} 
		else 
		{
			tempMin = WeatherUtils.celsiusToFahrenheit(tempMin);
			tempMax = WeatherUtils.celsiusToFahrenheit(tempMax);
			((WeatherInfoView) findViewById(aResourceID)).setTempFahrenheitMinMax(tempMin, tempMax);
		}*/
	}
    
    private void ResetWeatherInfoViews() 
    {
		((WeatherInfoView)findViewById(R.id.weatherToday)).Reset();
		((WeatherInfoView)findViewById(R.id.weather1)).Reset();
		((WeatherInfoView)findViewById(R.id.weather2)).Reset();
		((WeatherInfoView)findViewById(R.id.weather3)).Reset();
	}
}