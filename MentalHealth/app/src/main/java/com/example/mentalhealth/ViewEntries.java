package com.example.mentalhealth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ViewEntries extends AppCompatActivity {

    String[] titleArray;
    String[] quoteArray;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);

        getArray();
    }

    public void getArray() {
        File file = new File(this.getFilesDir(), "userEntry.txt");

        if (file != null) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(file));
                String line;

                while( (line = reader.readLine()) != null) {

                }
                reader.close();

            } catch (IOException e) {
                Log.d("ReadWriteFile", "Unable to read the TestFile.txt file.");
            }

            EditText textBoxInpit = (EditText) findViewById(R.id.testBox);
            textBoxInpit.setText(textFromFile);
        }
    }

    public void clearEntries(View view) {
        File file = new File(this.getFilesDir(), "userEntry.txt");

        if(file.delete()) {
            Toast.makeText(this, "All entries celared", Toast.LENGTH_LONG).show();
        }
    }
}
