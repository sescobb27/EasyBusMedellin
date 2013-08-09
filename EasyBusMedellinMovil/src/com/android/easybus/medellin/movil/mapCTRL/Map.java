package com.android.easybus.medellin.movil.mapCTRL;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.easybus.medellin.movil.R;
import com.android.easybus.medellin.movil.RutasBuses;
import com.android.easybus.medellin.movil.listeners.SelectorListener;
import com.android.easybus.medellin.movil.listeners.SelectorRutasListener;
import com.android.easybus.medellin.movil.model.SingletonSolucionManager;
import com.android.easybus.medellin.movil.model.Transporte;
import com.android.easybus.medellin.movil.model.Solucion.Solucion;
import com.android.easybus.medellin.movil.webserviceCTRL.ConectarWebService;
import com.android.easybus.medellin.movil.webserviceCTRL.WebServiceSubscriptor;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;


public class Map extends MapActivity implements SelectorListener,WebServiceSubscriptor {

	protected SingletonSolucionManager sol_manager = SingletonSolucionManager
			.getInstance();
	protected LinkedList<Transporte> list_transporte;
	protected MapView mapa;
	protected MapController mc;
	protected GeoPoint geo;

	protected ConectarWebService conexion;

	protected PintarRuta paint;
	

	protected List<Overlay> capas;
	
	protected final String paso1 = "Dirijase";
	protected final String paso2 = "Hasta";
	protected final String paso3 = "Tome el";
	protected final String paso4 = "La estacion";
	protected final String paso5 = "Hacia";
	protected final String paso6 = "Desde";
	protected final String paso7 = "Camine";
	protected final String paso8 = "Haga transferencia a";
	protected int LATITUDES = 0;
	protected int LONGITUDES = 1;
	
