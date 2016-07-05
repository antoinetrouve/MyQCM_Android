package fr.iia.cdsmat.myqcm.data.webservice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;

import fr.iia.cdsmat.myqcm.data.UserSQLiteAdapter;
import fr.iia.cdsmat.myqcm.entity.User;
import fr.iia.cdsmat.myqcm.view.login.LoginActivity;
import fr.iia.cdsmat.myqcm.view.menu.MenuActivity;

/**
 * Created by Antoine Trouvé on 28/05/2016.
 * antoinetrouve.france@gmail.com
 */
public class UserWSAdapter {

    //to make request (get,post...)
    private static AsyncHttpClient client = new AsyncHttpClient();
    String response;
    private long result;
    Context context;
    private AlertDialog alertDialog;

    /**
     * UserWSAdapter's Constructor
     * @param context
     */
    public UserWSAdapter(Context context){
        this.context = context;
    }

    /**
     *
     * @param context
     * @param idMessage
     * id Message : 1 = a user already exist
     * id Message : 2 = user is up to date, nothing to do
     * id Message : 3 = user is create
     * id Message : 4 = user update error
     */
    public void ManageUserMessage(final Context context, int idMessage, final int idUserToDelete, final User user) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        System.out.println("OK ManageUserMessage");
            System.out.println("OK set alert dialog");
            alertDialogBuilder.setTitle("Attention compte utilisateur déjà existant !");
            alertDialogBuilder.setMessage("Un compte utilisateur existe déjà pour cette application ! " +
                    "Si vous continuez, l'application effaçera toutes les données relatives à cet utilisateur. " +
                    "Êtes-vous sûr de vouloir continuer ?");
            alertDialogBuilder.setPositiveButton("OUI",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.out.println("OK click on yes");

                            //Delete database
                            File databaseFile = context.getDatabasePath(MyQCMConstants.APP_DB_NAME + MyQCMConstants.APP_DB_EXTENSION);
                            SQLiteDatabase.deleteDatabase(databaseFile);

                            System.out.println("OK database deleted");

                            //New User have to be created
                            UserSQLiteAdapter userSQLiteAdapter = new UserSQLiteAdapter(context);
                            userSQLiteAdapter.open();
                            result = userSQLiteAdapter.insert(user);
                            userSQLiteAdapter.close();
                            if (result > 0) {
                                Toast.makeText(context, MyQCMConstants.CONST_MESS_CREATEDB + user.getUsername(), Toast.LENGTH_SHORT).show();
                                //Launch main activity
                                //Intent intent = new Intent(context,MenuActivity.class);
                                //startActivity(intent);
                            } else {
                                Toast.makeText(context, MyQCMConstants.CONST_MESS_CREATEDBERROR, Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                            //Toast.makeText(context, MyQCMConstants.CONST_MESS_DELETEDB, Toast.LENGTH_SHORT).show();
                        }
                    });
            alertDialogBuilder.setNegativeButton("NON",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Get user information json flow
     * @param username
     * @param url
     * @return User
     */
//    public void getUserInformationRequest(String username, String url){
//        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//        asyncHttpClient.setConnectTimeout(MyQCMConstants.CONST_CONNECT_TIMEOUT);
//        asyncHttpClient.setTimeout(MyQCMConstants.CONST_SET_TIMEOUT);
//        RequestParams params = new RequestParams();
//        params.put(CONST_USERNAME, username);
//
//        asyncHttpClient.post(url + "." + MyQCMConstants.CONST_FLOW_FORMAT, params, new TextHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                response = responseString;
//                //Create user with userInformation json
//                //User user = JsonToItem(response);
//                try {
//                    String result = new ManageDBUserAsyncTask().execute(response).get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                response = responseString;
//                System.out.println("On failure");
//            }
//        });
//    }

    /**
     * AsyncTask to manage user's process in local database after authentification process
     */
//    public class ManageDBUserAsyncTask extends AsyncTask<String, Void, String>{
//
//        @Override
//        protected String doInBackground(String... params) {
//            long result;
//            String response = null;
//
//            //Create user with userInformation json
//            User user = JsonToItem(response);
//
//            if(user instanceof User){
//                UserSQLiteAdapter userSQLiteAdapter = new UserSQLiteAdapter(context);
//                userSQLiteAdapter.open();
//                User userDB = userSQLiteAdapter.getUserByIdServer(user.getIdServer());
//
//                //if user exist in database
//                if(userDB != null){
//                    //if flow is more recent than user in local database
//                    if (user.getUpdatedAt().compareTo(userDB.getUpdatedAt()) > 0){
//                        result = userSQLiteAdapter.update(user);
//                        userSQLiteAdapter.close();
//                        if(result > 0){
//                            response = MyQCMConstants.CONST_MESS_UPDATEDB;
//                            return response;
//                        }
//                    }
//                }else{
//                    result = userSQLiteAdapter.insert(user);
//                    userSQLiteAdapter.close();
//                    if(result > 0){
//                        response = MyQCMConstants.CONST_MESS_CREATEDB + userDB.getUsername();
//                    }
//                }
//            }else{
//                return response;
//            }
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//    }



    /**
     * Construct User from user flow
     * @param response
     * @return User
     */
    public static User JsonToItem(String response) {

        //Date format
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        //Deserialize Webservice user information flow
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type userType = new TypeToken<User>(){}.getType();
        User user = gson.fromJson(response, userType);
        return user;
    }
}
