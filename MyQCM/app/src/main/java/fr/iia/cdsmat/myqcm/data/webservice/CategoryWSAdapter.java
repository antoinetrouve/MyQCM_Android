package fr.iia.cdsmat.myqcm.data.webservice;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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

import fr.iia.cdsmat.myqcm.data.CategorySQLiteAdapter;
import fr.iia.cdsmat.myqcm.entity.Category;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;

/**
 * Created by Antoine Trouvé on 14/05/2016.
 * antoinetrouve.france@gmail.com
 */
public class CategoryWSAdapter {

    // server table name
    private static final String CONST_CATEGORY = "category";

    //to make requeste (get,post...)
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String CONST_USERID = "userId";
    private static final String IDSERVER = "idServer";
    private static final String NAME = "name";
    private static final String UPDATEDAT = "updatedAt";

    String response;
    Context context;

    /**
     * COnstructor
     * @param context
     */
    public CategoryWSAdapter(Context context){
        this.context = context;
    }

    public void getCategoryRequest(int userId, String url){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(MyQCMConstants.CONST_CONNECT_TIMEOUT);
        asyncHttpClient.setTimeout(MyQCMConstants.CONST_SET_TIMEOUT);
        RequestParams params = new RequestParams();
        params.put(MyQCMConstants.CONST_VALUE_USERID, userId);

        asyncHttpClient.post(url + "." + MyQCMConstants.CONST_FLOW_FORMAT, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;
                ArrayList<Category> categories = responseToList(response);

                for(Category category:categories) {
                    System.out.println("On success = " + category.getName());
                }
                ManageCategoryDB(categories);
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
     * Compare data in database with flow and make modification if needed
     * @param response
     */
    private void ManageCategoryDB(ArrayList<Category> response) {
        if (response.isEmpty() == false) {
            // get the list of categ in Flux and add on listView
            ArrayList<Category> list = response;
            ArrayList<String> listResult = null;

            // Call the AsyncTask to add Category on the DB and returns result list
            try {
                listResult = new ManageCategoryDBTask().execute(list).get();
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
     * @return
     */
    private ArrayList<Category> responseToList(String response) {
        //Format of the recup Date
        String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Category>>(){}.getType();

        ArrayList<Category> categories = new ArrayList<Category>();
        categories = (ArrayList<Category>) gson.fromJson(response, collectionType);
        return categories;
    }

    /**
     * Todo : Id or not ?
     * Json to object Category
     */
    public static Category jsonToItem(JSONObject json) throws JSONException {

        Date updatedAt = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String name = json.getString(NAME);
        int idServer = json.getInt(IDSERVER);
        String stringUpdatedAt =  json.getString(UPDATEDAT);
        try {
            updatedAt = simpleDateFormat.parse(stringUpdatedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Category item = new Category(idServer,name,updatedAt);
        return item;
    }

    public static JSONObject itemToJson(Category item) throws JSONException {
        JSONObject result = new JSONObject();
        if(item.getName() != null) {
            result.put(NAME,item.getName());
        }
        if(item.getIdServer() != 0) {
            result.put(IDSERVER,item.getIdServer());
        }
        if(item.getUpdatedAt() != null) {
            result.put(UPDATEDAT,item.getUpdatedAt());
        }
        return result;
    }

    public static RequestParams ItemToParams(Category item) {
        RequestParams params = new RequestParams();
        params.put(IDSERVER,item.getIdServer());
        params.put(NAME,item.getName());
        params.put(UPDATEDAT,item.getUpdatedAt());
        return params;
    }

    public class ManageCategoryDBTask extends AsyncTask<ArrayList<Category>, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(ArrayList<Category>... params) {
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
                    results.add(String.valueOf(result));
                }
                else
                {
                    long result = categorySQLiteAdapter.update(category);
                    results.add(String.valueOf(result));
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
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
        }
    }
}
