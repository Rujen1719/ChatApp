package com.example.chatapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> mMessageList;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    Context context;

    public MessageAdapter(List<Messages> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_single_layout, viewGroup, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, int i) {

        Messages c = mMessageList.get(i);
        messageViewHolder.messageText.setText(c.getMessage());

        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();
        String fromUser = c.getFrom();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUser);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                Log.e("asdasd", new Gson().toJson(image));

                messageViewHolder.messageName.setText(name);

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
//                    Glide.with(context.getApplicationContext()).load(decodedString).into(messageViewHolder.profileImage);
                    messageViewHolder.profileImage.setImageBitmap(decodedByte);
                } catch (Exception e) {
                    Log.e("asdasd1", new Gson().toJson(e.getMessage()));
                    e.getLocalizedMessage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        if (fromUser != null && currentUserId != null && fromUser.equals(currentUserId)) {
//            messageViewHolder.messageText.setBackgroundColor(Color.WHITE);
//            messageViewHolder.messageText.setTextColor(Color.BLACK);
//        } else {
//            messageViewHolder.messageText.setBackgroundResource(R.drawable.message_text_background);
//            messageViewHolder.messageText.setTextColor(Color.WHITE);
//        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        CircleImageView profileImage;
        TextView messageName;
        TextView messageTime;


        public MessageViewHolder(View view) {
            super(view);

            messageText = view.findViewById(R.id.message_text_layout);
            profileImage = view.findViewById(R.id.message_profile_icon);
            messageName = view.findViewById(R.id.message_name);
            messageTime = view.findViewById(R.id.message_time);
        }
    }
}
