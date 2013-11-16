package easybus.medellin.movil.suscriptors;

import android.location.Location;

public interface NotifierSubscriptor {

	public void receiveLocationUpdate(Location location);
}
