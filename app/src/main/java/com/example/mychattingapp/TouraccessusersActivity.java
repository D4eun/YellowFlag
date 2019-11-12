package com.example.mychattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;

import com.example.mychattingapp.Chat.MessageActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;

public class TouraccessusersActivity extends AppCompatActivity {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    private RecyclerView recyclerView;
    private  String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touraccessusers);
        recyclerView = (RecyclerView)findViewById(R.id.touraccessusers_recyclerview);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView.setLayoutManager(new LinearLayoutManager(TouraccessusersActivity.this));
        recyclerView.setAdapter(new RecyclerViewAdapter());


    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
