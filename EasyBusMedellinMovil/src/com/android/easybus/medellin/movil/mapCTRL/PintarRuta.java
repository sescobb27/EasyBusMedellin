package com.android.easybus.medellin.movil.mapCTRL;

import java.util.LinkedList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import com.android.easybus.medellin.movil.model.Ruta;
import com.android.easybus.medellin.movil.model.Transporte;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/**
 * 
 * @author simon
 * 
 */
public class PintarRuta extends Overlay {

	private int LATITUDES = 0;
	private int LONGITUDES = 1;
	private Projection projection;
	private Paint mPaint;
	private LinkedList<Transporte> transportes;
	private Path path;

	/**
	 * Asigna la lista d etrnapsortes escogida por el usuario
	 * 
	 * @param transportes
	 */
	public PintarRuta(LinkedList<Transporte> transportes) {
		this.transportes = transportes;
	}

	/*
	 * Es el encargado de pintar la solucion en el mapa, este recibe los puntos
	 * del JSON Adapter.
	 */
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		mapView.setReticleDrawMode(MapView.ReticleDrawMode.DRAW_RETICLE_NEVER);
		if (shadow == false) {
			projection = mapView.getProjection();
			GeoPoint aux = null;
			Point point_aux = new Point();
			mPaint = new Paint();
			mPaint.setDither(true);
			mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeWidth(5);
			int x;
			for (int i = 0; i < transportes.size(); i++) {
				Transporte transporte = transportes.get(i);
				Ruta ruta = transporte.getRuta();
				for (int j = 0; j < ruta.getCoordenadas().get(LATITUDES).size(); j++) {
					GeoPoint geo_point = new GeoPoint(
							(int) (ruta.getCoordenadas().get(LATITUDES).get(j) * 1E6),
							(int) (ruta.getCoordenadas().get(LONGITUDES).get(j) * 1E6));
					Point punto = new Point();
					x = i % 2;
					switch (x) {
					case 0:
						mPaint.setStrokeWidth(5);
						mPaint.setColor(Color.BLUE);
						path = new Path();
						break;
					case 1:
						mPaint.setStrokeWidth(5);
						mPaint.setColor(Color.RED);
						path = new Path();
						break;
					}

					if (j == 0 && i == 0) {
						aux = geo_point;
						point_aux = punto;
						// canvas.drawText(transporte.getNombre(), punto.x,
						// punto.y, mPaint);
					} else if (j == 0 && i > 0) {
						aux = new GeoPoint((int) (ruta.getCoordenadas()
								.get(LATITUDES).get(j) * 1E6), (int) (ruta
								.getCoordenadas().get(LONGITUDES).get(j) * 1E6));
						point_aux = punto;
						// canvas.drawText(transporte.getNombre(), punto.x,
						// punto.y, mPaint);
					} else {
						projection.toPixels(aux, point_aux);
						projection.toPixels(geo_point, punto);
						path.moveTo(punto.x, punto.y);
						path.lineTo(point_aux.x, point_aux.y);
						canvas.drawPath(path, mPaint);
					}
					aux = geo_point;
					point_aux = punto;
					if (j == 0) {
						canvas.drawText(transporte.getNombre(), punto.x,
								punto.y, mPaint);
					}
				}
			}
		}
	}
}
