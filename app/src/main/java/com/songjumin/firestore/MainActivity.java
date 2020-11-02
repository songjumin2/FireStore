package com.songjumin.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 파이어스토어 연동방법
        // 빌드.그레드에 디펜던시에 파이어페이스 라이브러리를 저장한다.


        // 인스턴스를 가져온다.
        // 인스턴스란? 왜가져오냐?
        // 인스턴스는 한국말로하면 객체 - 메모리에 파이어베이스를 사용할수있는
        // 객체가 생성됨 - 클래스가 메모리에올라온게 객체라고함
        // 파이어베이스는 데이터베이스임
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        // 키벨류는 다큐먼트 형식(아래맵형식) 이 다큐먼트가 여러개있으면 컬렉션
        // 메모리(자바), 데이터를 메모리에 저장하는데 키벨류 형식으로 저장할수있는게 Map<왼쪽이 키, 오른쪽이 벨류>
        // 파이어스토어는 키벨류로 저장하니까 Map<>사용한다. 그렇게안하면 안돌아감
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        // 컬렉션이름 users
        // 유저클레스에 다큐먼트를 넣은것이다.
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    // 안드로이드가 호출해준다(onSuccess)
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                // 실패했을때, on으로 시작하는함수는 콜백함수이다.
                // 콜백함수가 뭘까? 호출이되어진다. 파이어스토어에 저장하고나면 실행시켜준다.
                // 개발자가 실행시키는게아니라 프레임워크에서 실행시켜주는 함수이다.
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });

        // 데이터 가져오는코드
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }

                    }
                });
    }
}