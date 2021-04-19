package com.example.servingwebcontent.service;

import com.example.servingwebcontent.concurrent.ManagedTask;

public class GreetingTask extends ManagedTask {

    public GreetingTask(String id) {
        super(id);
    }

    @Override
    public void run() {
        progress = "Started";
        int n = 50;
        for (int i = 0; i<n; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                progress = "Progress " + i + " of " + (n - 1);
            }
        }
    }
}