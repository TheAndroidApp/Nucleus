package com.nucleus.app;

/**
 * Created by Ronn on 29-Jan-16.
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import adapter.GridViewImageAdapter;
import helper.AppConstant;
import helper.Utils;

public class tab3 extends Fragment {

    private Utils utils;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_grid_view, container, false);

        gridView = (GridView) view.findViewById(R.id.grid_view);

        AppConstant.PHOTO_ALBUM = "Nucleus/Received Images";

        utils = new Utils(getActivity());

        // Initilizing Grid View
        InitilizeGridLayout();

        // loading all image paths from SD card
        imagePaths = utils.getFilePaths();

        // Gridview adapter
        adapter = new GridViewImageAdapter(getActivity(), imagePaths,
                columnWidth);

        adapter.notifyDataSetChanged();

        // setting grid view adapter
        gridView.setAdapter(adapter);
        gridView.invalidateViews();
        //return inflater.inflate(R.layout.activity_grid_view,null);
        return view;
    }

    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstant.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

        gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

}