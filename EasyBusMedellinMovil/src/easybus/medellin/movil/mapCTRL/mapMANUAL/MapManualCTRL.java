package easybus.medellin.movil.mapCTRL.mapMANUAL;


import easybus.medellin.movil.R;
import easybus.medellin.movil.mapCTRL.Map;

import com.google.android.maps.GeoPoint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * 
 * @author simon
 * 
 */
public class MapManualCTRL extends Map {

	private OverlayMapIni om;

	/*
	 * Aqui se hace referencia al mapa y se definen los controles que trabajan
	 * sobre el.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		om = new OverlayMapIni(new MapUpdater(), this);
		// om = new PintarRuta(new MapUpdater());
		capas.add(om);
		mapa.postInvalidate();
		mc.animateTo(new GeoPoint(6239623, -75578141));
		mc.setZoom(15);
		mapa.invalidate();
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
			om = new OverlayMapIni(new MapUpdater(), this);
			capas.add(om);
			conexion.setLat_ini(0);
			conexion.setLong_ini(0);
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

	@Override
	public void onBackPressed() {
		try {
			conexion.setLat_ini(0);
			conexion.setLong_ini(0);
		} catch (NullPointerException null_ex) {
		}
		super.onBackPressed();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return true;
	}

}
