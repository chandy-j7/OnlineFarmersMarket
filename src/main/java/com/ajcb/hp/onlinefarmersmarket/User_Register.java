package com.ajcb.hp.onlinefarmersmarket;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class User_Register extends AppCompatActivity {
EditText name,password,repassword;
Button Signup;
String email,passwords;
ProgressDialog pb;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register);
        mAuth =FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("User Register");
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        Signup = findViewById(R.id.signUpButton);
        pb = new ProgressDialog(this);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = name.getText().toString();
                passwords = password.getText().toString();
                if(email.equals("") && passwords.equals("")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            User_Register.this);
                    alertDialogBuilder.setTitle("Please enter E-mail and password to register");
                    alertDialogBuilder
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    alertDialogBuilder.setIcon(R.drawable.outline_error_outline_black_48dp);
                    alertDialogBuilder.setCancelable(true);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else if(email.equals("")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            User_Register.this);
                    alertDialogBuilder.setTitle("Please enter the Email Id to register");
                    alertDialogBuilder
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    alertDialogBuilder.setIcon(R.drawable.outline_error_outline_black_48dp);
                    alertDialogBuilder.setCancelable(true);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();}
                else if(password.equals("")){AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        User_Register.this);
                    alertDialogBuilder.setTitle("Please enter the password to register");
                    alertDialogBuilder
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    alertDialogBuilder.setIcon(R.drawable.outline_error_outline_black_48dp);
                    alertDialogBuilder.setCancelable(true);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();}
                if (repassword.getText().toString().equals(password.getText().toString())) {
                    if (email.equals("") && passwords.equals("")) {
                        email = "dksjfn";
                        passwords = "dskjcskj";
                    }
                    pb.setMessage("Registering User...");
                    pb.show();
                    su();
                    name.setText("");
                    password.setText("");
                }
                else if(!repassword.getText().toString().equals(password.getText().toString())){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            User_Register.this);
                    alertDialogBuilder.setTitle("ReTyped password is not same as the password typed.");
                    alertDialogBuilder
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    alertDialogBuilder.setIcon(R.drawable.outline_error_outline_black_48dp);
                    alertDialogBuilder.setCancelable(true);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            }
        });
    }

    void su() {
        mAuth.createUserWithEmailAndPassword(email, passwords)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pb.setCanceledOnTouchOutside(false);
                            pb.dismiss();
                            Toast.makeText(User_Register.this, "Account Registered Successfully.",
                                    Toast.LENGTH_SHORT).show();
                            //onFinishFragment();
                            User_Register.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            pb.setCanceledOnTouchOutside(false);
                            pb.dismiss();
                            Toast.makeText(User_Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    Intent intent = new Intent(User_Register.this, UserLogin.class);
                    startActivity(intent);
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
