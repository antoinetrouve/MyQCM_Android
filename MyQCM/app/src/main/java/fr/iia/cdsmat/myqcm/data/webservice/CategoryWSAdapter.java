package fr.iia.cdsmat.myqcm.data.webservice;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.TextHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

import fr.iia.cdsmat.myqcm.data.AsyncTask.OnTaskCompleted;
import fr.iia.cdsmat.myqcm.data.CategorySQLiteAdapter;
import fr.iia.cdsmat.myqcm.entity.Category;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;

/**
 * Created by Antoine Trouvé on 14/05/2016.
 * antoinetrouve.france@gmail.com
 */
public class CategoryWSAdapter{

    // server table name
    private static final String CONST_CATEGORY = "category";

    //to make request (get,post...)
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String IDSERVER = "idServer";
    private static final String NAME = "name";
    private static final String UPDATEDAT = "updatedAt";
    private OnTaskCompleted taskCompleted;

    String response;
    Context context;

    /**
     * Constructor
     * @param context
     */
    public CategoryWSAdapter(Context context){
        this.context = context;
    }

    public interface CallBack{
        void methods(String response);
    }

    /**
     * Get json flow Category
     * @param userId
     * @param url
     */
    public void getCategoryRequest(int userId, String url) {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(MyQCMConstants.CONST_CONNECT_TIMEOUT);
        asyncHttpClient.setTimeout(MyQCMConstants.CONST_SET_TIMEOUT);
        RequestParams params = new RequestParams();
        params.put(MyQCMConstants.CONST_VALUE_USERID, userId);
        RequestHandle post = asyncHttpClient.post(url + "." + MyQCMConstants.CONST_FLOW_FORMAT, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;
                ArrayList<Category> categories = ResponseToList(response);
                if (categories != null) {
                    ManageCategoryDB(categories);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = responseString;
                System.out.println("On failure");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                String str = null;
                try {
                    str = new String(responseBytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("On failure = " + str);
                response = "false";
            }
        });

    }

    /**
     * Get Category from WebService with Callback param for user first connection to the app
     * return by callback category's json flow if ok or "OnFailure" if not
     * @param userId
     * @param url
     * @param callback
     */
    public void getCategoryRequest(int userId,String url,final CallBack callback ){
        //Set connection to Web service
        //-----------------------------
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(MyQCMConstants.CONST_CONNECT_TIMEOUT);
        asyncHttpClient.setTimeout(MyQCMConstants.CONST_SET_TIMEOUT);

        //Set param to build post request
        //-------------------------------
        RequestParams params = new RequestParams();
        params.put(MyQCMConstants.CONST_VALUE_USERID, userId);
        RequestHandle post = asyncHttpClient.post(url + "." + MyQCMConstants.CONST_FLOW_FORMAT, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;
                callback.methods(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = "OnFailure";
                System.out.println("getCatgeoryRequest - FirstConnection :" + response);
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
                System.out.println("getCatgeoryRequest - FirstConnection - On failure 2 = " + str);
                response = "OnFailure";
                callback.methods(response);
            }
        });
    }

    /**
     * Compare data in database with flow and make modification if needed
     * @param response
     */
    private void ManageCategoryDB(ArrayList<Category> response) {
        if (response.isEmpty() == false) {
            // get the list of categ in Flux and add on listView
            ArrayList<Category> list = response;

            // Call the AsyncTask to add Category on the DB and returns result list
            try {
                new ManageCategoryDBTask().execute(list).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Convert Category flow in a Category list
     * @param response
     * @return Category list
     */
    public static ArrayList<Category> ResponseToList(String response) {
        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Category>>(){}.getType();
        //ArrayList<Category> categories = new ArrayList<Category>();
        ArrayList<Category> categories = (ArrayList<Category>) gson.fromJson(response, collectionType);
        return categories;
    }


    /**
     * AsyncTask to manage Object Category in Local database
     */
    public class ManageCategoryDBTask extends AsyncTask<ArrayList<Category>, Void, Void>{

        @Override
        protected Void doInBackground(ArrayList<Category>... params) {
            ArrayList<Category> categories =  new ArrayList<Category>();
            ArrayList<String> results = new ArrayList<String>();
            categories = params[0];

            CategorySQLiteAdapter categorySQLiteAdapter = new CategorySQLiteAdapter(context);
            categorySQLiteAdapter.open();
            ArrayList<Category> categoriesDB = categorySQLiteAdapter.getAllCategory();

            for(Category category : categories)
            {
                Category tempCategory ;
                //Try to find a Category with this id_server
                tempCategory = categorySQLiteAdapter.getCategoryByIdServer(category.getIdServer());

                //If Category not exist on Mobile DB
                if(tempCategory == null)
                {
                    //Add category on the DB
                    long result = categorySQLiteAdapter.insert(category);
                }
                else
                {
                    //Update data
                    System.out.println("Update des éléments");
                    if (category.getUpdatedAt().compareTo(tempCategory.getUpdatedAt()) > 0) {
                        System.out.println("Commparaison de date mise à jour : date du flux  " + category.getUpdatedAt()
                                + " Date de la BDD" + tempCategory.getUpdatedAt());
                        long result = categorySQLiteAdapter.update(category);
                    }
                }

            }

            //delete check is exist on the DB but not
            if(categoriesDB != null) {
                for (Category categoryDB : categoriesDB) {
                    Boolean isExist = false;

                    for (Category category : categories) {
                        if (category.getIdServer() == categoryDB.getIdServer()) {
                            isExist = true;
                        }
                    }
                    if (isExist == false) {
                        long result = categorySQLiteAdapter.delete(categoryDB);
                    }
                }
            }
            categorySQLiteAdapter.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    /**
     * Manage get flow error
     * @param context
     */
    public void CategoryErrorMessage(Context context, int idError){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Message d'erreur");
        if (idError == 1) {
            alertDialog.setMessage("Impossible de récupérer le flux de données." +
                    "Êtes-vous sûr d'avoir une connexion au réseau ?");
        }else if (idError == 2) {
            alertDialog.setMessage("Aucune Categorie n'est paramétrée pour votre compte." +
                    "Veuillez contacter votre administrateur");
        } else if (idError == 3) {
            alertDialog.setMessage("Attention ! votre base de données n'est pas complète" +
                    "Veuillez contacter votre administrateur");
        }

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
