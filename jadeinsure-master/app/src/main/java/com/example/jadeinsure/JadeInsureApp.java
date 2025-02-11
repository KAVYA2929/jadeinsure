package com.example.jadeinsure;

import android.app.Application;
import com.example.jadeinsure.network.SessionManager;

public class JadeInsureApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SessionManager.init(this);
    }
}