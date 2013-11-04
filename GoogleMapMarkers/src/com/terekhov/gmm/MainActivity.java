package com.terekhov.gmm;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";
	private static final int RQS_GooglePlayServices = 100;

	// Google Map
	private GoogleMap mGoogleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Check status of Google Play Services
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		// Check Google Play Service Available
		try {
			if (status != ConnectionResult.SUCCESS) {
				GooglePlayServicesUtil.getErrorDialog(status, this,
						RQS_GooglePlayServices).show();
			} else {
				try {
					setContentView(R.layout.activity_main);
					// Loading map
					setUpMapIfNeeded();

				} catch (Exception e) {
					e.printStackTrace();

					Toast.makeText(getApplicationContext(),
							R.string.error_create_maps, Toast.LENGTH_SHORT)
							.show();
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: GooglePlayServiceUtil: " + e);
		}

	}

	private void setUpMapIfNeeded() {
		// load map. If map is not created it will create it
		if (mGoogleMap == null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			if (fragmentManager != null) {
				SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager
						.findFragmentById(R.id.map);
				if (mapFragment != null) {
					mGoogleMap = mapFragment.getMap();

					// check if map is created successfully or not
					if (mGoogleMap != null) {
						if (Utils.isGPSEnabled(this)
								&& Utils.isNetworkLocationEnabled(this)) {
							mGoogleMap.setMyLocationEnabled(true);
						}

						testAddCustomMarkers();
					}
				}
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void testAddCustomMarkers() {
		// GREEN color marker
		addDefaultMarker(37.6155600, 55.7522200,
				BitmapDescriptorFactory.HUE_GREEN);

		// BLUE color marker
		addDefaultMarker(37.6155600, 55.9522200,
				BitmapDescriptorFactory.HUE_BLUE);

		// add custom marker
		addMarker(37.8155600, 55.5522200, R.drawable.map_marker);

		// add custom marker
		addMarker(37.5155600, 55.5522200, R.drawable.google_map_markers);
	}

	private void addDefaultMarker(double lon, double lat, float hue) {
		if (mGoogleMap == null) {
			Log.w(TAG, "GoogleMap is not initialized");
			return;
		}

		LatLng currentPosition = new LatLng(lat, lon);

		mGoogleMap.addMarker(new MarkerOptions()
				.position(currentPosition)
				.title(String.format(
						getResources().getString(R.string.marker_baloon), lon,
						lat)).icon(BitmapDescriptorFactory.defaultMarker(hue)));
	}

	private void addMarker(double lon, double lat, int resourceId) {
		if (mGoogleMap == null) {
			Log.w(TAG, "GoogleMap is not initialized");
			return;
		}

		LatLng currentPosition = new LatLng(lat, lon);

		mGoogleMap.addMarker(new MarkerOptions()
				.position(currentPosition)
				.title(String.format(
						getResources().getString(R.string.marker_baloon), lon,
						lat))
				.icon(BitmapDescriptorFactory.fromResource(resourceId)));

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				currentPosition, 10));
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
	}
}
