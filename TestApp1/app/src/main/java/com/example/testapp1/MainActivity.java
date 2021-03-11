package com.example.testapp1;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp1.ui.about.AboutFragment;
import com.example.testapp1.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


    public String memoryDetailsObj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        /**
         * Get App Memory Usage
         **/

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager memoryResult = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        memoryResult.getMemoryInfo(memoryInfo);
        Runtime getRuntime = Runtime.getRuntime();

        StringBuilder memoryDetailsStringBuilder = new StringBuilder();

        long gb = 1000 * 1000 * 1000;

        /**
         * Capture Memory Details of the Device in a neat format !!!
         **/
        memoryDetailsStringBuilder.append("\n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("Low Memory: " + memoryInfo.lowMemory + "\n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("\n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("Memory Details: \n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("] Available Memory: " + String.format("%.2f", (double) memoryInfo.availMem / gb) + " Gb \n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("] Threshold Memory: " + String.format("%.2f", (double) memoryInfo.threshold / gb) + " Gb \n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("] Total Memory: " + String.format("%.2f", (double) memoryInfo.totalMem / gb) + " Gb \n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("\n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("Run Time Memory Info: \n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("] Free Memory: " + String.format("%.2f", (double) getRuntime.freeMemory() / gb) + " Gb \n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("] Max Memory: " + String.format("%.2f", (double) getRuntime.maxMemory() / gb) + " Gb \n");
        memoryDetailsStringBuilder.append("\t\t");
        memoryDetailsStringBuilder.append("] Total Memory: " + String.format("%.2f", (double) getRuntime.totalMemory() / gb) + " Gb \n");


        AboutFragment aboutFragment = new AboutFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().commit();
        aboutFragment.updateMemoryDetails(memoryDetailsStringBuilder.toString());
        aboutFragment.setMemoryStats(memoryDetailsStringBuilder.toString());


        this.memoryDetailsObj = memoryDetailsStringBuilder.toString();

    }
}