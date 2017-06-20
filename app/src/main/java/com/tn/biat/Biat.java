package com.tn.biat;

import android.app.Application;
import android.content.Context;

/**
 * Created by mednaceur on 25/04/2017.
 */

public class Biat extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Biat.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Biat.context;
    }
}
