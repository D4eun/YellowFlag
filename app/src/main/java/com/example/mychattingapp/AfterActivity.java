package com.example.mychattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mychattingapp.model.AfterModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AfterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

        recyclerView = (RecyclerView)findViewById(R.id.afteractivity_recyclerview);

    }
    void afterRoom(){
        FirebaseDatabase.getInstance().getReference().child("users").child("after").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerView.setLayoutManager(new LinearLayoutManager(AfterActivity.this));
                recyclerView.setAdapter(new afterrecyclerviewAdapter());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    class afterrecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<AfterModel> afterModels = new ArrayList<>();

        public afterrecyclerviewAdapter(){

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_after,parent,false);

            return new AfterviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            AfterviewHolder afterviewHolder= ((AfterviewHolder)holder);
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        private class AfterviewHolder extends RecyclerView.ViewHolder {
            public TextView aftername;
            public TextView afterfavor;
            public TextView aftermessage;
            public TextView aftertime;
            public TextView afterleft;
            public TextView afterright;
            public LinearLayout linearLayout_after;
            public RelativeLayout relativeLayout_after;

            public AfterviewHolder(View view) {
                super(view);
                aftername = (TextView)findViewById(R.id.afteritem_textview_name);
                afterfavor = (TextView)findViewById(R.id.afteritem_textview_favor);
                aftermessage = (TextView)findViewById(R.id.afteritem_textview_after);
                aftertime = (TextView)findViewById(R.id.afteritem_textview_timestamp);
                afterleft = (TextView)findViewById(R.id.afteritem_textview_left);
                afterright = (TextView)findViewById(R.id.afteritem_textview_right);
                linearLayout_after = (LinearLayout)findViewById(R.id.afteritem_linearlayout);
                relativeLayout_after = (RelativeLayout)findViewById(R.id.afteritem_relativelayout);
            }
        }
    }

    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fromleft, R.anim.toright);
    }


}
