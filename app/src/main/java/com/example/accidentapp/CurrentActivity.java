package com.example.accidentapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class CurrentActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Geocoder geocoder;

    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;

    TextView title;

    private CheckBox mSatellite;

    double mLatitude1;  //위도
    double mLongitude1; //경도

    FirebaseDatabase database;
    DatabaseReference myRef;

    ListView listView;
    AccDtoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_current);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mSatellite = (CheckBox) findViewById(R.id.checkBox);
        title = findViewById(R.id.textView2);
        listView = findViewById(R.id.listView);
        adapter = new AccDtoAdapter();

        mSatellite.setOnClickListener(satelliteOnClickListener);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accident");


        //사용자의 현재 위치 위. 경도
        /*
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Location userLocation = getMyLocation();
        if( userLocation != null ) {
            mLatitude1 = userLocation.getLatitude();
            mLongitude1 = userLocation.getLongitude();
        }

         */

        mLatitude1 = 35.129352;
        mLongitude1 = 129.107118;


        Geocoder mGeoCoder = new Geocoder(this);
        try {
            List<Address> cur_address = mGeoCoder.getFromLocation(mLatitude1, mLongitude1,1);
            title.setText(cur_address.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private CheckBox.OnClickListener satelliteOnClickListener = new CheckBox.OnClickListener() {
        @Override
        public void onClick(View v) {
            setSatellite();
        }
    };

    private void setSatellite() {
        if (mSatellite.isChecked()) { mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID); }
        else { mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); } };



    // 현재 위치 구하기
    private Location getMyLocation() {
        Location currentLocation = null;
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("////////////사용자에게 권한을 요청해야함");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_CODE_LOCATION);
            getMyLocation();
        }
        else {
            System.out.println("////////////권한요청 안해도됨");

            // 수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if (currentLocation != null) {
                double lng = currentLocation.getLongitude();
                double lat = currentLocation.getLatitude();
            }
        }
        return currentLocation;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.current_location);
        Bitmap place = bitmapdraw.getBitmap();
        Bitmap placebit = Bitmap.createScaledBitmap(place, 100, 100, false);

        // 현재위치
        LatLng cur = new LatLng(mLatitude1, mLongitude1);
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(cur);
        markerOptions1.icon(BitmapDescriptorFactory.fromBitmap(placebit));
        googleMap.addMarker(markerOptions1);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cur));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));


        myRef.child("list").orderByChild("distance").limitToFirst(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    double xPos = dataSnapshot1.getValue(AccDto.class).getXpos(); // x 좌표
                    double yPos = dataSnapshot1.getValue(AccDto.class).getYpos(); // y 좌표
                    String title = dataSnapshot1.getValue(AccDto.class).getTitle(); // 시도
                    String subtitle = dataSnapshot1.getValue(AccDto.class).getSubtitle(); // 지역명

                    double occrrnc_cnt_txt = dataSnapshot1.getValue(AccDto.class).getOccrrnc_cnt_txt(); // 발생건수
                    double caslt_cnt_txt = dataSnapshot1.getValue(AccDto.class).getCaslt_cnt_txt(); // 사상자 수
                    double dth_dnv_cnt_txt = dataSnapshot1.getValue(AccDto.class).getDth_dnv_cnt_txt(); // 사망자 수
                    double se_dnv_cnt_txt = dataSnapshot1.getValue(AccDto.class).getSe_dnv_cnt_txt(); // 중상자 수
                    double sl_dnv_cnt_txt = dataSnapshot1.getValue(AccDto.class).getSl_dnv_cnt_txt(); // 경상자 수
                    double distance = dataSnapshot1.getValue(AccDto.class).getDistance();

                    //String data = xPos + yPos + title + subtitle + occrrnc_cnt_txt + caslt_cnt_txt + dth_dnv_cnt_txt + se_dnv_cnt_txt + sl_dnv_cnt_txt;
                    //AccDto accDto = new AccDto(xPos, yPos, title, subtitle, occrrnc_cnt_txt, caslt_cnt_txt, dth_dnv_cnt_txt, se_dnv_cnt_txt, sl_dnv_cnt_txt);
                    LatLng acc = new LatLng(xPos, yPos);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(acc);
                    markerOptions.title(title);
                    markerOptions.snippet(subtitle);
                    mMap.addMarker(markerOptions);


                    listView.setAdapter(adapter);

                    adapter.addItem(subtitle, distance, occrrnc_cnt_txt, sl_dnv_cnt_txt, se_dnv_cnt_txt, dth_dnv_cnt_txt);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

