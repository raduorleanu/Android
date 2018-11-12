package io.github.raduorleanu.and1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.raduorleanu.and1.activities.NewPlaceActivity;
import io.github.raduorleanu.and1.activities.SignUpActivity;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";

    private EditText username, password;
    private Button signIn, signUp, signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_view);
        mAuth = FirebaseAuth.getInstance();

        username = (EditText) findViewById(R.id.uNameView);
        password = (EditText) findViewById(R.id.passView);
        signIn = (Button) findViewById(R.id.signInBtn);
        signUp = (Button) findViewById(R.id.signUpBtn);
        signOut = (Button) findViewById(R.id.signOutBtn);

        if (isLoggedIn()) goToMain();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = username.getText().toString();
                String cpass = password.getText().toString();

                if (isLoggedIn()) goToMain();

                if (!uName.equals("") && !cpass.equals("")) {
//                    checkUsername(uName, cpass);
                    signIn(uName, cpass);
                } else {
                    toastMessage("You didn't fill in all the fields.");
                }
            }

        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(toSignUp);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
                toastMessage("Signed out");
            }
        });
    }

//    private void checkUsername(String uName, String cpass) {
//        access.checkUsername(uName, cpass);
//    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            toastMessage(user.getEmail() + " is signed in");
                            clearFields();
                            goToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            toastMessage("Authentication failed.");
                        }


                    }
                });
    }

    //add a toast to show when successfully signed in

    /**
     * customizable toast
     *
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void clearFields() {
        username.setText("");
        password.setText("");
    }

    private boolean isLoggedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return (currentUser != null);
    }

    private void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void logOut(){
        mAuth.signOut();

    }

    public void define(){}
}

