package easybus.medellin.movil.mapCTRL.mapGPS;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import easybus.medellin.movil.R;
import easybus.medellin.movil.gpsCTRL.CapturarLocacionCtrl;
import easybus.medellin.movil.mapCTRL.IMapUpdater;
import easybus.medellin.movil.mapCTRL.OverlayMApEasyBus;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

/**
 * 
 * @author EasyBus Medellin
 * 
 */
public class OverlayMap extends OverlayMApEasyBus {

	public static double latitud = (CapturarLocacionCtrl.latitud * 1E6);
	public static double longitud = (CapturarLocacionCtrl.longitud * 1E6);

	/*
	 * Inicializa un IMapUpdater y context, se hace en esta clase para evitar
	 * ciclos infinitos con el metodo pintar (Canvas -> Updater).
	 */
	public OverlayMap(IMapUpdater ma, Context con) {
		super(ma, con);
	}

	/*
	 * Encargado de agregar los puntos en el mapa que selecciona el usuario
	 */

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		// Ubicar el punto en el mapa apartir de las coordenadas
		GeoPoint geoPoint = new GeoPoint((int) latitud, (int) longitud);
		if (shadow == false) {

			Point centro = new Point();
			projection.toPixels(geoPoint, centro);

			// Definimos el pincel de dibujo
			Paint p = new Paint();
			p.setColor(Color.BLUE);
			Bitmap bm = BitmapFactory.decodeResource(mapView.getResources(),
					R.drawable.marcador_google_maps);
			canvas.drawBitmap(bm, centro.x - bm.getWidth(),
					centro.y - bm.getHeight(), p);

			if (punto_fin != null) {
				Point toque = new Point();
				projection.toPixels(punto_fin, toque);
				canvas.drawBitmap(bm, toque.x - bm.getWidth(),
						toque.y - bm.getHeight(), p);
			}
		}

	}

	/*
	 * Inicializa el metodo de pintar las marcas iniciales y finales cuando el
	 * usuario hace un toque sobre el mapa.
	 */
	@Override
	public boolean onTap(final GeoPoint point, MapView mapView) {
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setMessage("¿desea usted que este sea su punto de destino?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								punto_fin = point;
								ma.refreshMapView();
								conexion.setLat_final((point.getLatitudeE6() / 1E6));
								conexion.setLong_final((point.getLongitudeE6() / 1E6));
								showRango();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show();

		return true;
	}
}
