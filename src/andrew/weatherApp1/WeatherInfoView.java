package andrew.weatherApp1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import andrew.weatherApp1.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WeatherInfoView extends LinearLayout 
{
	// ===========================================================
	// Fields
	// ===========================================================

	private ImageView _weatherImageView = null;
	private LinearLayout _linearLayout = null;
	private TextView _dayView = null;
	private TextView _minMaxTextView = null;
	private TextView _conditionTextView = null;
		
	// ===========================================================
	// Constructors
	// ===========================================================

	public WeatherInfoView(Context context) 
	{
		super(context);
	}
	
	public WeatherInfoView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		/* Setup the ImageView that will show weather-icon. */
		_weatherImageView = new ImageView(context);
		_weatherImageView.setImageDrawable(getResources().getDrawable(R.drawable.dunno));

		_linearLayout = new LinearLayout(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(5, 5, 5, 5);
		_linearLayout.setOrientation(LinearLayout.VERTICAL);
		
		/* Setup the textView that will show the day */
		_dayView = new TextView(context);
		_dayView.setText("");
		_dayView.setTextSize(14);
		_dayView.setTypeface(Typeface.create("Tahoma", Typeface.BOLD));
		
		/* Setup the textView that will show the min/max temperature. */
		_minMaxTextView = new TextView(context);
		_minMaxTextView.setText("? °C");
		_minMaxTextView.setTextSize(12);
		_minMaxTextView.setTypeface(Typeface.create("Tahoma", Typeface.BOLD));
		
		/* Setup the textView that will show the day */
		_conditionTextView = new TextView(context);
		_conditionTextView.setText("");
		_conditionTextView.setTextSize(14);
		_conditionTextView.setTypeface(Typeface.create("Tahoma", Typeface.BOLD));

		/* Add child views to this object. */
		addView(_weatherImageView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
		addView(_linearLayout, layoutParams);
		_linearLayout.addView(_dayView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		_linearLayout.addView(_minMaxTextView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		_linearLayout.addView(_conditionTextView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		/*addView(_weatherImageView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addView(_dayView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addView(_minMaxTextView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addView(_conditionTextView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));*/
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void Reset() 
	{
		_weatherImageView.setImageDrawable(getResources().getDrawable(R.drawable.dunno));
		_minMaxTextView.setText("? °C");
	}

	/** Sets the Child-ImageView of this to the URL passed. */
	public void SetImage(Bitmap bm) 
	{
		_weatherImageView.setImageBitmap(bm);
	}

	public void SetDay(String day) 
	{
		_dayView.setText(day);
	}
	
	public void SetTempCelcius(int aTemp) 
	{
		_minMaxTextView.setText("" + aTemp + " °C");
	}
	
	public void SetCondition(String condition) 
	{
		_conditionTextView.setText(condition);
	}
	
	public void SetTempCelciusMinMax(int aMinTemp, int aMaxTemp) 
	{
		_minMaxTextView.setText("" + aMinTemp + "/" + aMaxTemp + " °C");
	}

	/*public void setTempFahrenheit(int aTemp) 
	{
		_minMaxTextView.setText("" + aTemp + " °F");
	}

	public void setTempFahrenheitMinMax(int aMinTemp, int aMaxTemp) 
	{
		_minMaxTextView.setText("" + aMinTemp + "/" + aMaxTemp + " °F");
	}*/
}
