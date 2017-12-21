package com.ubb.zeb.mastermind;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by zeb on 12/21/17.
 */

public class DatePickerReminder extends AppCompatActivity{

    private static final String TAG = "Sebi - DatePicker";

    TextView displayDate;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_date_reminder);

        displayDate = (TextView) findViewById(R.id.textView_date);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DatePickerReminder.this, // s-ar putea sa nu mearga
                        android.R.style.Theme_Dialog,
                        dateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT)
                );
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d(TAG, "onDateSet");

                String date = month + " / " + dayOfMonth + " / " + year;
                displayDate.setText(date);
            }
        };

    }
}