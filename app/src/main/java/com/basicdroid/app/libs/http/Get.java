package com.basicdroid.app.libs.http;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2/10/2016.
 */
public class Get {
    private static final String USER_AGENT = "Mozilla/5.0";

    public static class PostBuilder {
        private String url;
        private GettingResponseListener listener;

        public String getUrl() {
            return url;
        }

        public PostBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public GettingResponseListener getListener() {
            return listener;
        }

        public PostBuilder setListener(GettingResponseListener listener) {
            this.listener = listener;
            return this;
        }
    }

    // HTTP GET request
    private static String sendGet(final PostBuilder restBuilder) {

        String response_str = "";

        try {
            URL obj = new URL(restBuilder.getUrl());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\n Sending 'GET' request to URL : " + restBuilder.getUrl());
            System.out.println("\n Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            response_str = response.toString();

            System.out.println("\n response_str : " + response_str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response_str;

    }

    public static void sendGetAync(final PostBuilder restBuilder) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {

                String response_str = sendGet(restBuilder);
                return response_str;
            }

            @Override
            protected void onPostExecute(String response_str) {
                super.onPostExecute(response_str);

                if (restBuilder.getListener() != null) {
                    restBuilder.getListener().onGettingResponse(response_str);
                }
            }
        }.execute();

    }


    public interface GettingResponseListener {
        void onGettingResponse(String response);
    }
}
