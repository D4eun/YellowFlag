package com.example.mychattingapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mychattingapp.fragment.MypageFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class AfterFragment extends Fragment {public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_after, container, false);

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.afterfragment_recyclerview);
    recyclerView.setAdapter(new AfterRecyclerViewAdapter());
    recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

    Intent intent = new Intent(view.getContext(), MypageFragment.class);

    ActivityOptions activityOptions = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
        activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromleft,R.anim.toright);
        startActivity(intent,activityOptions.toBundle());
    }
    return view;
}
    class AfterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
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

    public AfterFragment() {
        // Required empty public constructor
    }



}
