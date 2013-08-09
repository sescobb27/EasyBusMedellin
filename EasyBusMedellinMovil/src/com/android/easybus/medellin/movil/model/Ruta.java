package com.android.easybus.medellin.movil.model;

import java.util.LinkedList;

/**
 * 
 * @author simon
 * 
 */
public class Ruta {

	private LinkedList<LinkedList<Double>> coordenadas;
	private LinkedList<String> estaciones;

	/**
	 * Inicializa la ruta de cada transporte
	 * 
	 * @param coordenadas
	 */
	public Ruta(LinkedList<LinkedList<Double>> coordenadas) {
		this.coordenadas = coordenadas;
		setEstaciones(new LinkedList<String>());
	}

	/**
	 * Devuelve la lista longitudes y latidudes de cada transporte
	 * 
	 * @return the coordenadas
	 */
	public LinkedList<LinkedList<Double>> getCoordenadas() {
		return coordenadas;
	}

	/**
	 * Asigana la lista longitudes y latidudes de cada transporte
	 * 
	 * @param coordenadas
	 *            the coordenadas to set
	 */
	public void setCoordenadas(LinkedList<LinkedList<Double>> coordenadas) {
		this.coordenadas = coordenadas;
	}

	/**
	 * Asigna la lista longitudes y latidudes de cada estacion
	 * 
	 * @param estaciones
	 */
	public void setEstaciones(LinkedList<String> estaciones) {
		this.estaciones = estaciones;
	}

	/**
	 * Devuelve la lista longitudes y latidudes del metro
	 * 
	 * @return
	 */
	public LinkedList<String> getEstaciones() {
		return estaciones;
	}

	/**
	 * Agrega la estaciones y el trnapsorte tipo metro
	 * 
	 * @param estacion
	 */
	public void addEstaciones(String estacion) {
		estaciones.add(estacion);
	}
}
