package com.phoenix.rat.helper;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dahlia on 2/25/17.
 */
public class AsyncHttp extends AsyncTask<Void, Void, String> {

    public static AsyncHttp newInstance(String url, ResponseListener responseListener){
        return new AsyncHttp(url, responseListener);
    }


    private AsyncHttp(String url, ResponseListener responseListener){
        this.req_url = url;
        this.responseListener = responseListener;
    }

    String req_url = "";
    ResponseListener responseListener = null;


    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(req_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                Log.d("null", "zddzndhsrhsrhrh");
                return null;
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    protected void onPostExecute(String page)
    {
        if (responseListener != null){
            if (page != null){
                responseListener.onResponse(page);
            }else{
                responseListener.onError();
            }
        }
    }


    public interface ResponseListener{
        void onResponse(String res);
        void onError();
    }


    public void run(){
        execute();
    }
}