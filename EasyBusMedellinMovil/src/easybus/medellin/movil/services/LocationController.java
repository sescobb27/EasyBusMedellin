package easybus.medellin.movil.services;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import easybus.medellin.movil.exceptions.NoLocationServiceEnable;
import easybus.medellin.movil.suscriptors.LocationSuscriptor;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

public class LocationController implements LocationListener {

	private LocationManager location_manager;
	private Criteria location_criteria;
	
	private LocationSuscriptor suscriptor;
	
	private NoLocationServiceEnable service_exception;
	
	private LatLng last_location;
	
//	CONSTANTS
	private final int _5_MIN = 500000;
//	END CONSTANTS
	
	public LocationController(Context context) {
		location_manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		location_criteria = new Criteria();
		location_criteria.setAccuracy(Criteria.ACCURACY_HIGH);
		service_exception = new NoLocationServiceEnable();
	}
	
	public String getBestLocationByCriteria(boolean enable_only) {
		return location_manager.getBestProvider(location_criteria, enable_only);
	}
	
	public List<String> getAllBestProvidersByCriteria(boolean enable_only) {
		return location_manager.getProviders(location_criteria, enable_only);
	}
	
	public List<String> getAllProviders() {
		return location_manager.getAllProviders();
	}
	
	public void suscribeToOnLocationChange( LocationSuscriptor suscriptor ) {
		this.suscriptor = suscriptor;
	}
	
	private void setLocationUpdates(String provider, long time) {
		location_manager.requestLocationUpdates(
			provider, time, 0, this);
	}
	
	private boolean isGpsEnable() {
		return location_manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	private boolean isWifiLocationEnable() {
		return location_manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}
	
	public LocationProvider getGpsProvider() {
		return location_manager.getProvider(LocationManager.GPS_PROVIDER);
	}
	
	public LocationProvider getNetworkProvider() {
		return location_manager.getProvider(LocationManager.NETWORK_PROVIDER);
	}
	
	public boolean setGpsOrNetworLocation() throws NoLocationServiceEnable {
		if ( setGpsAsProvider() || setNetworAsProvider()  )
			return true;
//		if (isGpsEnable()) {
//			setLocationUpdates(LocationManager.GPS_PROVIDER,
//					LocationConstants.FAST_INTERVAL_CEILING_IN_MILLISECONDS);
//			return getGpsProvider();
//		} else if (isWifiLocationEnable()) {
//			setLocationUpdates(LocationManager.NETWORK_PROVIDER,
//					LocationConstants.FAST_INTERVAL_CEILING_IN_MILLISECONDS);
//			return getNetworkProvider();
//		} else
		throw service_exception;
	}
	
	public boolean setGpsAsProvider() {
		if (isGpsEnable()) {
			setLocationUpdates(LocationManager.GPS_PROVIDER,
					LocationConstants.FAST_INTERVAL_CEILING_IN_MILLISECONDS);
//			return getGpsProvider();
			return true;
		}
		return false;
	}
	
	public boolean setNetworAsProvider() {
		if (isWifiLocationEnable()) {
			setLocationUpdates(LocationManager.NETWORK_PROVIDER,
					LocationConstants.FAST_INTERVAL_CEILING_IN_MILLISECONDS);
//			return getNetworkProvider();
			return true;
		}
		return false;
	}
	
	public void updateUpdateRate(String provider) {
		setLocationUpdates(provider, _5_MIN);
	}
	
	public void removeUpdates() {
		location_manager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		LatLng new_location = new LatLng(location.getLatitude(), location.getLongitude());
		if (last_location ==  null) {
			last_location = new_location;
		}
		suscriptor.OnLocationChange(new_location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		if (!isWifiLocationEnable() && !isGpsEnable())
			suscriptor.OnGpsAndNetworkDisable();
	}

	@Override
	public void onProviderEnabled(String provider) {
		if (provider.equals(LocationManager.GPS_PROVIDER))
			suscriptor.OnGpsProviderEnable();
		else if (isWifiLocationEnable())
			suscriptor.OnNetworkProviderEnable();	
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO
	}
}
