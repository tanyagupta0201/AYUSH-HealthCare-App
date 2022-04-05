package com.example.ayushhealthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HospitalDetail extends AppCompatActivity {

    TextView hospital_name;
    TextView hospital_address;
    TextView time;
    TextView website_link;
    TextView hospital_distance;
    TextView facilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_info);

       hospital_name=findViewById(R.id.hospitalName);
       hospital_address=findViewById(R.id.hospital_address);
       time=findViewById(R.id.time);
       website_link=findViewById(R.id.website_link);
       hospital_distance=findViewById(R.id.hospital_distance);
       facilities = findViewById(R.id.facility);

        Bundle extras = getIntent().getBundleExtra("data");

        //getIntent().getStringExtra()
        if (extras != null) {

            hospital_name.setText(extras.getString("hospitalName"));
            hospital_address.setText(extras.getString("hospitalAddress"));
            hospital_distance.setText(extras.getString("distance"));
            time.setText(extras.getString("time"));
            facilities.setText(extras.getString("services"));
            website_link.setText(extras.getString("website"));
            Log.v("hello",extras.getString("hospitalName"));
        }

    }
}