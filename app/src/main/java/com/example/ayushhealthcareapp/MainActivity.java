package com.example.ayushhealthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayushhealthcareapp.Adapters.MainAdapter;
import com.example.ayushhealthcareapp.Models.MainModel;
import com.example.ayushhealthcareapp.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LocationListener {

    /*
    ListView listView;
    String[] NameList = {"Vivekanand Hospital", "Bapu Nature Cure Hospital", "Sri Sai Ayurvedic Hospital", "Swami Parmand Prakritik",
                         "Kailash Institute of Naturopathy", "Jivagram Center for Wellbeing", "AyurVAID Kalmatia", "Jaipur Ayurveda Hospital"};
    ArrayAdapter<String> arrayAdapter;
    */
    ActivityMainBinding binding;
    LocationManager mLocationManager;
    ArrayList<MainModel> list = new ArrayList<>();
    private RequestQueue mRequestQueue;
    MainAdapter adapter;
    double lati, longi;
    TextView locaView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locaView = findViewById(R.id.loca_view);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        getLocation();

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);
        /*
        listView = findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, NameList);
        listView.setAdapter(arrayAdapter);
        */


//
//        list.add(new MainModel(R.drawable.hospital, "AyurVAID Kalmatia", "Kalimat Estate, Kasar Devi Upper Binsar Road, Uttrakhand, 263601", "9 AM to 5 PM"));
//        list.add(new MainModel(R.drawable.hospital, "AyurVAID Kalmatia", "Kalimat Estate, Kasar Devi Upper Binsar Road, Uttrakhand, 263601", "9 AM to 5 PM"));
//        list.add(new MainModel(R.drawable.hospital, "AyurVAID Kalmatia", "Kalimat Estate, Kasar Devi Upper Binsar Road, Uttrakhand, 263601", "9 AM to 5 PM"));
//        list.add(new MainModel(R.drawable.hospital, "AyurVAID Kalmatia", "Kalimat Estate, Kasar Devi Upper Binsar Road, Uttrakhand, 263601", "9 AM to 5 PM"));
//        list.add(new MainModel(R.drawable.hospital, "AyurVAID Kalmatia", "Kalimat Estate, Kasar Devi Upper Binsar Road, Uttrakhand, 263601", "9 AM to 5 PM"));
//        list.add(new MainModel(R.drawable.hospital, "AyurVAID Kalmatia", "Kalimat Estate, Kasar Devi Upper Binsar Road, Uttrakhand, 263601", "9 AM to 5 PM"));
//        list.add(new MainModel(R.drawable.hospital, "AyurVAID Kalmatia", "Kalimat Estate, Kasar Devi Upper Binsar Road, Uttrakhand, 263601", "9 AM to 5 PM"));
//        list.add(new MainModel(R.drawable.hospital, "AyurVAID Kalmatia", "Kalimat Estate, Kasar Devi Upper Binsar Road, Uttrakhand, 263601", "9 AM to 5 PM"));
//        list.add(new MainModel(R.drawable.hospital, "AyurVAID Kalmatia", "Kalimat Estate, Kasar Devi Upper Binsar Road, Uttrakhand, 263601", "9 AM to 5 PM"));
//        list.add(new MainModel(R.drawable.hospital, "AyurVAID Kalmatia", "Kalimat Estate, Kasar Devi Upper Binsar Road, Uttrakhand, 263601", "9 AM to 5 PM"));

        adapter = new MainAdapter(list, this);
        fillModelList();
//        adapter = new MainAdapter(list, this);
        binding.recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,MainActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void fillModelList(){

        mRequestQueue = Volley.newRequestQueue(MainActivity.this);

        String url="https://ayushservice.azurewebsites.net/getAllInfoToSend/77.376184/28.594191";
        Log.v("just","statrt");
        JsonArrayRequest
                jsonArrayRequest
                = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    Log.v("check","sstarting");
                    for(int i=0;i<response.length();i++){
                        try {
                            MainModel p = new MainModel();
                            JSONObject jsonObject= response.getJSONObject(i);

                            ArrayList<String> temp = new ArrayList<String>();

                            for(int j=0;j< jsonObject.getJSONArray("services").length();j++){

                                temp.add( jsonObject.getJSONArray("services").getString(j) );
                            }

                            String time="Open time: "+jsonObject.getString("open_time")+ "-"+"Close time "+jsonObject.getString("close_time");
                            p.setServices(temp);
                            p.setName(jsonObject.getString("hospital_name"));
                            p.setAddress(jsonObject.getString("address"));
                            p.setWebsite_link(jsonObject.getString("website_link"));
                            p.setPhone_no(jsonObject.getString("phone_No"));
                            p.setImage(R.drawable.hospital);
                            p.setTime(time);
                            p.setDistance(jsonObject.getInt("distance"));
                               // list.add(p);
//                                adapter.setList(list);
                               // adapter.notifyDataSetChanged();

                            Log.v("check",p.getName());
                            list.add(p);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("check",e.getMessage());
                        }
                    }
                },
                error -> Log.v("just","error"));
        mRequestQueue.add(jsonArrayRequest);
        Log.v("just","end");
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
    }


    @Override
    public void onLocationChanged(Location location) {
        lati = location.getLatitude();
        longi = location.getLongitude();

        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);
//            locaView.setText(address);
            Log.d("SS", "onLocationChanged: "+address);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter .getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

     */
}