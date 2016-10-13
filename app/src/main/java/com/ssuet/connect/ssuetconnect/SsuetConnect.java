package com.ssuet.connect.ssuetconnect;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by DELL on 10/13/2016.
 */

public class SsuetConnect extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        if(!FirebaseApp.getApps(this).isEmpty()){

            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }



    }
}

