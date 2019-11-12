package com.example.mychattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mychattingapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.io.IOException;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private EditText email;
    private EditText name;
    private EditText password;
    private EditText address;
    private Double latitude;
    private Double longitude;
    private Button signup;
    private Button plusfavor1;
    private Button plusfavor2;
    private Button plusfavor3;
    private Button plusfavor4;
    private EditText favor1;
    private EditText favor2;
    private EditText favor3;
    private EditText favor4;
    private EditText favor5;
    private LinearLayout layoutfavor2;
    private LinearLayout layoutfavor3;
    private LinearLayout layoutfavor4;
    private LinearLayout layoutfavor5;
    private String splash_background;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private android.widget.Toast Toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        splash_background = mFirebaseRemoteConfig.getString(getString(R.string.rc_color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(Color.parseColor(splash_background));

        }
        Button btn_search = (Button)findViewById(R.id.button);
        if (btn_search != null)

            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SignupActivity.this, AddressActivity.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });


        email = (EditText)findViewById(R.id.signupActivity_edittext_email);
        name = (EditText)findViewById(R.id.signupActivity_edittext_name);
        password = (EditText)findViewById(R.id.signupActivity_edittext_password);
        address = (EditText)findViewById(R.id.signupActivity_edittext_address);
        signup = (Button)findViewById(R.id.signupActivity_button_signup);
        signup.setBackgroundColor(Color.parseColor(splash_background));
        plusfavor1 = (Button)findViewById(R.id.signupActivity_edittext_plusfavor1);
        plusfavor2 = (Button)findViewById(R.id.signupActivity_edittext_plusfavor2);
        plusfavor3 = (Button)findViewById(R.id.signupActivity_edittext_plusfavor3);
        plusfavor4 = (Button)findViewById(R.id.signupActivity_edittext_plusfavor4);
        favor1 = (EditText)findViewById(R.id.signupActivity_edittext_favor1);
        favor2 = (EditText)findViewById(R.id.signupActivity_edittext_favor2);
        favor3 = (EditText)findViewById(R.id.signupActivity_edittext_favor3);
        favor4 = (EditText)findViewById(R.id.signupActivity_edittext_favor4);
        favor5 = (EditText)findViewById(R.id.signupActivity_edittext_favor5);
        layoutfavor2 = (LinearLayout)findViewById(R.id.signupActivity_linearlayout_favor2);
        layoutfavor3 = (LinearLayout)findViewById(R.id.signupActivity_linearlayout_favor3);
        layoutfavor4 = (LinearLayout)findViewById(R.id.signupActivity_linearlayout_favor4);
        layoutfavor5 = (LinearLayout)findViewById(R.id.signupActivity_linearlayout_favor5);

        plusfavor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favor1 != null){
                    layoutfavor2.setVisibility(View.VISIBLE);
                }
            }
        });
        plusfavor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favor2 != null){
                    layoutfavor3.setVisibility(View.VISIBLE);
                }
            }
        });
        plusfavor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favor3 != null){
                    layoutfavor4.setVisibility(View.VISIBLE);
                }
            }
        });
        plusfavor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favor4 != null){
                    layoutfavor5.setVisibility(View.VISIBLE);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString() == null || name.getText().toString() == null || password.getText().toString() == null || address.getText().toString() == null || favor1.getText().toString() == null){
                    return;
                }
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {

                                final String uid = task.getResult().getUser().getUid();

                                        UserModel userModel = new UserModel();

                                        userModel.name = name.getText().toString();
                                        userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        userModel.address = address.getText().toString();
                                        userModel.latitude = latitude.toString();
                                        userModel.longitude = longitude.toString();
                                        userModel.favor1 = favor1.getText().toString();
                                        userModel.favor2 = favor2.getText().toString();
                                        userModel.favor3 = favor3.getText().toString();
                                        userModel.favor4 = favor4.getText().toString();
                                        userModel.favor5 = favor5.getText().toString();

                                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {

                                    @Override

                                    public void onSuccess(Void aVoid) {

                                        SignupActivity.this.finish();

                                    }

                                });
                                FirebaseDatabase.getInstance().getReference().child("tourist").child(uid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {

                                    @Override

                                    public void onSuccess(Void aVoid) {

                                        SignupActivity.this.finish();

                                    }

                                });
                            }
                        });
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode){
            case SEARCH_ADDRESS_ACTIVITY:
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data");
                    if (data != null)
                        address.setText(data);
                }
                break;
        }
    }

    public void geoLocate(View view) {
        EditText findLocation = (EditText)findViewById(R.id.signupActivity_edittext_address);
        String locationText = findLocation.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = null;


        try {
            list = gc.getFromLocationName(locationText, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Address address = list.get(0);

        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        latitude = address.getLatitude();
        longitude = address.getLongitude();
    }


}
