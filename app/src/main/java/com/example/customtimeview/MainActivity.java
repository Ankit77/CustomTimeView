package com.example.customtimeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created By S.B on 19/04/2016
 * DateView with its DatePicker and Custom Border which color we want we does.
 */
public class MainActivity extends AppCompatActivity implements CustomDateandTimeView.dateTimeSelecter {
    /***
     * Initialization of DateView and year,month,day for DateTime Picker Dialog
     */

    /**
     * Gives Date picker id for Dialog Size
     * /**
     *
     * @param savedInstanceState this is inbuilt parameter which is called automatically with onCreate Method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomDateandTimeView customDateandTimeView = (CustomDateandTimeView) findViewById(R.id.customtimeview);
        customDateandTimeView.setDateTimeSelecter(this);
    }

    @Override
    public void onSelect() {
        Toast.makeText(getApplicationContext(), "sdk1234", Toast.LENGTH_LONG).show();
    }
}