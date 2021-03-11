package com.example.testapp1.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapp1.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
//    userTextViewId - Heading
//    userEditTextId - File Content
//    button - Load
//    button2 - Save


    private static final String FILE_NAME = "go-micron.txt";
    private static final int STRING_LEN = 450000;
    private static final int NO_OF_LINES = 450000;

    /**
     * https://stackoverflow.com/questions/12116092/android-random-string-generator/12116194
     **/

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);

        for (int i = 0; i < sizeOfRandomString; ++i) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));

            if (i % 50 == 0 && i > 0) {
                sb.append("\n");
            }
        }



        return sb.toString();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView userEditText = root.findViewById(R.id.userEditTextId);

        Button loadButton = root.findViewById(R.id.button);
        Button saveButton = root.findViewById(R.id.button2);

        Toast.makeText(getActivity(), "Home Fragment ... onCreateView", Toast.LENGTH_SHORT);


        /**
         * Generate a Random String of length N
         */
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast Message
                Toast begintoast = Toast.makeText(
                        getActivity().getApplicationContext(),
                        "Generating Random Text",
                        Toast.LENGTH_SHORT
                );

                begintoast.show();


                userEditText.setText(getRandomString((int) STRING_LEN));
                System.out.println("loadButton is clicked.....!" + userEditText.getText().length() );
                // Toast Message
                Toast toast = Toast.makeText(
                        getActivity().getApplicationContext(),
                        "Generated Text of " + userEditText.getText().length() + " chars",
                        Toast.LENGTH_SHORT
                );

                toast.show();
            }
        });


        /**
         * Save the Random String as Text File
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("saveButton is clicked.....!" + userEditText.getText().length() );

                // Toast Message
                Toast toast = Toast.makeText(
                        getActivity().getApplicationContext(),
                        "Writing Text to File...",
                        Toast.LENGTH_SHORT
                );

                toast.show();

                String textToBeWritten = userEditText.getText().toString();
                FileOutputStream fileOutputStream = null;
                try {

                    if (textToBeWritten == null) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a text !!! ", Toast.LENGTH_SHORT).show();
                    } else {
                        String fileName = getRandomString((int) 9) + ".txt";
                        System.out.println("Saved to location: " + getActivity().getFilesDir() + "/" + fileName);
                        fileOutputStream = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
                        fileOutputStream.write(textToBeWritten.getBytes());
                        userEditText.setText(null);
                        Toast.makeText(getActivity().getApplicationContext(), "Saved to location: " + getActivity().getFilesDir() + "/" + fileName, Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "File Not Found Exception", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "IO Exception", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });




        /**
         *
         */
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                userEditText.setText(s);
            }
        });

        return root;
    }

    private void ToastMessages(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
    }
}