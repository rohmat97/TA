package com.example.innstant.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.innstant.data.OpenConnectionTask;

public class idleViewModel extends ViewModel {
    public void openServerConnection() {
        new OpenConnectionTask().execute();
    }
}
