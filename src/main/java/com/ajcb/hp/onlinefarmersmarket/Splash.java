package com.ajcb.hp.onlinefarmersmarket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Splash extends AppCompatActivity {
Button user,farmer;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        user = (Button)findViewById(R.id.UserButton);
        farmer = (Button)findViewById(R.id.FarmerButton);
        findViewById(R.id.FarmerButton).setAnimation(AnimationUtils.loadAnimation(Splash.this,R.anim.downtoup));
        findViewById(R.id.UserButton).setAnimation(AnimationUtils.loadAnimation(Splash.this,R.anim.uptodown));

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, UserLogin.class);
                startActivity(intent);
            }
        });
        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this,FarmerLogin.class);
                startActivity(intent);
            }
        });
    }

    private void checkSharedPreferences(){
        String checkbox = mPreferences.getString(getString(R.string.checkbox),"false");



        if(checkbox.equals("true")){

        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    Splash.this);
            alertDialogBuilder.setTitle("Set Permissions");
            alertDialogBuilder.setMessage("Please allow the requested permissions by the app to proceed.");
            alertDialogBuilder.setIcon(R.drawable.outline_error_outline_black_48dp);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OPEN SETTINGS", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mEditor.putString(getString(R.string.checkbox),"true");
                    mEditor.commit();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
