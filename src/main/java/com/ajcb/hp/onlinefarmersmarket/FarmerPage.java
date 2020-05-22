package com.ajcb.hp.onlinefarmersmarket;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FarmerPage extends AppCompatActivity {
    Context context=this;
    DatabaseReference reference,reference1,reference2,reference3,reference4,reference5,reference6;
    RecyclerView recyclerView;
    ArrayList<Profile> list;
    TextView t;
    MyAdapter adapter;
    Profile p;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_page);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Loading...");
        pb.show();
        findViewById(R.id.fab).setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        t =(TextView)findViewById(R.id.nocomp);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<Profile>();

        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference().child("post").child("0 - 10 days").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        p = dataSnapshot1.getValue(Profile.class);
                        list.add(p);
                        t.setText("");
                        adapter = new MyAdapter(list, FarmerPage.this);
                        recyclerView.setAdapter(adapter);
                        pb.dismiss();
                    }
                }
                else {pb.dismiss();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FarmerPage.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                pb.dismiss();
            }
        });
        reference2 = FirebaseDatabase.getInstance().getReference().child("post").child("10 - 30 days").child(user.getUid());
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        p = dataSnapshot1.getValue(Profile.class);
                        list.add(p);
                        t.setText("");
                        adapter = new MyAdapter(list, FarmerPage.this);
                        recyclerView.setAdapter(adapter);
                        pb.dismiss();
                    }
                }
                else {pb.dismiss();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FarmerPage.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                pb.dismiss();
            }
        });
        reference3 = FirebaseDatabase.getInstance().getReference().child("post").child("No expiry").child(user.getUid());
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        p = dataSnapshot1.getValue(Profile.class);
                        list.add(p);
                        t.setText("");
                        adapter = new MyAdapter(list, FarmerPage.this);
                        recyclerView.setAdapter(adapter);
                        pb.dismiss();
                    }
                }
                else {pb.dismiss();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FarmerPage.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                pb.dismiss();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                                Intent i= new Intent(context,PostUpload.class);
                                startActivity(i);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logouts:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        FarmerPage.this);
                alertDialogBuilder.setTitle("Info");
                alertDialogBuilder.setMessage("Do you want to logout or exit the app?(App remembers the account)");
                alertDialogBuilder.setIcon(R.drawable.baseline_account_circle_black_48dp);
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        });
                alertDialogBuilder
                        .setNegativeButton("LOGOUT", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(FarmerPage.this,"Logged Out",Toast.LENGTH_LONG).show();
                                Intent i= new Intent(context,FarmerLogin.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            FarmerPage.this);
                    alertDialogBuilder.setTitle("Info");
                    alertDialogBuilder.setMessage("Do you want to logout or exit the app?(App remembers the account)");
                    alertDialogBuilder.setIcon(R.drawable.baseline_account_circle_black_48dp);
                    alertDialogBuilder
                            .setCancelable(true)
                            .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            });
                    alertDialogBuilder
                            .setNegativeButton("LOGOUT", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    FirebaseAuth.getInstance().signOut();
                                    Intent i= new Intent(context,FarmerLogin.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);

                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();*/
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
