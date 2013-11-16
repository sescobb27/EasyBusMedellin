package easybus.medellin.movil.mapCTRL.mapGPS;

import easybus.medellin.movil.R;
import easybus.medellin.movil.gpsCTRL.CapturarLocacionCtrl;
import easybus.medellin.movil.mapCTRL.Map;

import com.google.android.maps.GeoPoint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * 
 * @author EasyBus Medellin
 * 
 */
public class MapActivityCtrl extends Map {

	private int longitud;
	private int latitud;
	private OverlayMap om;


	/*
	 * Aqui se hace referencia al mapa y se definen los controles que trabajan
	 * sobre el.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		longitud = (int) (CapturarLocacionCtrl.longitud * 1E6);
		latitud = (int) (CapturarLocacionCtrl.latitud * 1E6);

		om = new OverlayMap(new MapUpdater(), this);
		capas.add(om);

		// Convertir las coordenadas de latitudes y longitudes a grados
		geo = new GeoPoint(latitud, longitud);

		// Se acerca al mapa con las coordenadas suministradas
		mc.animateTo(geo);
		mc.setZoom(15);
		mapa.postInvalidate();
	}

	/*
	 * Definido por Google Maps
	 */
	@Override
	protected boolean isRouteDisplayed() {
		return true;
	}

	/*
	 * Opciones que aparecen si es seleccionado el boton de menu en el mapa.
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.clearmap:
			capas.clear();
			sol_manager.resetSolution();
			om = new OverlayMap(new MapUpdater(), this);
			capas.add(om);
			conexion.setLat_final(0);
			conexion.setLong_final(0);
			mapa.postInvalidate();
			return true;
		case R.id.others:
			if (sol_manager.getSolucion().isEmpty()) {
				Toast.makeText(this, "No Hay Soluciones Disponibles.",
						Toast.LENGTH_SHORT).show();
				return true;
			}
			capas.remove(paint);
			seleccionarRutas();
			return true;
		case R.id.rangos:
			if (!conexion.areNull()) {
				Toast.makeText(this, "Seleccione su origen y destino",
						Toast.LENGTH_SHORT).show();
				return true;
			}
			om.showRango();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
}