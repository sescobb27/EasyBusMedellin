package easybus.medellin.movil.webserviceCTRL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import easybus.medellin.movil.jsonCTRL.JSONAdapter;
import easybus.medellin.movil.model.Solucion.Solucion;

/**
 * 
 * @author Mateo
 * 
 */
public class ConectarWebService implements Runnable {
	private HttpClient http_client;
	private HttpGet http_get;
	private double long_ini = 0;
	private double long_final = 0;
	private double lat_ini = 0;
	private double lat_final = 0;
	private int rango;
	private JSONAdapter json_adapter;

	/**
	 * 
	 */
	private ArrayList<WebServiceSubscriptor> subscriptores;

	private static ConectarWebService con_ws = null;

	/**
	 * hacer a los mapscontrollers suscriptores del WebService
	 * 
	 * @param subscriptor
	 */
	public void subscribir(WebServiceSubscriptor subscriptor) {
		subscriptores.add(subscriptor);
	}

	/**
	 * Cuando se sale de esta opcion quita el mapcontroller de la subscipcion
	 * para uq deje de recibir notificaciones
	 * 
	 * @param subscriptor
	 */
	public void desubscribir(WebServiceSubscriptor subscriptor) {
		subscriptores.remove(subscriptor);
	}

	/**
	 * Notifica a los mapcontrollers de que el JSON fue adaptado
	 * 
	 * @param solucion
	 */
	public void notificarSubscriptores(LinkedList<Solucion> solucion) {
		for (WebServiceSubscriptor subscriptor : subscriptores) {
			subscriptor.responseObtained(solucion);
		}
	}

	/**
	 * Asigna el rango
	 * 
	 * @param rango
	 */
	public void setRango(int rango) {
		this.rango = rango;
	}

	/**
	 * Asigna la longitud inicial
	 * 
	 * @param long_ini
	 */
	public void setLong_ini(double long_ini) {
		this.long_ini = long_ini;
	}

	/*
	 * Valida que la coordenas esten bien asignadas
	 */
	public boolean areNull() {
		if (lat_final == 0 || lat_ini == 0 || long_final == 0 || long_ini == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Asigna la longitud final
	 * 
	 * @param long_final
	 */
	public void setLong_final(double long_final) {
		this.long_final = long_final;
	}

	/**
	 * Asigna la latitud inicial
	 * 
	 * @param lat_ini
	 */
	public void setLat_ini(double lat_ini) {
		this.lat_ini = lat_ini;
	}

	/**
	 * Asigna la latitud final
	 * 
	 * @param lat_final
	 */
	public void setLat_final(double lat_final) {
		this.lat_final = lat_final;
	}

	/**
	 * Crea una y sola conexion al WebService (Singleton)
	 * 
	 * @return
	 */
	public static ConectarWebService getInstance() {
		if (con_ws == null) {
			con_ws = new ConectarWebService();
		}
		return con_ws;
	}

	/**
	 * Inicializa la variables necesaria para trbaajr con el WebService
	 */
	private ConectarWebService() {
		http_client = new DefaultHttpClient();
		subscriptores = new ArrayList<WebServiceSubscriptor>();
		json_adapter = new JSONAdapter();
	}

	/**
	 * Para la conexion con el WebService o sino hay conexion captura la
	 * exepcion
	 * 
	 * @throws NullPointerException
	 */
	public void stopConexion() throws NullPointerException {
		http_get.abort();
	}

	/**
	 * Aqui es donde estan definos los dato para realizar la conexion con el
	 * Webservice
	 */
	public void iniciarConexion() {
		// http_get = new
		// HttpGet("http://localhost:8084/EasyBusMedellinWeb/EjecutarServlet?LatI="+lat_ini+"&LongI="+long_ini+"&LatD="+lat_final+"&LongD="+long_final);
		http_get = new HttpGet(
				"http://192.168.0.104:8080/EasyBusMedellinWeb/EjecutarServlet?LatI="
						+ lat_ini + "&LongI=" + long_ini + "&LatD=" + lat_final
						+ "&LongD=" + long_final + "&Rango=" + rango);
		Log.v("x, y", "" + lat_ini + "---" + long_ini);
		Log.v("x, y", "" + lat_final + "---" + long_final);
		// http_get = new
		// HttpGet("http://192.168.43.162:8084/EasyBusMedellinWeb/EjecutarServlet?LatI="+6.243932+"&LongI="+-75.611035+"&LatD="+6.19999+"&LongD="+-75.577787);
		http_get.setHeader("content-type", "application/json");
		try {
			getResponse();
			Log.v("ws", "exito servidor");
		} catch (ClientProtocolException cpe) {
			Log.v("client", "Error servidor");
		} catch (JSONException JSONex) {
			Log.v("json", "Error servidor");
		} catch (IOException ioe) {
			Log.v("io", ioe.getMessage());
		}
	}

	/**
	 * Encargado de recibir la respuesta del Webservice
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	private void getResponse() throws ClientProtocolException, IOException,
			JSONException {
		HttpResponse resp = http_client.execute(http_get);
		String respStr = EntityUtils.toString(resp.getEntity());
		JSONObject responseJSON = new JSONObject(respStr);
		Log.v("exito", "exito");
		notificarSubscriptores(json_adapter.parse(responseJSON));
	}

	/**
	 * Hilo de la conexion al WebService
	 */
	public void run() {
		iniciarConexion();
	}
}
