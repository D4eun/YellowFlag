package com.example.mychattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mychattingapp.data.TourlistData;
import com.example.mychattingapp.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;

public class NaverMapActivity extends AppCompatActivity {

    private FusedLocationSource locationSource;
    private NaverMap mNaverMap;
    private DatabaseReference mDatabase;
    private ArrayList<TourlistData> mTourlistDatas = new ArrayList<>();
    private ArrayList<Marker> mMarkerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_map);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setMapInit();
    }
    private void setMapInit() {// 네이버 맵 세팅
        // 로케이션 잡기 위한 변수
        locationSource = new FusedLocationSource(this, 1000);
//        맵은 내부 프래그먼트변수로 사용하는것이 편함
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {            // 맵 프래그먼트 초기화
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }
        // 맵 프래그먼트에 맵 연결
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull NaverMap naverMap) {
                mNaverMap = naverMap;
                mNaverMap.setLocationSource(locationSource);
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                // 맵을 현재 위치 따라오게 연결//
                setMapUiSetting();
                setMarker();
//                setMapLocationChanged();
//                setCameraChanged();
            }
        });
    }

    private void setMarker() {
        mTourlistDatas.clear();
        mDatabase.child("tourist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    if(userModel.uid.equals(myUid)){
                        continue;
                    }
                    mTourlistDatas.add(snapshot.getValue(TourlistData.class));

                }
                for(TourlistData d : mTourlistDatas) {
                    Log.e("name", d.getName()+d.getLatitude()+d.getLongitude());
                }
                drawMarker();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void drawMarker() {
        for (Marker m : mMarkerList) {
            m.setMap(null);
        }
        mMarkerList.clear();
        OverlayImage tourIcon = OverlayImage.fromResource(R.drawable.guideflag);

        for(TourlistData d : mTourlistDatas) {
            Marker marker = new Marker();
            marker.setPosition(new LatLng(
                    Double.parseDouble(d.getLatitude()),
                    Double.parseDouble(d.getLongitude())));
            marker.setIcon(tourIcon);
            marker.setMap(mNaverMap);

            mMarkerList.add(marker);
        }
    }



    private void setMapUiSetting() {
        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setLocationButtonEnabled(true);
    }
}
