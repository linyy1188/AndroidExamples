package com.kashflow.geolacationexample;

import android.Manifest;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.*;
import android.widget.TextView;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    TextView textx, texty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        textx = (TextView) findViewById(R.id.x);
        texty = (TextView) findViewById(R.id.y);
        // alternative : LocationManager.NETWORK_PROVIDER
        String locationProvider = LocationManager.GPS_PROVIDER;
        // Define a listener that responds to location updates

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider
                updateLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        //request permission part
        if (!checkGpsPermission()) {
            ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION },  1);
        }
        if (checkGpsPermission()) {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
            //update first location info
            updateLocation(locationManager.getLastKnownLocation(locationProvider));
        } else {

            textx.setText("Longitude: GPS IS DISABLED");
            texty.setText("Latitude: GPS IS DISABLED");
        }
        // Register the listener with the Location Manager to receive location updates
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private boolean checkGpsPermission(){
        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED ) {
            return true;
        }
        return false;
    }

    private void updateLocation(Location location){
        textx.setText("Longitude: " + location.getLongitude());
        texty.setText("Latitude: " + location.getLatitude());
    }
}
