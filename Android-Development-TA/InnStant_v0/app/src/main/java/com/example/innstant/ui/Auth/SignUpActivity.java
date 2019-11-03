package com.example.innstant.ui.Auth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.innstant.R;
import com.example.innstant.data.AppHelper;
import com.example.innstant.data.PreferenceHelper;
import com.example.innstant.data.VolleyMultipartRequest;
import com.example.innstant.data.model.Room;
import com.example.innstant.data.model.User;
import com.example.innstant.ui.HostRoom.AddPictureActivity;
import com.example.innstant.viewmodel.SignUpViewModel;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);


        selfie.setOnClickListener(new selfie());
        selfieWithIDCard.setOnClickListener(new selfieWithIdCard());
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
            PostGambar(selfieImage.getDrawable(),"profile");

        } else if (requestCode == 2) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            selfieWithIDCardImage.setImageBitmap(bitmap);
            PostGambar(selfieWithIDCardImage.getDrawable(),"user_with_id_card");
        }
        if (requestCode == 3) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            idCardImage.setImageBitmap(bitmap);
            PostGambar(idCardImage.getDrawable(),"id_card");

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
        ArrayList<Room> a =new ArrayList<Room>();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/users";
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setIdCardNumber(idCardNumber.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setPin(pin.getText().toString());
        user.setRooms(a);
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

    private void PostGambar(Drawable drawable, String targetLocation) {
        if (drawable != null) {
            mViewModel.openServerConnection();
            RequestQueue requstQueue = Volley.newRequestQueue(this);
            String url = PreferenceHelper.getBaseUrl() + "/photos/upload_photo";
            VolleyMultipartRequest jsonobj = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {

                        @Override
                        public void onResponse(NetworkResponse response) {
                            Map<String, String> responseHeaders = response.headers;
                            try {
                                JSONObject json = new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
                                if(targetLocation == "profile"){
                                    user.setProfilePhoto(json.get("fileName").toString());
                                }else if( targetLocation == "id_card"){
                                    user.setIdCardPhoto(json.get("fileName").toString());
                                }else if( targetLocation == "user_with_id_card"){
                                    user.setUserWithIdCardPhoto(json.get("fileName").toString());
                                }



                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(AddPictureActivity.this, "gagal  Edit   :" + error.toString(), Toast.LENGTH_LONG).show();
                            Log.d("RESPONBRO", error.toString());

                        }

                    }

            ) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("save_directory", targetLocation);
                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    // file name could found file base or direct access from real path
                    // for now just get bitmap data from ImageView
                    params.put("file", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(),drawable), "image/jpeg"));
//                    params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));

                    return params;
                }
            };
            requstQueue.add(jsonobj);
        }
    }
}
