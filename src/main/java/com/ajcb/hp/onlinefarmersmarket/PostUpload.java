package com.ajcb.hp.onlinefarmersmarket;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class PostUpload extends AppCompatActivity {
    private static final int REQUEST_CODE = 1000;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    Context context=this;
    Button post;
    ImageButton gallery,camera;
    TextView t,dp,n;
    String exp,des,name,sto,pr,wt;
    EditText description,stocks,prodname,price;
    ImageView showImage;
    Uri file,photourl;
    String url;
    ProgressDialog pb;
    Bitmap bitmap;
    Spinner spinner,weight;
    Task<Uri> downloadUri;
    CheckBox doordelivery,selfpickup;
    double lat;
    double lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_upload);
        findViewById(R.id.cv).setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        findViewById(R.id.ok1).setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        final int random = new Random().nextInt(100000) + 2000;
        pb = new ProgressDialog(this);
       final CustomDialogClass cdd=new CustomDialogClass(PostUpload.this);
        post = (Button) findViewById(R.id.ok1);
        gallery = (ImageButton) findViewById(R.id.addi);
        camera = (ImageButton) findViewById(R.id.camera);
        description = (EditText) findViewById(R.id.dept);
        prodname = (EditText) findViewById(R.id.prodname);
        stocks = (EditText) findViewById(R.id.stock1);
        spinner = (Spinner) findViewById(R.id.spinner);
        price = (EditText) findViewById(R.id.price);
        weight = (Spinner) findViewById(R.id.weight);
        doordelivery = (CheckBox) findViewById(R.id.doordelivery);
        selfpickup = (CheckBox) findViewById(R.id.selfpickup);
        ArrayAdapter<String> myada = new ArrayAdapter<String>(PostUpload.this,
                android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.expiry));
        myada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myada);

        ArrayAdapter<String> myada1 = new ArrayAdapter<String>(PostUpload.this,
                android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.weight));
        myada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight.setAdapter(myada1);


        selfpickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selfpickup.isChecked()) {
                    // your code to checked checkbox
                    cdd.show();
                } else {

                }
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), 7);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (getFromPref(context, ALLOW_KEY)) {
                        showSettingsAlert();
                    } else if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.CAMERA)

                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (getFromPref(context, ALLOW_KEY)) {
                        showSettingsAlert2();
                    } else if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)

                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            showSettingsAlert2();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    openCamera();
                }
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (description.getText().toString().equals("") || price.getText().toString().equals("") || weight.getSelectedItem().toString() == "--SELECT--" || (bitmap == null) || spinner.getSelectedItem().toString() == "--SELECT--" || prodname.getText().toString().equals("") || stocks.getText().toString().equals("") || (!selfpickup.isChecked() && !doordelivery.isChecked())) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            PostUpload.this);
                    alertDialogBuilder.setTitle("Please fill up all fields and select image file");
                    alertDialogBuilder.setMessage("");
                    alertDialogBuilder.setIcon(R.drawable.outline_error_outline_black_48dp);
                    alertDialogBuilder.setCancelable(true);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    pb.setMessage("uploading");
                    pb.show();
                    pb.setCanceledOnTouchOutside(false);
                    des = description.getText().toString();
                    name = prodname.getText().toString();
                    sto = stocks.getText().toString();
                    exp = spinner.getSelectedItem().toString();
                    pr = price.getText().toString();
                    wt = weight.getSelectedItem().toString();
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = sdf.format(calendar.getTime());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("post");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("description").setValue(des);
                    myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("name").setValue(name);
                    myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("expiry").setValue(exp);
                    myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("stocks").setValue(sto);
                    myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("uid").setValue(user.getUid());
                    myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("time").setValue(strDate);
                    myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("price").setValue(pr+"₹"+wt);
                    myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("randomID").setValue(String.valueOf(random));
                    if(doordelivery.isChecked() && selfpickup.isChecked()){
                        myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("DeliveryType").setValue("DoorDelivery or SelfPickup");
                        myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("selfaddress").setValue(cdd.getAddress());
                        myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("selfphone").setValue(cdd.getPhone());
                    }
                    else if(doordelivery.isChecked()){
                        myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("DeliveryType").setValue("DoorDelivery Only");
                    }
                    else {
                        myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("selfaddress").setValue(cdd.getAddress());
                        myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("selfphone").setValue(cdd.getPhone());
                        myRef.child(exp).child(user.getUid()).child(String.valueOf(random)).child("DeliveryType").setValue("SelfPickup Only");
                    }
                    StorageReference mStorageRef;
                    mStorageRef = FirebaseStorage.getInstance().getReference();
                    //Uri file = Uri.fromFile(new File("file:///android_asset/splash.jpg"));
                    final StorageReference riversRef = mStorageRef.child("post").child(exp).child(user.getUid()).child(String.valueOf(random));


                    riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    pb.dismiss();
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            PostUpload.this);
                                    alertDialogBuilder.setTitle("Product Posted");
                                    alertDialogBuilder
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                    Intent i= new Intent(context,FarmerPage.class);
                                                    startActivity(i);
                                                }
                                            });
                                    alertDialogBuilder.setIcon(R.drawable.baseline_send_black_48dp);
                                    alertDialogBuilder.setCancelable(false);
                                    alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialogInterface) {
                                            PostUpload.this.finish();
                                            Intent i= new Intent(context,FarmerPage.class);
                                            startActivity(i);
                                        }
                                    });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();

                                    //Do what you need to do with url
                                }
                            });
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            PostUpload.this);
                                    alertDialogBuilder.setTitle("Product not Posted");
                                    alertDialogBuilder.setMessage("");
                                    alertDialogBuilder.setIcon(android.R.drawable.ic_delete);
                                    alertDialogBuilder.setCancelable(true);
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                }
                            });


                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            file = data.getData();

            try {

                // Getting selected image into Bitmap.
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), file);
                gallery.setImageBitmap(bitmap);
                camera.setImageResource(R.drawable.baseline_camera_alt_black_48dp);
                // Setting up bitmap selected image into ImageView.

                // After selecting image change choose button above text.


            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
        else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                camera.setImageBitmap(bitmap);
                gallery.setImageResource(R.drawable.round_collections_black_48dp);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
                file = Uri.parse(path);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }
    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }
    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(PostUpload.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(PostUpload.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }
    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(PostUpload.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(PostUpload.this);
                    }
                });

        alertDialog.show();
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];

                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean
                                showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        this, permission);

                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(PostUpload.this, ALLOW_KEY, true);
                        }
                    }
                }
            }
            case REQUEST_CODE: {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    protected void onResume() {
        super.onResume();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent,1);
    }
    private void showSettingsAlert2() {
        AlertDialog alertDialog = new AlertDialog.Builder(PostUpload.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the External Storage.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(PostUpload.this);
                    }
                });

        alertDialog.show();
    }
    private void showAlert2() {
        AlertDialog alertDialog = new AlertDialog.Builder(PostUpload.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the External Storage");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(PostUpload.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    PostUpload.this.finish();
                    Intent i= new Intent(context,FarmerPage.class);
                    startActivity(i);

            }

        }
        return super.onKeyDown(keyCode, event);
    }

}
