package com.example.mychattingapp;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;

public class NaverMapActivity2 extends AppCompatActivity {

    private FusedLocationSource locationSource;
    private NaverMap mNaverMap;
    private DatabaseReference mDatabase;
    private ArrayList<TourlistData> mTourlistDatas = new ArrayList<>();
    private ArrayList<Marker> mMarkerList = new ArrayList<>();

    LinearLayout panel;
    TextView name, lat, lng;
    Button info, req;

    private boolean isShowPanel = false;
    private String markerTag = "";

    Overlay.OnClickListener markerClickListener = new Overlay.OnClickListener() {
        @Override
        public boolean onClick(@NonNull Overlay overlay) {
            String b4MarketTag = markerTag;
            markerTag = overlay.getTag().toString();
            showData(markerTag);

            if(isShowPanel) {
                if(b4MarketTag.equals(markerTag)) {
                    hidePanel();
                }
            } else {
                showPanel();
            }

            return true;
        }
    };

    private void showPanel() {
        isShowPanel = true;
        float distance = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0,
                getResources().getDisplayMetrics()
        );
        panel.animate().translationY(distance).setDuration(300).start();
    }

    private void hidePanel() {
        isShowPanel = false;
        float distance = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 200,
                getResources().getDisplayMetrics()
        );
        panel.animate().translationY(distance).setDuration(300).start();
    }

    private void hidePanelFast() {
        float distance = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 200,
                getResources().getDisplayMetrics()
        );
        panel.animate().translationY(distance).setDuration(10).start();
        panel.setVisibility(View.VISIBLE);
    }


    private void showData(String markerTag) {

        TourlistData data = new TourlistData();
        for(TourlistData d : mTourlistDatas) {
            if(d.getUid().equals(markerTag)) {
                data = d;
                break;
            }
        }

        name.setText(data.getName());
        lat.setText(data.getLatitude());
        lng.setText(data.getLongitude());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_map2);

        panel = findViewById(R.id.map_ll_panel);
        name = findViewById(R.id.map_tv_name);
        lat = findViewById(R.id.map_tv_lat);
        lng = findViewById(R.id.map_tv_lng);
        info = findViewById(R.id.map_btn_info);
        req = findViewById(R.id.map_btn_req);

        hidePanelFast();
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
            marker.setTag(d.getUid());
            marker.setPosition(new LatLng(
                    Double.parseDouble(d.getLatitude()),
                    Double.parseDouble(d.getLongitude())));
            marker.setIcon(tourIcon);
            marker.setMap(mNaverMap);
            marker.setOnClickListener(markerClickListener);

            mMarkerList.add(marker);
        }

        mNaverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                hidePanel();
            }
        });
    }



    private void setMapUiSetting() {
        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setLocationButtonEnabled(true);
    }
}
