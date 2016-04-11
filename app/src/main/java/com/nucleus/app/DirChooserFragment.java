package com.nucleus.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import net.rdrei.android.dirchooser.DirectoryChooserConfig;
import net.rdrei.android.dirchooser.DirectoryChooserFragment;

import helper.AppConstant;

/**
 * Created by Sreeraag on 10-04-2016.
 */
public class DirChooserFragment extends Activity implements
        DirectoryChooserFragment.OnFragmentInteractionListener {

    private DirectoryChooserFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                .newDirectoryName("DialogSample")
                .build();
        mDialog = DirectoryChooserFragment.newInstance(config);

        mDialog.show(getFragmentManager(), null);
    }
    @Override
    public void onSelectDirectory(@NonNull String path) {
        String[] parts = path.split("/0/");
        AppConstant.PHOTO_ALBUM = parts[1];
        mDialog.dismiss();
        Toast.makeText(getApplicationContext(),AppConstant.PHOTO_ALBUM+"has been selected", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DirChooserFragment.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCancelChooser() {
        mDialog.dismiss();
    }
}