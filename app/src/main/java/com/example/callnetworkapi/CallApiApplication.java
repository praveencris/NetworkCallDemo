package com.example.callnetworkapi;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallApiApplication extends Application {
    ExecutorService executorService = Executors.newFixedThreadPool(4);
}
