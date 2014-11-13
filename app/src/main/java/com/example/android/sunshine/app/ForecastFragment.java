package com.example.android.sunshine.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ralph on 11/12/14.
 */

public class ForecastFragment extends Fragment {
    private static String TAG="com.example.android.sunshine.app.ForecastFragment";
    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.forecastfragment, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_refresh) {
            new FetchWeatherTask().execute("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

/*
        ArrayList<String> values = new ArrayList(Arrays.asList("Today-Sunny - 88/63", "tomorrow 10", "next day- rain"));
        Log.i(TAG,"QQQ: values: " + values);
        ArrayAdapter mAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_forecast, R.id.list_item_forecast_textview, values);
        Log.i(TAG,"QQQ: mAdapter: " + mAdapter);
        ListView lv = (ListView)rootView.findViewById(R.id.listview_forecast);
        Log.i(TAG,"QQQ: lv: " + lv);
        lv.setAdapter(mAdapter);
*/
        new FetchWeatherTask().execute("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");

        return rootView;
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... strings) {
            int count = strings.length;
            String forecastJsonStr = null;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                //publishProgress((int) ((i / (float) count) * 100));
                // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

// Will contain the raw JSON response as a string.


                try {
                    // Construct the URL for the OpenWeatherMap query
                    // Possible parameters are avaiable at OWM's forecast API page, at
                    // http://openweathermap.org/API#forecast
                    //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
                    Log.d(TAG,"QQQ: link: " + strings[i]);
                    URL url = new URL(strings[i]);

                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        forecastJsonStr = null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        forecastJsonStr = null;
                    }
                    forecastJsonStr = buffer.toString();
                } catch (IOException e) {
                    Log.e("com.example.android.sunshine.app.ForecastFragment", "Error ", e);
                    // If the code didn't successfully get the weather data, there's no point in attemping
                    // to parse it.
                    forecastJsonStr = null;
                } finally{
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(TAG, "QQQ: Error closing stream", e);
                        }
                    }
                }




                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return forecastJsonStr;
        }
/*
      protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                totalSize += Downloader.downloadFile(urls[i]);
                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return totalSize;
        }
*/
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            //showDialog("Downloaded " + result + " bytes");
            Log.d(TAG, "QQQ: result: " + result);
/*
            ArrayList<String> values = new ArrayList(Arrays.asList("Today-Sunny - 88/63", "tomorrow 10", "next day- rain"));
            Log.i(TAG,"QQQ: values: " + values);
            ArrayAdapter mAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.list_item_forecast, R.id.list_item_forecast_textview, values);
            Log.i(TAG,"QQQ: mAdapter: " + mAdapter);
            ListView lv = (ListView)rootView.findViewById(R.id.listview_forecast);
            Log.i(TAG,"QQQ: lv: " + lv);
            lv.setAdapter(mAdapter);
            */
        }
    }
}
