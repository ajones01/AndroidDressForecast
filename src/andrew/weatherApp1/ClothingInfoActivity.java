package andrew.weatherApp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClothingInfoActivity  extends Activity 
{
	 public void onCreate(Bundle savedInstanceState) 
	 {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothinginfo);

        Intent intent = getIntent();
        WeatherData weatherData = (WeatherData)intent.getSerializableExtra("weatherData");
        
        Button next = (Button) findViewById(R.id.ButtonBack);
        next.setOnClickListener(
        	new View.OnClickListener() 
        	{
        		public void onClick(View view) 
        		{
	                Intent intent = new Intent();
	                setResult(RESULT_OK, intent);
	                finish();
        		}
	        }
        );
        
        
        TextView txtDay = (TextView)findViewById(R.id.txtDay);
        TextView txtCondition = (TextView) findViewById(R.id.txtCondition);
        TextView txtTempCelcius = (TextView) findViewById(R.id.txtTempCelcius);
        TextView txtTempMin = (TextView) findViewById(R.id.txtTempMin);
        TextView txtTempMax = (TextView) findViewById(R.id.txtTempMax);
        TextView txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        TextView txtWindCondition = (TextView) findViewById(R.id.txtWindCondition);
        TextView txtFemaleRecommendation = (TextView) findViewById(R.id.txtFemaleRecommendation);
        TextView txtMaleRecommendation = (TextView) findViewById(R.id.txtMaleRecommendation);
        
        txtDay.setText(R.string.today);
        
        txtCondition.setText(getString(R.string.condition) + " " + weatherData.getCondition());
        txtTempCelcius.setText(getString(R.string.temperature) + " " + weatherData.getTempCelcius().toString() + "°C");
        txtTempMin.setText(getString(R.string.mintemp) + " " + weatherData.getTempMinCelsius().toString() + "°C");
        txtTempMax.setText(getString(R.string.maxtemp) + " " + weatherData.getTempMaxCelsius().toString() + "°C");
        txtHumidity.setText(weatherData.getHumidity());
        txtWindCondition.setText(weatherData.getWindCondition());
        
        txtFemaleRecommendation.setText("Women: Wear nothing");
        txtMaleRecommendation.setText("Men: Wear lots of clothes");
	 }
}
