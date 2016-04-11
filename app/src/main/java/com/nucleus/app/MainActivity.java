
package com.nucleus.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Checkable;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import helper.AppConstant;

public class MainActivity extends AppCompatActivity {

    //Change WiFi Name Variables
    WifiP2pManager mWifiP2pManager;
    WifiP2pManager.Channel mChannel;
    String myNewDeviceName;
    String TAG = "Nucleus";

    //Defining Variables
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private TextView resultText;
    private String username;
    private String gPassword;
    private String gPasswordConf;
    private Switch mySwitch;
    Switch switchButton, switchButton2;
    TextView textView, textView2;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";


    //Changes WiFi Direct Name
    public void changeName(String input){

        //inner class needs to have access to new name

        myNewDeviceName = input;

        //
        //  This will get the WifiDirect manager for use
        //


        mWifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        if (mWifiP2pManager != null) {
            mChannel = mWifiP2pManager.initialize(this, getMainLooper(), null);
            if (mChannel == null) {
                //Failure to set up connection
                Log.e(TAG, "Failed to set up connection with wifi p2p service");
                mWifiP2pManager = null;
            }
        } else {
            Log.e(TAG, "mWifiP2pManager is null !");
        }

        //
        //  Setup for using the reflection API to actually call the methods we want
        //

        int numberOfParams = 3;
        Class[] methodParameters = new Class[numberOfParams];
        methodParameters[0] = WifiP2pManager.Channel.class;
        methodParameters[1] = String.class;
        methodParameters[2] = WifiP2pManager.ActionListener.class;

        Object arglist[] = new Object[numberOfParams];
        arglist[0] = mChannel;
        arglist[1] = myNewDeviceName;
        arglist[2] = new WifiP2pManager.ActionListener() {
            public void onSuccess() {
                String resultString = "WiFi Direct Service has started.";
                Log.e(TAG, resultString);
                Toast.makeText(getApplicationContext(), resultString, Toast.LENGTH_LONG).show();
            }

            public void onFailure(int reason) {
                String resultString = "Failed to Change! Is your WiFi on?";
                Log.e(TAG,resultString);
                Toast.makeText(getApplicationContext(), resultString,Toast.LENGTH_LONG).show();
            }
        };

        //
        //  Now we use the reflection API to call a method we normally wouldn't have access to.
        //

        ReflectionUtils.executePrivateMethod(mWifiP2pManager,WifiP2pManager.class,"setDeviceName",methodParameters,arglist);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if (useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getDrawable(R.drawable.ic_add_black_24dp));

        resultText = (TextView) findViewById(R.id.result);

        final FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();


        SubActionButton.Builder itemBuilder1 = new SubActionButton.Builder(this);
        ImageView cameraIcon = new ImageView(this);
        cameraIcon.setImageDrawable(getDrawable(R.drawable.ic_add_a_photo_black_18dp));
        final SubActionButton cameraButton = itemBuilder1.setContentView(cameraIcon).build();

        SubActionButton.Builder itemBuilder2 = new SubActionButton.Builder(this);
        ImageView groupIcon = new ImageView(this);
        groupIcon.setImageDrawable(getDrawable(R.drawable.ic_group_add_black_18dp));
        final SubActionButton groupButton = itemBuilder2.setContentView(groupIcon).build();

        SubActionButton.Builder itemBuilder3 = new SubActionButton.Builder(this);
        ImageView wifiIcon = new ImageView(this);
        wifiIcon.setImageDrawable(getDrawable(R.drawable.ic_wifi_tethering_black_18dp));
        final SubActionButton nameButton = itemBuilder2.setContentView(wifiIcon).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(cameraButton)
                .addSubActionView(groupButton)
                .addSubActionView(nameButton)
                .attachTo(actionButton)
                .build();

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Camera option selected ", Toast.LENGTH_SHORT).show();
                //final static int cameraData = 0;
                //final int cameraData = 0;
                Intent startCamera = new Intent(MainActivity.this, Camera.class);
                startActivity(startCamera);
//                startActivityForResult(startCamera, cameraData);

            }
        });

        //Directly get into the WiFi P2P Activity without asking for the dialog box.
        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WiFiDirectActivity.class);
                startActivity(intent);
            }
        });

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final EditText deviceName = (EditText) promptView.findViewById(R.id.edittext);

                builder.setView(promptView);
                builder.setTitle("ENTER DEVICE NAME");
                builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            changeName(deviceName.getText().toString());
                            dialog.dismiss();
                            Intent intent = new Intent(getBaseContext(), WiFiDirectActivity.class);
                            startActivity(intent);
                        }
                        //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                    });
                }//Set to null. We override the onclick
        });



        /* Navigation Drawer Functions Start Here. Please do not change! */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        /* Tab Fragments Initialized Here */
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                mDrawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    // For rest of the options we just show a toast on click
                    case R.id.navGallery:
                        AppConstant.PHOTO_ALBUM="DCIM/Camera";
                        Intent startGallery = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(startGallery);
                        return true;
                    case R.id.navCompressed:
                        AppConstant.PHOTO_ALBUM="Nucleus/Compressed Images";
                        Intent startCompressed = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(startCompressed);
                        return true;
                    case R.id.navReceived:
                        AppConstant.PHOTO_ALBUM="Nucleus/Received Images";
                        Intent startReceived= new Intent(MainActivity.this,MainActivity.class);
                        startActivity(startReceived);
                        return true;
                    case R.id.location:
                        Intent location = new Intent(MainActivity.this,DirChooserFragment.class);
                        startActivity(location);
                        return true;
                    case R.id.about:
                        Intent about_intent = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(about_intent);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Something is Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we don't want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                actionButton.setEnabled(true);
                cameraButton.setEnabled(true);
                groupButton.setEnabled(true);
                nameButton.setEnabled(true);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we don't want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                final LayoutInflater factory = getLayoutInflater();

                final View switchView = factory.inflate(R.layout.action_view_switch, null);


                switchButton = (Switch) findViewById(R.id.mySwitch);
                switchButton.setChecked(useDarkTheme);
                switchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        toggleTheme(isChecked);
                    }
                });
            }

            @Override
            public void onDrawerSlide(View drawerView, float offset) {
                actionButton.setAlpha(1 - offset);
                cameraButton.setAlpha(1 - offset);
                groupButton.setAlpha(1-offset);
                nameButton.setAlpha(1 - offset);
                actionButton.setEnabled(false);
                cameraButton.setEnabled(false);
                groupButton.setEnabled(false);
                nameButton.setEnabled(false);

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);

    }


//    public void startGallery(View v) {
//
//        Button startGallery = (Button) findViewById(R.id.btnGallery);
//        startGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent startCamera = new Intent(MainActivity.this, GridViewActivity.class);
//                startActivity(startCamera);
//            }
//        });
//
//    }

}

