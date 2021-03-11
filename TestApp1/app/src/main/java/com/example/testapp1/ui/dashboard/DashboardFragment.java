package com.example.testapp1.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapp1.R;

import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class DashboardFragment extends Fragment implements AdapterView.OnItemClickListener {

    private DashboardViewModel dashboardViewModel;

    public String fileName; // File Name
    public String fileSize; // File Size
    public String CreatedAt; // Created At timestamp

    public static final int BUFFER_SIZE = 1024;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final TextView textView = root.findViewById(R.id.text_dashboard);

        Button replicateButton = root.findViewById(R.id.replicateButtonId);
        Button deleteButton = root.findViewById(R.id.deleteButtonId);

        /**
         * Replicate all the files
         */
        replicateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Replicating Files.....!");
                // Toast Message
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Replicating Files", Toast.LENGTH_SHORT);
                toast.show();


                File fileSavedPath = getActivity().getFilesDir();

                // Source and Dest are same
                String fileSourceDestPath = String.valueOf(fileSavedPath);

                File f = new File(String.valueOf(fileSavedPath));

                System.out.println("File Path : " + f.getFreeSpace() + "|" + f.getTotalSpace());

                File filesList[] = f.listFiles();

                if (filesList.length > 0) {

                    for(File fileI : f.listFiles()) {

                        System.out.println(String.valueOf(fileSavedPath) + fileI.separator + fileI.getName().replace(".txt", "-copy.txt"));
                        //File fileICopy = new File(String.valueOf(fileSavedPath))

                        String fromFilePath = String.valueOf(fileSavedPath) + fileI.separator + fileI.getName();
                        String toFilePath = String.valueOf(fileSavedPath) + fileI.separator + fileI.getName().replace(".txt", "-copy.txt");


                        try  {
                            Process copy = Runtime.getRuntime().exec(
                                    new String[]{"dd", String.format("if=%s", fromFilePath), String.format("of=%s", toFilePath)}
                            );

                            Toast.makeText(getActivity().getApplicationContext(), fileI.getName() + " copied !", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(getActivity().getApplicationContext(), "IO Exception", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    }


                } else {
                    Toast noFilesToast = Toast.makeText(getActivity().getApplicationContext(), "0 Files to replicate !", Toast.LENGTH_SHORT);
                    noFilesToast.show();
                }

            }
        });


        /**
         * Delete all the files
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Deleting all Files.....!");
                // Toast Message
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Deleting all Files", Toast.LENGTH_SHORT);
                toast.show();

                File fileSavedPath = getActivity().getFilesDir();

                File f = new File(String.valueOf(fileSavedPath));

                System.out.println("File Path : " + f.getFreeSpace() + "|" + f.getTotalSpace());

                File filesList[] = f.listFiles();

                if (filesList.length > 0) {
                    for (int i = 0; i < filesList.length; i++) {
                        System.out.println("File: " + filesList[i] + " | " + filesList[i].getName() + " | " + filesList[i].getFreeSpace());
                        filesList[i].delete();


                        // Toast Message
                        Toast fileDeleted = Toast.makeText(getActivity().getApplicationContext(),
                                "Deleted " + filesList[i].getName(),
                                Toast.LENGTH_SHORT);
                        fileDeleted.show();
                    }
                } else {
                    Toast noFilesToast = Toast.makeText(getActivity().getApplicationContext(), "0 Files to delete !", Toast.LENGTH_SHORT);
                    noFilesToast.show();
                }
            }
        });




        // Get Title Text for the Dashboard Page
        
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

                System.out.println("\n\n\n\n\n");
                System.out.println("Fetching Files List .............................");
                System.out.println("Saved to location: " + getActivity().getFilesDir());
            }
        });


        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        File fileSavedPath = getActivity().getFilesDir();

        File f = new File(String.valueOf(fileSavedPath));

        System.out.println("File Path : " + f.getFreeSpace() + "|" + f.getTotalSpace());

        File filesList[] = f.listFiles();
        ArrayList<String> filesListArray = new ArrayList<>();

        if (filesList.length > 0) {
            for (int i=0; i<filesList.length; i++) {
                System.out.println("File: " + filesList[i] + " | " + filesList[i].getName() + " | " + filesList[i].getFreeSpace());
                String elementName = filesList[i].getName();
                long mib = 1000 * 1000;
                double elementInMegaBytes =  (double) filesList[i].length() / mib;
                String mmResult = String.format("%.2f",elementInMegaBytes );
                String element = elementName + " \t [ " + mmResult + "  (Mb) ] ";
                filesListArray.add(element);
            }
        }

        ListView filesListViewId = (ListView) view.findViewById(R.id.filesListViewId);
        ArrayAdapter<String> filesListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, filesListArray);
        filesListViewId.setAdapter(filesListAdapter);
        filesListViewId.setOnItemClickListener(this);
        filesListAdapter.notifyDataSetChanged();
    }


    /***
     * Click events for the list items on Dashboard page
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File fileSavedPath = getActivity().getFilesDir();
        File f = new File(String.valueOf(fileSavedPath));
        System.out.println("File Path : " + f.getFreeSpace() + "|" + f.getTotalSpace());
        File filesList[] = f.listFiles();
        String toastText = "Path: " + filesList[position];

        if(position >= 0) {
            Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
        }
    }
}