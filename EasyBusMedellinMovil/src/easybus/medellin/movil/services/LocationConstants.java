package easybus.medellin.movil.services;

public final class LocationConstants {
//	CONSTANTS
	/***** Constants for location update parameters *****/
//     Milliseconds per second
    public static final int MILLISECONDS_PER_SECOND = 1000;
//     The update interval
    public static final int UPDATE_INTERVAL_IN_SECONDS = 60;
//     A fast interval ceiling
    public static final int FAST_CEILING_IN_SECONDS = 1;
//     Update interval in milliseconds
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
//     A fast ceiling of update intervals, used when the app is visible
    public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS =
            MILLISECONDS_PER_SECOND * FAST_CEILING_IN_SECONDS;
    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
	public static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	public static final String LOCATION_UPDATE = "LOCATION_UPDATE";
//	END CONSTANTS
}
