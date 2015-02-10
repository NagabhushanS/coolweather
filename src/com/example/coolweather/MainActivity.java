package com.example.coolweather;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText latitude, longitude;
	Button forecast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		latitude = (EditText) findViewById(R.id.editText1);
		longitude = (EditText) findViewById(R.id.editText2);
		forecast = (Button) findViewById(R.id.forecastButton);

		forecast.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String s1 = null;
				s1 = latitude.getText().toString();
				String s2 = null;
				s2 = longitude.getText().toString();
				Double latitudeValue = 0.0;
				Log.d("Hello", "" + s1 + "  " + s2);

				if (!s1.equals("") && !s2.equals("")) {
					try {
						latitudeValue = Double.parseDouble(s1);
						Double longitudeValue = 0.0;
						longitudeValue = Double.parseDouble(s2);
						Intent i = new Intent(MainActivity.this,
								ForecastActivity.class);
						i.putExtra("latitude", latitudeValue);
						i.putExtra("longitude", longitudeValue);
						startActivity(i);
					} catch (Exception e) {

						AlertDialog.Builder builder = new AlertDialog.Builder(
								MainActivity.this);
						builder.setMessage("Invalid latitude or longitude!");
						builder.setTitle("Oops Sorry!");
						builder.setPositiveButton(android.R.string.ok, null);
						AlertDialog dialog = builder.create();
						dialog.show();
					}
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setMessage("Invalid latitude or longitude!");
					builder.setTitle("Oops Sorry!");
					builder.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();

				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_one, menu);
		return true;
	}

}
