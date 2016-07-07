package fr.iia.cdsmat.myqcm.configuration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Antoine Trouvé on 04/07/2016.
 * antoinetrouve.france@gmail.com
 */
public class Utils {

    /**
     * Check Internet connection
     * @param context
     * @return boolean true if connect to server ok
     */
    static public boolean CheckInternetConnection(Context context) {
        //Get connectivity manager object to check connection
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Check for network connection
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
