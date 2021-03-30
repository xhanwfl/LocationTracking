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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddFriendActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private EditText mycode,friendCode;
    private DocumentReference docRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        mycode = findViewById(R.id.editTextAddFriendMyCode);
        mycode.setText(user.getUid());
        friendCode = findViewById(R.id.editTextAddFriendFriendCode);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonAddFriendCodeCheck :
                        docRef = db.collection("users").document(friendCode.getText().toString());
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                UserProfile friend = documentSnapshot.toObject(UserProfile.class);
                                startToast(friend.getName()+"\n"+friend.getAge());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("친구코드를 확인하세요");
                            }
                        });
                        break;
                    case R.id.buttonAddFriendAddFriend:
                        docRef = db.collection("users").document(user.getUid());
                        docRef.update("friendCode",friendCode.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("친구추가 성공");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("친구추가 실패");
                            }
                        });

                        break;
                }
            }
        };

        findViewById(R.id.buttonAddFriendCodeCheck).setOnClickListener(onClickListener);
        findViewById(R.id.buttonAddFriendAddFriend).setOnClickListener(onClickListener);
    }

    public void startToast(String msg){
        Toast.makeText(this.getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}