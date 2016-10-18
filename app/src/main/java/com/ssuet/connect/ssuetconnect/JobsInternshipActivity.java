package com.ssuet.connect.ssuetconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class JobsInternshipActivity extends AppCompatActivity {

    private CircleImageView mcircleImageViewJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_internship);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        mcircleImageViewJobs = (CircleImageView) findViewById(R.id.profile_image_jobs);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            String getImageJob = (String) bd.get("job_url");


            Picasso.with(JobsInternshipActivity.this).load(getImageJob).into(mcircleImageViewJobs);
        }


    }
}
