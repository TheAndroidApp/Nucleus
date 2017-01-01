package com.nucleus.app;

/**
 * Created by Anand.M.P on 2/22/2016.
 */

import helper.Utils;
import adapter.FullScreenImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class FullScreenViewActivity extends Activity {

    private Utils utils;
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        viewPager = (ViewPager) findViewById(R.id.pager);

        utils = new Utils(getApplicationContext());

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        String action = i.getAction();
        int position = 0;
        Uri selectedImageUri;

        //if intent is from the share menu of gallery
        if(i.ACTION_SEND.equals(action)) {
            if(extras.containsKey(i.EXTRA_STREAM)) {
                selectedImageUri = (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);
                Log.e("console", "Uri" + selectedImageUri);
            }
        }
        else {
            position = i.getIntExtra("position", 0);
            Log.e("console", "Value " + position);
        }

        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
                utils.getFilePaths());

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
    }
}
