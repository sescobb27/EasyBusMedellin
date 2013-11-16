package easybus.medellin.movil.services.notifier;

import easybus.medellin.movil.services.LocationConstants;
import easybus.medellin.movil.suscriptors.NotifierSubscriptor;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

public class NotifierReceiver extends BroadcastReceiver {

	private static NotifierSubscriptor subscriptor;
	private static NotifierReceiver notifier;
	private NotifierReceiver() {
		
	}
	
	public static NotifierReceiver getNotifierReceiver() {
		if ( notifier == null)
			notifier = new NotifierReceiver();
		return notifier;
	}
	
	public static void subscribeToNotifications( NotifierSubscriptor _subscriptor ) {
		subscriptor = _subscriptor;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		Location location = (Location) intent.getExtras().get(LocationConstants.LOCATION_UPDATE);
		subscriptor.receiveLocationUpdate(location);
	}

}
