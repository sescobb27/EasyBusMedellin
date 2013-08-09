package com.android.easybus.medellin.movil.model;

import java.util.LinkedList;

import com.android.easybus.medellin.movil.model.Solucion.Solucion;

/**
 * 
 * @author EasyBus Medellin
 * 
 */
public class SingletonSolucionManager {

	private LinkedList<Solucion> solucion;
	private static SingletonSolucionManager sol_manager;

	/**
	 * Inicializa un singleton de la solucion para pasar objetos entre
	 * activities
	 */
	private SingletonSolucionManager() {
		solucion = new LinkedList<Solucion>();
	}

	/**
	 * Devuelve la instacia solucion
	 * 
	 * @return
	 */
	public static SingletonSolucionManager getInstance() {
		if (sol_manager == null) {
			sol_manager = new SingletonSolucionManager();
		}
		return sol_manager;
	}

	/**
	 * Hace un reset a solucion para calcular otra solucion
	 */
	public void resetSolution() {
		solucion = new LinkedList<Solucion>();
	}

	/**
	 * Devuelve la solucion
	 * 
	 * @return
	 */
	public LinkedList<Solucion> getSolucion() {
		return solucion;
	}

	/**
	 * Asigna la solucion
	 * 
	 * @param solucion
	 */
	public void setSolucion(LinkedList<Solucion> solucion) {
		this.solucion = solucion;
	}

}
