package easybus.medellin.movil.gpsCTRL;

import easybus.medellin.movil.R;
import easybus.medellin.movil.mapCTRL.mapGPS.MapActivityCtrl;
import easybus.medellin.movil.mapCTRL.mapGPS.OverlayMap;
import easybus.medellin.movil.mapCTRL.mapMANUAL.MapManualCTRL;
import easybus.medellin.movil.webserviceCTRL.ConectarWebService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @author simon
 * 
 */
public class CapturarLocacionCtrl extends Activity implements OnClickListener,
		LocationListener {

	private static final String LOG_TAG = "LocationActivity";
	public static double longitud;
	public static double latitud;
	private ConectarWebService conexion = ConectarWebService.getInstance();
	private LocationManager locationManager;
	private String provider;
	private Location location;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.capturarcoordenadas);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, true);
		if (provider == null) {
			((ImageView) findViewById(R.id.ButtonPosicionManu))
					.setOnClickListener(this);
			((ImageView) findViewById(R.id.buttonPosicionAuto)).setVisibility(View.GONE);
		} else {
			locationManager.requestLocationUpdates(provider, 0, 1000, this);
			location = locationManager.getLastKnownLocation(provider);
			if (location != null) {
				((ImageView) findViewById(R.id.buttonPosicionAuto))
						.setOnClickListener(this);
				((ImageView) findViewById(R.id.ButtonPosicionManu))
						.setOnClickListener(this);
			} else {
				Toast.makeText(this,
						"Encienda el GPS o la localizacion por redes",
						Toast.LENGTH_SHORT).show();
			}
		}

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		try {
			locationManager.removeUpdates(this);
		} catch (Exception e) {
			Log.v(LOG_TAG, "No se puede pq el gps no esta activado");
		}
	}

	/**
	 * Este metodo inicia la localizacion por GPS
	 */
	private void comenzarLocalizacionGPS() {
		longitud = location.getLongitude();
		latitud = location.getLatitude();
		OverlayMap.latitud = latitud * 1E6;
		OverlayMap.longitud = longitud * 1E6;
		conexion.setLong_ini(longitud);
		conexion.setLat_ini(latitud);
		mostrarPosicion();
	}

	/**
	 * Este metodo inicia el Mapa (Google maps)
	 * 
	 * @param loc
	 */
	private void mostrarPosicion() {
		Intent i = new Intent(CapturarLocacionCtrl.this, MapActivityCtrl.class);
		startActivity(i);
		this.finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}

	/**
	 * En este metodo se da inicia a la localizacion por GPS cuando se presiono
	 * el boton "GPS" o se abre el mapa para dar la coordenadas iniciales y
	 * finales si se oprime el bot�n "Ubicacion Manual"
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonPosicionAuto:
			try {
				comenzarLocalizacionGPS();
				// locationManager.removeUpdates(locListener);
				// this.finish();
			} catch (Exception ex) {
				Toast.makeText(
						this,
						"Enciende el gps o la localizacion por redes para hacer uso de esta opcion",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.ButtonPosicionManu:
			Intent i = new Intent(CapturarLocacionCtrl.this,
					MapManualCTRL.class);
			startActivity(i);
			this.finish();
			break;
		}

	}

	/*
	 * Aqui nos registramos para recibir actulizacion del GPS, ademas de enviar
	 * las coordenadas a la clase OverlayMap
	 */

	public void onLocationChanged(Location location) {
		longitud = location.getLongitude();
		latitud = location.getLatitude();
		OverlayMap.latitud = latitud * 1E6;
		OverlayMap.longitud = longitud * 1E6;
		conexion.setLong_ini(longitud);
		conexion.setLat_ini(latitud);
		locationManager.removeUpdates(this);
	}

	/*
	 * Si el gps esta apagado
	 */

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	/*
	 * Si el gps esta prendido
	 */

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	/*
	 * Si el gps se ha actulizado (posici�n)
	 */

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}