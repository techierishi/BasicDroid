package com.basicdroid.app.libs.http;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2/10/2016.
 */
public class Post {
    private static final String USER_AGENT = "Mozilla/5.0";

    public static class PostBuilder {
        private String url;
        private Map<String, String> postValues;
        private GettingResponseListener listener;

        public String getUrl() {
            return url;
        }

        public PostBuilder setUrl(String url) {
            this.url = url;

            return this;
        }

        public Map getPostValues() {
            return postValues;
        }

        public PostBuilder setPostValues(Map<String, String> postValues) {
            this.postValues = postValues;
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

    public static String sendPost(final PostBuilder restBuilder) {

        String response_str = "";
        // Making HTTP request
        try {

            URL url = new URL("" + restBuilder.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("User-Agent", USER_AGENT);

            //Post Data
            Map<String, String> nameValuePair = restBuilder.getPostValues();

            System.out.println("\n Sending 'GET' request to URL : " + restBuilder.url);
            System.out.println("\n Response Code : " + nameValuePair);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(nameValuePair));

            writer.flush();
            writer.close();
            os.close();

            conn.connect();


            try {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                is.close();

                response_str = sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error converting result " + e.toString());
            }


            conn.disconnect();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(" response_str " + response_str);

        return response_str;
    }

    public static void sendPostAync(final PostBuilder restBuilder) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {

                String response_str = sendPost(restBuilder);
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


    private static String getQuery(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode("" + pair.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode("" + pair.getValue(), "UTF-8"));
            it.remove(); // avoids a ConcurrentModificationException
        }

        return result.toString();
    }

    public interface GettingResponseListener {
        void onGettingResponse(String response);
    }
}
