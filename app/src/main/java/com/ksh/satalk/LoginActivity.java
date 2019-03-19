package com.ksh.satalk;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class LoginActivity extends AppCompatActivity {

    private EditText id, pwd;
    private Button login, signup;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;          // 로그인이 되었는지 체크 해주는 것.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signOut();

        String splash_background = mFirebaseRemoteConfig.getString(getString(R.string.rc_color));
        getWindow().setStatusBarColor(Color.parseColor(splash_background));

        id = (EditText) findViewById(R.id.loginActivity_edittext_id);
        pwd = (EditText) findViewById(R.id.loginActivity_edittext_pwd);
        login = (Button) findViewById(R.id.loginActivity_button_login);
        signup = (Button) findViewById(R.id.loginActivity_button_signup);

        login.setBackgroundColor(Color.parseColor(splash_background));
        signup.setBackgroundColor(Color.parseColor(splash_background));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEvent();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        // 로그인 인터페이스 리스너
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    // 로그인
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    // 로그아웃
                }

            }
        };
    }

    void loginEvent() {
        mFirebaseAuth.signInWithEmailAndPassword(id.getText().toString(), pwd.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }
}
