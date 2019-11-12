package com.example.mychattingapp.data;

import androidx.annotation.Keep;

@Keep
public class TourlistData {
    String name;
    String uid;
    String favor1;
    String favor2;
    String favor3;
    String favor4;
    String favor5;
    String latitude;
    String longitude;

    public TourlistData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public String getFavor1() {
        return favor1;
    }

    public void setFavor1(String favor1) {
        this.favor1 = favor1;
    }

    public String getFavor2() {
        return favor2;
    }

    public void setFavor2(String favor2) {
        this.favor2 = favor2;
    }

    public String getFavor3() {
        return favor3;
    }

    public void setFavor3(String favor3) {
        this.favor3 = favor3;
    }

    public String getFavor4() {
        return favor4;
    }

    public void setFavor4(String favor4) {
        this.favor4 = favor4;
    }

    public String getFavor5() {
        return favor5;
    }

    public void setFavor5(String favor5) {
        this.favor5 = favor5;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
