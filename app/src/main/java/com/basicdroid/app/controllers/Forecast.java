package com.basicdroid.app.controllers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.basicdroid.app.R;
import com.basicdroid.app.adapters.CustomAdapter;
import com.basicdroid.app.libs.http.MultipartRequest;
import com.basicdroid.app.libs.http.RequestUtil;
import com.basicdroid.app.libs.http.VolleySingleton;
import com.basicdroid.app.models.RowItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Forecast extends Activity {

    String forecast_url = "http://api.openweathermap.org/data/2.5/forecast?&appid=cd50922557b3102188ce5521a3f42866&id=";

    ProgressDialog PD;

    ListView lv;
    CustomAdapter adapter;
    List<RowItem> rowItems = new ArrayList<RowItem>();

    String place_id, place_name;

    JSONObject mJsonObj;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast);
        lv = (ListView) findViewById(R.id.flv);
        Intent i = getIntent();
        place_id = i.getExtras().getString("id");
        place_name = i.getExtras().getString("name");

        //getActionBar().setSubtitle(place_name);

        PD = new ProgressDialog(Forecast.this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        String full_url = forecast_url + place_id;

        adapter = new CustomAdapter(getApplicationContext(), rowItems);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        "Id: " + place_id + "Date: "
                                + rowItems.get(position).getMdate(),
                        Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), Interval.class);

                i.putExtra("placeid", place_id);
                i.putExtra("name", place_name);

                i.putExtra("mDate", rowItems.get(position).getMdate());
                i.putExtra("jo", mJsonObj.toString());

                startActivity(i);

            }
        });

        makejsonreq(full_url);

    }

    public void makejsonreq(String url) {

        PD.show();
        JsonObjectRequest jsonObjReqq = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                mJsonObj = response;
                try {
                    JSONArray jlist = response.getJSONArray("list");

                    String dt = null;
                    String pre_dt = null;

                    for (int i = 0; i < jlist.length(); i++) {
                        dt = jlist.getJSONObject(i).getString("dt_txt")
                                .substring(8, 10);

                        if (i > 1 || i == 1) {
                            pre_dt = jlist.getJSONObject(i - 1)
                                    .getString("dt_txt")
                                    .substring(8, 10);

                            if (!(dt.equals(pre_dt))) {

                                String mdate = jlist.getJSONObject(i)
                                        .getString("dt_txt")
                                        .substring(0, 10);

                                JSONObject main = jlist
                                        .getJSONObject(i)
                                        .getJSONObject("main");

                                int temp, temp_max, temp_min;

                                temp = (int) (main.getDouble("temp") - 273.15);
                                temp_max = (int) (main
                                        .getDouble("temp_max") - 273.15);
                                temp_min = (int) (main
                                        .getDouble("temp_min") - 273.15);

                                JSONArray weather = jlist
                                        .getJSONObject(i).getJSONArray(
                                                "weather");

                                String icon = weather.getJSONObject(0)
                                        .getString("icon");
                                String description = weather
                                        .getJSONObject(0).getString(
                                                "description");

                                RowItem rItem = new RowItem(temp,
                                        temp_max, temp_min,
                                        description, icon, mdate);

                                rowItems.add(rItem);
                            } // if end
                        } // if end
                    } // for end

                } catch (JSONException e) {
                    e.printStackTrace();
                    PD.dismiss();
                }
                adapter.notifyDataSetChanged();
                PD.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReqq);

    }


    /**
     * Example function
     *
     * @param context
     */
    public void saveFilesToServer(final Context context) {
        String url = "http://192.168.1.38/upload/upload.php";


        Map<String, String> stringParams = new HashMap<>();
        stringParams.put("title", "Foo");
        stringParams.put("description", "Bar");
        List<MultipartRequest.FileParam> fileParams = new ArrayList<>();

        fileParams.add(new MultipartRequest.FileParam("attachment", RequestUtil.getFileName("file//filepath"), RequestUtil.readFile("file//filepath")));


        MultipartRequest multipartRequest = new MultipartRequest(url, null, stringParams, fileParams, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Upload successfully! " + response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Upload failed!\r\n" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(multipartRequest);
    }

}
