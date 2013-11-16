package easybus.medellin.movil.detectors;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.content.Context;

public class GoogleServicesDetector {
	
	private static GoogleServicesDetector services_detector;
	private static Context context;
	
//	CONSTANTS
	public static final int TRUE = 1;
//	END CONSTANTS
	
	private GoogleServicesDetector(Context new_context) {
		context = new_context;
	}
	
	public static GoogleServicesDetector getGoogleServicesDetector(Context new_context) {
		if (services_detector ==  null)
			services_detector = new GoogleServicesDetector(new_context);
		else
			context = new_context;
		return services_detector;
	}
	
	public int isGoogleServicesEnable() {
		int result_code = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
		if ( result_code == ConnectionResult.SUCCESS ) {
			return TRUE;
		}
		return result_code;
	}
}
