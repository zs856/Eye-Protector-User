package comp5216.sydney.edu.au.garbagecollection;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CollectionTimeForm extends LinearLayout {
    EditText mTime;
    EditText mDate;
    final Calendar myCalendar = Calendar.getInstance();



    public CollectionTimeForm(final Context context, AttributeSet attrs){
        super(context, attrs);
        inflate(context, R.layout.form_collection_time, this);

        mTime = (EditText) findViewById(R.id.time_value);
        mDate = (EditText) findViewById(R.id.date_value);

        mDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });

        mTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(context,time, 10,0,true).show();
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hr, int min) {
            mTime.setText((String.format("%02d", hr) + ":" +String.format("%02d", min)));
        }
    };


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mDate.setText(sdf.format(myCalendar.getTime()));

    }
}
