package com.example.chatapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {


    private RecyclerView mFriendsList;

    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUserDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private View mMainView;

    private FriendsAdapter mAdapter;

    ArrayList<Friends> arrayList = new ArrayList<>();

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_friends, container, false);

        mFriendsList = mMainView.findViewById(R.id.friends_list);
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mFriendsDatabase.keepSynced(true);

        mFriendsList.setHasFixedSize(true);

        //fragment ko state loss hunxa every time
        // so the code gets executed again and again when we swipe through the fragments
        //so clear the arraylist every time
        arrayList.clear();

        mFriendsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Friends friends = snapshot.getValue(Friends.class);
                    getName(friends, dataSnapshot.getRef().getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mMainView;
    }
    private void getName(final Friends friends, String key) {
        mUserDatabase.child(friends.getId()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                getOnlineStatus(friends, name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getOnlineStatus(final Friends friends, final String name) {
        mUserDatabase.child(friends.getId()).child("online").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                String onlineStatus = dataSnapshot.getValue().toString();
                arrayList.add(new Friends(friends.getDate(), friends.getId(), name, onlineStatus));
                Log.wtf("asdasd", new Gson().toJson(arrayList));
                mAdapter = new FriendsAdapter(arrayList, getActivity());
                mFriendsList.setLayoutManager(new LinearLayoutManager(getContext()));
                mFriendsList.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
