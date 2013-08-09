package com.android.easybus.medellin.movil.listeners;

import com.android.easybus.medellin.movil.webserviceCTRL.ConectarWebService;

import android.content.DialogInterface;

public class DistanciasListener implements DialogInterface.OnClickListener {

	private int rango = 0;
	private Thread second;
	private ConectarWebService conexion;

	public DistanciasListener(Thread second, ConectarWebService conexion) {
		this.second = second;
		this.conexion = conexion;
	}

	public int getRango() {
		return rango;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case 0:
			rango = 200;
			break;
		case 1:
			rango = 400;
			break;
		case 2:
			rango = 600;
			break;
		case 3:
			rango = 800;
			break;
		case 4:
			rango = 1000;
			break;
		case 5:
			rango = 1200;
			break;
		case 6:
			rango = 1400;
			break;
		}
		conexion.setRango(rango);
		if (second == null) {
			second = new Thread(conexion);
			second.start();
		} else {
			second = null;
			second = new Thread(conexion);
			second.start();
		}
	}

}
