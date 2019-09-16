package com.example.innstant.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.innstant.data.OpenConnectionTask;

public class DashboardViewModel extends ViewModel {
    public void openServerConnection() {
        new OpenConnectionTask().execute();
    }
}
