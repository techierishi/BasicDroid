package in.thelattice.gluconnect.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.thelattice.gluconnect.R;
import in.thelattice.gluconnect.adapters.PatientInsulinListAdapter;
import in.thelattice.gluconnect.libs.debug.DBG;
import in.thelattice.gluconnect.libs.icenet.Body;
import in.thelattice.gluconnect.libs.icenet.Header;
import in.thelattice.gluconnect.libs.icenet.IceNet;
import in.thelattice.gluconnect.libs.icenet.RequestCallback;
import in.thelattice.gluconnect.libs.icenet.RequestError;
import in.thelattice.gluconnect.models.PatientInsulinRowItem;
import in.thelattice.gluconnect.models.User;
import io.paperdb.Paper;

public class PatientInsulinMonitoringActivity extends BaseController implements AdapterView.OnItemClickListener, RequestCallback {

    String[] time = {"6:30 PM", "9:30 PM", "10:30 PM"};
    String[] field1 = {"235 mg/dL", "235 mg/dL", "235 mg/dL"};
    String[] field2 = {"4 Units", "4 Units", "4 Units"};
    String[] field3 = {"Q1H", "Q1H", "Q1H"};
    String[] field4 = {"Oral Feed", "Oral Feed", "Oral Feed"};
    String[] field5 = {"Regular", "Regular", "Regular"};
    String[] date = {"20th October, 2015", "20th October, 2015", "20th October, 2015"};

    @Bind(R.id.llPatientsInsulin)
    ListView listView;
    PatientInsulinListAdapter mAdapter;

    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientinsulinmonitoring);
        mContext = PatientInsulinMonitoringActivity.this;
        ButterKnife.bind(this);
        String patientName = getIntent().getStringExtra("name");
        setTitle(patientName);

        inits();
        patientDetailRequest();
    }

    private void inits() {
        List<PatientInsulinRowItem> rowItems;
        rowItems = new ArrayList<PatientInsulinRowItem>();

        // dummyData(rowItems);
        mAdapter = new PatientInsulinListAdapter(this, rowItems);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
    }

    private void dummyData(List<PatientInsulinRowItem> rowItems) {
        for (int i = 0; i < time.length; i++) {
            PatientInsulinRowItem rowItem = new PatientInsulinRowItem();
            rowItem.setFeed_status(field1[i]);
            rowItem.setGlucose_reading(field2[i]);
            rowItem.setTime_of_glucose_reading(field3[i]);


            rowItems.add(rowItem);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private void patientDetailRequest() {


        Body.Builder builder = new Body.Builder();
        Body bodyRequest = new Body(builder);

        String params = "&values={\"patient_id\":\"2\",\"user_category_id\":\"1\"}";

        Header header = new Header(new Header.Builder().add("Content-Type", "application/x-www-form-urlencoded"));
        IceNet.connect()
                .createRequest()
                .post(header, bodyRequest)
                .pathUrl(REST_API.GET_PATIENT_RECORDS + params)
                .fromString()
                .execute(REST_API.GET_PATIENT_RECORDS, PatientInsulinMonitoringActivity.this);

    }

    @Override
    public void onRequestSuccess(Object o) {

        DBG.d("PatientInsulinMonitoringActivity", "" + ((String) o));




        try {
            JSONObject jsonObject = new JSONObject(((String) o));


            try {

                JSONArray successObj = jsonObject.getJSONArray("success");
                Gson gson = new Gson();

                Type type = new TypeToken<List<PatientInsulinRowItem>>() {
                }.getType();

                List<PatientInsulinRowItem> patientInsulinRowItems = gson.fromJson(successObj.toString(), type);
                mAdapter.changeData(patientInsulinRowItems);

            } catch (JSONException ex) {
                DBG.printStackTrace(ex);
                String errorStr = jsonObject.getString("error");
                Toast.makeText(mContext, "" + errorStr, Toast.LENGTH_LONG).show();


            }


        } catch (JSONException ex) {
            DBG.printStackTrace(ex);
        }


    }

    @Override
    public void onRequestError(RequestError error) {

    }
}
