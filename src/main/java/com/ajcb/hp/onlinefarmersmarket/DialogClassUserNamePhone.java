package com.ajcb.hp.onlinefarmersmarket;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

public class DialogClassUserNamePhone extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public EditText nam1,phon1;
    public Button o1;
    public  String dates1;

    public DialogClassUserNamePhone(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_class_user_name_phone);
        nam1 = (EditText) findViewById(R.id.nam1);
        phon1 = (EditText) findViewById(R.id.phon1);
        o1 = (Button)findViewById(R.id.o1);
        CalendarView c1 = (CalendarView)findViewById(R.id.calendarView1) ;
        c1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month, int date) {
                dates1 = date+"/"+month+"/"+year;
            }
        });
        o1.setOnClickListener(this);
    }
    public String getNam(){
        return nam1.getText().toString();
    }
    public String getPhon(){
        return phon1.getText().toString();
    }
    public String getDates1(){return dates1;}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.o1:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}