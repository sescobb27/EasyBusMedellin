package easybus.medellin.movil.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import easybus.medellin.movil.detectors.GoogleServicesDetector;
import easybus.medellin.movil.services.notifier.Notifier;

public class GoogleLocationService extends IntentService implements LocationListener,
											GooglePlayServicesClient.ConnectionCallbacks,
											GooglePlayServicesClient.OnConnectionFailedListener {
	
	public final static String class_name = "GoogleLocationService"; 
	private Notifier notifier;
	
	public GoogleLocationService() {
		super(class_name);
	}

	private static LocationRequest location_request;
	private static LocationClient location_client;
	private GoogleServicesDetector services_detector;
	
	private void stopUpdates() {
		if ( location_client.isConnected() ) {
			location_client.removeLocationUpdates(this);
		}
		location_client.disconnect();
	}

	private void startUpdates() {
		services_detector = GoogleServicesDetector.getGoogleServicesDetector(getApplicationContext());
		int result_code = services_detector.isGoogleServicesEnable();
		if ( result_code == GoogleServicesDetector.TRUE )
			location_client.requestLocationUpdates( location_request, this );
		else {
			showErrorDialog( result_code, 0);
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		notifier.notifyChanges(location);
	}
	
	private void showErrorDialog(int error_code, int request_code) {
//        Dialog error_dialog = GooglePlayServicesUtil.getErrorDialog( error_code, getApplicationContext(), request_code );
//        // If Google Play services can provide an error dialog
//        if (error_dialog != null) {
//            ErrorDialogFragment errorFragment = new ErrorDialogFragment();
//            errorFragment.setDialog(error_dialog);
//            errorFragment.show(getSupportFragmentManager(), "Desbarate");
//        }
		Toast.makeText(getApplicationContext(), "ShowErrorDialog on GoogleLocationService class", Toast.LENGTH_LONG).show();
    }

	@Override
	public void onConnectionFailed(ConnectionResult result) {
/*		if ( result.hasResolution() ) {
			try {
				result.startResolutionForResult(this, LocationConstants.CONNECTION_FAILURE_RESOLUTION_REQUEST);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			showErrorDialog(result.getErrorCode(), LocationConstants.CONNECTION_FAILURE_RESOLUTION_REQUEST);
		}*/
		Toast.makeText(getApplicationContext(), "ConnectionFailed on GoogleLocationService class", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		startUpdates();
	}

	@Override
	public void onDisconnected() {
		stopUpdates();
	}

	public void disconnect() {
		if ( notifier != null )
			notifier.unRegister();
		notifier = null;
		location_client.removeLocationUpdates(this);
		// After disconnect() is called, the client is considered "dead".
		location_client.disconnect();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		location_request = LocationRequest.create();
		location_request.setInterval(LocationConstants.UPDATE_INTERVAL_IN_MILLISECONDS);
		location_request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		location_request.setFastestInterval(LocationConstants.FAST_INTERVAL_CEILING_IN_MILLISECONDS);
		location_client = new LocationClient(getApplicationContext(), this, this);
		location_client.connect();
		notifier = new Notifier(getApplicationContext());
	}
}
