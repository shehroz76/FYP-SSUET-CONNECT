package com.ssuet.connect.ssuetconnect;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SportsNewsEventsActivity extends AppCompatActivity {

    private CircleImageView mCircleImageViewOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_news_events);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

    mCircleImageViewOthers =(CircleImageView) findViewById(R.id.profile_image_others);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            String getImageOthers = (String) bd.get("others_url");


            Picasso.with(SportsNewsEventsActivity.this).load(getImageOthers).into(mCircleImageViewOthers);
        }


    }
}
