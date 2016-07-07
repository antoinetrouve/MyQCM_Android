package fr.iia.cdsmat.myqcm.data.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.data.UserSQLiteAdapter;
import fr.iia.cdsmat.myqcm.entity.User;

/**
 * AsyncTask to manage user's process in local database after authentification process
 * Created by Antoine Trouvé on 04/06/2016.
 * antoinetrouve.france@gmail.com
 */
public class ManageDBUserAsyncTask extends AsyncTask<User, Void, String>{
    private OnTaskCompleted taskCompleted;
    private User user;

    public ManageDBUserAsyncTask(OnTaskCompleted context, User user){
        this.taskCompleted = context;
        this.user = user;
    }

    /**
     * Update User Async Task
     * @param params
     * @return String message
     */
    @Override
    protected String doInBackground(User... params) {
        long result;
        String resultMessage;
        UserSQLiteAdapter userSQLiteAdapter = new UserSQLiteAdapter((Context)taskCompleted);
        userSQLiteAdapter.open();

        //Update user in database
        result = userSQLiteAdapter.update(user);
        System.out.println("update user OK");
        userSQLiteAdapter.close();
        if(result > 0){
            resultMessage = MyQCMConstants.CONST_MESS_UPDATEDB;
            return resultMessage;
        }else{
            resultMessage = MyQCMConstants.CONST_MESS_UPDATEDBERROR;
            return resultMessage;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        taskCompleted.onTaskCompleted(s);
    }
}
