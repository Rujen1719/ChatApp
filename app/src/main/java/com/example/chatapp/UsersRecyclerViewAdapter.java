package com.example.chatapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder> {

    Activity activity;

    ArrayList<Users> arrayList = new ArrayList<>();


    public UsersRecyclerViewAdapter(Activity activity, ArrayList<Users> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public UsersRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_single_layout, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull UsersRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.mDisplayName.setText(arrayList.get(i).getName());
        viewHolder.mStatus.setText(arrayList.get(i).getStatus());

        byte[] decodedString = Base64.decode(arrayList.get(i)
                .getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        String s = null;
        try {
            s = new String(decodedString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Uri stringURI = Uri.parse(s);

        Glide.with(activity).load(decodedString)
                .into(viewHolder.mImageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, ProfileActivity.class)
                        .putExtra("id", arrayList.get(i).getId()));
                activity.finish();

            }

        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_single_name)
        TextView mDisplayName;
        @BindView(R.id.user_single_status)
        TextView mStatus;
        @BindView(R.id.user_single_image)
        CircleImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
