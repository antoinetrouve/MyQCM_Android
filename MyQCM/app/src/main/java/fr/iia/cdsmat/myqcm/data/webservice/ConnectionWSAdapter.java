package fr.iia.cdsmat.myqcm.data.webservice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
/**
 * Created by Antoine Trouvé on 14/05/2016.
 * antoinetrouve.france@gmail.com
 */
public class ConnectionWSAdapter {
    String response;
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

    public void ConnectionRequest (String url,String username, String password, final CallBack callback){
     AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
          asyncHttpClient.setConnectTimeout(60000);
          asyncHttpClient.setTimeout(600000);
     RequestParams params = new  RequestParams();
     params.put(MyQCMConstants.CONST_VALUE_LOGIN, username);
     params.put(MyQCMConstants.CONST_VALUE_PWD, password);
     asyncHttpClient.post(url + ".json",params, new TextHttpResponseHandler(){

         @Override
         public void onSuccess(int statusCode, Header[] headers, String responseString) {
             response = responseString;
             callback.methods(response);
         }

         @Override
         public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
             response = responseString;
             callback.methods(response);
         }

         @Override
         public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
             String str = null;
             try {
                      str = new String(responseBytes, "UTF-8");
                 } catch (UnsupportedEncodingException e) {
                     e.printStackTrace();
                 }
             response ="false";
             callback.methods(response);
         }
     });
    }

    public interface CallBack{
        void methods(String reponse);
    }

    public void connectionErrorMessage(Context context){
         AlertDialog alertDialog = new AlertDialog.Builder(context).create();
         alertDialog.setTitle("Erreur dans la connexion");
         alertDialog.setMessage("Impossible d'effectuer la connexion.");
         alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
             new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                 }
             });
         alertDialog.show();
    }
}
