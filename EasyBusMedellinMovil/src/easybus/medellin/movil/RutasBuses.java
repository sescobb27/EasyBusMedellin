package easybus.medellin.movil;

import android.content.Context;


public class RutasBuses extends android.app.AlertDialog.Builder implements Runnable{

	public RutasBuses(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		this.create().show();
		
	}
	
}
