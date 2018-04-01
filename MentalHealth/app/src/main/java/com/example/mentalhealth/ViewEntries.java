package com.example.mentalhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ViewEntries extends AppCompatActivity {

    String[] titleArray;
    String[] entryArray;
    String[] quoteArray;
    ListView listView;

    public static final String EXTRA_TITLE = "com.example.mentalhealth.title";
    public static final String EXTRA_QUOTE = "com.example.mentalhealth.quote";
    public static final String EXTRA_ENTRY = "com.example.mentalhealth.entry";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);

        try {
            getArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CustomListAdapter custoomList = new CustomListAdapter(this, titleArray, entryArray );
        listView = (ListView) findViewById(R.id.listViewID);
        listView.setAdapter(custoomList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(ViewEntries.this, EntryDetail.class);
                String currentTitle = titleArray[position];
                String currentEntry = entryArray[position];
                String currentQuote = quoteArray[position];

                intent.putExtra(EXTRA_TITLE, currentTitle);
                intent.putExtra(EXTRA_ENTRY, currentEntry);
                intent.putExtra(EXTRA_QUOTE, currentQuote);

                startActivity(intent);
            }
        });
    }

    public void getArray() throws IOException {
        //Get the number of files, loop through and all entries and put in array
       int numOfFiles = getCounter();
       titleArray = new String[numOfFiles + 1];
       entryArray = new String[numOfFiles + 1];
       quoteArray = new String[numOfFiles + 1];
       int numOfElem = 0;

       for(int i = 0; i <= numOfFiles; ++i) {
           String count = Integer.toString(i);
           File file = new File(this.getFilesDir(), "userEntry" + count + ".txt");
           File quoteFile = new File(this.getFilesDir(), "quote" + count + ".txt");

           //If file exist, add title and entry to array
           if(file.exists() && quoteFile.exists()) {
               try {
                   BufferedReader reader = new BufferedReader(new FileReader(file));
                   String line = reader.readLine().toString();
                   titleArray[numOfElem] = line;

                   String entry = "";
                   while( (line = reader.readLine()) != null) {
                        entry += line.toString();
                   }
                   entryArray[numOfElem] = entry;

                   reader.close();

                   BufferedReader quoteReader = new BufferedReader(new FileReader(quoteFile));
                   String quote = "";
                   while( (line = quoteReader.readLine()) != null) {
                       quote += line.toString();
                   }
                   quoteArray[numOfElem] = quote;
                   ++numOfElem;
               } catch (IOException e) {
                   Log.d("ReadWriteFile", "Unable to read the TestFile.txt file.");
               }
           }

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
