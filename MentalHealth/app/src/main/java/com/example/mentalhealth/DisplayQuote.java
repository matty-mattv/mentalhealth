package com.example.mentalhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayQuote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_quote);

        Intent intent = getIntent();
        String quote = intent.getStringExtra(NewEntry.EXTRA_ENTRY);

        TextView displayEditText = (TextView) findViewById(R.id.quoteDisplay);
        displayEditText.setText(quote);
    }
}
