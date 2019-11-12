package com.example.mychattingapp.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mychattingapp.AfterActivity;
import com.example.mychattingapp.R;
import com.example.mychattingapp.TouraccessusersActivity;
import com.example.mychattingapp.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MypageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        // Inflate the layout for this fragment
        final TextView name = (TextView) view.findViewById(R.id.mypage_textview_name);
        final TextView favor1 = (TextView) view.findViewById(R.id.mypage_textview_favor1);
        final TextView favor2 = (TextView) view.findViewById(R.id.mypage_textview_favor2);
        final TextView favor3 = (TextView) view.findViewById(R.id.mypage_textview_favor3);
        final TextView favor4 = (TextView) view.findViewById(R.id.mypage_textview_favor4);
        final TextView favor5 = (TextView) view.findViewById(R.id.mypage_textview_favor5);
        Button after = (Button) view.findViewById(R.id.mypage_button_after);
        Button touraccess = (Button)view.findViewById(R.id.mypage_button_touraccess);

        touraccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), TouraccessusersActivity.class);

                ActivityOptions activityOptions = null;
                activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright,R.anim.toleft);
                startActivity(intent,activityOptions.toBundle());
            }
        });

        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AfterActivity.class);

                ActivityOptions activityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright,R.anim.toleft);
                    startActivity(intent,activityOptions.toBundle());
                }
            }
        });

        final List<UserModel> userModels;
        userModels=new ArrayList<>();
        final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModels.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    if(userModel.uid.equals(myUid)){
                        userModels.add(userModel);
                        name.setText(userModel.name);
                        favor1.setText(userModel.favor1);
                        favor2.setText(userModel.favor2);
                        favor3.setText(userModel.favor3);
                        favor4.setText(userModel.favor4);
                        favor5.setText(userModel.favor5);
                    }
                    continue;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;

    }

    public MypageFragment() {
        // Required empty public constructor


    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }



}
