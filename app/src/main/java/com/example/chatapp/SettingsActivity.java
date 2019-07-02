package com.example.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import pl.aprilapps.easyphotopicker.EasyImage;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    //Android Layout
    private CircleImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;

    private Button mStatusBtn;
    private Button mImageBtn;

    //storage Firebase
    StorageReference mImageStorage;

    private ProgressDialog mProgressDialog;

    String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayImage = findViewById(R.id.settings_image);
        mName = findViewById(R.id.settings_name);
        mStatus = findViewById(R.id.settings_status);

        mStatusBtn = findViewById(R.id.settings_status_btn);
        mImageBtn = findViewById(R.id.settings_image_btn);

        mImageStorage = FirebaseStorage.getInstance().getReference();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mName.setText(name);
                mStatus.setText(status);

                byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                String s = null;
                try {
                    s = new String(decodedString, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Uri stringURI = Uri.parse(s);
                try {


                    Glide.with(SettingsActivity.this)
                            .load(decodedString)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(mDisplayImage);
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        current_user_id = mCurrentUser.getUid();

        mStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status_value = mStatus.getText().toString();

                Intent status_intent = new Intent(SettingsActivity.this, StatusActivity.class);
                status_intent.putExtra("status_value", status_value);
                startActivity(status_intent);
            }
        });

        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EasyImage.openChooserWithGallery(SettingsActivity.this, "Photo", 0);
            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {

            }

            @Override
            public void onImagePicked(final File imageFile, EasyImage.ImageSource source, int type) {
                if (type == 0) {

                    mProgressDialog = new ProgressDialog(SettingsActivity.this);
                    mProgressDialog.setTitle("Uploading Image...");
                    mProgressDialog.setMessage("Please wait while we upload and process the image.");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

//                    final Uri result = Uri.fromFile(imageFile);

                    final String name = getFileToByte(imageFile.getAbsolutePath());


                    Bitmap thumb_bitmap = null;
                    try {
                        thumb_bitmap = new Compressor(SettingsActivity.this)
                                .setMaxWidth(200)
                                .setMaxHeight(200)
                                .setQuality(75)
                                .compressToBitmap(imageFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    final byte[] thumb_byte = baos.toByteArray();


                    StorageReference filePath = mImageStorage.child("profile_images").child(current_user_id)
                            .child(current_user_id + ".jpg");
                    final StorageReference thumb_filepath = mImageStorage.child("profile_images").child("thumbs")
                            .child(current_user_id).child(current_user_id + ".jpg");

                    filePath.putFile(Uri.fromFile(imageFile)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {

                                UploadTask uploadTask = thumb_filepath.putBytes(thumb_byte);
                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {

                                        if (thumb_task.isSuccessful()) {

                                            Map update_hashMap = new HashMap();
                                            update_hashMap.put("image", name);
                                            update_hashMap.put("thumb_image", name);

                                            mUserDatabase.updateChildren(update_hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        mProgressDialog.dismiss();
                                                        Toast.makeText(SettingsActivity.this, "Success uploading.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(SettingsActivity.this, "Error in uploading thumbnail.", Toast.LENGTH_SHORT).show();
                                            mProgressDialog.dismiss();
                                        }

                                    }
                                });

                            } else {
                                Toast.makeText(SettingsActivity.this, "Error in uploading.", Toast.LENGTH_SHORT).show();
                                mProgressDialog.dismiss();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    //base64 code
    public static String getFileToByte(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 75, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }
}
