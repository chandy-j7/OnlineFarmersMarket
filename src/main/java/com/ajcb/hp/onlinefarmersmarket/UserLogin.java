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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.ajcb.hp.onlinefarmersmarket.FarmerLogin.flag;
public class UserLogin extends AppCompatActivity {
private EditText username,password;
private Button signIn,ToRegister;
String email,passwords;
ProgressDialog pb;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mAuth =FirebaseAuth.getInstance();


        getSupportActionBar().setTitle("User Login");
        pb = new ProgressDialog(this);
        username = (EditText)findViewById(R.id.email1);
        password = (EditText)findViewById(R.id.password1);
        signIn = (Button) findViewById(R.id.signInButton);
        ToRegister = (Button) findViewById(R.id.sendVerificationButton);
        email = username.getText().toString().trim();
        passwords = password.getText().toString().trim();
        ToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLogin.this, User_Register.class);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = username.getText().toString().trim();
                passwords = password.getText().toString().trim();
                if(email.equals("") && passwords.equals("")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            UserLogin.this);
                    alertDialogBuilder.setTitle("Please enter E-mail and password to login");
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
                            UserLogin.this);
                    alertDialogBuilder.setTitle("Please enter the Email Id to login");
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
                else if(passwords.equals("")){AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        UserLogin.this);
                    alertDialogBuilder.setTitle("Please enter the password to login");
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
                else {
                    pb.setMessage("Logging in...");
                    pb.show();
                    lg();
                    password.setText("");
                }

            }
        });
    }

    void lg(){
        mAuth.signInWithEmailAndPassword(email,passwords)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            flag = 1;
                            pb.dismiss();
                            Toast.makeText(UserLogin.this, "Login successful.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("names");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Intent i= new Intent(UserLogin.this,UserPage.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            pb.dismiss();
                            Toast.makeText(UserLogin.this, "Authentication failed.",
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
                    Intent intent = new Intent(UserLogin.this,Splash.class);
                    startActivity(intent);
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
