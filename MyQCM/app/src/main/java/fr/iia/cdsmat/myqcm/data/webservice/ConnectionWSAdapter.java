package fr.iia.cdsmat.myqcm.data.webservice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;

/**
 * Class to manage authentification to the WebService
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 14/05/2016
 */
public class ConnectionWSAdapter {
    String response;

    /**
     * Get user information for authentification
     * @param url
     * @param username
     * @param password
     * @param callback
     * @return false if authentification error
     */
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
             response ="false";
             callback.methods(response);
         }
     });
    }

    /**
     * Callback after ConnectionRequest
     */
    public interface CallBack{
        void methods(String reponse);
    }

    /**
     * Show error Message Authentification
     * @param context
     */
    public void connectionErrorMessage(Context context){
         AlertDialog alertDialog = new AlertDialog.Builder(context).create();
         alertDialog.setTitle("Erreur dans la connexion");
         alertDialog.setMessage("Impossible d'effectuer la connexion. Veuillez vérifier votre identifiant et mot de passe");
         alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
             new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                 }
             });
         alertDialog.show();
    }
}
