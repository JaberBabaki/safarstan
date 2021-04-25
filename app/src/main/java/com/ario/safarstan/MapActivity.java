package com.ario.safarstan;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
  private GoogleMap googleMap;
  private Marker userMarker;
  private Marker destinationMarker;
  private Location userLocation;
  private Location destinationLocation;
  private TextView distanceTextView;
  private static final int REQUEST_CODE = 101;
  boolean b = false;
  double lat;
  double lang;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      lat = extras.getDouble("lat");
      lang = extras.getDouble("lang");
    }
    Log.i("LOC", "1");
    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    supportMapFragment.getMapAsync(this);
    Log.i("LOC", "2");
    statusCheck();
    if (b == true) {
      setUpLocationManger();
    }
  }

  public void statusCheck() {
    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      buildAlertMessageNoGps();

    }
  }

  private void buildAlertMessageNoGps() {

    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
      .setCancelable(false)
      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        public void onClick(final DialogInterface dialog, final int id) {
          startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
          b = true;
        }
      })
      .setNegativeButton("No", new DialogInterface.OnClickListener() {
        public void onClick(final DialogInterface dialog, final int id) {
          dialog.cancel();
        }
      });
    final AlertDialog alert = builder.create();
    alert.show();
    b = false;
  }

  private void setUpLocationManger() {
    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    if (locationManager != null) {
      Log.i("LOC", "3");
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        Log.i("LOC", "4");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);

        }

        return;
      }
      Log.i("LOC", "5");
      if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        Log.i("LOC", "6");
        Location location = getLastKnownLocation();
        onLocationChanged(location);
        Log.i("LOC", "6 next");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 5, this);
      }
    }
  }

  LocationManager mLocationManager;
  Location myLocation = getLastKnownLocation();

  private Location getLastKnownLocation() {
    mLocationManager = (LocationManager) Base.getContext().getSystemService(LOCATION_SERVICE);
    List<String> providers = mLocationManager.getProviders(true);
    Location bestLocation = null;
    for (String provider : providers) {
      Location l = mLocationManager.getLastKnownLocation(provider);
      if (l == null) {
        continue;
      }
      if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
        // Found best last known location: %s", l);
        bestLocation = l;
      }
    }
    return bestLocation;
  }

  @Override
  public void onLocationChanged(Location location) {
    Log.i("LOC", "7");
    Log.i("LOC", "8" + location);
    Log.i("LOC", "9" + googleMap);
    if (location != null && googleMap != null) {
      Log.i("LOC", "8");
      userLocation = location;
      LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
      if (userMarker != null) {
        Log.i("LOC", "9");
        userMarker.remove();
      }
      userMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title("موقعیت شما").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_marker)));
      CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 6);
      googleMap.animateCamera(cameraUpdate);
      destinationLocation = new Location(LocationManager.GPS_PROVIDER);
      Log.i("LOC", "9" + destinationLocation);
      LatLng latLng2 = new LatLng(lat, lang);
      destinationLocation.setLatitude(latLng2.latitude);
      Log.i("LOC", "lang  " + lat+"  "+  lang);
      destinationLocation.setLongitude(latLng2.longitude);
      if (destinationMarker != null) {
        destinationMarker.remove();
      } else {
        distanceTextView = (TextView) findViewById(R.id.text_distance);
        distanceTextView.setVisibility(View.VISIBLE);
      }
      distanceTextView.setText(" فاصله شما تامقصد " + String.valueOf(userLocation.distanceTo(destinationLocation)) + " متر " + "\n" + " معادل با " + String.valueOf(userLocation.distanceTo(destinationLocation) / 1000) + "کیلومتر");
      destinationMarker = googleMap.addMarker(new MarkerOptions().position(latLng2).title("مقصد شما").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker)));
    }
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(String provider) {

  }

  @Override
  public void onProviderDisabled(String provider) {

  }

  @Override
  public void onMapReady(final GoogleMap googleMap) {
    this.googleMap = googleMap;

    googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
      @Override
      public void onMapLongClick(LatLng latLng) {
      }
    });
    setUpLocationManger();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CODE) {
      if (grantResults[0] == 0) {
        setUpLocationManger();
      }
    }
  }
}
