package com.example.wagh.dynamic;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wagh on 18/10/17.
 */

public class Image extends AppCompatActivity {


    ImageView imageView;
    Button selectImage,uploadImage;
    Bitmap bitmap;
    int IMAGE_SELECT=111;
    String URL="";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);

        imageView=(ImageView)findViewById(R.id.imageView);
        selectImage=(Button)findViewById(R.id.selectImageButton);
        uploadImage=(Button)findViewById(R.id.convertpdfButton);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii= new Intent();
                ii.setType("image/*");
                ii.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(ii,"Select Chooser"),IMAGE_SELECT);
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog=new ProgressDialog(Image.this);
                progressDialog.setMessage("Uploading Image");
                progressDialog.show();
                    startTheCall();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==111 && data!=null && data.getData() !=null)
        {
            Uri filePath=data.getData();
            try
            {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(),"THIS IS THE FILE PATH :: "+filePath,Toast.LENGTH_LONG).show();
                Log.d("FILE PATH :: ",filePath.toString());

            }
            catch (Exception e)
            {
                Log.e("ISSUE IN BITMAP",e.getStackTrace().toString());
            }
        }
    }

    void startTheCall()
    {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes=baos.toByteArray();
        final String imageString= Base64.encodeToString(imageBytes,Base64.DEFAULT);

        StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"UPLOAD DONE :: "+response,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"ERROR IN COMMUNICATION :: "+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                data.put("image",imageString);
                return super.getParams();
            }
        };

        RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
        rq.add(request);
    }
}
