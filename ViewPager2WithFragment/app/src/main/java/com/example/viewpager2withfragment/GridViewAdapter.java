package com.example.viewpager2withfragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> fileNames;

    private String folder;

    public GridViewAdapter(Context context, List<String> fileNames, String folder) {
        this.context = context;
        this.fileNames = fileNames;
        this.folder = folder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        String currentImgPath = folder + fileNames.get(position);
        Bitmap myBitmap = BitmapFactory.decodeFile(currentImgPath);
        imageView.setImageBitmap(myBitmap);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(340, 340));
        return imageView;
    }

    @Override
    public int getCount() {
        return fileNames.size();
    }

    @Override
    public Object getItem(int position) {
        return fileNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
