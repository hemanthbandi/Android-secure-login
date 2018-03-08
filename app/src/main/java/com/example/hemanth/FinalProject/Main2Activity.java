package com.example.hemanth.FinalProject;
/**
 * Created by hemanth on 06/30/2017.
 */

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import java.lang.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;



public class Main2Activity extends AppCompatActivity {


    Button button;
    Switch aSwitch;
    private static final int Hema_PHONE = 1;
    private static final int ADMIN_INTENT = 1;
    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, Admin.class);
        aSwitch = (Switch) findViewById(R.id.switch1);
        if (mDevicePolicyManager != null && mDevicePolicyManager.isAdminActive(mComponentName)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                aSwitch.setChecked(true);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (aSwitch.isChecked()) {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Administrator description");
                    startActivityForResult(intent, ADMIN_INTENT);
                    aSwitch.setText("ON");
                    WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);

                } else {
                    aSwitch.setText("OFF");
                    WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(false);
                    mDevicePolicyManager.removeActiveAdmin(mComponentName);
                    Toast.makeText(getApplicationContext(), "Admin registration removed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        button = (Button) findViewById(R.id.lock);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              checkPhonePermission();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        button.setEnabled(true);
    }

    public void checkPhonePermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!checkPermission(Main2Activity.this, new String[]{Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET})) {
                requestSystemPermission(new String[]{Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET}, Hema_PHONE);
            } else {
                lockMobile();
            }
        } else {

            lockMobile();

        }
    }

    public void requestSystemPermission(String[] permission, int opeeration) {
        requestPermissions(new String[]{permission[0], permission[1], permission[2], permission[3], permission[4],permission[5]}, opeeration);
    }

    boolean checkPermission(Context aContext, String[] aPermission) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (aContext.checkSelfPermission(aPermission[0]) == PackageManager.PERMISSION_GRANTED&&aContext.checkSelfPermission(aPermission[1]) == PackageManager.PERMISSION_GRANTED&&aContext.checkSelfPermission(aPermission[2]) == PackageManager.PERMISSION_GRANTED&&aContext.checkSelfPermission(aPermission[3]) == PackageManager.PERMISSION_GRANTED&&aContext.checkSelfPermission(aPermission[4]) == PackageManager.PERMISSION_GRANTED) {
                return true;

            }
        } else {
            return true;
        }

        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Hema_PHONE :
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED&&grantResults[2]==PackageManager.PERMISSION_GRANTED&&grantResults[3]==PackageManager.PERMISSION_GRANTED&&grantResults[4]==PackageManager.PERMISSION_GRANTED&&grantResults[5] == PackageManager.PERMISSION_GRANTED) {
                     lockMobile();
                   // checkSimID();
                } else {
                  //  getDialog("Accept all the permissions to process",0);
                }
            default: super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
    public void lockMobile()
    {
          boolean isAdmin = mDevicePolicyManager.isAdminActive(mComponentName);
                if (isAdmin) {
                    button.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Locking", Toast.LENGTH_LONG).show();
                    try {

                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mDevicePolicyManager.lockNow();
                } else {
                    Toast.makeText(getApplicationContext(), "Not Registered as admin", Toast.LENGTH_SHORT).show();
                }
    }

}


