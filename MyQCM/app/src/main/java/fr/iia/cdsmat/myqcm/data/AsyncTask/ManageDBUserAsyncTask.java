package fr.iia.cdsmat.myqcm.data.AsyncTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import java.util.ArrayList;

import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.data.UserSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.UserWSAdapter;
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

//    @Override
//    protected void onPreExecute() {
//        //Create user with userInformation json
//        User user = UserWSAdapter.JsonToItem(response);
//        System.out.println("Pre execute : " + response);
//        System.out.println("Pre execute : JsonToItem OK");
//        if (user instanceof User){
//            UserSQLiteAdapter userSQL = new UserSQLiteAdapter((Context)taskCompleted);
//            userSQL.open();
//            //user existing in database
//            ArrayList<User> users = userSQL.getAllUser();
//            User userDB = userSQL.getUserByIdServer(user.getIdServer());
//            //if a user exist in database and is not the current user
//            if (users.size() > 0 && userDB == null){
//                alertDialog = new AlertDialog.Builder((Context)taskCompleted).create();
//                alertDialog.setTitle("Attention compte utilisateur déjà existant !");
//                alertDialog.setMessage("Un compte utilisateur existe déjà pour cette application ! " +
//                        "Si vous continuez, l'application effaçera toutes les données relatives à cet utilisateur. " +
//                        "Êtes-vous sûr de vouloir continuer ?");
//                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OUI",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                alertDialog.dismiss();
//                            }
//                        });
//                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NON",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                isDelete = true;
//                                cancel(true);
//                                alertDialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
//            }
//            super.onPreExecute();
//        }else{
//            cancel(true);
//        }
//    }
//
//    @Override
//    protected void onCancelled(String s) {
//        super.onCancelled(s);
//    }

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
