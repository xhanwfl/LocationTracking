package com.example.zenly2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText editTextName,editTextAge;
    private String name,age;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.editTextUpdateProfileName);
        editTextAge = findViewById(R.id.editTextUpdateProfileAge);

        findViewById(R.id.buttonUpdateProfileUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextName.getText().toString();
                age = editTextAge.getText().toString();

                UserProfile data = new UserProfile(name,age,user.getUid());

                db.collection("users").document(user.getUid()).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startToast("회원정보 등록 성공");
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        startToast("회원정보 등록 실패");
                    }
                });
            }
        });

    }

    public void startToast(String msg){
        Toast.makeText(this.getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}