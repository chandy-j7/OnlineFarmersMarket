package com.ajcb.hp.onlinefarmersmarket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UserPage extends AppCompatActivity {
    Context context=this;
    DatabaseReference reference,reference1,reference2;
    RecyclerView recyclerView1;
    ProgressDialog pd;
    ArrayList<Profile> list;
    TextView t;
    String sts;
    MyAdapter adapter;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static int boo = 0;
    TextView no,ten,thirty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        t = (TextView) findViewById(R.id.noprod);
        no = (TextView) findViewById(R.id.noexp);
        thirty = (TextView) findViewById(R.id.tenthirtyexp);
        ten = (TextView) findViewById(R.id.zerotenexp);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        recyclerView1 = (RecyclerView) findViewById(R.id.prodRecycler);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
    }
    public void noexp(View view){
        no.setTextColor(Color.parseColor("#008577"));
        ten.setTextColor(Color.parseColor("#000A09"));
        thirty.setTextColor(Color.parseColor("#000A09"));
        pd.show();
        reference2 = FirebaseDatabase.getInstance().getReference().child("post").child("No expiry");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot4) {
                list = new ArrayList<Profile>();
                for (DataSnapshot dataSnapshot5 : dataSnapshot4.getChildren()) {
                    reference2 = FirebaseDatabase.getInstance().getReference().child("post").child("No expiry").child(dataSnapshot5.getKey());
                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot8) {
                            for (DataSnapshot dataSnapshot6 : dataSnapshot8.getChildren()) {
                                Profile p = dataSnapshot6.getValue(Profile.class);
                                list.add(p);
                                t.setText("");
                            }
                            adapter = new MyAdapter(list, UserPage.this);
                            recyclerView1.setAdapter(adapter);

                        }

                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(context, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        pd.dismiss();
        if (recyclerView1.isAttachedToWindow()) {
            t.setText("no product available");
        } else {
            t.setText("");
        }
    }
    public void tenexp(View view){
        no.setTextColor(Color.parseColor("#000A09"));
        ten.setTextColor(Color.parseColor("#008577"));
        thirty.setTextColor(Color.parseColor("#000A09"));
        pd.show();
        reference = FirebaseDatabase.getInstance().getReference().child("post").child("0 - 10 days");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Profile>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    reference2 = FirebaseDatabase.getInstance().getReference().child("post").child("0 - 10 days").child(dataSnapshot1.getKey());
                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot4) {
                            for (DataSnapshot dataSnapshot6 : dataSnapshot4.getChildren()) {
                                Profile p = dataSnapshot6.getValue(Profile.class);
                                list.add(p);
                                t.setText("");
                            }
                            adapter = new MyAdapter(list, UserPage.this);
                            recyclerView1.setAdapter(adapter);

                        }

                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(context, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        pd.dismiss();
        if (recyclerView1.isAttachedToWindow()) {
            t.setText("no product available");
        } else {
            t.setText("");
        }
    }
    public void thirtyexp(View view){
        no.setTextColor(Color.parseColor("#000A09"));
        ten.setTextColor(Color.parseColor("#000A09"));
        thirty.setTextColor(Color.parseColor("#008577"));
        pd.show();
        reference1 = FirebaseDatabase.getInstance().getReference().child("post").child("10 - 30 days");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                list = new ArrayList<Profile>();
                for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                    reference2 = FirebaseDatabase.getInstance().getReference().child("post").child("10 - 30 days").child(dataSnapshot3.getKey());
                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot4) {
                            for (DataSnapshot dataSnapshot6 : dataSnapshot4.getChildren()) {
                                Profile p = dataSnapshot6.getValue(Profile.class);
                                list.add(p);
                                t.setText("");
                            }
                            adapter = new MyAdapter(list, UserPage.this);
                            recyclerView1.setAdapter(adapter);

                        }

                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(context, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        pd.dismiss();
        if (recyclerView1.isAttachedToWindow()) {
            t.setText("no product available");
        } else {
            t.setText("");
        }
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
                        UserPage.this);
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
                                Toast.makeText(UserPage.this,"Logged Out",Toast.LENGTH_LONG).show();
                                Intent i= new Intent(context,UserLogin.class);
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
                   /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            UserPage.this);
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
                                    Intent i= new Intent(context,UserLogin.class);
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
