package in.thelattice.gluconnect.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.channels.GatheringByteChannel;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.thelattice.gluconnect.R;
import in.thelattice.gluconnect.libs.debug.DBG;
import in.thelattice.gluconnect.libs.forms.Form;
import in.thelattice.gluconnect.libs.forms.SubmitHandler;
import in.thelattice.gluconnect.libs.forms.validators.EmailValidator;
import in.thelattice.gluconnect.libs.forms.validators.RequiredValidator;
import in.thelattice.gluconnect.libs.icenet.Body;
import in.thelattice.gluconnect.libs.icenet.Header;
import in.thelattice.gluconnect.libs.icenet.IceNet;
import in.thelattice.gluconnect.libs.icenet.RequestCallback;
import in.thelattice.gluconnect.libs.icenet.RequestError;
import in.thelattice.gluconnect.models.User;
import in.thelattice.gluconnect.util.CONST;
import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RequestCallback {


    @Bind(R.id.bLogin)
    Button bLogin;
    @Bind(R.id.etPassword)
    TextView etPassword;
    @Bind(R.id.etUsername)
    TextView etUsername;

    Context mContext;
    final String REQUEST_TAG = "LOGIN_REQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = LoginActivity.this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        TextView tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(this);

        etUsername.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.account), null, null, null);
        etPassword.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.key), null, null, null);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        User user = Paper.book().read("user");
        if(user !=null){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
        doLogin();
    }

    private void doLogin() {
        Form form = new Form((Activity) this);

        form.addFormElement(R.id.etUsername)
                .setName("usernametext")
                .addValidator(new RequiredValidator());

        form.addFormElement(R.id.etPassword)
                .setName("passwordtext")
                .addValidator(new RequiredValidator());

        form.addSubmit(R.id.bLogin, this)
                .setName("submit")
                .addSubmitHandler(new SubmitHandler() {
                    @Override
                    public void submit(Form form) {

                        loginRequest();

                    }
                });
    }

    private void loginRequest() {
        Body.Builder builder = new Body.Builder();
        Body bodyRequest = new Body(builder);

        String params = "&values={\"username\":\"" + etUsername.getText().toString() + "\",\"password\":\"" + etPassword.getText().toString() + "\"}";

        Header header = new Header(new Header.Builder().add("Content-Type", "application/x-www-form-urlencoded"));
        IceNet.connect()
                .createRequest()
                .post(header, bodyRequest)
                .pathUrl(CONST.REST_API.LOGIN_URL + params)
                .fromString()
                .execute(REQUEST_TAG, LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:

                break;
            case R.id.tvForgotPassword:
                break;
        }
    }

    @Override
    public void onRequestSuccess(Object o) {

        DBG.d("LoginActivity", "" + ((String) o));

        try {
            JSONObject jsonObject = new JSONObject(((String) o));


            try {
                JSONObject successObj = jsonObject.getJSONObject("success");
                Intent intent = new Intent(this, HomeActivity.class);

                User user = new User();
                user.setUser_id(""+successObj.getString("user_id"));
                user.setUser_category("" + successObj.getString("user_category"));
                user.setUser_category_id("" + successObj.getString("user_category_id"));
                user.setUsername("" + successObj.getString("username"));


                Paper.book().write("user", user); // Primitive


                startActivity(intent);
                finish();

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
        DBG.d("LoginActivity", "" + error.toString());
    }
}
