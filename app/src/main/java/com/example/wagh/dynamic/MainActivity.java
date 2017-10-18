package com.example.wagh.dynamic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    LinearLayout layout;
    int i=0;
    int inside=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layout=(LinearLayout)findViewById(R.id.ourLayout);


        Button br=(Button)findViewById(R.id.buttonqw);

        Button br2=(Button)findViewById(R.id.callmeNextActivity);

        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i++;
                checkAll(i);
            }
        });

        br.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                layout.removeAllViews();

                return true;
            }
        });

        br2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    Intent ii=new Intent(MainActivity.this,Image.class);
                    Toast.makeText(getApplicationContext(),"Next Page",Toast.LENGTH_LONG).show();
                    startActivity(ii);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });



    }


    void checkAll(int i)
    {

        TextView tv;
        tv=new TextView(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText("Textview :: "+i);
        tv.setId(i);
        layout.addView(tv);

        EditText et;
        et=new EditText(this);
        et.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        et.setText("Edittext:: "+i);
        et.setId(i);
        layout.addView(et);

        Button br;
        br=new Button(this);
        br.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        br.setText("Button :: "+i);
        br.setId(i);
        layout.addView(br);

        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inside++;
                inside(inside);

            }
        });

        final Button br2;
        br2=new Button(this);
        br2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        br2.setText("Remove :: "+i);
        br2.setId(i);
        layout.addView(br2);

        br2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete(br2.getId());

            }
        });


    }

    void inside(int inside)
    {
        Button br;
        br=new Button(getApplicationContext());
        br.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        br.setText("Add More :: "+inside);
        br.setId(inside);
        layout.addView(br);
    }

    void delete(int i)
    {
        Toast.makeText(getApplicationContext(),"I m Removed"+findViewById(i).getId(),Toast.LENGTH_LONG).show();
        layout.removeView(findViewById(i));
    }
}
