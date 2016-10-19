package com.ssuet.connect.ssuetconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.ssuet.connect.ssuetconnect.RegisterationActivity.EMAIL_ADDRESS;
import static com.ssuet.connect.ssuetconnect.RegisterationActivity.MIN_PASSWORD_LENGTH;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "SignIn :" ;
    private EditText mEditTextEmail;
    private EditText mEditTextpass;

    private TextView mTextViewsignup;
    private Button mSignInButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabaseUser;

    private ProgressDialog mprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mprogress =new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        // ...
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent mainActivityIntent =  new Intent(LoginActivity.this , MainActivity.class);
                    mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainActivityIntent);


                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        // ...




        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUser.keepSynced(true);

        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextpass = (EditText) findViewById(R.id.editTextPassword);
        mTextViewsignup = (TextView) findViewById(R.id.textViewSignUp);

        mEditTextEmail.addTextChangedListener(mTextWatcher);
        mEditTextpass.addTextChangedListener(mTextWatcher);


        mSignInButton = (Button) findViewById(R.id.signinbutton);


        mTextViewsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUpIntentActivity = new Intent(LoginActivity.this , RegisterationActivity.class);
                signUpIntentActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signUpIntentActivity);
            }
        });


        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkingSignIn();


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

    private void checkingSignIn() {

        String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextpass.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(LoginActivity.this , "Fields Should not be left Empty" ,
                    Toast.LENGTH_SHORT).show();

        }
        else if(email.length()==0 || !EMAIL_ADDRESS.matcher(email).matches() ) {
            mEditTextEmail.setError("Enter Valid Email Address");
            Toast.makeText(LoginActivity.this, "Invalid Email",
                    Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<MIN_PASSWORD_LENGTH){
            mEditTextpass.setError("Passwords should not less than 6");
            Toast.makeText(LoginActivity.this , "password should not less than 6" ,
                    Toast.LENGTH_SHORT).show();


        }
        else if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){



            mprogress.setMessage("Signing In");
            mprogress.show();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            if(task.isSuccessful()){

                                mprogress.dismiss();

                                Intent mainActvityIntent = new Intent(LoginActivity.this , MainActivity.class);
                                mainActvityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainActvityIntent);


                            }

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {

                                mprogress.dismiss();

                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, "WRONG CREDENTIALS ",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });

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



        String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextpass.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            mSignInButton.setEnabled(false);
            mSignInButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        }
        else if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            mSignInButton.setEnabled(true);
            mSignInButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }







        }


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
