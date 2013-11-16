package easybus.medellin.movil.mapCTRL;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import easybus.medellin.movil.R;
import easybus.medellin.movil.detectors.ConnectionDetector;
import easybus.medellin.movil.detectors.GoogleServicesDetector;
import easybus.medellin.movil.services.GoogleLocationService;
import easybus.medellin.movil.services.notifier.NotifierReceiver;
import easybus.medellin.movil.suscriptors.NotifierSubscriptor;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MapActivity extends android.support.v4.app.FragmentActivity
							implements OnMapClickListener, OnMarkerClickListener,
							NotifierSubscriptor {

	private CameraUpdate camara;
	private LatLng posicion;
	private GoogleMap mapa;
	private MarkerOptions marker;
	
	private ConnectionDetector connection;
	private GoogleServicesDetector service_detector;
	private boolean manual_location = false;
	
//	CONSTANTES
	private final double LATITUD_INICIAL = 6.235357;
	private final double LONGITUD_INICIAL = -75.575480;
	private final int ZOOM = 14;
//	FIN CONSTANTES
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		
//		Accederemos al mapa llamando al método getMap() del fragmento MapFragment via getSupportFragment
//		que contenga nuestro mapa
		mapa = (
					(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)
				).getMap();
		mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		posicion = new LatLng(LATITUD_INICIAL, LONGITUD_INICIAL);
		camara = CameraUpdateFactory.newLatLngZoom(posicion, ZOOM);
		mapa.moveCamera(camara);
		
		connection = ConnectionDetector.getConnectionDetector(getApplicationContext());
		if (!connection.isConnectedToInternet()) {
			Toast.makeText(
				getApplicationContext(),
				"Active los datos o conectate al wifi mas cercano",
				Toast.LENGTH_LONG
			).show();
		} else {
			detectGoogleServices();
		}
		mapa.setOnMapClickListener( this );
		mapa.setOnMarkerClickListener( this );
		marker = new MarkerOptions();
	}

	private void detectGoogleServices() {
		service_detector = GoogleServicesDetector.getGoogleServicesDetector(this);
		int result_code = service_detector.isGoogleServicesEnable();
		if (result_code == GoogleServicesDetector.TRUE) {
			Intent intent = new Intent(this,GoogleLocationService.class);
			NotifierReceiver.subscribeToNotifications(this);
			startService(intent);
		} else {
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.easybus_menu, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch( item.getItemId() ){
		case R.id.manual_location:
			if (manual_location) {
				manual_location = false;
				item.setChecked(false);
			} else {
				manual_location = true;
				item.setChecked(true);
			}
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onMapClick(LatLng point) {
		if( manual_location ) {
			mapa.clear();
			marker.position(point).title("Help/Ayuda");
			mapa.addMarker(marker);
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		return true;
	}

	@Override
	public void receiveLocationUpdate(Location location) {
		mapa.clear();
		marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
		mapa.addMarker(marker);
	}
	

}
