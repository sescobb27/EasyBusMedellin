package com.android.easybus.medellin.movil.webserviceCTRL;

import java.util.LinkedList;

import com.android.easybus.medellin.movil.model.Solucion.Solucion;

/**
 * 
 * @author Mateo
 * 
 */
public interface WebServiceSubscriptor {

	/**
	 * Este es el interface para emular herencia multiple y es para volver a los
	 * mapscontrollers suscriptores del WebService
	 * 
	 * @param solucion
	 */
	public void responseObtained(LinkedList<Solucion> solucion);
}
