package com.example.mentalhealth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by matt on 3/31/18.
 */

public class CustomListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;
    private final String[] titleTextArray;
    private final String[] entryTextArray;

    public CustomListAdapter(Activity context, String[] titleTextParam, String[] entryTextParam){

        super(context,R.layout.listview_row , titleTextParam);

        this.context = context;
        this.titleTextArray = titleTextParam;
        this.entryTextArray = entryTextParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row, null, true);

        TextView titleTextField = (TextView) rowView.findViewById(R.id.titleTextList);
        TextView entryTextField = (TextView) rowView.findViewById(R.id.entryTextList);

        titleTextField.setText(titleTextArray[position]);
        entryTextField.setText(entryTextArray[position]);

        return rowView;
    }
}
