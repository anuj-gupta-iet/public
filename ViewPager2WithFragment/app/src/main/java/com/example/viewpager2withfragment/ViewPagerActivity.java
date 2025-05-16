package com.example.viewpager2withfragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.viewpager_activity);
        //setContentView(R.layout.fragment_viewpager);
        //setContentView(R.layout.example_textview_activity);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        ArrayList<String> fileNames = bundle.getStringArrayList("fileNames");
        int position = bundle.getInt("position");
        String folder = bundle.getString("folder");
        //String path = intent.getExtras().getString("path");
        //int position = intent.getExtras().getInt("position");
        //ArrayList<String> paths = bundle.getStringArrayList("paths");

        //Log.e("E", "Inside ViewPagerActivity: Path: " + path + ", Position: " + position);
        //Log.e("E", "fragmentData: " + fragmentData.size() + ", paths: " + paths.size());

        ViewPager2 viewPager = findViewById(R.id.viewpager);

        ViewPagerAdapter pageAdapter = new ViewPagerAdapter(this, viewPager,  fileNames, getBaseContext(), folder);
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(position);

    }
}
