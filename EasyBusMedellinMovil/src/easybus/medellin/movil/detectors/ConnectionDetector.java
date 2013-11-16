package easybus.medellin.movil.detectors;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
	
    private static Context context;
    private static ConnectionDetector internet_detector;

    private ConnectionDetector(Context new_context) {
        context = new_context;
    }

    public static ConnectionDetector getConnectionDetector(Context new_context){
        if(internet_detector == null)
            internet_detector = new ConnectionDetector(new_context);
        else
        	context = new_context;
        return internet_detector;
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; ++i)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
