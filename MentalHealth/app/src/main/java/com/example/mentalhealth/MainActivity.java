package com.example.mentalhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newEntry(View view) {
        Intent intent = new Intent(this, NewEntry.class);
        startActivity(intent);
    }

    public void viewEntries(View view) {
        Intent intent = new Intent(this, ViewEntries.class);
        startActivity(intent);
    }

    public void clearEntries(View view) throws IOException {

        int numOfFiles = getCounter();
        if ( numOfFiles > 0 ) {
            for(int i = 0; i < numOfFiles; ++i) {
                String count = Integer.toString(i);
                File file = new File(this.getFilesDir(), "userEntry" + count + ".txt");
                File quoteFile = new File(this.getFilesDir(), "quote" + count + ".txt");

                if(file.exists()) {
                    file.delete();
                }

                if(quoteFile.exists()) {
                    quoteFile.delete();
                }
            }
        }

        File counterFile = new File(this.getFilesDir(), "counter.txt");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(counterFile, false));
            writer.write("0");
            writer.close();
        } catch (IOException e) {
            Log.d("CounterFile", "Unable to clear to counter.txt file.");
        }


    }

    //Grab the count from counter.txt
    private int getCounter() throws IOException {
        int counter = 0;

        try {
            File file = new File(this.getFilesDir(), "counter.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            counter = Integer.parseInt(reader.readLine().toString());
            Log.d("**^^Counter^^**", Integer.toString(counter));
        } catch (IOException e) {
            Log.d("CounterFile", "Unable to read from counter.txt file.");
        }

        return counter;
    }
}
