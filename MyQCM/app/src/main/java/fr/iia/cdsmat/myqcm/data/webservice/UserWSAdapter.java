package fr.iia.cdsmat.myqcm.data.webservice;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;

import fr.iia.cdsmat.myqcm.data.UserSQLiteAdapter;
import fr.iia.cdsmat.myqcm.entity.User;

/**
 * Created by Antoine Trouvé on 28/05/2016.
 * antoinetrouve.france@gmail.com
 */
public class UserWSAdapter {

    //to make request (get,post...)
    private static AsyncHttpClient client = new AsyncHttpClient();
    String response;
    Context context;

    /**
     * UserWSAdapter's Constructor
     * @param context
     */
    public UserWSAdapter(Context context){
        this.context = context;
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
