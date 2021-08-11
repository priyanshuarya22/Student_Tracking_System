package com.example.studenttrackingsystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.studenttrackingsystem.databinding.ActivityStudentsMapsBinding;

public class StudentsMapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    GetLocDataFromServer getLoc;
    CountDownTimer ct;
    private GoogleMap mMap;
    private ActivityStudentsMapsBinding binding;
    LocationManager locationManager;
    LatLng lg;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String dateTime;
    SendLocDataToServer sendLoc;
    String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStudentsMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        //getting the user id and password
        sid = sh.getString("sid", "");
        ct = new CountDownTimer(10000, 5000) {
            @SuppressLint("SimpleDateFormat")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTick(long millisUntilFinished) {
                if (lg == null) {
                    if (ActivityCompat.checkSelfPermission(StudentsMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(StudentsMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    lg = new LatLng(location.getLatitude(), location.getLongitude());
                }
                calendar = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                dateTime = simpleDateFormat.format(calendar.getTime()).toString();
                //updating the camera to that location and zooming to 15 percent
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(lg, 15);
                //animating the camera to that location
                mMap.animateCamera(cameraUpdate);
                //creating object of Marker
                MarkerOptions markerOptions = new MarkerOptions();
                //positioning the marker on the searched location
                markerOptions.position(lg);
                //adding title to the marker equals to the locality of the searched location
                markerOptions.title(dateTime);
                //adding the marker on the map
                mMap.addMarker(markerOptions);
                sendLoc = new SendLocDataToServer();
                String lat = lg.latitude + "";
                String lng = lg.longitude + "";
                String result = sendLoc.sendLocationData(sid, lat, lng, dateTime);
                Toast.makeText(StudentsMapsActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                ct.start();
            }
        };
        ct.start();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ActivityCompat.requestPermissions(StudentsMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, StudentsMapsActivity.this);
        getLoc = new GetLocDataFromServer();
        String response = getLoc.getLocationData(sid);
        if(!response.equals("")) {
            String[] result = response.split(";");
            for (String record : result) {
                String[] recordArray = record.split("<--->");
                LatLng loc = new LatLng(Float.parseFloat(recordArray[0]), Float.parseFloat(recordArray[1]));
                mMap.addMarker(new MarkerOptions().position(loc).title(recordArray[2]));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lg = new LatLng(location.getLatitude(), location.getLongitude());
    }
}