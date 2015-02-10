package com.example.coolweather;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class ForecastActivity extends ActionBarActivity {
	protected double latitude = 0.0;
	protected double longitude = 0.0;
	protected String responseString = null;

	public static final String API_KEY_WEATHER = "f1d9d98c775d8822550e4ecf20fbbda5";
	public static final String API_KEY_LOCATION = "Fmjtd%7Cluu821612g%2C2g%3Do5942096";
	public String forecastUrl = null;
	

	private VariablesAndConstants data;
	
	TextView city;

	TextView time;

	TextView humidity;

	TextView preciptation;

	TextView summary;

	TextView temperature;

	ImageView cloudImage;
	
	ProgressBar myProgressBar;
	
	ProgressBar mainProgressBar;
	
	ImageView refreshImage;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forecast);
		//temperature.setText("");
		define();
		initialize();
		myProgressBar.setVisibility(View.VISIBLE);
		refreshImage.setVisibility(View.INVISIBLE);

		refreshImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getWeatherForecast();
				
				
			}
		});

		
		getWeatherForecast();

	}

	
	private void initialize() {
		Intent i = getIntent();
		latitude = i.getDoubleExtra("latitude", 0.0);
		longitude = i.getDoubleExtra("longitude", 0.0);
		forecastUrl = "https://api.forecast.io/forecast/" + API_KEY_WEATHER
				+ "/" + latitude + "," + longitude;
		
	}


	private void getWeatherForecast() {
		mainProgressBar.setVisibility(View.VISIBLE);
		myProgressBar.setVisibility(View.VISIBLE);
	    refreshImage.setVisibility(View.INVISIBLE);
		
		
		if (isConnectionAvailableAndConnected()) {

			OkHttpClient foreCastClient = new OkHttpClient();

			Request myForeCastRequest = new Request.Builder().url(forecastUrl)
					.build();
			Call myCall = foreCastClient.newCall(myForeCastRequest);
			myCall.enqueue(new Callback() {
				

				@Override
				public void onResponse(Response response) {
					
					
					try {
						responseString = response.body().string();
						if (response.isSuccessful()) {

							data = getWeatherDetails(responseString);

							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									ForecastActivity.this.setViewRepresentation();
									myProgressBar.setVisibility(View.INVISIBLE);
									refreshImage.setVisibility(View.VISIBLE);
									mainProgressBar.setVisibility(View.INVISIBLE);

								}

							});
						} else {
							alert();
						}
					} catch (IOException e) {

					} catch (JSONException e) {

					}

				}

				@Override
				public void onFailure(Request request, IOException e) {

				}
			});
		} else {
			mainProgressBar.setVisibility(View.INVISIBLE);
			Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG).show();
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ForecastActivity.this);
			builder.setMessage("No active network available!");
			builder.setTitle("Oops Sorry!");
			builder.setPositiveButton(android.R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

	private void define() {
		city = (TextView) findViewById(R.id.cityLabel);

		time = (TextView) findViewById(R.id.timeLabel);

		humidity = (TextView) findViewById(R.id.humidity);

		preciptation = (TextView) findViewById(R.id.prcip);

		summary = (TextView) findViewById(R.id.summary);

		temperature = (TextView) findViewById(R.id.actualTemp);

		cloudImage = (ImageView) findViewById(R.id.imageIcon);
		
		myProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
		
		refreshImage = (ImageView) findViewById(R.id.refreshImageView);
		
		mainProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		
		
	}

	protected void setViewRepresentation() {
		temperature.setText("" + data.getMyTemperature() + "");
		humidity.setText((int)(data.myHumidity*100.0) + "%");
		preciptation.setText((int)(data.myPrecipChance * 100.0) + "%"); // percentage
		time.setText(data.getFormattedTime()+"");
		city.setText(data.timeZone + "");
		summary.setText(data.mySummary + "");
		cloudImage.setImageDrawable(getResources().getDrawable(data.getId()));

	}
	
	public void refreshRepresentation(){
		if(myProgressBar.getVisibility()==View.VISIBLE){
			myProgressBar.setVisibility(View.INVISIBLE);
			refreshImage.setVisibility(View.VISIBLE);
			
		}
		else{
			myProgressBar.setVisibility(View.VISIBLE);
			refreshImage.setVisibility(View.INVISIBLE);
			
		}
		
	}

	protected VariablesAndConstants getWeatherDetails(String jsonString)
			throws JSONException {
		VariablesAndConstants dataInMethod = new VariablesAndConstants();

		JSONObject parentJSONObject = new JSONObject(jsonString);
		String timeZone = parentJSONObject.getString("timezone");

		JSONObject currently = parentJSONObject.getJSONObject("currently");
		dataInMethod.myIcon = currently.getString("icon");
		dataInMethod.myPrecipChance = currently.getDouble("precipProbability");
		dataInMethod.mySummary = currently.getString("summary");
		dataInMethod.myTemperature = currently.getDouble("temperature");
		dataInMethod.myTime = currently.getLong("time");
		dataInMethod.myHumidity = currently.getDouble("humidity");
		dataInMethod.timeZone = timeZone;

		// Log.d("Time", dataInMethod.getFormattedTime());
		Log.d("dsdcs", jsonString);

		return dataInMethod;
	}

	private boolean isConnectionAvailableAndConnected() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info != null && info.isConnected()) {
			return true;
		}

		else
			return false;

	}

	protected void alert() {
		AlertDialogFragment dialog = new AlertDialogFragment();
		dialog.show(getFragmentManager(), "Alert Dialog Fragment");

	}
	
	

	
}
