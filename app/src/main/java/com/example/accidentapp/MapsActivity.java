package com.example.accidentapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button;
    private EditText editText;


    int year[] = {2014, 2015, 2016, 2017, 2018};
    int sido[] = {11, 26, 27, 28, 29, 30, 31, 36, 41, 42, 43, 44, 45, 46, 47, 48, 50};


    int gun[][] = {{680, 740, 305, 500, 620, 215, 530, 545, 350, 320, 230, 590, 440, 410, 650, 200, 290, 710, 470, 560, 170, 380, 110, 140, 260},
            {440, 410, 710, 290, 170, 260, 230, 320, 530, 380, 140, 500, 470, 200, 110, 350},
            {200, 290, 710, 140, 230, 170, 260, 110},
            {710, 245, 170, 200, 140, 237, 260, 185, 720, 110},
            {200, 155, 110, 170, 140},

            {230, 110, 170, 200, 140},
            {140, 170, 200, 710, 110},
            {110},
            {820, 281, 283, 285, 287, 290, 210, 610, 310, 410, 570, 360, 250, 197, 199, 195, 135, 131, 133, 113, 117, 111, 115, 390, 270, 273, 271, 550, 173, 171, 630, 830, 730, 670, 800, 370, 460, 463, 465, 461, 430, 150, 500, 480, 220, 810, 650, 450, 590},
            {150, 820, 170, 230, 210, 800, 830, 750, 130, 810, 770, 780, 110, 190, 760, 720, 790, 730},

            {760, 800, 720, 740, 730, 770, 150, 745, 750, 710, 111, 112, 114, 113, 130},
            {250, 150, 710, 230, 830, 270, 180, 760, 210, 770, 200, 730, 810, 130, 131, 133, 790, 825, 800},
            {790, 130, 210, 190, 730, 800, 770, 710, 140, 750, 740, 113, 111, 180, 720},
            {810, 770, 720, 230, 730, 170, 710, 110, 840, 780, 150, 910, 130, 870, 830, 890, 880, 800, 900, 860, 820, 790},
            {290, 130, 830, 190, 720, 150, 280, 920, 250, 840, 170, 770, 760, 210, 230, 900, 940, 930, 730, 820, 750, 850, 111, 113},

            {310, 880, 820, 250, 840, 160, 270, 240, 860, 332, 330, 720, 170, 190, 740, 110, 125, 127, 123, 121, 129, 220, 850, 730, 870, 890},
            {130, 110}};
    Document doc;
    int error_code = 0;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;

    double mLatitude1;  //위도
    double mLongitude1; //경도


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        Button current_Button = findViewById(R.id.current_button);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accident");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /*
        //사용자의 현재 위치 위. 경도
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Location userLocation = getMyLocation();
        if( userLocation != null ) {
            mLatitude1 = userLocation.getLatitude();
            mLongitude1 = userLocation.getLongitude();
        }*/


        mLatitude1 = 35.129352;
        mLongitude1 = 129.107118;

        //if 있으면 안함
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    for (int y = 0; y < year.length; y++) {
                        for (int i = 0; i < sido.length; i++) {

                            for (int j = 0; j < gun[i].length; j++) {
                                try {
                                    String urlStr = "http://apis.data.go.kr/B552061/jaywalking/getRestJaywalking?" +
                                            "serviceKey=2myVWVPJFf%2FYAfyI%2BZK6x%2FoWVl3g8R9shatZYGa%2BPQgRBFCLonZ6sUXmijrrVAJJLhwQ5uidYxjWKLEupa7Bxw%3D%3D&" +
                                            "searchYearCd=" + year[y] + "&siDo=" + sido[i] + "&guGun=" + gun[i][j] + "&type=xml&numOfRows=10&pageNo=1";

                                    URL url = new URL(urlStr);
                                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                                    DocumentBuilder db = dbf.newDocumentBuilder();
                                    doc = db.parse(new InputSource(url.openStream()));
                                    doc.getDocumentElement().normalize();

                                    NodeList errorCheckList = doc.getElementsByTagName("header");

                                    for (int k = 0; k < errorCheckList.getLength(); k++) {

                                        Node node = errorCheckList.item(k);
                                        Element fstElmnt = (Element) node;

                                        NodeList sido_sgg_nm = fstElmnt.getElementsByTagName("resultCode");
                                        String resultCode = sido_sgg_nm.item(0).getChildNodes().item(0).getNodeValue();
                                        error_code = Integer.parseInt(resultCode);

                                    }

                                    if (error_code != 00) {
                                        continue;
                                    }

                                    NodeList nodeList = doc.getElementsByTagName("item");

                                    for (int a = 0; a < nodeList.getLength(); a++) {

                                        Node node = nodeList.item(a);
                                        Element fstElmnt = (Element) node;

                                        NodeList la_crd = fstElmnt.getElementsByTagName("la_crd");
                                        double xPos = Double.parseDouble(la_crd.item(0).getChildNodes().item(0).getNodeValue());

                                        NodeList lo_crd = fstElmnt.getElementsByTagName("lo_crd");
                                        double yPos = Double.parseDouble(lo_crd.item(0).getChildNodes().item(0).getNodeValue());

                                        NodeList sido_sgg_nm = fstElmnt.getElementsByTagName("sido_sgg_nm");
                                        final String title = sido_sgg_nm.item(0).getChildNodes().item(0).getNodeValue();

                                        NodeList spot_nm = fstElmnt.getElementsByTagName("spot_nm");
                                        final String subtitle = spot_nm.item(0).getChildNodes().item(0).getNodeValue();

                                        NodeList occrrnc_cnt = fstElmnt.getElementsByTagName("occrrnc_cnt");
                                        final double occrrnc_cnt_txt = Double.parseDouble(occrrnc_cnt.item(0).getChildNodes().item(0).getNodeValue()); // 발생 건수

                                        NodeList caslt_cnt = fstElmnt.getElementsByTagName("caslt_cnt");
                                        final double caslt_cnt_txt = Double.parseDouble(caslt_cnt.item(0).getChildNodes().item(0).getNodeValue()); // 사상자수

                                        NodeList dth_dnv_cnt = fstElmnt.getElementsByTagName("dth_dnv_cnt");
                                        final double dth_dnv_cnt_txt = Double.parseDouble(dth_dnv_cnt.item(0).getChildNodes().item(0).getNodeValue()); // 사망자수

                                        NodeList se_dnv_cnt = fstElmnt.getElementsByTagName("se_dnv_cnt");
                                        final double se_dnv_cnt_txt = Double.parseDouble(se_dnv_cnt.item(0).getChildNodes().item(0).getNodeValue()); // 중상자수

                                        NodeList sl_dnv_cnt = fstElmnt.getElementsByTagName("sl_dnv_cnt");
                                        final double sl_dnv_cnt_txt = Double.parseDouble(sl_dnv_cnt.item(0).getChildNodes().item(0).getNodeValue()); // 경상자 수

                                        double dist = distance(mLatitude1, mLongitude1, xPos, yPos , "meter");

                                        AccDto accDto = new AccDto(xPos, yPos, title, subtitle, occrrnc_cnt_txt, caslt_cnt_txt, dth_dnv_cnt_txt, se_dnv_cnt_txt, sl_dnv_cnt_txt, dist);
                                        //accList.add(new AccDto(xPos, yPos, title, subtitle, occrrnc_cnt_txt, caslt_cnt_txt, dth_dnv_cnt_txt, se_dnv_cnt_txt, sl_dnv_cnt_txt));

                                        // db write
                                        myRef.child("list").push().setValue(accDto);


                                    }

                                } catch (Exception e) {
                                    j--;
                                }
                            }
                        }
                    }
                } else {
                    Log.d("있다", "요깄네");
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 버튼 클릭 intent
        current_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CurrentActivity.class);
                startActivity(intent);


            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(SEOUL);
        markerOptions1.title("서울");
        markerOptions1.snippet("수도");
        googleMap.addMarker(markerOptions1);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(addressList.get(0).toString());
                // 콤마를 기준으로 split
                String[] splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
                System.out.println(address);

                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                System.out.println(latitude);
                System.out.println(longitude);

                // 좌표(위도, 경도) 생성
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                // 마커 생성
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title("search result");
                mOptions2.snippet(address);
                mOptions2.position(point);
                // 마커 추가
                mMap.addMarker(mOptions2);
                // 해당 좌표로 화면 줌
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
            }
        });

        myRef.child("list").addValueEventListener(new ValueEventListener() {
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

                    //String data = xPos + yPos + title + subtitle + occrrnc_cnt_txt + caslt_cnt_txt + dth_dnv_cnt_txt + se_dnv_cnt_txt + sl_dnv_cnt_txt;
                    //AccDto accDto = new AccDto(xPos, yPos, title, subtitle, occrrnc_cnt_txt, caslt_cnt_txt, dth_dnv_cnt_txt, se_dnv_cnt_txt, sl_dnv_cnt_txt);

                    LatLng acc = new LatLng(xPos, yPos);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(acc);
                    markerOptions.title(title);
                    markerOptions.snippet(subtitle);

                    BitmapDrawable bitmapdrawred = (BitmapDrawable) getResources().getDrawable(R.drawable.warning_red);
                    Bitmap b_red = bitmapdrawred.getBitmap();
                    Bitmap warning_red = Bitmap.createScaledBitmap(b_red, 30, 30, false);

                    BitmapDrawable bitmapdrawgreen = (BitmapDrawable) getResources().getDrawable(R.drawable.warning_green);
                    Bitmap b_green = bitmapdrawgreen.getBitmap();
                    Bitmap warning_green = Bitmap.createScaledBitmap(b_green, 30, 30, false);

                    BitmapDrawable bitmapdrawyellow = (BitmapDrawable) getResources().getDrawable(R.drawable.warning_yellow);
                    Bitmap b_yellow = bitmapdrawyellow.getBitmap();
                    Bitmap warning_yellow = Bitmap.createScaledBitmap(b_yellow, 30, 30, false);

                    if (dth_dnv_cnt_txt > 0) { // 사망자가 있으면
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(warning_red));
                    } else if (se_dnv_cnt_txt >= sl_dnv_cnt_txt) { // 중상자가 경상자 보다 많을 경우
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(warning_yellow));
                    } else { // 일반
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(warning_green));
                    }


                    mMap.addMarker(markerOptions);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    // 현재 위치 구하기
    private Location getMyLocation() {
        Location currentLocation = null;
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("////////////사용자에게 권한을 요청해야함");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_CODE_LOCATION);
            getMyLocation(); //이건 써도되고 안써도 되지만, 전 권한 승인하면 즉시 위치값 받아오려고 썼습니다!
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



    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if (unit == "meter") {
            dist = dist * 1609.344;
        }
        return (dist);
    }
}
