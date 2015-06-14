package com.example.sahith.eventifi3;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.List;


public class MainActivity extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener, OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Vpha2WmhDUlGNClOGPGItvEvzLRvzryD5SpJolDC", "e3vCwF3bDU9NjdUB1tISyB1ZSpe83DnwSC7hhqxL");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(final GoogleMap map) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(13)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.whereEqualTo("display", true);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        ParseObject event = list.get(i);
                        String name = event.getString("name");
                        String address = event.getString("address");
                        String description = event.getString("description");
                        double[] coords = getCoords(address);
                        Marker marker = map.addMarker(new MarkerOptions()
                                .position(new LatLng(coords[0], coords[1]))
                                .title(name)
                                .snippet(description));
                        marker.showInfoWindow();
                    }
                } else {

                }
            }
        });
    }
    public static final String EXTRA_MESSAGE = "message";

    public void createNewEvent(View v)
    {
        Intent intent = new Intent(this, NewEventActivity.class);
        startActivity(intent);
    }

    public double[] getCoords(String address) {
        Geocoder tempGeo = new Geocoder(this.getApplicationContext());
        try {
            List<Address> listAdd = tempGeo.getFromLocationName(address, 1);
            double lng = listAdd.get(0).getLongitude();
            double lat = listAdd.get(0).getLatitude();
            double[] cor = new double[2];
            cor[0] = lat;
            cor[1] = lng;
            return cor;
        } catch (IOException e) {
            Log.d("Tag", e.toString());
            return null;
        }
    }

}
