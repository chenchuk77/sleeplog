package net.devopskb.sleeplog;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chen on 1/22/17.
 */

public class RetrieveFeedTask extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... urls) {

        try {
            //URL url = new URL("http://84.111.154.20:5000/sleepws/ping");
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally{
                urlConnection.disconnect();
            }
        } catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String resp) {
        // TODO: check this.exception
        // TODO: do something with the feed
        Log.d("ERROR", "DBG - resp received: " + resp);
//        Toast.makeText(getApplicationContext(), "This is my Toast message!",
//                Toast.LENGTH_LONG).show();
//        //final Button btnSleep = (Button)findViewById(R.id.btn_sleep);
//        showDialog("Downloaded " + result + " bytes");

    }
}