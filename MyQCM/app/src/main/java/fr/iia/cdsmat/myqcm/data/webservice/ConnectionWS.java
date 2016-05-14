package fr.iia.cdsmat.myqcm.data.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by antoi on 14/05/2016.
 * antoinetrouve.france@gmail.com
 */
public class ConnectionWS {
    /**
     * Check the connection to the server
     * @param context
     * @param serverUrl
     * @return boolean
     */
    static public boolean isURLReachable(Context context,String serverUrl) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL(serverUrl);
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                // 10 s.
                urlc.setConnectTimeout(10 * 1000);
                urlc.connect();
                // 200 = "OK" code (http connection is fine).
                if (urlc.getResponseCode() == 200) {
                    Log.wtf("Connection", "Success !");
                    return true;
                } else {
                    return false;
                }
            } catch (MalformedURLException e1) {
                return false;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

}
