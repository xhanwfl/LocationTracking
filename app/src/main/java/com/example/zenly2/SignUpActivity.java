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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText email,pwd,pwd2;
    String emailText,pwdText,pwd2Text;
    private FirebaseAuth mAuth;
    private static final String TAG = "SIGNUP";
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.editTextSignUpEmail);
        pwd = findViewById(R.id.editTextSignUpPwd);
        pwd2 = findViewById(R.id.editTextSignUpPwd2);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        findViewById(R.id.buttonSIgnUpSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               emailText = email.getText().toString();
               pwdText = pwd.getText().toString();
               pwd2Text = pwd2.getText().toString();

               if(isValidEmail(emailText)){
                   if(pwdText.length()>5){
                       if(pwdText.equals(pwd2Text)){
                           mAuth.createUserWithEmailAndPassword(emailText, pwdText)
                                   .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                       @Override
                                       public void onComplete(@NonNull Task<AuthResult> task) {
                                           if (task.isSuccessful()) {
                                               // Sign in success, update UI with the signed-in user's information
                                               Log.d(TAG, "createUserWithEmail:success");
                                               FirebaseUser user = mAuth.getCurrentUser();

                                               startToast("회원가입 완료 !");

                                               finish();

                                           } else {
                                               // If sign in fails, display a message to the user.
                                               Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                               startToast("회원가입 실패");
                                           }
                                           // ...
                                       }
                                   });
                       }else{
                           startToast("비밀번호가 일치하지않습니다.");
                       }
                   }else{
                       startToast("비밀번호는 6자리 이상이여야 합니다.");
                   }
               }else{
                   startToast("이메일 형식이 잘못되었습니다.");
               }

            }
        });

    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex); Matcher m = p.matcher(email);
        if(m.matches()) { err = true; } return err;
    }



    public void startToast(String msg){
        Toast.makeText(this.getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }

    public void startIntent(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}