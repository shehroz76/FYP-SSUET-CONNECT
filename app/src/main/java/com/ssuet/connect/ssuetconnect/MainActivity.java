package com.ssuet.connect.ssuetconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "User" ;
    private CardView merpCardView;
    private CardView mchatCardView;
    private CardView mlibraryCardView;
    private CardView mloacFinderCardView;
    private CardView mJobsCardView;
    private CardView mOthersCardView;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out

                    Intent SignInActivityIntent = new Intent(MainActivity.this , LoginActivity.class);
                    SignInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(SignInActivityIntent);


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


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

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

//       if(item.getItemId() == action_log) {
//           Signout();
//       }


        return super.onOptionsItemSelected(item);
    }
}


