package com.ssuet.connect.ssuetconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CardView merpCardView;
    private CardView mchatCardView;
    private CardView mlibraryCardView;
    private CardView mloacFinderCardView;
    private CardView mJobsCardView;
    private CardView mOthersCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    }


