package com.example.finalandroid;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalandroid.ui.main.SectionsPagerAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onitemClickListerner {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<Image> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recyclerView);
        imageAdapter = new ImageAdapter(imageList);
        loadList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action G'mate ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void loadList() {
        String url = "https://my-json-server.typicode.com/Insaovisal123/image-api/images";
        //creating a string request to send request to the url
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("kk", response);
                        // Refresh Data
                        ((RecyclerView)findViewById(R.id.recyclerView)).setAdapter(new ImageAdapter (Arrays.asList(new Gson().fromJson(response, Image[].class))));
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("kk", "" + error);
                        //displaying the error in toast if occurred
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        //adding the string request to request queue
        MyApplication.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(this, "Position "+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, FullImageActivity.class );
        intent.putExtra("position",position);
        startActivity(intent);
    }
}