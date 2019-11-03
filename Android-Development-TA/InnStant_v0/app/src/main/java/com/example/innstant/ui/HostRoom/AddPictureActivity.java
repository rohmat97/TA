package com.example.innstant.ui.HostRoom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
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
import com.example.innstant.viewmodel.AddPictureViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPictureActivity extends AppCompatActivity {
    @BindView(R.id.textGambar1)
    TextView textGambar1;
    @BindView(R.id.textGambar2)
    TextView textGambar2;
    @BindView(R.id.gambar1)
    ImageView gambar1;
    @BindView(R.id.gambar2)
    ImageView gambar2;
    @BindView(R.id.textGambar3)
    TextView textGambar3;
    @BindView(R.id.textGambar4)
    TextView textGambar4;
    @BindView(R.id.gambar3)
    ImageView gambar3;
    @BindView(R.id.gambar4)
    ImageView gambar4;
    @BindView(R.id.textGambar5)
    TextView textGambar5;
    @BindView(R.id.textGambar6)
    TextView textGambar6;
    @BindView(R.id.gambar5)
    ImageView gambar5;
    @BindView(R.id.gambar6)
    ImageView gambar6;
    @BindView(R.id.nextsetpricing)
    Button nextsetpricing;
    private AddPictureViewModel mViewModel;
    Room room = new Room();

    ArrayList<String> listImage =new ArrayList<String>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);

        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("dataRoom");
        String json1 = bundle.getString("email");
        Gson gson = new Gson();
        room = gson.fromJson(json, Room.class);

        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(AddPictureActivity.this).get(AddPictureViewModel.class);

        nextsetpricing = (Button) findViewById(R.id.nextsetpricing);
        gambar1 = (ImageView) findViewById(R.id.gambar1);
        gambar2 = (ImageView) findViewById(R.id.gambar2);
        gambar3 = (ImageView) findViewById(R.id.gambar3);
        gambar4 = (ImageView) findViewById(R.id.gambar4);
        gambar5 = (ImageView) findViewById(R.id.gambar5);
        gambar6 = (ImageView) findViewById(R.id.gambar6);
        textGambar1 = (TextView) findViewById(R.id.textGambar1);
        textGambar2 = (TextView) findViewById(R.id.textGambar2);
        textGambar3 = (TextView) findViewById(R.id.textGambar3);
        textGambar4 = (TextView) findViewById(R.id.textGambar4);
        textGambar5 = (TextView) findViewById(R.id.textGambar5);
        textGambar6 = (TextView) findViewById(R.id.textGambar6);
        gambar1.setOnClickListener(new gambar1());
        gambar2.setOnClickListener(new gambar2());
        gambar3.setOnClickListener(new gambar3());
        gambar4.setOnClickListener(new gambar4());
        gambar5.setOnClickListener(new gambar5());
        gambar6.setOnClickListener(new gambar6());

        nextsetpricing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPictureActivity.this, SetRoomPricingActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(room);
                intent.putExtra("dataRoom", json);
                intent.putExtra("email", json1);
                startActivity(intent);
            }
        });
    }


    class gambar1 implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }
    }

    class gambar2 implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 2);
        }
    }

    class gambar3 implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 3);
        }
    }

    class gambar4 implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 4);
        }
    }

    class gambar5 implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 5);
        }
    }

    class gambar6 implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 6);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            gambar1.setImageBitmap(bitmap);
            Log.e("test", "onActivityResult: " + gambar1.getTag());

            PostData(gambar1.getDrawable());
        } else if (requestCode == 2) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            gambar2.setImageBitmap(bitmap);
            PostData(gambar2.getDrawable());
        } else if (requestCode == 3) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            gambar3.setImageBitmap(bitmap);
            PostData(gambar3.getDrawable());
        } else if (requestCode == 4) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            gambar4.setImageBitmap(bitmap);
            PostData(gambar4.getDrawable());
        } else if (requestCode == 5) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            gambar5.setImageBitmap(bitmap);
            PostData(gambar5.getDrawable());
        } else if (requestCode == 6) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            gambar6.setImageBitmap(bitmap);
            PostData(gambar6.getDrawable());

        }
    }

    private void PostData(Drawable drawable) {
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
                                listImage.add(json.get("fileName").toString());
                                if(listImage.size()>5){
                                    room.setPhotos(listImage);
                                }
                                Log.d("RESPONBRO", listImage.toString());
                                Log.d("DATA ROOM", room.toString());
                                Log.d("SIZE", String.valueOf(listImage.size()));


                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddPictureActivity.this, "gagal  Edit   :" + error.toString(), Toast.LENGTH_LONG).show();
                            Log.d("RESPONBRO", error.toString());

                        }

                    }

            ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("save_directory", "room");
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
