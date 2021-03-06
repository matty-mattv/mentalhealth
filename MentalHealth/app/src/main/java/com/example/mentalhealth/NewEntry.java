
package com.example.mentalhealth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

    public static final String EXTRA_ENTRY = "com.example.mentalhealth.entry";
    private String dateTitle;
    private String matchingQuote;
    static final int REQUEST_IMAGE_CAPTURE = 1;

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

    public void submitEntry(View view) {

        EditText entryInout = (EditText) findViewById(R.id.entryText);
        String entry = "?entry=" + entryInout.getText().toString();

        Log.d("Entry", entry);
        if(entry.length() == 0) {
            entry = "";
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String address = getString(R.string.address);
        String url = address + entry;

        Log.d("Address", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response String", response);

                        try {
                            JSONObject quoteObject = new JSONObject(response);
                            //Need to change the json property
                            matchingQuote = quoteObject.get("quote").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON Error", "Error in creating JSON object");
                        }

                        //Save the entry and quote in a file before displaying
                        try {
                            saveEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(NewEntry.this, DisplayQuote.class);
                        intent.putExtra(EXTRA_ENTRY, matchingQuote);
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
    public void saveEntry() throws IOException {
        EditText entryTextInput = (EditText) findViewById(R.id.entryText);
        EditText entryTitleInput = (EditText) findViewById(R.id.entryTitle);
        String entryText = entryTextInput.getText().toString();
        String entryTitle = entryTitleInput.getText().toString();

        String counter = getCounter();

        try {
            File file = new File(this.getFilesDir(), "userEntry" + counter + ".txt");
            File quoteFile = new File(this.getFilesDir(), "quote" + counter + ".txt");

            file.createNewFile();
            quoteFile.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            Log.d("Title", entryTitle);
            Log.d("Text", entryText);
            writer.write(entryTitle + "\n" + entryText );
            writer.close();

            BufferedWriter quoteWriter = new BufferedWriter(new FileWriter(quoteFile, false));
            quoteWriter.write(matchingQuote);
            quoteWriter.close();
        } catch (IOException e) {
            Log.d("ReadWriteFile", "Unable to write to the userEntry.txt file.");
        }
    }


    private String getCounter() throws IOException {

        File file = new File(this.getFilesDir(), "counter.txt");
        String counterString = "";

        if( ! file.exists()) {
            file.createNewFile();
            counterString = "0";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                writer.write(counterString);
                writer.close();
            } catch (IOException e) {
                Log.d("CounterFile", "Unable to write to counter.txt file.");
            }
        }
        else {
            try {
                //Read counter from file and increment then return
                BufferedReader reader = new BufferedReader(new FileReader(file));
                int counter = Integer.parseInt(reader.readLine().toString());
                Log.d("***Counter***", Integer.toString(counter));
                ++counter;
                counterString = Integer.toString(counter);
                reader.close();

                BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                writer.write(counterString);
                writer.close();
            } catch (IOException e) {
                Log.d("CounterFile", "Unable to write to counter.txt file.");
            }
        }

        return counterString;
    }

    public void onButtonTap(View v){
        takePhoto();
    }

    private void takePhoto() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = (ImageView) findViewById(R.id.imageViewID);
            imageView.setImageBitmap(imageBitmap);
        }
    }
}
