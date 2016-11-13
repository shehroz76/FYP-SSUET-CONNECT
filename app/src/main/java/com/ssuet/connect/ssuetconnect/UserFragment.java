package com.ssuet.connect.ssuetconnect;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 10/29/2016.
 */



public class UserFragment extends Fragment {


    private DatabaseReference mdatabaseFriendReqRef;
    private DatabaseReference mdatabaseFriendPendingRef;
    private DatabaseReference mdatabaseFriendsRef;
    private DatabaseReference mDatabase;
    private DatabaseReference mpendingref;
    private String namePending;
    private String batchPending;
    private String namechehcking;
    private String batchCheking;
    private String UserPostion;

    public UserFragment() {

    }

    String UserUid;
    String name;
    String batch;
    private boolean mFriend_Req = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.chat_list, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(
                R.id.chat_lists_recylrview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseAuth mAuth =  FirebaseAuth.getInstance();
        UserUid = mAuth.getCurrentUser().getUid();



        DatabaseReference mref=mDatabase.child(UserUid);
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Map<String, String> map = (Map)dataSnapshot.getValue();
                name = map.get("Name");
                batch =map.get("Batch");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        DatabaseReference mcheckingref=mDatabase.child(UserUid).child("Friend_Req");
        mcheckingref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Map<String, String> map = (Map)dataSnapshot.getValue();
                namechehcking = map.get("Name");
                batchCheking =map.get("batch");



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        FirebaseRecyclerAdapter<UserModel,UserViewHolder> firebaseRecyclerAdapter =new
                FirebaseRecyclerAdapter<UserModel, UserViewHolder>(

                        UserModel.class,
                        R.layout.user_row,
                        UserViewHolder.class,
                        mDatabase
                ) {
                    @Override
                    protected void populateViewHolder(final UserViewHolder viewHolder, UserModel model, int position) {

                       UserPostion = getRef(position).getKey();


                        mpendingref=mDatabase.child(UserPostion);
                        mpendingref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                Map<String, String> map = (Map)dataSnapshot.getValue();
                                namePending = map.get("Name");
                                batchPending =map.get("Batch");

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });








                        viewHolder.setImage(getActivity(), model.getProfile_Image());
                        viewHolder.setTitle(model.getName());

                        if(UserUid.equals(UserPostion)){

                            viewHolder.request.setImageResource(R.drawable.current_user);
                        }
                        else
                        viewHolder.request.setImageResource(R.drawable.ic_action_user_add);
                        
                        
                        
                        
                        


                        viewHolder.request.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mFriend_Req = true;

                                if(mFriend_Req) {

                                if(UserUid.equals(UserPostion)){

                                    viewHolder.request.setImageResource(R.drawable.current_user);
                                }else


                                    mdatabaseFriendReqRef = FirebaseDatabase.getInstance().getReference().child("Users").child(UserPostion).child("Friend_Req");

                                    mdatabaseFriendReqRef.child(UserUid).child("Name").setValue(name);
                                    mdatabaseFriendReqRef.child(UserUid).child("batch").setValue(batch);



                                    mdatabaseFriendPendingRef = FirebaseDatabase.getInstance().getReference().child("Users").child(UserUid).child("Friends_Pending");


                                    mdatabaseFriendPendingRef.child(UserPostion).child("Name").setValue(namePending);
                                    mdatabaseFriendPendingRef.child(UserPostion).child("batch").setValue(batchPending);

                                    if(!UserUid.equals(UserPostion)) {


                                        viewHolder.request.setImageResource(R.drawable.request);
                                    }



                                    mFriend_Req =false;

                                }




                            }
                        });


                    }
                };

        rv.setAdapter(firebaseRecyclerAdapter);



        return rootView;
    }

    private void FriendReuest() {



    }

//    private void FirebaseAdapter() {
//
//
//    }




    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View mView;

        ImageView request;

        public UserViewHolder(View itemView) {
            super(itemView);
            request = (ImageView) itemView.findViewById(R.id.friend_request_icon);
            mView = itemView;
        }


        public void setImage(Context context,String image){

            CircleImageView user_Image = (CircleImageView) mView.findViewById(R.id.profile_image_users);
            Picasso.with(context).load(image).into(user_Image);


        }





        public void setTitle(String title){

            TextView post_title = (TextView) mView.findViewById(R.id.Users_Name);
            post_title.setText(title);
        }



    }


}
