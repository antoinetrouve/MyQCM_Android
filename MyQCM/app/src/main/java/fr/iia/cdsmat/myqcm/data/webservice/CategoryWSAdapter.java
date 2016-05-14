package fr.iia.cdsmat.myqcm.data.webservice;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.iia.cdsmat.myqcm.entity.Category;

/**
 * Created by antoi on 14/05/2016.
 * antoinetrouve.france@gmail.com
 */
public class CategoryWSAdapter {

    //Url vers le Webservice
    private static final String BASE_URL ="http://192.168.1.14/qcm/web/app_dev.php/api/categoriesusers";
    //    private static final String ENTITY = "book";
    // nom de la table distance
    private static final String ENTITY = "category"; // exemple
    private static final int VERSION = 1;
    //to make requeste (get,post...)
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String ID = "id";
    private static final String IDSERVER = "idServer";
    private static final String NAME = "name";
    private static final String UPDATEDAT = "updatedAt";

    public static void getCategory(int idServer,AsyncHttpResponseHandler handler) {

        String url = String.format("%s/%s",BASE_URL,idServer);
        client.get(url,handler);
    }

    public static void getAll() {

    }

    public static void post(Category item,AsyncHttpResponseHandler responseHandler) throws JSONException {

        RequestParams params = CategoryWSAdapter.ItemToParams(item);
        String url = String.format("%s/%s",BASE_URL,ENTITY);
        client.post(url,params,responseHandler);

    }

    public static void put(Category item,AsyncHttpResponseHandler responseHandler) {

        RequestParams params = CategoryWSAdapter.ItemToParams(item);
        String url = String.format("%s/%s",BASE_URL,ENTITY);
        client.put(url, params, responseHandler);
    }

    public static void delete(Category item,AsyncHttpResponseHandler responseHandler) {

        String url = String.format("%s/%s/%s",BASE_URL,ENTITY,item.getId());
        client.post(url,responseHandler);

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
}
