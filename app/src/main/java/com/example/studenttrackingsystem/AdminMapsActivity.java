package com.example.studenttrackingsystem;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.studenttrackingsystem.databinding.ActivityAdminMapsBinding;

public class AdminMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityAdminMapsBinding binding;
    EditText et1, et2;
    Button b1;
    GetLocDataFromServer getLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        et1 = findViewById(R.id.admin_et1);
        et2 = findViewById(R.id.admin_et2);
        b1 = findViewById(R.id.admin_b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid = et1.getText().toString();
                String date = et2.getText().toString();
                getLoc = new GetLocDataFromServer();
                String response = getLoc.getLocationData(sid);
                if(!response.equals("")) {
                    String[] result = response.split(";");
                    for (String record : result) {
                        String[] recordArray = record.split("<--->");
                        String[] date_time = recordArray[2].split(" ");
                        if(date_time[0].equals(date)) {
                            LatLng loc = new LatLng(Float.parseFloat(recordArray[0]), Float.parseFloat(recordArray[1]));
                            //updating the camera to that location and zooming to 15 percent
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(loc, 15);
                            //animating the camera to that location
                            mMap.animateCamera(cameraUpdate);
                            //creating object of Marker
                            MarkerOptions markerOptions = new MarkerOptions();
                            //positioning the marker on the searched location
                            markerOptions.position(loc);
                            //adding title to the marker equals to the locality of the searched location
                            markerOptions.title(date_time[1]);
                            //adding the marker on the map
                            mMap.addMarker(markerOptions);
                        }
                    }
                }
            }
        });
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
    }
}