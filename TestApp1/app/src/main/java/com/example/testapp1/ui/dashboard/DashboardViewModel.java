package com.example.testapp1.ui.dashboard;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapp1.R;

import java.io.File;
import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Files Created");
    }

    public LiveData<String> getText() {
        return mText;
    }
}