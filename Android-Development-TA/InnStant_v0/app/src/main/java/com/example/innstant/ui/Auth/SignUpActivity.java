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

import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    private static final int CAM_REQUEST=1313;
    private SignUpViewModel mViewModel;
    EditText firstname ;
    EditText lastname ;
    EditText idCardNumber;
    EditText phoneNumber;
    EditText emailAddress;
    EditText password;
    EditText pin;

    ImageView selfieImage;
    ImageView idCardImage;
    ImageView selfieWithIdCardImage;

    Button SignUp;
    Button selfie;
    Button idCard;
    Button selfieWithIdCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);


        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);


       firstname =(EditText) findViewById(R.id.firstName);
       lastname =(EditText) findViewById(R.id.lastName);
       idCardNumber=(EditText) findViewById(R.id.idCardNumber);
       phoneNumber =(EditText) findViewById(R.id.phoneNumber);
       emailAddress =(EditText) findViewById(R.id.email);
       password = (EditText) findViewById(R.id.password);
       pin =(EditText) findViewById(R.id.pin);
       SignUp = (Button) findViewById(R.id.next);
       selfieImage=(ImageView) findViewById(R.id.selfieImage);
       idCardImage=(ImageView) findViewById(R.id.idCardImage);
       selfieWithIdCardImage=(ImageView) findViewById(R.id.selfieWithIDCardImage);
       selfie = (Button) findViewById(R.id.selfie);
       idCard= (Button) findViewById(R.id.idCard);
       selfieWithIdCard = (Button) findViewById(R.id.selfieWithIDCard);


        selfie.setOnClickListener(new selfie());
        selfieWithIdCard.setOnClickListener(new selfieWithIdCard());
        idCard.setOnClickListener(new idCard());

       SignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               postData();
           }
       });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            selfieImage.setImageBitmap(bitmap);

        }else if(requestCode == 2){

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            selfieWithIdCardImage.setImageBitmap(bitmap);
        }if(requestCode == 3){

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            idCardImage.setImageBitmap(bitmap);

        }
    }

    class selfie implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,1);
        }
    }
    class selfieWithIdCard implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,2);
        }
    }
    class idCard implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,3);
        }
    }

    public void postData()  {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/users";
        User user = new User();
        user.setFirstName(firstname.getText().toString());
        user.setLastName(lastname.getText().toString());
        user.setIdCardNumber(idCardNumber.getText().toString());
        user.setEmail(emailAddress.getText().toString());
        user.setPassword(password.getText().toString());
        user.setPin(pin.getText().toString());
        String paramString = new GsonBuilder().create().toJson(user);
        try {
            JSONObject param = new JSONObject(paramString);

            JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(SignUpActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // Toast.makeText(SignUpActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                            Intent intent =new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
            ){
                //here I want to post data to sever
            };
            requstQueue.add(jsonobj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
