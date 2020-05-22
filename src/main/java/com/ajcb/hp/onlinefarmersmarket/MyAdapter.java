package com.ajcb.hp.onlinefarmersmarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import static com.ajcb.hp.onlinefarmersmarket.FarmerLogin.flag;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHoder> {

    List<Profile> list;
    Context context;
    static int f1 = 0;
    String url = "u";
    public MyAdapter(List<Profile> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;

    }
    @Override
    public void onBindViewHolder(final MyHoder holder, final int position) {
        final Profile mylist = list.get(position);
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        holder.time.setText("posted on: " + mylist.getTime());
        holder.Expiry.setText(mylist.getExpiry());
        holder.name.setText(mylist.getName());
        holder.price.setText(mylist.getPrice());
        holder.Desc.setText(mylist.getDescription());
        holder.stocks.setText("Stock available: " + mylist.getStocks());
        holder.paymeth.setText(mylist.getDeliveryType());
        DatabaseReference reference;
        StorageReference mStorageRef,storageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference riversRef = mStorageRef.child("post").child(mylist.getExpiry()).child(mylist.getUid()).child(mylist.getRandomid());
        final long ONE_MEGABYTE = 1024 * 1024;
        riversRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    public void onSuccess(byte[] bytes) {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.pic.setImageBitmap(bm);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        if(flag==1) {
            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), user_post_details.class);
                    i.putExtra("Expiry", mylist.getExpiry());
                    i.putExtra("Uid", mylist.getUid());
                    i.putExtra("Desc", mylist.getDescription());
                    i.putExtra("Name", mylist.getName());
                    i.putExtra("Time", mylist.getTime());
                    i.putExtra("Stocks", mylist.getStocks());
                    i.putExtra("Price", mylist.getPrice());
                    i.putExtra("DeliveryType", mylist.getDeliveryType());
                    i.putExtra("SelfAddress", mylist.getSelfaddress());
                    i.putExtra("SelfPhone", mylist.getSelfphone());
                    i.putExtra("RandomId", mylist.getRandomid());
                    v.getContext().startActivity(i);
                }
            });
        }
        if(flag==0) {
            holder.d.setVisibility(View.VISIBLE);
            holder.d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);
                    alertDialogBuilder.setTitle("Info");
                    alertDialogBuilder.setMessage("Do you want to delete the product post?");
                    alertDialogBuilder.setIcon(R.drawable.outline_error_outline_black_48dp);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    DatabaseReference myRef = ref.child("post").child(mylist.getExpiry()).child(mylist.getUid());
                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            dataSnapshot.getRef().removeValue();
                                            f1 = 1;
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                        }
                                    });
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            context);
                                    alertDialogBuilder.setTitle("Complaint will be deleted shortly...");
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
                            });
                    alertDialogBuilder
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            });
        }
        if(flag==0) {
            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), PostDetails.class);
                    i.putExtra("Expiry", mylist.getExpiry());
                    i.putExtra("Uid", mylist.getUid());
                    i.putExtra("Desc", mylist.getDescription());
                    i.putExtra("Name", mylist.getName());
                    i.putExtra("Time", mylist.getTime());
                    i.putExtra("Stocks", mylist.getStocks());
                    i.putExtra("Price", mylist.getPrice());
                    i.putExtra("DeliveryType", mylist.getDeliveryType());
                    i.putExtra("SelfAddress", mylist.getSelfaddress());
                    i.putExtra("SelfPhone", mylist.getSelfphone());
                    i.putExtra("RandomId", mylist.getRandomid());
                    v.getContext().startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        int arr = 0;

        try{

                arr=list.size();



        }catch (Exception e){



        }

        return arr;

    }

    class MyHoder extends RecyclerView.ViewHolder{
        TextView Expiry,name,Desc,time,stocks,price,paymeth;
        ImageView pic;
        CardView cv;
        Button d;



        public MyHoder(View itemView) {
            super(itemView);
            Expiry = (TextView) itemView.findViewById(R.id.expire);
            name= (TextView) itemView.findViewById(R.id.productname1);
            pic = (ImageView) itemView.findViewById(R.id.imageView2);
            Desc= (TextView) itemView.findViewById(R.id.description);
            stocks = (TextView) itemView.findViewById(R.id.stocks);
            price = (TextView) itemView.findViewById(R.id.price);
            paymeth = (TextView) itemView.findViewById(R.id.paymeth);
            cv = (CardView) itemView.findViewById(R.id.cardview);
            d = (Button) itemView.findViewById(R.id.delete);
            time =(TextView) itemView.findViewById(R.id.postedOn);
        }
    }
}