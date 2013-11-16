package easybus.medellin.movil;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_layout);
		TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent register_intent = new Intent( getApplicationContext(), EasyBusMedellinMovil.class );
                startActivity( register_intent );
                finish();
            }
        };
        Timer timer_on_task = new Timer();
        timer_on_task.schedule( task, 5000 );
	}
}
