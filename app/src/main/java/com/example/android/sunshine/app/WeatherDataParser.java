package com.example.android.sunshine.app;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ralph on 11/14/14.
 */
public class WeatherDataParser {
    private static String TAG = WeatherDataParser.class.getSimpleName();
    /**
     * Given a string of the form returned by the api call:
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
     * retrieve the maximum temperature for the day indicated by dayIndex
     * (Note: 0-indexed, so 0 would refer to the first day).
     */
    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex)
            throws JSONException {
        // TODO: add parsing code here
        JSONObject jObject = new JSONObject(weatherJsonStr);
        JSONArray jArray = jObject.getJSONArray("list");

        if (dayIndex < jArray.length()){
            return jArray.getJSONObject(dayIndex).getJSONObject("temp").getDouble("max");
        }
        // debug
        for (int i=0; i < jArray.length(); i++) {
                JSONObject oneObject = jArray.getJSONObject(i);
            Log.d(TAG,"QQQ: oneobject: " + oneObject);
        }

        return -1;
    }

}
