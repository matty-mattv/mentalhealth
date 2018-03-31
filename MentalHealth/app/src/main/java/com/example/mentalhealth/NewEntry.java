
package com.example.mentalhealth;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class NewEntry extends AppCompatActivity {

    public static final String EXTRA_ENTRY = "com.example.mentalhealth,entry";
    private String dateTitle;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        //Get date and display as title
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();

        dateTitle = formatter.format(date).toString();
        EditText titleInput = (EditText) findViewById(R.id.entryTitle);
        titleInput.setText(dateTitle
        );
    }

    public void submitEntry() {
        EditText entryInout = (EditText) findViewById(R.id.entryText);
        String entry = "?Entry=" + entryInout.getText().toString();

        if(entry.length() == 0) {
            entry = "";
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String ipAddress = getString(R.string.ipAddress);
        String url = "http://" + ipAddress + ":8080/new/Test" + entry;

        Log.d("Address", url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response String", response);

                        String quote = "";
                        try {
                            JSONObject quoteObject = new JSONObject(response);
                            quote = quoteObject.get("Entry").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON Error", "Error in creating JSON object");
                        }

                        Intent intent = new Intent(NewEntry.this, DisplayQuote.class);
                        intent.putExtra(EXTRA_ENTRY, quote);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Could not get quote");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        System.out.println("End");
    }

    //Save file internally before displaying quote
    public void saveEntry(View view) throws IOException {
        EditText entryTextInput = (EditText) findViewById(R.id.entryText);
        EditText entryTitleInput = (EditText) findViewById(R.id.entryTitle);
        String entryText = entryTextInput.getText().toString();
        String entryTitle = entryTitleInput.getText().toString();

        try {
            File file = new File(this.getFilesDir(), "userEntry.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true /*append*/));
            Log.d("Title", entryTitle);
            Log.d("Text", entryText);

            writer.write(entryTitle + " " + entryText + "\n");
            writer.close();


        } catch (IOException e) {
            Log.d("ReadWriteFile", "Unable to write to the TestFile.txt file.");
        }

        submitEntry();
    }

    public void testRead() {

        File file = new File(this.getFilesDir(), "userEntry.txt");
        String textFromFile = "";

        if (file != null) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(file));
                String line;

                while( (line = reader.readLine()) != null) {
                    textFromFile += line.toString();
                    textFromFile += "***";
                }
                Log.d("Read File", textFromFile);
                reader.close();
            } catch (IOException e) {
                Log.d("ReadWriteFile", "Unable to read the TestFile.txt file.");
            }

        }


    }
}
