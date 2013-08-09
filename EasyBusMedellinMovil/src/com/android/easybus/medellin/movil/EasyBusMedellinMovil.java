package com.android.easybus.medellin.movil;

import com.android.easybus.medellin.movil.gpsCTRL.CapturarLocacionCtrl;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class EasyBusMedellinMovil extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		((ImageView) findViewById(R.id.btntransporte)).setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
		this.finish();
	}

	/**
	 * Se implementa por medio de un Listener, ejecuta la accion cuando el boton
	 * es presionado
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btntransporte:
			Intent intent = new Intent(EasyBusMedellinMovil.this,
					CapturarLocacionCtrl.class);
			startActivity(intent);
			break;
		}

	}

}
