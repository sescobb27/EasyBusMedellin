package easybus.medellin.movil.mapCTRL.mapMANUAL;

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
import easybus.medellin.movil.mapCTRL.IMapUpdater;
import easybus.medellin.movil.mapCTRL.OverlayMApEasyBus;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

/**
 * 
 * @author simon
 * 
 */
public class OverlayMapIni extends OverlayMApEasyBus implements
		DialogInterface.OnClickListener {

	private double longitud_inicial = 0;
	private double latitud_inicial = 0;
	private double longitud_fin = 0;
	private double latitud_fin = 0;
	private AlertDialog.Builder msg;
	private final CharSequence[] options = {
			"¿desea volver a colocar su posicion inicial?",
			"¿desea volver a colocar su posicion de destino" };
	private AlertDialog.Builder builder;

	public OverlayMapIni(IMapUpdater ma, Context con) {
		super(ma, con);
		msg = new AlertDialog.Builder(this.con);
		msg.setCancelable(false);
		msg.setNegativeButton("No", this);
		builder = new AlertDialog.Builder(con);
		builder.setTitle("resetear puntos");
	}

	/*
	 * Encargado de agregar los puntos en el mapa que selecciona el usuario
	 */

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

		if (shadow == false) {

			Point centro = new Point();
			// Definimos el pincel de dibujo
			Paint p = new Paint();
			// Marca Bitmap
			Bitmap bm = BitmapFactory.decodeResource(mapView.getResources(),
					R.drawable.marcador_google_maps);
			Point toque = new Point();
			if (punto_ini != null) {
				// Ubicar el punto en el mapa apartir de las coordenadas
				GeoPoint geoPoint = new GeoPoint((int) latitud_inicial,
						(int) longitud_inicial);
				projection.toPixels(geoPoint, centro);
				p.setColor(Color.BLUE);
				projection.toPixels(punto_ini, toque);
				canvas.drawBitmap(bm, toque.x - bm.getWidth(),
						toque.y - bm.getHeight(), p);

			}
			if (punto_fin != null) {
				// Ubicar el punto en el mapa apartir de las coordenadas
				GeoPoint geoPoint = new GeoPoint((int) latitud_fin,
						(int) longitud_fin);
				projection.toPixels(geoPoint, centro);
				p.setColor(Color.BLUE);
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
	public boolean onTap(final GeoPoint point, final MapView mapView) {
		if (punto_ini == null) {
			msg.setMessage("¿desea usted que este sea su punto de inicio?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									punto_ini = point;
									ma.refreshMapView();
									conexion.setLat_ini((latitud_inicial = (point
											.getLatitudeE6() / 1E6)));
									conexion.setLong_ini((longitud_inicial = (point
											.getLongitudeE6() / 1E6)));
									if (punto_fin != null) {
										second = null;
										showRango();
									}
								}
							});
			msg.show();
			return true;
		} else if (punto_fin == null) {
			msg.setMessage("¿desea usted que este sea su punto de destino?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									punto_fin = point;
									ma.refreshMapView();
									conexion.setLong_final((longitud_fin = (point
											.getLongitudeE6() / 1E6)));
									conexion.setLat_final((latitud_fin = (point
											.getLatitudeE6() / 1E6)));
									showRango();
								}
							});
			msg.show();
			return true;
		} else {
			builder.setItems(options, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						punto_ini = null;
						conexion.setLat_ini(0);
						conexion.setLong_ini(0);
						ma.refreshMapView();
						break;
					case 1:
						punto_fin = null;
						conexion.setLat_final(0);
						conexion.setLong_final(0);
						ma.refreshMapView();
						break;
					}

				}
			});
			builder.create();
			builder.show();
			return true;
		}
	}

	public void onClick(DialogInterface dialog, int which) {
		dialog.cancel();
	}

}
