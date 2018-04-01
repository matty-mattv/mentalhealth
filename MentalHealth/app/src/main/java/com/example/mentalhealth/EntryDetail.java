package com.example.mentalhealth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class EntryDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(ViewEntries.EXTRA_TITLE);
        String entry = bundle.getString(ViewEntries.EXTRA_ENTRY);
        String quote = bundle.getString(ViewEntries.EXTRA_QUOTE);

        TextView titleEditText = (TextView) findViewById(R.id.entryTitleDetailID);
        EditText entryEditText = (EditText) findViewById(R.id.entryTextDetailID);
        EditText quoteEditText = (EditText) findViewById(R.id.quoteTextDetailID);

        titleEditText.setText(title);
        entryEditText.setText(entry);
        quoteEditText.setText(quote);

    }
}
