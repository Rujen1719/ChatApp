package com.example.chatapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    ArrayList<Friends> arrayList = new ArrayList<>();
    Activity activity;

    public FriendsAdapter(ArrayList<Friends> arrayList, Activity activity) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friends_single_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.mText.setText(arrayList.get(i).getDate());
        viewHolder.mName.setText(arrayList.get(i).getName());
        String onlineStatus = arrayList.get(i).getOnlineStatus();

        if (onlineStatus.equals("true")){
            viewHolder.mOnlineIcon.setVisibility(View.VISIBLE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[] = new CharSequence[]{
                  "Open Profile", "Send message"
                };

                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle("Select Options");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int j) {
                        //Click event for each item
                        if (j==0){
                            activity.startActivity(new Intent(activity, ProfileActivity.class)
                                    .putExtra("id", arrayList.get(i).getId()));
                            activity.finish();
                        }

                        if (j==1){
                            activity.startActivity(new Intent(activity, ChatActivity.class)
                                    .putExtra("id", arrayList.get(i).getId()));
                            activity.finish();
                        }
                    }
                });
                builder.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mText;
        TextView mName;
        ImageView mOnlineIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.friends_single_date);
            mName = itemView.findViewById(R.id.friends_single_name);
            mOnlineIcon = itemView.findViewById(R.id.friends_single_online_icon);
        }
    }
}
