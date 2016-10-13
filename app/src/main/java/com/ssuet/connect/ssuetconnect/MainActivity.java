package com.ssuet.connect.ssuetconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.jar.Attributes;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ssuet.connect.ssuetconnect.R.id.action_log;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "User" ;
    private CardView merpCardView;
    private CardView mchatCardView;
    private CardView mlibraryCardView;
    private CardView mloacFinderCardView;
    private CardView mJobsCardView;
    private CardView mOthersCardView;

    private ProgressDialog mProgress;


    private CircleImageView mProfileImageMain;
    private TextView mProfileUserNameMain;
    private TextView mProfileUserBatchMain;

    private DatabaseReference mDatabaseReferenceUserInfo;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private DrawerLayout mDrawerLayout;
//    private DatabaseReference mDatabaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mProgress = new ProgressDialog(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }


        mAuth = FirebaseAuth.getInstance();
        mDatabaseReferenceUserInfo = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseReferenceUserInfo.keepSynced(true);

//        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
//        mDatabaseUser.keepSynced(true);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    mProgress.setMessage("Updating..");
                    mProgress.show();

                    String UserUid = mAuth.getCurrentUser().getUid();
                    DatabaseReference mref=mDatabaseReferenceUserInfo.child(UserUid);
                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            Map <String, String> map = (Map)dataSnapshot.getValue();
                            String name = map.get("Name");
                            String batch =map.get("Batch");
                            String image =map.get("Profile Image");

                            if (!TextUtils.isEmpty(name)){
                                mProfileUserNameMain.setText(name);}
                            else
                                mProfileUserNameMain.setText("User Name");

                            if (!TextUtils.isEmpty(batch)) {
                                mProfileUserBatchMain.setText(batch);
                            }
                            else
                                mProfileUserBatchMain.setText("Batch No : -- -- --");

                            if(!TextUtils.isEmpty(image)){

                                Picasso.with(MainActivity.this).load(image).into(mProfileImageMain);
                            }else
                                Picasso.with(MainActivity.this).load(R.drawable.profile_placeholder).into(mProfileImageMain);


                            mProgress.dismiss();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });






                } else {
                    // User is signed out

                    Intent loginActivityIntent = new Intent(MainActivity.this , LoginActivity.class);
                    loginActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginActivityIntent);

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        merpCardView = (CardView) findViewById(R.id.erp_cardView);
        mchatCardView = (CardView) findViewById(R.id.chat_cardView);
        mlibraryCardView = (CardView) findViewById(R.id.library_cardView);
        mloacFinderCardView = (CardView) findViewById(R.id.location_finder_cardView);
        mJobsCardView = (CardView) findViewById(R.id.jobs_cardView);
        mOthersCardView = (CardView) findViewById(R.id.others_cardView);

//        UserProfileInfo();


        mProfileImageMain = (CircleImageView) findViewById(R.id.profile_image_main);
        mProfileUserNameMain = (TextView) findViewById(R.id.user_profile_name_main);
        mProfileUserBatchMain = (TextView) findViewById(R.id.user_profile_batch_no);



        merpCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent erpactivityIntent = new Intent(MainActivity.this , StudentErpActivity.class);
                erpactivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(erpactivityIntent);
            }
        });

        mchatCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatActivityIntent = new Intent(MainActivity.this , ChatActivity.class);
                chatActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(chatActivityIntent);
            }
        });

        mlibraryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LibraryActivityIntent = new Intent(MainActivity.this , LibraryActivity.class);
                LibraryActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(LibraryActivityIntent);
            }
        });

        mloacFinderCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LocFindActivityIntent = new Intent(MainActivity.this , LocationFinderActivity.class);
                LocFindActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(LocFindActivityIntent);
            }
        });

        mJobsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JobActivityIntent = new Intent(MainActivity.this , JobsInternshipActivity.class);
                JobActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(JobActivityIntent);
            }
        });

        mOthersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OthersActivityIntent = new Intent(MainActivity.this , SportsNewsEventsActivity.class);
                OthersActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(OthersActivityIntent);
            }
        });


    }

//    private void UserProfileInfo() {
//
//
//    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
//        UserProfileInfo();


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }


    private void Signout() {
        FirebaseAuth.getInstance().signOut();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_log:
                Signout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    public void onBackPressed() {
        DrawerLayout layout = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}


