package in.thelattice.gluconnect.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.thelattice.gluconnect.R;
import in.thelattice.gluconnect.adapters.PatientListAdapter;
import in.thelattice.gluconnect.libs.debug.DBG;
import in.thelattice.gluconnect.libs.icenet.Body;
import in.thelattice.gluconnect.libs.icenet.Header;
import in.thelattice.gluconnect.libs.icenet.IceNet;
import in.thelattice.gluconnect.libs.icenet.RequestCallback;
import in.thelattice.gluconnect.libs.icenet.RequestError;
import in.thelattice.gluconnect.models.PatientRowItem;
import in.thelattice.gluconnect.models.User;
import in.thelattice.gluconnect.util.CONST;
import io.paperdb.Paper;

public class HomeActivity extends BaseController
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener,RequestCallback {

    String[] dummyNames = {"John Doe", "Mary Jane", "Tony Stark"};
    String[] dummyWards = {"6/314G", "5/124G", "6/78SP"};
    int[] dummyAge = {50, 67, 23};
    char[] dummyGenders = {'M', 'F', 'M'};
    Context mContext;
    User mUser;
    private PatientListAdapter mAdapter;

    @Bind(R.id.llPatients) ListView listView;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.homeFab) FloatingActionButton fab;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mContext= HomeActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        mUser = Paper.book().read("user");
        Toast.makeText(mContext," Hello "+mUser.getUsername(),Toast.LENGTH_LONG).show();
        patientListRequest();

        inits(toolbar);
    }

    private void inits(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        fab.attachToListView(listView);
        List<PatientRowItem> rowItems;
        rowItems = new ArrayList<PatientRowItem>();
        getDummyData(rowItems);
        mAdapter = new PatientListAdapter(this, rowItems);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
    }

    private void getDummyData(List<PatientRowItem> rowItems) {
        for ( int i = 0; i < dummyNames.length ; i++)
        {
            PatientRowItem item = new PatientRowItem(dummyNames[i],dummyWards[i],dummyAge[i],dummyGenders[i]);
            rowItems.add(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PatientInsulinMonitoringActivity.class);
        intent.putExtra("name", dummyNames[position]);
        intent.putExtra("ward", dummyWards[position]);
        intent.putExtra("age", dummyAge[position]);
        intent.putExtra("gender", dummyGenders[position]);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Paper.book().delete("user");
            finish();

        } else if (id == R.id.nav_feedback) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void patientListRequest() {

        if (mUser != null) {
            Body.Builder builder = new Body.Builder();
            Body bodyRequest = new Body(builder);

            String params = "&values={\"user_id\":\"" + mUser.getUser_id() + "\",\"user_category_id\":\"1\"}";

            Header header = new Header(new Header.Builder().add("Content-Type", "application/x-www-form-urlencoded"));
            IceNet.connect()
                    .createRequest()
                    .post(header, bodyRequest)
                    .pathUrl(REST_API.GET_PATIENT_LIST + params)
                    .fromString()
                    .execute(REST_API.GET_PATIENT_LIST, HomeActivity.this);
        }
    }

    @Override
    public void onRequestSuccess(Object o) {

        DBG.d("HomeActivity", "" + ((String) o));
    }

    @Override
    public void onRequestError(RequestError error) {

    }
}
