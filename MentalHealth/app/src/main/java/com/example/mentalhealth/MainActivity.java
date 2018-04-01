package com.example.mentalhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    public void clearEntries(View view) {
        //IMPLEMENT LATER
//        File countFile = new File(this.getFilesDir(), "counter.txt");
//
//        for(int i = 0; i < )
//        File file = new File(this.getFilesDir(), "userEntry.txt");
//
//        if(file.delete()) {
//            Toast.makeText(this, "All entries celared", Toast.LENGTH_LONG).show();
//        }
    }
}
