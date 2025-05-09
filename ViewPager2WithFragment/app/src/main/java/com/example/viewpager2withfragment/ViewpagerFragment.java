package com.example.viewpager2withfragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import java.io.File;
import java.util.ArrayList;

public class ViewpagerFragment extends Fragment {

    //private TextView textView;
    private ImageView imageView;
    private Context context;
    private FragmentManager fragmentManager;
    private ViewPager2 viewPager;
    public ViewpagerFragment(Context context, ViewPager2 viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }
    public ViewpagerFragment(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String fileName = getArguments().getString("fileName");
        String folder = getArguments().getString("folder");
        int position = getArguments().getInt("position");
        //ArrayList<String> paths = getArguments().getStringArrayList("paths");

        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        //textView = view.findViewById(R.id.textView);
        //textView.setText(data);
        imageView = view.findViewById(R.id.imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String currentImgPath = folder + fileName;
        Log.e("E", "currentImgPath: " + currentImgPath);
        Bitmap myBitmap = BitmapFactory.decodeFile(currentImgPath);
        imageView.setImageBitmap(myBitmap);

        //ViewpagerFragment ob = this;

        GestureDetector gDetecter = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {
                Log.e("E", "Double Tap: Position: " + position + ", Path: " + folder + fileName);
                Toast toast = Toast.makeText(context, "...", Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 300);
                //String mainDirPath = "/sdcard/DCIM/Screenshots/";
                //int toIndex = mainDirPath.lastIndexOf("/");
                //int fromIndex = mainDirPath.substring(0, mainDirPath.length() - 2).lastIndexOf("/");
                int toIndex = folder.lastIndexOf("/");
                int fromIndex = folder.substring(0, folder.length() - 2).lastIndexOf("/");
                String shortlistedFolderName = folder.substring(fromIndex + 1, toIndex) + "_shortlisted/";
                File currentFileOb = new File(folder+fileName);
                File moveFileOb = new File(folder + shortlistedFolderName + fileName);
                currentFileOb.renameTo(moveFileOb);

                //viewPager.setCurrentItem(position + 1, true);
                //paths.remove(position);
                //fragmentManager.beginTransaction().remove(ob).commit();
                return super.onDoubleTap(e);
            }
        });
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.e("E", "Touched 11");
                return gDetecter.onTouchEvent(event);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
