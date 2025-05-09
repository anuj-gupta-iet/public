package com.example.viewpager2withfragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final String PERMISSION_READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int READ_PERMISSION_REQ_CODE = 200;
    private static final int WRITE_PERMISSION_REQ_CODE = 201;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("================Starting Application=============");
        System.out.println("=================================================");
        System.out.println("=================================================");
        System.out.println("=================================================");
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        // permissions
        ActivityCompat.requestPermissions(this, new String[]{PERMISSION_WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQ_CODE);
        if (ActivityCompat.checkSelfPermission(this, PERMISSION_READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.e("E","Permission Granted - READ_EXTERNAL_STORAGE");
        } else {
            Log.e("E","Permission Not granted - READ_EXTERNAL_STORAGE");
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_READ_EXTERNAL_STORAGE}, READ_PERMISSION_REQ_CODE);
        }

        if (ActivityCompat.checkSelfPermission(this, PERMISSION_WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.e("E","Permission Granted - WRITE_EXTERNAL_STORAGE");
        } else {
            Log.e("E","Permission Not granted - WRITE_EXTERNAL_STORAGE");
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQ_CODE);
        }

        //fragmentExample();

        String mainDirPath = "/sdcard/DCIM/Screenshots/";
        File dirFileOb = new File(mainDirPath);
        Log.e("E",dirFileOb.toString());
        Log.e("E",dirFileOb.listFiles()==null?"null":"notnull");
        Log.e("E",dirFileOb.listFiles().length+"");
        List<File> imgFiles = Arrays.stream(dirFileOb.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".jpg")) {
                    return true;
                } else {
                    return false;
                }
            }
        })).collect(Collectors.toList());
        imgFiles.sort(new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                Long f2Date = Long.valueOf(f2.lastModified());
                Long f1Date = Long.valueOf(f1.lastModified());
                //Log.e("E", "Last Modified: " + f2.lastModified() + ", " + f1.lastModified());
                //return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
                if (f2Date == null || f1Date == null) {
                    Log.e("E", "Last Modified: " + f2.lastModified() + ", " + f1.lastModified());
                }
                return f2Date.compareTo(f1Date);
            }
        });

        ArrayList<String> fileNames = (ArrayList<String>) imgFiles.stream().map(e -> e.getName()).collect(Collectors.toList());
        String folder = "/sdcard/DCIM/Screenshots/";
        //ArrayList<String> paths = (ArrayList<String>) imgFiles.stream().map(e -> e.getAbsolutePath()).collect(Collectors.toList());
        //"/sdcard/DCIM/Screenshots/Screenshot_20250427-123736_Instagram.jpg"

        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewAdapter(getBaseContext(), fileNames, folder));
        //gridView.setVisibility(View.GONE);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("E", "Item Clicked: Position: " + position + ", FileName: " + fileNames.get(position));
                //String path = paths.get(position);
                Intent intent = new Intent(getApplicationContext(), ViewPagerActivity.class);
                Bundle bundle = new Bundle();
                Log.e("E", "fragmentData(0): " + fileNames.get(position));

                //bundle.putStringArrayList("paths", paths);
                //intent.putExtra("path", path);
                //intent.putExtra("position", position);
                bundle.putStringArrayList("fileNames", fileNames);
                bundle.putInt("position", position);
                bundle.putString("folder", folder);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }

    private void fragmentExample() {
        String mainDirPath = "/sdcard/DCIM/Screenshots/";
        File dirFileOb = new File(mainDirPath);
        Log.e("E",dirFileOb.toString());
        Log.e("E",dirFileOb.listFiles()==null?"null":"notnull");
        Log.e("E",dirFileOb.listFiles().length+"");
        List<File> imgFiles = Arrays.stream(dirFileOb.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".jpg")) {
                    return true;
                } else {
                    return false;
                }
            }
        })).collect(Collectors.toList());
        imgFiles.sort(new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                Long f2Date = Long.valueOf(f2.lastModified());
                Long f1Date = Long.valueOf(f1.lastModified());
                //return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
                return f2Date.compareTo(f1Date);
            }
        });

        Log.e("E",imgFiles.get(0).getAbsolutePath());

        ViewPager2 viewPager = findViewById(R.id.viewpager);

        List<String> fragmentData = imgFiles.stream().map(e -> e.getName()).collect(Collectors.toList());
        ArrayList<String> paths = (ArrayList<String>) imgFiles.stream().map(e -> e.getAbsolutePath()).collect(Collectors.toList());

        //getSupportFragmentManager().beginTransaction().remove(new ViewpagerFragment(getBaseContext(), fragmentManager)).commit();

        ViewPagerAdapter pageAdapter = new ViewPagerAdapter(this, fragmentData, getBaseContext(), getSupportFragmentManager(), paths);
        viewPager.setAdapter(pageAdapter);
    }
}
