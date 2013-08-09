package com.android.easybus.medellin.movil.model.Solucion;

import java.util.LinkedList;

import com.android.easybus.medellin.movil.model.Transporte;

/**
 * 
 * @author Mateo
 * 
 */
public class Solucion {

	private LinkedList<Transporte> solucion;

	/**
	 * Incializa la solucion que contiene una lista de transportes
	 */
	public Solucion() {
		solucion = new LinkedList<Transporte>();
	}

	/**
	 * Este metodo agrega el trmao al trsporte especificado en el JSON Adapter
	 * 
	 * @param bus
	 */
	public void agregarTransporteTramo(Transporte bus) {
		solucion.add(bus);
	}

	/**
	 * Devuelve los nombres de las rutas que hacen parte de la solucion
	 * 
	 * @return
	 */
	public String getNamesRutaFinal() {
		String names = "";
		for (Transporte transporte : solucion) {
			names += transporte.toString() + "\n";
		}
		return names;
	}

	/**
	 * Devuelve la lista d etrnapsorte que hacen parte de la solucion
	 * 
	 * @return
	 */
	public LinkedList<Transporte> getRutaFinal() {
		return solucion;
	}

	/**
	 * Asigna rutas al tranporte
	 * 
	 * @param Ruta
	 */
	public void setRutafinal(LinkedList<Transporte> Ruta) {
		solucion = Ruta;
	}

	/*
	 * Devuelve la cadena de caracteres de la solucion
	 */
	@Override
	public String toString() {
		return "Solucion";
	}
}
