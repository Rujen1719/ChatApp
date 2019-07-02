//package com.example.chatapp;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.util.Base64;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {
//
//    ArrayList<ChatsFragmentData> arrayList = new ArrayList<>();
//    Activity activity;
//
//    public ChatsAdapter(ArrayList<ChatsFragmentData> arrayList, Activity activity) {
//        this.arrayList = arrayList;
//        this.activity = activity;
//    }
//
//    @NonNull
//    @Override
//    public ChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chats_single_layout, viewGroup, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder viewHolder, final int i) {
//        Log.e("asdasd5", new Gson().toJson(arrayList));
//        viewHolder.mName.setText(arrayList.get(i).getName());
//        String image = arrayList.get(i).getImage();
//
//        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        String s = null;
//        try {
//            s = new String(decodedString, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Uri stringURI = Uri.parse(s);
//        try {
////                    Glide.with(context.getApplicationContext()).load(decodedString).into(messageViewHolder.profileImage);
//            viewHolder.mImage.setImageBitmap(decodedByte);
//        } catch (Exception e) {
//            Log.e("asdasd1", new Gson().toJson(e.getMessage()));
//            e.getLocalizedMessage();
//        }
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView mMessageText;
//        TextView mName;
//        CircleImageView mImage;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mMessageText = itemView.findViewById(R.id.chats_single_message);
//            mName = itemView.findViewById(R.id.chats_single_name);
//            mImage = itemView.findViewById(R.id.chats_single_image);
//        }
//    }
//}
