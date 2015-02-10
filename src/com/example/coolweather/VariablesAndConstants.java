package com.example.coolweather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;

public class VariablesAndConstants {

	public String myIcon;
	public long myTime;
	public double myTemperature;
	public double myHumidity;
	public double myPrecipChance;
	public String mySummary;
	public String timeZone;

	@SuppressLint("SimpleDateFormat")
	public String getFormattedTime() {

		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = new Date(myTime * 1000);
		String formattedTime = formatter.format(date);

		return formattedTime;

	}

	public long getMyTemperature() {
		long t = 0;
		final double n = 0.55555;
		t = (long) (n * (myTemperature - 32.00));
		return t;
	}

	public int getId() {
		int iconId = R.drawable.clear_day;
		if (myIcon.equals("clear-day")) {
			iconId = R.drawable.clear_day;
		} else if (myIcon.equals("clear-night")) {
			iconId = R.drawable.clear_night;
		} else if (myIcon.equals("rain")) {
			iconId = R.drawable.rain;
		} else if (myIcon.equals("snow")) {
			iconId = R.drawable.snow;
		} else if (myIcon.equals("sleet")) {
			iconId = R.drawable.sleet;
		} else if (myIcon.equals("wind")) {
			iconId = R.drawable.wind;
		} else if (myIcon.equals("fog")) {
			iconId = R.drawable.fog;
		} else if (myIcon.equals("cloudy")) {
			iconId = R.drawable.cloudy;
		} else if (myIcon.equals("partly-cloudy-day")) {
			iconId = R.drawable.partly_cloudy;
		} else if (myIcon.equals("partly-cloudy-night")) {
			iconId = R.drawable.cloudy_night;
		}

		return iconId;
	}

}
