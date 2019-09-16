package com.example.innstant.ui.Auth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.innstant.R;
import com.example.innstant.data.PreferenceHelper;
import com.example.innstant.data.model.User;
import com.example.innstant.viewmodel.SignUpViewModel;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    private static final int CAM_REQUEST = 1313;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.idCardNumber)
    EditText idCardNumber;
    @BindView(R.id.phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.pin)
    EditText pin;
    @BindView(R.id.selfieImage)
    ImageView selfieImage;
    @BindView(R.id.selfie)
    Button selfie;
    @BindView(R.id.idCardImage)
    ImageView idCardImage;
    @BindView(R.id.idCard)
    Button idCard;
    @BindView(R.id.selfieWithIDCardImage)
    ImageView selfieWithIDCardImage;
    @BindView(R.id.selfieWithIDCard)
    Button selfieWithIDCard;
    @BindView(R.id.next)
    Button next;
    private SignUpViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);


        selfie.setOnClickListener(new selfie());
        selfieImage.setOnClickListener(new selfieWithIdCard());
        idCard.setOnClickListener(new idCard());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            selfieImage.setImageBitmap(bitmap);

        } else if (requestCode == 2) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            selfieImage.setImageBitmap(bitmap);
        }
        if (requestCode == 3) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            idCardImage.setImageBitmap(bitmap);

        }
    }

    class selfie implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }
    }

    class selfieWithIdCard implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 2);
        }
    }

    class idCard implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 3);
        }
    }

    public void postData() {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/users";
        User user = new User();
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setIdCardNumber(idCardNumber.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setPin(pin.getText().toString());
        String paramString = new GsonBuilder().create().toJson(user);
        try {
            JSONObject param = new JSONObject(paramString);

            JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url, param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(SignUpActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Toast.makeText(SignUpActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
            ) {
                //here I want to post data to sever
            };
            requstQueue.add(jsonobj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
