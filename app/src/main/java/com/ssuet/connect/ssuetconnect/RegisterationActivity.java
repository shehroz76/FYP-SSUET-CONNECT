package com.ssuet.connect.ssuetconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterationActivity extends AppCompatActivity {

    private static final String TAG = "User signup :" ;
    private EditText mEditTextEmail;
    private EditText mEditTextBatchNo;
    private EditText mEditTextfName;
    private EditText mEditTextLname;
    private EditText mEditTextPassword;
    private EditText mEditTextConfimPassword;

    public static final int MIN_PASSWORD_LENGTH = 6;

    private Button mSignUpButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsersignUp;
    private ProgressDialog mProgress;

    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        mAuth = FirebaseAuth.getInstance();

        mDatabaseUsersignUp = FirebaseDatabase.getInstance().getReference().child("Users");

        mProgress = new ProgressDialog(this);


        mEditTextEmail = (EditText) findViewById(R.id.editText_email);
        mEditTextBatchNo = (EditText) findViewById(R.id.editText_batchNo);
        mEditTextfName = (EditText) findViewById(R.id.editText_fname);
        mEditTextLname = (EditText) findViewById(R.id.editText_lastName);
        mEditTextPassword = (EditText) findViewById(R.id.editText_password);
        mEditTextConfimPassword = (EditText) findViewById(R.id.editText_confirmPassword);

        AddOnFocusListners();

        mSignUpButton = (Button) findViewById(R.id.buttonSignUp);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartRegister();
            }
        });


    }

    private void StartRegister() {



        final String userEmail, userBatchNo, userPassword, userConfirmPassword, userFirstName, userLastName;


        userEmail = mEditTextEmail.getText().toString().trim();
        userBatchNo = mEditTextBatchNo.getText().toString().trim();
        userFirstName = mEditTextfName.getText().toString().trim();
        userLastName = mEditTextLname.getText().toString().trim();
        userPassword = mEditTextPassword.getText().toString().trim();
        userConfirmPassword = mEditTextConfimPassword.getText().toString().trim();



        if(TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userBatchNo) ||TextUtils.isEmpty(userFirstName)
                || TextUtils.isEmpty(userLastName) || TextUtils.isEmpty(userPassword)
                || TextUtils.isEmpty(userConfirmPassword)){

            Toast.makeText(RegisterationActivity.this , "Fields Should not be left Empty" ,
                    Toast.LENGTH_SHORT).show();

        }else if(userEmail.length()==0 || !EMAIL_ADDRESS.matcher(userEmail).matches() ){
            mEditTextEmail.setError("Enter Valid Email Address");
            Toast.makeText(RegisterationActivity.this , "Invalid Email" ,
                    Toast.LENGTH_SHORT).show();

        }
        else if(userFirstName.length()== 0 || !userFirstName.matches("[a-zA-Z ]+")){
            mEditTextfName.setError("Invalid Name");
            Toast.makeText(RegisterationActivity.this , "Invalid first Name" ,
                    Toast.LENGTH_SHORT).show();

        }
        else if(userLastName.length() == 0 || !userLastName.matches("[a-zA-Z ]+")){
            mEditTextLname.setError("Invalid Name");
            Toast.makeText(RegisterationActivity.this , "Invalid Last Name" ,
                    Toast.LENGTH_SHORT).show();

        }
        else if(userPassword.length()<MIN_PASSWORD_LENGTH){
            mEditTextPassword.setError("Passwords should not less than 6");
            Toast.makeText(RegisterationActivity.this , "password should not less than 6" ,
                    Toast.LENGTH_SHORT).show();


        }
        else if (!userPassword.equals(userConfirmPassword)) {
            mEditTextConfimPassword.setError("Passwords does not match");
            Toast.makeText(RegisterationActivity.this , "password does'not match" ,
                    Toast.LENGTH_SHORT).show();

        }

        else if(!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userBatchNo) &&
                !TextUtils.isEmpty(userFirstName) && !TextUtils.isEmpty(userLastName)
                && !TextUtils.isEmpty(userPassword)
                && !TextUtils.isEmpty(userConfirmPassword)) {

            mProgress.setMessage("Signing Up...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if(task.isSuccessful()) {
                                String user_id = mAuth.getCurrentUser().getUid();


                                DatabaseReference current_user_db = mDatabaseUsersignUp.child(user_id);


                                current_user_db.child("Email").setValue(userEmail);
                                current_user_db.child("Name").setValue(userFirstName + " " + userLastName);
                                current_user_db.child("Batch").setValue(userBatchNo);
                                mProgress.dismiss();

                                Intent signinIntent = new Intent(RegisterationActivity.this, SetupActivity.class);
                                signinIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(signinIntent);

                            }

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                mProgress.dismiss();
                                Toast.makeText(RegisterationActivity.this, "SomeThing Went Wrong",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // ...
                        }
                    });
        }
    }

    private void AddOnFocusListners(){

        mEditTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){
                    String  text= ((EditText)v).getText().toString().trim();
                    if (text.length()==0){
                        mEditTextEmail.setError("Enter Email Address");
                    }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                        mEditTextEmail.setError("Enter Valid Email Address");
                    }
                }

            }
        });


        mEditTextfName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String  text= ((EditText)v).getText().toString().trim();
                    if (text.length()==0){
                        mEditTextfName.setError("Enter First Name");
                    } else if (!text.matches("[a-zA-Z ]+")) {
                        mEditTextfName.setError("Invalid Name");
                    }
                }
            }
        });
        mEditTextLname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String text = ((EditText) v).getText().toString().trim();
                    if (text.length() == 0) {
                        mEditTextLname.setError("Enter Last Name");
                    } else if (!text.matches("[a-zA-Z ]+")) {
                        mEditTextLname.setError("Invalid Name");
                    }
                }
            }
        });


    }


}
