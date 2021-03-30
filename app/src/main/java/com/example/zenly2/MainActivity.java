package com.example.zenly2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    private FirebaseAuth mAuth;
    String emailText,passwordText;
    private static final String TAG = "MAINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editTextMainEmail);
        password = findViewById(R.id.editTextMainPwd);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonMainLogin :
                        emailText = email.getText().toString();
                        passwordText = password.getText().toString();
                        mAuth.signInWithEmailAndPassword(emailText, passwordText)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "signInWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            startToast("로그인 성공");
                                            startIntent(GPSActivity.class);
                                            finish();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                            startToast("아이디와 비밀번호를 확인해주세요");
                                        }

                                    }
                                });

                        break;
                    case R.id.buttonMainSignUp :
                        startIntent(SignUpActivity.class);
                        break;
                }
            }
        };


        findViewById(R.id.buttonMainLogin).setOnClickListener(onClickListener);
        findViewById(R.id.buttonMainSignUp).setOnClickListener(onClickListener);




    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
           /*startIntent(AddFriendActivity.class);
           finish();*/
        }else{

        }
    }

    public void startIntent(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void startToast(String msg){
        Toast.makeText(this.getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}