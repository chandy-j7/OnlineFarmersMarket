package com.ajcb.hp.onlinefarmersmarket;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class user_post_details extends AppCompatActivity {
    String exp,uid,desc,time,name,strstock,pr,sa1,sp1,dt,selectedItem,ranid,buyinf;
    int stock;
    DatabaseReference reference;
    ImageView img;
    TextView expiry,descrip,postedon,stocks,names,price,sa,sp,buyinfo;
    EditText qty;
    ProgressDialog pd;
    Spinner spinnerd;
    DatabaseReference reference1;
    File imagePath;
    Button buyprod;
    ArrayList<Profile> list;
    int sto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post_details);
        final DialogClassUser dcu=new DialogClassUser(user_post_details.this);
        final DialogClassUserNamePhone dcunp=new DialogClassUserNamePhone(user_post_details.this);
        pd = new ProgressDialog(this);
        sa1 = "1";
        exp = getIntent().getExtras().getString("Expiry");
        uid = getIntent().getExtras().getString("Uid");
        desc = getIntent().getExtras().getString("Desc");
        name = getIntent().getExtras().getString("Name");
        stock = Integer.parseInt(getIntent().getExtras().getString("Stocks"));
        time = getIntent().getExtras().getString("Time");
        pr = getIntent().getExtras().getString("Price");
        sa1 = getIntent().getExtras().getString("SelfAddress");
        sp1 = getIntent().getExtras().getString("SelfPhone");
        dt = getIntent().getExtras().getString("DeliveryType");
        ranid = getIntent().getExtras().getString("RandomId");
        img = (ImageView) findViewById(R.id.compimg1);
        names = (TextView) findViewById(R.id.productname1);
        descrip = (TextView) findViewById(R.id.textdesc1);
        stocks = (TextView) findViewById(R.id.stock1);
        postedon = (TextView) findViewById(R.id.postedon1);
        buyinfo = (TextView) findViewById(R.id.buyinfo);
        spinnerd = (Spinner) findViewById(R.id.delivmeth);
        expiry = (TextView) findViewById(R.id.exp1);
        price = (TextView) findViewById(R.id.price1);
        buyprod = (Button)findViewById(R.id.buyprod1);
        qty = (EditText)findViewById(R.id.qty1);
        sa = (TextView) findViewById(R.id.selfaddress);
        sp = (TextView) findViewById(R.id.selfphone);
        pd.setMessage("Complaint Loading...");
        pd.show();
        expiry.setText(exp);
        postedon.setText("POSTED ON: "+time);
        stocks.setText("PRODUCT STOCK AVAILABLE: "+stock);
        descrip.setText(desc);
        names.setText(name);
        price.setText(pr);
        if(sa1==null){
            sa.setText("");
            sp.setText("");
        }
        else{
        sa.setText("SelfPickup Address: "+sa1);
        sp.setText("SelfPickup Phone: "+sp1);}

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference1 = FirebaseDatabase.getInstance().getReference().child("buyers").child(exp).child(uid).child(user.getUid()).child(ranid);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                if (dataSnapshot2.exists()) {
                    BuyerProfile p = dataSnapshot2.getValue(BuyerProfile.class);
                    buyinfo.setText("Product already ordered,"+"\n"+"Stock ordered: " + p.getStoc() +"\n"+ "DueDate: " + p.getDates());
                }
            }

            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(user_post_details.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        ArrayAdapter<String> myada = new ArrayAdapter<String>(user_post_details.this,
                android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.deliverymethod));
        myada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerd.setAdapter(myada);
        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference riversRef = mStorageRef.child("post").child(exp).child(uid).child(ranid);
        final long ONE_MEGABYTE = 1024 * 1024;
        riversRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    public void onSuccess(byte[] bytes) {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        img.setImageBitmap(bm);
                        pd.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });



        spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
               /* if (getIntent().getExtras().getString("DeliveryType").equals("DoorDelivery or SelfPickup")) {

                }*/
               if(selectedItem.equals("--SELECT--")){

               }
               else if(getIntent().getExtras().getString("DeliveryType").equals(selectedItem)){
                   if (selectedItem.equals("DoorDelivery Only")) {
                       // do your stuff
                       dcu.show();

                   } else {
                       dcunp.show();
                   }
               }
               else if(getIntent().getExtras().getString("DeliveryType").equals("DoorDelivery or SelfPickup")){
                    if (selectedItem.equals("DoorDelivery Only")) {
                        // do your stuff
                        dcu.show();
                    } else {
                        dcunp.show();
                    }
                }
                else {
                   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                           user_post_details.this);
                   alertDialogBuilder.setTitle("Info");
                   alertDialogBuilder.setMessage("You Cannot select this delivery method");
                   alertDialogBuilder.setIcon(R.drawable.outline_error_outline_black_48dp);
                   alertDialogBuilder
                           .setCancelable(false)
                           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {

                               }
                           });
                   AlertDialog alertDialog = alertDialogBuilder.create();
                   alertDialog.show();
               }
            }
             // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });


            buyprod.setOnClickListener(new View.OnClickListener() {
    @Override
            public void onClick(View view) {
        sto = Integer.parseInt(qty.getText().toString());

                            if(stock<sto){
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        user_post_details.this);
                                alertDialogBuilder.setTitle("Info");
                                alertDialogBuilder.setMessage("Product Not Available");
                                alertDialogBuilder.setIcon(R.drawable.outline_error_outline_black_48dp);
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }else if(stock>=sto) {
                                stock = stock - sto;
                                strstock = String.valueOf(stock);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef1 = database.getReference("post");
                                myRef1.child(exp).child(uid).child(ranid).child("stocks").setValue(strstock);
                                FirebaseDatabase databased = FirebaseDatabase.getInstance();
                                DatabaseReference myRef1s = databased.getReference("buyers");
                                if(selectedItem.equals("DoorDelivery Only")) {
                                    FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                                    myRef1s.child(exp).child(uid).child(u.getUid()).child(ranid).child("names").setValue(dcu.getNames() + "(" + dcu.getPhone() + ")");
                                    myRef1s.child(exp).child(uid).child(u.getUid()).child(ranid).child("address").setValue("address: "+dcu.getAddress());
                                    myRef1s.child(exp).child(uid).child(u.getUid()).child(ranid).child("stoc").setValue(String.valueOf(sto));
                                    myRef1s.child(exp).child(uid).child(u.getUid()).child(ranid).child("dates").setValue(dcu.getDates());
                                }
                                if(selectedItem.equals("SelfPickup Only")){
                                    FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                                    myRef1s.child(exp).child(uid).child(u.getUid()).child(ranid).child("names").setValue(dcunp.getNam() + "(" + dcunp.getPhon() + ")");
                                    myRef1s.child(exp).child(uid).child(u.getUid()).child(ranid).child("address").setValue("Self Pickup");
                                    myRef1s.child(exp).child(uid).child(u.getUid()).child(ranid).child("stoc").setValue(String.valueOf(sto));
                                    myRef1s.child(exp).child(uid).child(u.getUid()).child(ranid).child("dates").setValue(dcunp.getDates1());
                                }
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        user_post_details.this);
                                alertDialogBuilder.setTitle("Info");
                                alertDialogBuilder.setMessage("Process Successfull Product is available");
                                alertDialogBuilder.setIcon(R.drawable.outline_error_outline_black_48dp);
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent i = new Intent(user_post_details.this,UserPage.class);
                                                startActivity(i);
                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        }
                    });
                }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    user_post_details.this.finish();
                    Intent i= new Intent(user_post_details.this,UserPage.class);
                    startActivity(i);

            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
