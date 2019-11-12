package com.example.mychattingapp.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mychattingapp.Chat.MessageActivity;
import com.example.mychattingapp.NaverMapActivity;
import com.example.mychattingapp.NaverMapActivity2;
import com.example.mychattingapp.R;
import com.example.mychattingapp.data.TourlistData;
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

import java.sql.BatchUpdateException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MapContainerFragment extends Fragment {private FusedLocationSource locationSource;
//    private NaverMap mNaverMap;
//    private DatabaseReference mDatabase;
//    private ArrayList<TourlistData> mTourlistDatas = new ArrayList<>();
//    private ArrayList<Marker> mMarkerList = new ArrayList<>();
//    private View view;
    private Button mapbutton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        setMapInit();
        mapbutton = (Button)view.findViewById(R.id.mapfragment_matbutton);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NaverMapActivity2.class);
                ActivityOptions activityOptions = null;
                activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright, R.anim.toleft);
                startActivity(intent, activityOptions.toBundle());
            }
        });

        return view;


    }

//    private void setMapInit() {// 네이버 맵 세팅
//        // 로케이션 잡기 위한 변수
//        locationSource = new FusedLocationSource(this, 1000);
////        맵은 내부 프래그먼트변수로 사용하는것이 편함
//
//        MapFragment mapFragment = (MapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment == null) {            // 맵 프래그먼트 초기화
//            mapFragment = MapFragment.newInstance();
//            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
//        }
//        // 맵 프래그먼트에 맵 연결
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(@NonNull NaverMap naverMap) {
//                mNaverMap = naverMap;
//                mNaverMap.setLocationSource(locationSource);
//                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
//                // 맵을 현재 위치 따라오게 연결//
//                setMapUiSetting();
//                setMarker();
////                setMapLocationChanged();
////                setCameraChanged();
//            }
//        });
//    }
//
//    private void setMarker() {
//        mTourlistDatas.clear();
//        mDatabase.child("tourist").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    mTourlistDatas.add(ds.getValue(TourlistData.class));
//
//                }
//
//                for(TourlistData d : mTourlistDatas) {
//                    Log.e("name", d.getName()+d.getLatitude()+d.getLongitude());
//                }
//                drawMarker();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    private void drawMarker() {
//        for (Marker m : mMarkerList) {
//            m.setMap(null);
//        }
//        mMarkerList.clear();
//        OverlayImage tourIcon = OverlayImage.fromResource(R.drawable.fortour);
//
//        for(TourlistData d : mTourlistDatas) {
//            Marker marker = new Marker();
//            marker.setPosition(new LatLng(
//                    Double.parseDouble(d.getLatitude()),
//                    Double.parseDouble(d.getLongitude())));
//            marker.setIcon(tourIcon);
//            marker.setMap(mNaverMap);
//            mMarkerList.add(marker);
//        }
//    }
//
//
//
//    private void setMapUiSetting() {
//        UiSettings uiSettings = mNaverMap.getUiSettings();
//        uiSettings.setCompassEnabled(true);
//        uiSettings.setLocationButtonEnabled(true);
//    }

}