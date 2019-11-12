package com.example.mychattingapp.model;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {
    public Map<String,Boolean> users = new HashMap<>(); //채팅방의 유저들
    public Map<String,Comment> comments = new HashMap<>();//채팅방의 대화내용
    public Map<String,Boolean> toursuccess = new HashMap<>(); //채팅방 유저들의 투어 성공,실패


    public static class Comment {
        public String uid;
        public String message;
        public Object timestamp;
    }
}
