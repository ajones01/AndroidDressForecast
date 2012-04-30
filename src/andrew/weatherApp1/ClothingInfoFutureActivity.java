package andrew.weatherApp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClothingInfoFutureActivity extends Activity 
{
	 public void onCreate(Bundle savedInstanceState) 
	 {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.clothinginfofuture);

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
	        TextView txtTempMin = (TextView) findViewById(R.id.txtTempMin);
	        TextView txtTempMax = (TextView) findViewById(R.id.txtTempMax);
	        TextView txtFemaleRecommendation = (TextView) findViewById(R.id.txtFemaleRecommendation);
	        TextView txtMaleRecommendation = (TextView) findViewById(R.id.txtMaleRecommendation);
	        
	        txtDay.setText(weatherData.getDayofWeek());
	        
	        txtCondition.setText(getString(R.string.condition) + " " + weatherData.getCondition());
	        txtTempMin.setText(getString(R.string.mintemp) + " " + weatherData.getTempMinCelsius().toString() + "°C");
	        txtTempMax.setText(getString(R.string.maxtemp) + " " + weatherData.getTempMaxCelsius().toString() + "°C");
	         
	        txtFemaleRecommendation.setText("Women: Wear nothing");
	        txtMaleRecommendation.setText("Men: Wear lots of clothes");
	 }
}