	protected static int pos_ruta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mapview);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		conexion = ConectarWebService.getInstance();
		conexion.subscribir(this);
		
		// Referencia del MapView XML
		mapa = (MapView) findViewById(R.id.mapa);
		// Toma el control del mapa
		mc = mapa.getController();
		// Controles de zoom sobre el mapa
		mapa.setBuiltInZoomControls(true);
		// Aniadimos la capa de marcas
		capas = mapa.getOverlays();
	}

	/**
	 * Como esta clase esta subscrita al WebService, cuando esta ultimo manda el
	 * JSON y este se adapta, le comunica al WebService que ya fue adpatad, y es
	 * aqui q se recibe esa notificacion
	 */
	public void responseObtained(LinkedList<Solucion> solucion) {
		if (paint != null) {
			capas.remove(paint);
		}
		seleccionarRutas();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return true;
	}
	
	/*
	 * Aqui se definen los pasos que debe realizar el usuario para coger los
	 * tranpostes de la solucion
	 */
	protected void pasos() {
		String pasos = "", color = "", temp = "";
		int x = -1, y;
		boolean keep = true;
		while (keep) {
			x++;
			y = x % 2;
			switch (y) {
			case 0:
				color = "Azul";
				break;
			case 1:
				color = "Rojo";
				break;
			}
			if (x >= list_transporte.size())
				break;
			if (list_transporte.get(x).getTipo().equals("Metro")) {
				pasos += x + 1 + ": " + list_transporte.get(x).getNombre()
						+ " = " + color + "\n";
				if (x != 0) {
					if (list_transporte.get(x - 1).getTipo().equals("Metro")) {
						temp = paso8 + " "
								+ list_transporte.get(x - 1).getNombre();
					} else {
						geo = new GeoPoint((int) (list_transporte.get(x - 1)
								.getRuta().getCoordenadas().get(LATITUDES)
								.getLast() * 1E6), (int) (list_transporte
								.get(x - 1).getRuta().getCoordenadas()
								.get(LONGITUDES).getLast() * 1E6));
						temp = ConvertPointToLocation(geo);
					}
					pasos += paso6 + " " + temp + " ";
				}
				pasos += paso1
						+ " a "
						+ paso4
						+ " "
						+ list_transporte.get(x).getRuta().getEstaciones()
								.getFirst() + "\n";
				pasos += paso3
						+ " "
						+ list_transporte.get(x).getTipo()
						+ " "
						+ paso5
						+ " "
						+ paso4
						+ " "
						+ list_transporte.get(x).getRuta().getEstaciones()
								.getLast() + "\n";
				continue;
			} else if (list_transporte.get(x).getTipo().equals("A Pie")) {
				pasos += x + 1 + ": " + list_transporte.get(x).getNombre()
						+ " = " + color + "\n";
				if (x != 0) {
					geo = new GeoPoint((int) (list_transporte.get(x - 1)
							.getRuta().getCoordenadas().get(LATITUDES)
							.getLast() * 1E6), (int) (list_transporte
							.get(x - 1).getRuta().getCoordenadas()
							.get(LONGITUDES).getLast() * 1E6));
					temp = ConvertPointToLocation(geo);
					pasos += paso6 + " " + temp + "\n";
				}
				geo = new GeoPoint(
						(int) (list_transporte.get(x).getRuta()
								.getCoordenadas().get(LATITUDES).getLast() * 1E6),
						(int) (list_transporte.get(x).getRuta()
								.getCoordenadas().get(LONGITUDES).getLast() * 1E6));
				temp = ConvertPointToLocation(geo);
				pasos += paso7 + " " + paso2 + " " + temp + "\n";
				continue;
			} else {
				pasos += x + 1 + ": " + list_transporte.get(x).getNombre()
						+ " = " + color + "\n";
				if (x != 0) {
					geo = new GeoPoint((int) (list_transporte.get(x - 1)
							.getRuta().getCoordenadas().get(LATITUDES)
							.getLast() * 1E6), (int) (list_transporte
							.get(x - 1).getRuta().getCoordenadas()
							.get(LONGITUDES).getLast() * 1E6));
					temp = ConvertPointToLocation(geo);
					pasos += paso6 + " " + temp + " ";
				}
				geo = new GeoPoint(
						(int) (list_transporte.get(x).getRuta()
								.getCoordenadas().get(LATITUDES).getFirst() * 1E6),
						(int) (list_transporte.get(x).getRuta()
								.getCoordenadas().get(LONGITUDES).getFirst() * 1E6));
				temp = ConvertPointToLocation(geo);
				pasos += paso1 + " a " + temp + "\n";
				pasos += paso3 + " " + list_transporte.get(x).getTipo() + " "
						+ list_transporte.get(x).getNombre() + "\n";
				pasos += paso1 + " en " + list_transporte.get(x).getNombre()
						+ " ";
				geo = new GeoPoint(
						(int) (list_transporte.get(x).getRuta()
								.getCoordenadas().get(LATITUDES).getLast() * 1E6),
						(int) (list_transporte.get(x).getRuta()
								.getCoordenadas().get(LONGITUDES).getLast() * 1E6));
				temp = ConvertPointToLocation(geo);
				pasos += paso2 + " " + temp + "\n";
			}

		}

		((TextView) findViewById(R.id.txtpasos)).setText(pasos);
		((TextView) findViewById(R.id.txtpasos)).setTextColor(Color.BLACK);
	}
	
	/*
	 * Se encarga de converir GeoPoints a un address (Direccion fisica)
	 */
	private String ConvertPointToLocation(GeoPoint gp) {
		String address = "";
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		try {
			List<Address> addresses = geoCoder.getFromLocation(
					(gp.getLatitudeE6() / 1E6), (gp.getLongitudeE6() / 1E6), 1);
			if (addresses.size() != 0) {
				for (int index = 0; index < addresses.get(0)
						.getMaxAddressLineIndex(); index++)
					address += addresses.get(0).getAddressLine(index) + " ";
			}
		} catch (Exception e) {

		}
		return address;
	}

	
	protected void seleccionarRutas() {
		final RutasBuses rutas = new RutasBuses(this);
		CharSequence[] disponibles;
		if( (disponibles = extraerRutas()) == null) return;
		rutas.setTitle("Rutas Disponibles");
		SelectorRutasListener selectorListener = new SelectorRutasListener();
		selectorListener.reset();
		selectorListener.subscribirse(this);
		rutas.setItems(disponibles, selectorListener);
		new Thread(){
			public void run(){
				Map.this.runOnUiThread(rutas);
			}
		}.start();
		
	}
	
	private CharSequence[] extraerRutas()
	{
		ArrayList<String> nombres = new ArrayList<String>();
		LinkedList<Solucion> solucion = sol_manager.getSolucion();
		int cont = 1;
		if (solucion.size() == 0) {
			Toast.makeText(this, "No Hay Soluciones Disponibles.",
					Toast.LENGTH_LONG).show();
			Toast.makeText(this, "Presione Volver Para Ir De Nuevo Al Mapa.",
					Toast.LENGTH_LONG).show();
			return null;
		} else {
			for (Solucion sol : solucion) {
				nombres.add(sol.toString() + " " + cont + ": \n"
						+ sol.getNamesRutaFinal());
				cont++;
			}
			CharSequence[] names = new String[nombres.size()];
			for (int i = 0; i < nombres.size(); i++) {
				names[i] = nombres.get(i);
			}
			return names;
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		try {
			conexion.setLat_final(0);
			conexion.setLong_final(0);
			conexion.desubscribir(this);
			conexion.stopConexion();
		} catch (NullPointerException null_ex) {
		}
		this.finish();
	}
	
	/*
	 * "Constructor" boton menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.easybus_menu, menu);
		return true;
	}

	@Override
	public void responseSelectorObtained(int which) {
		list_transporte = sol_manager.getSolucion().get(which)
				.getRutaFinal();
		paint = new PintarRuta(list_transporte);
		capas.add(paint);
		mapa.postInvalidate();
		pasos();
	}
	
	/**
	 * Repinta el mapa
	 * 
	 * @author EasyBus Medellin
	 * 
	 */
	public class MapUpdater implements IMapUpdater {

		/**
    	 * 
    	 */
		public void refreshMapView() {
			mapa.postInvalidate();

		}
	}


}
