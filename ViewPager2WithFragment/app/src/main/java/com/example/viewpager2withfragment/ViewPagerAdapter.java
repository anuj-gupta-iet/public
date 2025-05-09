package com.example.viewpager2withfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private String folder;
    private List<String> fileNames;
    private int numPages;
    private Context context;
    private FragmentManager fragmentManager;
    private ArrayList<String> paths;
    private ViewPager2 viewPager;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ViewPager2 viewPager, List<String> fileNames, Context context, String folder) {
        super(fragmentActivity);
        this.fileNames = fileNames;
        this.viewPager = viewPager;
        this.numPages = fileNames.size();
        this.context = context;
        this.folder = folder;
    }
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<String> fragmentData, Context context, FragmentManager fragmentManager, ArrayList<String> paths) {
        super(fragmentActivity);
        this.fileNames = fragmentData;
        this.numPages = fragmentData.size();
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.paths = paths;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {


        Bundle bundle = new Bundle();
        bundle.putString("fileName", fileNames.get(position));
        bundle.putInt("position", position);
        bundle.putString("folder", folder);
        ViewpagerFragment fragment = new ViewpagerFragment(context, viewPager);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return numPages;
    }
}
