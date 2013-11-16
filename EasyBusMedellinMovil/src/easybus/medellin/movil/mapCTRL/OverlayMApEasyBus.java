package easybus.medellin.movil.mapCTRL;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import easybus.medellin.movil.listeners.DistanciasListener;
import easybus.medellin.movil.webserviceCTRL.ConectarWebService;

public class OverlayMApEasyBus extends Overlay {

	private CharSequence[] distancias = { "200 m", "400 m", "600 m", "800 m",
			"1 km", "1.2 km", "1.4 km" };
	protected Context con;
	protected IMapUpdater ma;
	protected Thread second;
	protected ConectarWebService conexion = ConectarWebService.getInstance();
	protected Projection projection;
	protected GeoPoint punto_ini = null, punto_fin = null;

	public OverlayMApEasyBus(IMapUpdater ma, Context con) {
		this.ma = ma;
		this.con = con;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
		// Para poder hacer conversiones precisas de coordenadas en grados a
		// pixels en la pantalla
		mapView.setReticleDrawMode(MapView.ReticleDrawMode.DRAW_RETICLE_NEVER);
		// Para poder hacer conversiones precisas de coordenadas en grados a
		// pixels en la pantalla
		projection = mapView.getProjection();
	}

	/*
	 * Mustra el rango que debe seleccionar el usuario para encontrar su
	 * solucion (cuanto esta dipuesto a caminar para cojer el bus)
	 */
	public void showRango() {
		AlertDialog.Builder rango_max = new AlertDialog.Builder(con);
		rango_max
				.setTitle("¿Cuanto estas dispuesto a caminar hasta el transporte?");
		rango_max
				.setItems(distancias, new DistanciasListener(second, conexion));
		rango_max.create();
		rango_max.show();
	}
}
