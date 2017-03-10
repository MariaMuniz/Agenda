package com.example.cida.agenda.app;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by AdilsonMarcelino on 21/07/2016.
 */
public class ViewHelp {
public static ArrayAdapter<String> createArrayAdapter(Context ctx, Spinner spinner)
    {
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        return arrayAdapter;
}
}
