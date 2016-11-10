package com.ssuet.connect.ssuetconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.data;
import static com.ssuet.connect.ssuetconnect.RegisterationActivity.EMAIL_ADDRESS;

public class SetupActivity extends AppCompatActivity {

    private CircleImageView mProfile_image;
    private Button mUpload_dButton;
    private EditText mUser_Name;
    private Button mSubmit_button;
    private static final int Gallery_Request = 1 ;
    private Uri mImageUri1 = null;

    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private StorageReference mStorageUserImage;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mAuth = FirebaseAuth.getInstance();

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        mStorageUserImage = FirebaseStorage.getInstance().getReference();

        mProgress = new ProgressDialog(this);

        mProfile_image = (CircleImageView) findViewById(R.id.profile_image_setup);
        mUpload_dButton = (Button) findViewById(R.id.upload_button);
        mUser_Name = (EditText) findViewById(R.id.editTextUserName);
        mSubmit_button = (Button) findViewById(R.id.submitButton);



        mUser_Name.addTextChangedListener(mTextWatcher);



        mUser_Name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String  text= ((EditText)v).getText().toString().trim();
                    if (text.length()==0){
                        mUser_Name.setError("Enter User Name");
                    }
                }
            }
        });


        mUpload_dButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent profile_gallerY_intent = new Intent(Intent.ACTION_GET_CONTENT);
                profile_gallerY_intent.setType("image/*");
                startActivityForResult(profile_gallerY_intent , Gallery_Request);


            }
        });


        mSubmit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSettingUp();

            }
        });


    }

    private void startSettingUp() {

        final String userName = mUser_Name.getText().toString().trim();

        final String UserUid = mAuth.getCurrentUser().getUid();


        if (TextUtils.isEmpty(userName)){

            mUser_Name.setText("Enter User Name");
            Toast.makeText(SetupActivity.this , "Enter User Name" ,
                    Toast.LENGTH_SHORT).show();

        }
        else if(mImageUri1 == null){

            Toast.makeText(SetupActivity.this , "Uplad an Image" ,
                    Toast.LENGTH_SHORT).show();

        }
        else if(!TextUtils.isEmpty(userName) && mImageUri1 != null) {


            mProgress.setMessage("Setting Up");
            mProgress.show();

            StorageReference filepath = mStorageUserImage.child("User Images").child(mImageUri1.getLastPathSegment());
            filepath.putFile(mImageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String dowloadProfilePicUrl = taskSnapshot.getDownloadUrl().toString();

                    mDatabaseUsers.child(UserUid).child("User_Name").setValue(userName);
                    mDatabaseUsers.child(UserUid).child("Profile_Image").setValue(dowloadProfilePicUrl);

                    mProgress.dismiss();


                    Intent signinIntent = new Intent(SetupActivity.this , MainActivity.class);
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

                mImageUri1 = result.getUri();

                mProfile_image.setImageURI(mImageUri1);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }

    //  create a textWatcher member
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkingSignInforeileds();
        }
    };

    private void checkingSignInforeileds() {



        String userName = mUser_Name.getText().toString().trim();

        if (TextUtils.isEmpty(userName) || mImageUri1 == null){

            mSubmit_button.setEnabled(false);
            mSubmit_button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        }
        else if (!TextUtils.isEmpty(userName) && mImageUri1 != null){

            mSubmit_button.setEnabled(true);
            mSubmit_button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }







    }







}
