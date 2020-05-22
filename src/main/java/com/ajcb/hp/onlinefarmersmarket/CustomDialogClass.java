package com.ajcb.hp.onlinefarmersmarket;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public EditText address,phone;
    public Button ok;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.farmerdialog);
        address = (EditText) findViewById(R.id.address1);
        phone = (EditText) findViewById(R.id.phone1);
        ok = (Button)findViewById(R.id.ok1);
        ok.setOnClickListener(this);
    }
    public String getAddress(){
        return address.getText().toString();
    }
    public String getPhone(){
        return phone.getText().toString();
    }

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