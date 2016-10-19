package com.ssuet.connect.ssuetconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ImageViewActivity extends AppCompatActivity {

    private ImageView mImageViewChangeImage;
    private Button mUplodButton;
    private Button mChangeImageButton;
    private static final int Gallery_Request = 1;
    private Uri mImageUri2 = null;
    private ProgressDialog mProgress;

    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private StorageReference mStorageUserImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        mStorageUserImage = FirebaseStorage.getInstance().getReference();



        mImageViewChangeImage = (ImageView) findViewById(R.id.ImageView_ProfileImage);
        mUplodButton = (Button) findViewById(R.id.upload_newPic);
        mChangeImageButton = (Button) findViewById(R.id.Submit_button);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            String getImage = (String) bd.get("Image_url");


            Picasso.with(ImageViewActivity.this).load(getImage).into(mImageViewChangeImage);
        }


        mUplodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile_gallerY_intent = new Intent(Intent.ACTION_GET_CONTENT);
                profile_gallerY_intent.setType("image/*");
                startActivityForResult(profile_gallerY_intent , Gallery_Request);



            }
        });

        mChangeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage();


            }
        });



    }


    private void uploadImage() {

        final String UserUid = mAuth.getCurrentUser().getUid();

        if(mImageUri2 != null){

            mProgress.setMessage("Uploading");
            mProgress.show();

            StorageReference filepath = mStorageUserImage.child("User Images").child(mImageUri2.getLastPathSegment());
            filepath.putFile(mImageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String dowloadProfilePicUrl = taskSnapshot.getDownloadUrl().toString();

                    mDatabaseUsers.child(UserUid).child("Profile Image").setValue(dowloadProfilePicUrl);

                    mProgress.dismiss();

                    Intent signinIntent = new Intent(ImageViewActivity.this , MainActivity.class);
                    signinIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signinIntent);



                }
            });


        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Request && resultCode == RESULT_OK) {

            Uri ImageUri1 = data.getData();

            CropImage.activity(ImageUri1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1 , 1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri2 = result.getUri();
                mImageViewChangeImage.setImageURI(mImageUri2);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }



}
