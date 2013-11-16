package easybus.medellin.movil.services.notifier;

import easybus.medellin.movil.services.LocationConstants;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;

public class Notifier {

	private LocalBroadcastManager local_broadcast;
	public static final String NOTIFIER_ACTION = "simon.proyecto.desbarate.services.notifier.NOTIFIER";
	
	public Notifier( Context context ) {
		local_broadcast = LocalBroadcastManager.getInstance(context);
		IntentFilter filter = new IntentFilter(NOTIFIER_ACTION);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		local_broadcast.registerReceiver(NotifierReceiver.getNotifierReceiver(), filter);
	}
	
	public void notifyChanges(Location location) {
		Intent intent = new Intent();
		intent.setAction(NOTIFIER_ACTION);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putExtra(LocationConstants.LOCATION_UPDATE, location);
		local_broadcast.sendBroadcast(intent);
	}
	
	public void unRegister() {
		local_broadcast.unregisterReceiver(NotifierReceiver.getNotifierReceiver());
	}
}
