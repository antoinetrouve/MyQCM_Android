package fr.iia.cdsmat.myqcm.data.webservice;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;

import java.lang.reflect.Type;
import fr.iia.cdsmat.myqcm.entity.User;

/**
 * Class to manage User with webservice
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 28/05/2016
 */
public class UserWSAdapter {

    //to make request (get,post...)
    private static AsyncHttpClient client = new AsyncHttpClient();
    private long result;
    Context context;

    /**
     * UserWSAdapter's Constructor
     * @param context
     */
    public UserWSAdapter(Context context){
        this.context = context;
    }

    /**
     * Serialize User from user flow
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
