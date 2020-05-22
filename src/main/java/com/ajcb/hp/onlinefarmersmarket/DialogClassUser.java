package com.ajcb.hp.onlinefarmersmarket;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class DialogClassUser extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public EditText address1,phone1,name1;
    public Button ok1;
public String dates;
    public DialogClassUser(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_class_user);
        address1 = (EditText) findViewById(R.id.address1);
        phone1 = (EditText) findViewById(R.id.phone1);
        name1 = (EditText) findViewById(R.id.name1);
        CalendarView c = (CalendarView)findViewById(R.id.calendarView) ;
        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month, int date) {
            dates = date+"/"+month+"/"+year;
            }
        });
        ok1 = (Button)findViewById(R.id.ok1);
        ok1.setOnClickListener(this);
    }
    public String getAddress(){
        return address1.getText().toString();
    }
    public String getPhone(){
        return phone1.getText().toString();
    }
    public String getNames(){
        return name1.getText().toString();
    }
    public String getDates(){return dates;}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok1:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}