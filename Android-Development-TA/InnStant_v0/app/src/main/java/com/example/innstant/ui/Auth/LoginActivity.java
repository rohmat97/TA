package com.example.innstant.ui.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.innstant.R;
import com.example.innstant.data.PreferenceHelper;
import com.example.innstant.ui.Admin.AdminActivity;
import com.example.innstant.ui.DashboardActivity;
import com.example.innstant.viewmodel.LoginViewModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.forgot)
    TextView forgot;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.signup)
    Button signup;
    @BindView(R.id.admin)
    Button admin;
    private LoginViewModel mViewModel;
    private String baseUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        baseUrl = PreferenceHelper.getBaseUrl() + "/users/authenticate";
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * This subclass handles the network operations in a new thread.
     * It starts the progress bar, makes the API call, and ends the progress bar.
     */


    /**
     * Open a new activity window.
     */
    private void goToSecondActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("email", username.getText().toString());
        startActivity(intent);
    }

    public void Login() {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
//                Toast.makeText(LoginActivity.this, "RESPONSE: " +response ,  Toast.LENGTH_SHORT ).show();
                goToSecondActivity();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "ERROR: " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            //here I want to post data to sever
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
//                String credentials = "dasukirohmat@gmail.com:secret";
                String credentials = username.getText()+":"+password.getText();
//                String credentials = "dasukirohmat@gmail.com:secret";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                Toast.makeText(LoginActivity.this, "ERROR: " + params.get("Authorization"), Toast.LENGTH_SHORT).show();
                //add params <key,value>
                return params;
            }
        };
        requstQueue.add(request);


    }
}
