package com.example.testapp1.ui.about;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapp1.MainActivity;
import com.example.testapp1.R;

import org.w3c.dom.Text;


public class AboutFragment extends Fragment {
    private AboutViewModel aboutViewModel;
    public String memoryStats = "Memory Details Goes Here ......"; // class instance variable

    TextView memoryDetailsText;

    public AboutFragment() {};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        final TextView textView = root.findViewById(R.id.textView);

        memoryDetailsText = root.findViewById(R.id.memoryDetailsId);

        aboutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        memoryStats = ((MainActivity)getActivity()).memoryDetailsObj;

        System.out.println("((MainActivity)getActivity()).memoryDetailsObj");
        System.out.println(memoryStats);

        memoryDetailsText.setText(memoryStats);

        return root;
    }

    public void setMemoryStats(String s) {
        this.memoryStats = s;
    }


    public void updateMemoryDetails(String ms) {
        AboutFragment af = new AboutFragment();
        af.memoryStats = ms;
        af.setMemoryStats(ms);
    }
}