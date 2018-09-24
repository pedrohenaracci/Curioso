package com.curioso.curiosoapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText emailDefault;
    private EditText pwd;

    private Button createAccount;

    private Button disconnect;

    private SignInButton signIn;

    ProgressDialog progressDialog;

    private Boolean pass = true;


    public static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    public static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializingViews();



    }

    public void initializingViews() {
        // variáveis da tela de perfil
       /* nome = findViewById(R.id.name);
        email = findViewById(R.id.email);
        perfil = findViewById(R.id.imageView);
*/
        // Botões de login Gmail
        signIn = (SignInButton)findViewById(R.id.sign_in_button);
        signIn.setOnClickListener(this);
        disconnect = (Button) findViewById(R.id.disconnect_button2);
        disconnect.setOnClickListener(this);


        emailDefault = (EditText) findViewById(R.id.editTextEmail);
        pwd = (EditText) findViewById(R.id.editTextPWD);



        createAccount = (Button) findViewById(R.id.createAccount_button);
        createAccount.setOnClickListener(this);





        // progressDialog
        progressDialog = new ProgressDialog(LoginActivity.this);

        // Recuperando os últimos dados salvos da tela de perfil com SharedPreference
      /*  sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(PASS)) {
            pass = Boolean.parseBoolean (sharedPreferences.getString(PASS,""));
        }*/



        // START initialize_auth
        mAuth = FirebaseAuth.getInstance();


        // Instanciando objeto do Gmail
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
       /* FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        updateUIDefault(currentUser);*/

      /* if (!isUserAuthenticated()) {
            return;
       }else {
           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
           startActivity(intent);
       }*/
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.disconnect_button2:
                disconnect();
                break;
            case R.id.createAccount_button:
                createAccount(emailDefault.getText().toString(),pwd.getText().toString());
        }

    }

    private void signIn() {
       /* SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASS, "true");
        editor.commit();*/

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void disconnect(){
        mAuth.signOut();

        // Google revoke access

        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) { updateUI(null); }
        });

        // Resetando os dados do usuário no perfil
       /* nome.setText("nome");
        perfil.setImageResource(R.mipmap.ic_launcher_round);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASS, "true");
        editor.commit();*/

        pass = true;
    }


    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        progressDialog.show();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            //updateUIDefault(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUIDefault(null);
                        }

                        // [START_EXCLUDE]
                        progressDialog.hide();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]

        /*SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASS, "false");
        editor.commit();*/
        pass = false;
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = emailDefault.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailDefault.setError("Required.");
            valid = false;
        } else {
            emailDefault.setError(null);
        }

        String password = pwd.getText().toString();
        if (TextUtils.isEmpty(password)) {
            pwd.setError("Required.");
            valid = false;
        } else {
            pwd.setError(null);
        }

        return valid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this,"you are not able to log in to google",Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }



    private void updateUI(FirebaseUser user) {

        //signOut.setVisibility(View.VISIBLE);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null && pass == true) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

/*

            // Parseando os dados do usuário para tela de perfil
            nome.setText(personName);
            email.setText(personEmail);
           */
/* SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(IMG, personPhoto.toString());
            editor.putString(NAME, personName);
            editor.commit();*//*

            Picasso.get().load(personPhoto.toString()).resize(300, 300).into(perfil);
*/


            Toast.makeText(this, "Usuário :" + personName + " Está conectado", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUIDefault (FirebaseUser user){
        if (user != null && pass == false) {

            String personEmail = user.getEmail();
            String personId = user.getUid();

            // Parseando os dados do usuário para tela de perfil

           /* email.setText(personEmail);
            nome.setText(personId);
*/

        } else {
           /* mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);*/
        }



    }

    private boolean isUserAuthenticated() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }



















}
