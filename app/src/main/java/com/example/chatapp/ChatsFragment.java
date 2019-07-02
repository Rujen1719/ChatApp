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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {


//    private RecyclerView mChatsList;
//    private FirebaseAuth mAuth;
//    private DatabaseReference mChatDatabase;
//    private DatabaseReference mUserDatabase;
//
//    private String mCurrent_user_id;
//
//    private View mMainView;
//
//    private ChatsAdapter mAdapter;
//
//    ArrayList<Chats> arrayList = new ArrayList<>();

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_chats, container, false);

//        mMainView = inflater.inflate(R.layout.fragment_chats, container, false);
//
//        mChatsList = mMainView.findViewById(R.id.chats_list);
//        mAuth = FirebaseAuth.getInstance();
//
//        mCurrent_user_id = mAuth.getCurrentUser().getUid();
//        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chat").child(mCurrent_user_id);
//        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
//
//        mChatDatabase.keepSynced(true);
//
//        mChatsList.setHasFixedSize(true);
//
//        //fragment ko state loss hunxa every time
//        // so the code gets executed again and again when we swipe through the fragments
//        //so clear the arraylist every time
//        arrayList.clear();
//
//        mChatDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Chats chats = snapshot.getValue(Chats.class);
//                    getNameImage(chats);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        return mMainView;
    }

//    private void getNameImage(final Chats chats) {
//        Log.e("asdasd6", new Gson().toJson(chats));
//        final ArrayList<ChatsFragmentData> arrayList = new ArrayList<>();
//        Log.e("qwe",chats.id);
//        mUserDatabase.child(chats.id).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String name = dataSnapshot.getValue().toString();
//                getImage(name, chats);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

//    private void getImage(final String name, Chats chats) {
//        final ArrayList<ChatsFragmentData> arrayList = new ArrayList<>();
//        mUserDatabase.child(chats.id).child("name").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String image = dataSnapshot.getValue().toString();
//                String name1 = name;
//                arrayList.add(new ChatsFragmentData(name1, image));
//
//                mChatsList.setLayoutManager(new LinearLayoutManager(getActivity()));
//                mAdapter = new ChatsAdapter(arrayList, getActivity());
//                mChatsList.setAdapter(mAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

}
