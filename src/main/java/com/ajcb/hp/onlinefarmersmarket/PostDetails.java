package com.ajcb.hp.onlinefarmersmarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class PostDetails extends AppCompatActivity {
    String exp,uid,desc,time,name,pr,dt,rid;
    int stock;
    DatabaseReference reference1;
    ImageView img;
    TextView expiry,descrip,postedon,stocks,names,price,deliveryt;
    ProgressDialog pd;
    File imagePath;
    Button buyprod;
    ArrayList<Profile> list;
    ArrayList<Item> Buyers=new ArrayList<>();
    ListView simpleList;
    int sto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        pd = new ProgressDialog(this);
        exp = getIntent().getExtras().getString("Expiry");
        uid = getIntent().getExtras().getString("Uid");
        desc = getIntent().getExtras().getString("Desc");
        name = getIntent().getExtras().getString("Name");
        stock = Integer.parseInt(getIntent().getExtras().getString("Stocks"));
        time = getIntent().getExtras().getString("Time");
        pr = getIntent().getExtras().getString("Price");
        dt = getIntent().getExtras().getString("DeliveryType");
        rid = getIntent().getExtras().getString("RandomId");
        img = (ImageView) findViewById(R.id.compimg1);
        names = (TextView) findViewById(R.id.productname1);
        descrip = (TextView) findViewById(R.id.textdesc1);
        stocks = (TextView) findViewById(R.id.stock1);
        postedon = (TextView) findViewById(R.id.postedon1);
        deliveryt = (TextView) findViewById(R.id.deliverytype);
        expiry = (TextView) findViewById(R.id.exp1);
        buyprod = (Button)findViewById(R.id.buyprod1);
        price = (TextView) findViewById(R.id.price2);
        simpleList = (ListView)findViewById(R.id.buyers);
        pd.setMessage("Complaint Loading...");
        pd.show();
        expiry.setText(exp);
        postedon.setText("POSTED ON: "+time);
        stocks.setText("PRODUCT STOCK AVAILABLE: "+stock);
        descrip.setText(desc);
        names.setText(name);
        price.setText(pr);
        deliveryt.setText(dt);


        reference1 = FirebaseDatabase.getInstance().getReference().child("buyers").child(exp).child(uid);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    reference1 = FirebaseDatabase.getInstance().getReference().child("buyers").child(exp).child(uid).child(dataSnapshot1.getKey()).child(rid);
                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                BuyerProfile p = dataSnapshot2.getValue(BuyerProfile.class);
                                Buyers.add(new Item(p.getNames(), p.getAddress(), p.getStoc(), p.getDates()));


                            final MyAdapterList myAdapterList = new MyAdapterList(PostDetails.this, R.layout.list_view_items, Buyers);
                            simpleList.setAdapter(myAdapterList);
                        }

                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(PostDetails.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PostDetails.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });




        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference riversRef = mStorageRef.child("post").child(exp).child(uid).child(rid);
        final long ONE_MEGABYTE = 1024 * 1024;
        riversRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    public void onSuccess(byte[] bytes) {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        /*DisplayMetrics dm = new DisplayMetrics();
                        WindowManager windowmanager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                        windowmanager.getDefaultDisplay().getMetrics(dm);

                        holder.pic.setMinimumHeight(dm.heightPixels);
                        holder.pic.setMinimumWidth(dm.widthPixels);*/
                        img.setImageBitmap(bm);
                        pd.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    PostDetails.this.finish();
                    Intent i= new Intent(PostDetails.this,FarmerPage.class);
                    startActivity(i);

            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
