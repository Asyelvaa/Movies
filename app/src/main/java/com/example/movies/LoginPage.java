package com.example.movies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USERNAME_KEY = "username_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String username, password;

    EditText etEmail;
    EditText etpassword;
    Button btnLogin;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        etEmail = findViewById(R.id.login_email);
        etpassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etEmail.getText().toString();
                password = etpassword.getText().toString();

                AndroidNetworking.post("https://mediadwi.com/api/latihan/login")
                        .addBodyParameter("username", username)
                        .addBodyParameter("password", password)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("sukses login", "onResponse: "+response.toString());
                                try {
                                    boolean status = response.getBoolean("status");
                                    String message = response.getString("message");
                                    if (status) {
                                        Toast.makeText(LoginPage.this, message, Toast.LENGTH_SHORT).show();
                                        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                                        username = sharedpreferences.getString(USERNAME_KEY, null);
                                        password = sharedpreferences.getString(PASSWORD_KEY, null);

                                        SharedPreferences.Editor editor = sharedpreferences.edit();

                                        editor.putString(USERNAME_KEY, etEmail.getText().toString());
                                        editor.putString(PASSWORD_KEY, etpassword.getText().toString());

                                        editor.apply();
                                        startActivity(new Intent(LoginPage.this, MainActivity.class));
                                        finish();
                                    }else {
                                        Toast.makeText(LoginPage.this, "gagal kau", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
            }
        });

        btnGoogle = findViewById(R.id.google_signin);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("184302030716-mo1622s0ri7n7f2qciglppbfsgh7bngv.apps.googleusercontent.com")
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            navigateToMainActivity();
        }

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString(USERNAME_KEY, etEmail.getText().toString());
//                editor.putString(PASSWORD_KEY, etpassword.getText().toString());
//                editor.apply();
            }

        });

    }
    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if ( account != null ) {
                    navigateToMainActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Sign-in failed", Toast.LENGTH_SHORT).show();
                }
            } catch (ApiException e) {
                int statusCode = e.getStatusCode();
                Log.e("LoginActivity", "Google sign-in failed", e);
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToMainActivity() {
        finish();
        Intent intent = new Intent(LoginPage.this, MainActivity.class);
        startActivity(intent);
    }
}