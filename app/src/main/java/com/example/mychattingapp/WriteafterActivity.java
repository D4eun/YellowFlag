package com.example.mychattingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mychattingapp.model.ChatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class WriteafterActivity extends AppCompatActivity {
    private EditText writeafter;
    private Button button;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writeafter);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        button = (Button)findViewById(R.id.writeafteractivity_button);
        writeafter = (EditText)findViewById(R.id.writeafteractivity_editText_all);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatModel chatModel = new ChatModel();

                chatModel.users.put(uid,true);
                if(writeafter != null){
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("after").setValue(writeafter).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                }
            }
        });
    }
}
