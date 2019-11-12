package com.example.mychattingapp.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mychattingapp.R;
import com.example.mychattingapp.model.ChatModel;
import com.example.mychattingapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MessageActivity extends AppCompatActivity {
    private String destinationUid;
    private Button button;
    private Button successbutton;
    private EditText editText;

    private String toursuccess;
    private String uid;
    private String chatRoomUid;
    private UserModel destinationUserModel;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();  //채팅을 요구 하는 아아디 즉 단말기에 로그인된 UID
        destinationUid = getIntent().getStringExtra("destinationUid"); // 채팅을 당하는 아이디

        button = (Button) findViewById(R.id.messageActivity_button);
        successbutton = (Button) findViewById(R.id.messageActivity_button_success); // 투어 하자!
        editText = (EditText) findViewById(R.id.messageActivity_editText);

        recyclerView = (RecyclerView)findViewById(R.id.messageActivity_recyclerview);

        final ChatModel chatModel = new ChatModel();

        successbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatModel.toursuccess.put(uid,true);

                FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("toursuccess").child(uid).setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatModel.users.put(uid,true);
                chatModel.users.put(destinationUid,true);

                if(chatRoomUid == null){
                    button.setEnabled(false);//서버에 리스너 인식되기 전에 버튼 잠금
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkChatRoom();
                        }
                    });
                }else {
                    ChatModel.Comment comment = new ChatModel.Comment();
                    comment.uid = uid;
                    comment.message = editText.getText().toString();
                    if (comment.message.length() > 0) {
                        comment.timestamp = ServerValue.TIMESTAMP;
                        FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                sendGcm();
                                editText.setText(""); //메세지 전송후 editText 초기화
                            }
                        });
                    }
                }

            }
        });
        checkChatRoom();
    }
//    void  sendGcm(){
//        Gson gson = new Gson();
//
//        NotificationModel notificationModel = new NotificationModel();
//        notificationModel.to = destinationUserModel.pushToken;
//        notificationModel.notification.title = "보낸이 아이디";
//        notificationModel.notification.text = editText.getText().toString();
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"), gson.toJson(notificationModel));
//
//        Request request = new Request.Builder()
//                .header("Content-Type", "application/json")
//                .addHeader("Authorization", "key=AIzaSyAgqklWiw09cykOnTDZYt1CdT79qgZw8bQ")
//                .url("https://gcm-http.googleapis.com/gcm/send")
//                .post(requestBody)
//                .build();
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//
//            }
//        });
//    }

     void checkChatRoom() {
        FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/"+uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    ChatModel chatModel = item.getValue(ChatModel.class);
                    if(chatModel.users.containsKey(destinationUid)){
                        chatRoomUid = item.getKey();
                        button.setEnabled(true);//서버에 리스너 인식되고 버튼 잠금 헤제
                        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                        recyclerView.setAdapter(new RecyclerViewAdapter());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
     }
     class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        List<ChatModel.Comment> comments;

        public RecyclerViewAdapter(){
            comments = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("users").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    destinationUserModel = dataSnapshot.getValue(UserModel.class);
                    getMessageList();



                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


                }
                void getMessageList(){
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            comments.clear();
                            for(DataSnapshot item :dataSnapshot.getChildren()){
                                comments.add(item.getValue(ChatModel.Comment.class));
                            }
                            //메세지가 갱신
                            notifyDataSetChanged();

                            //마지막 메세지의 위치로 이동
                            recyclerView.scrollToPosition(comments.size()-1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

         @NonNull
         @Override
         public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);

            return new MessageViewHolder(view);
         }

         @Override
         public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MessageViewHolder messageViewHolder = ((MessageViewHolder)holder);

            // 내가 보낸 메세지
            if(comments.get(position).uid.equals(uid)){
                messageViewHolder.textview_message.setText(comments.get(position).message);
                messageViewHolder.textview_message.setBackgroundResource(R.drawable.rightbubble);
                messageViewHolder.linearLayout_destination.setVisibility(View.INVISIBLE);
                messageViewHolder.textview_message.setTextSize(15);
                messageViewHolder.linearLayout_main.setGravity(Gravity.RIGHT);
            // 상대방이 보낸 메세지
            }else {
                messageViewHolder.textView_name.setText(destinationUserModel.name);
                messageViewHolder.linearLayout_destination.setVisibility(View.VISIBLE);
                messageViewHolder.textview_message.setBackgroundResource(R.drawable.leftbubble);
                messageViewHolder.textview_message.setText(comments.get(position).message);
                messageViewHolder.textview_message.setTextSize(15);
                messageViewHolder.linearLayout_main.setGravity(Gravity.LEFT);
            }
            long unixTime = (long) comments.get(position).timestamp;
             Date date = new Date(unixTime);
             simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
             String time = simpleDateFormat.format(date);
             messageViewHolder.textview_timestamp.setText(time);
         }

         @Override
         public int getItemCount() {
             return comments.size();
         }

         private class MessageViewHolder extends RecyclerView.ViewHolder {
            public TextView textview_message;
            public TextView textView_name;
            public LinearLayout linearLayout_destination;
            public LinearLayout linearLayout_main;
            public TextView textview_timestamp;

            public MessageViewHolder(View view) {
                 super(view);
                 textview_message = (TextView)view.findViewById(R.id.messageItem_textview_message);
                 textView_name = (TextView)view.findViewById(R.id.messageItem_textview_name);
                 linearLayout_destination = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_destination);
                 linearLayout_main = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_main);
                 textview_timestamp = (TextView)view.findViewById(R.id.messageItem_textview_timestamp);
             }
         }
     }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fromleft, R.anim.toright);
    }
}