package com.ajcb.hp.onlinefarmersmarket;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import static com.ajcb.hp.onlinefarmersmarket.FarmerLogin.flag;
public class UserSession extends Application {
String m;
Context context = this;
    public void onCreate() {
        super.onCreate();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        if (firebaseUser != null) {
            m=firebaseAuth.getCurrentUser().getEmail();
            if (m.length()>5) {
                Intent i = new Intent(context, UserPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                flag = 1;
                startActivity(i);
            } else if (m.length()<5) {
                Intent i = new Intent(context, FarmerPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                flag = 0;
                startActivity(i);
            } else {
                Intent i = new Intent(context, Splash.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }

        }

    }
}
