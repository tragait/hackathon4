package com.tracker.map.geo;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;


/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment{
    private LocationManager locationManager;
    MapView mMapView;
    private GoogleMap googleMap;


}
