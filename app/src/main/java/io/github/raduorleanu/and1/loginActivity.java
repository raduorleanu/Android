//package io.github.raduorleanu.and1;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//
//public class loginActivity extends AppCompatActivity implements View.OnClickListener  {
//
//    private static final String TAG = "GoogleActivity";
//    private static final int RC_SIGN_IN = 9001;
//
//    // [START declare_auth]
//    private FirebaseAuth mAuth;
//    // [END declare_auth]
//
//    private GoogleSignInClient mGoogleSignInClient;
//    private GoogleApiClient mGoogleApiClient;
//    private TextView mStatusTextView;
//
//
//    private SignInButton signInButton;
//    private Button signOutButton;
//    private Button disconnectButton;
//
//    // Db access
////    private static final String TAG = "NewPostActivity";
//    private static final String REQUIRED = "Required";
//
//    // [START declare_database_ref]
//    private DatabaseReference mDatabase;
//    private FirebaseDatabase mydb;
//    // [END declare_database_ref]
////
////    private static final String TAG = "MainActivity";
////
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_main);
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
//                .build();
//
//        // views
//        mStatusTextView = (TextView) findViewById(R.id.textView);
//        // btns
//        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
//        signInButton.setOnClickListener( this);
//
//        signOutButton = (Button) findViewById(R.id.signout);
//        signOutButton.setOnClickListener(this);
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        // [START initialize_auth]
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        mydb = FirebaseDatabase.getInstance();
//
//
//    }
//
//    private void pushAUser(FirebaseUser user){
//        DatabaseReference myref = mydb.getReference("users");
//
//    }
//
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    private void signOut() {
//        // Firebase sign out
//        mAuth.signOut();
//
//        // Google sign out
//        mGoogleSignInClient.signOut().addOnCompleteListener(this,
//                new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null);
//                    }
//                });
//    }
//
//    private void updateUI(FirebaseUser user) {
////        hideProgressDialog();
//        if (user != null) {
////            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
//
//            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.disconnectButtonbutton3).setVisibility(View.VISIBLE);
//        } else {
//            mStatusTextView.setText("Signed Out");
////            mDetailTextView.setText(null);
//
//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.disconnectButtonbutton3).setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//                // ...
//            }
//        }
//    }
//    private void revokeAccess() {
//        // Firebase sign out
//        mAuth.signOut();
//
//        // Google revoke access
//        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
//                new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null);
//                    }
//                });
//    }
//
//    // [START auth_with_google]
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//        // [START_EXCLUDE silent]
////        showProgressDialog();
//        // [END_EXCLUDE]
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            mDatabase.child("users").child(user.getEmail());
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
////                            Snackbar.make(findViewById(R.id.), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                        // [START_EXCLUDE]
////                        hideProgressDialog();/
//                        // [END_EXCLUDE]
//                    }
//                });
//    }
//    // [END auth_with_google]
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.sign_in_button) {
//            signIn();
//        } else if (i == R.id.signout) {
//            signOut();
//        } else if (i == R.id.disconnectButtonbutton3) {
//            revokeAccess();
//        }
//    }
////
//
//
//}
