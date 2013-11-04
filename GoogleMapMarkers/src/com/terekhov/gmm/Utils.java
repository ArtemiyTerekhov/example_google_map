package com.terekhov.gmm;

import android.content.Context;
import android.location.LocationManager;

public class Utils {
	public static boolean isGPSEnabled(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager != null) {
			return locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}

		return false;
	}

	public static boolean isNetworkLocationEnabled(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager != null) {
			return locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		}

		return false;
	}
}
