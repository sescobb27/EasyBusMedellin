package easybus.medellin.movil.jsonCTRL;

import java.util.LinkedList;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import easybus.medellin.movil.model.Ruta;
import easybus.medellin.movil.model.SingletonSolucionManager;
import easybus.medellin.movil.model.Transporte;
import easybus.medellin.movil.model.Solucion.Solucion;

/**
 * 
 * @author simon
 * 
 */
public class JSONAdapter {

	private SingletonSolucionManager sol_manager = SingletonSolucionManager
			.getInstance();

	/**
	 * Aqui se realiza la adaptacion del JSON Object que se recibe del
	 * WebService para posteriomente ser utilizado por el movil, (pintar
	 * solucion y desplegar los pasos).
	 * 
	 * @param jsonresponse
	 * @return
	 * @throws JSONException
	 */
	public LinkedList<Solucion> parse(JSONObject jsonresponse)
			throws JSONException {
		LinkedList<Solucion> solucion = new LinkedList<Solucion>();
		JSONObject ruta, transporte;
		String linea;
		Transporte bus;
		StringTokenizer token;
		LinkedList<LinkedList<Double>> coordenadas;
		String[] lat_long;
		if (jsonresponse != JSONObject.NULL) {
			for (int i = 0; i < 3; i++) {
				Solucion resolve = new Solucion();
				if (jsonresponse.isNull("Respuesta " + i)) {
					break;
				}
				ruta = jsonresponse.getJSONObject("Respuesta " + i);
				for (int j = 0; j < 6; j++) {
					if (ruta.isNull("ruta " + j)) {
						break;
					}
					transporte = ruta.getJSONObject("ruta " + j);
					bus = new Transporte(transporte.getString("Nombre"),
							transporte.getInt("Precio"),
							transporte.getString("Tipo"));
					token = new StringTokenizer(
							transporte.getString("Coordenada"));
					Ruta rutabus;
					coordenadas = new LinkedList<LinkedList<Double>>();
					coordenadas.add(new LinkedList<Double>());
					coordenadas.add(new LinkedList<Double>());
					while ((linea = token.nextToken(",")) != null) {
						linea = linea.replace("[", "");
						linea = linea.replace('"', ' ');
						linea = linea.replace("]", "");
						lat_long = linea.split("_");
						coordenadas.get(0).add(Double.parseDouble(lat_long[0]));
						coordenadas.get(1).add(Double.parseDouble(lat_long[1]));
						if (!(token.hasMoreTokens())) {
							break;
						}
					}
					rutabus = new Ruta(coordenadas);
					bus.setRuta(rutabus);
					if (bus.getTipo().equals("Metro")) {
						linea = null;
						StringTokenizer metro_token = new StringTokenizer(
								transporte.getString("Estaciones"));
						while ((linea = metro_token.nextToken(",")) != null) {
							linea = linea.replace("[", "");
							linea = linea.replace('"', ' ');
							linea = linea.replace("]", "");
							rutabus.addEstaciones(linea);
							if (!metro_token.hasMoreTokens()) {
								break;
							}
						}
					}
					resolve.agregarTransporteTramo(bus);
				}
				solucion.add(resolve);
			}
		}
		sol_manager.setSolucion(solucion);
		for (int i = 0; i < solucion.size(); i++) {
			for (int j = 0; j < solucion.get(i).getRutaFinal().size(); j++) {
				Log.v("mateo gay", solucion.get(i).getRutaFinal().get(j)
						.getNombre());
			}
		}
		return solucion;
	}

}
