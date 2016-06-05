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
public class ManageDBUserAsyncTask extends AsyncTask<String, Void, String>{
    private OnTaskCompleted taskCompleted;
    private String response;
    private AlertDialog alertDialog;
    private boolean isDelete;

    public ManageDBUserAsyncTask(OnTaskCompleted context, String response){
        this.taskCompleted = context;
        this.response = response;
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

    @Override
    protected String doInBackground(String... params) {
        long result;
        String resultMessage;
        isDelete = true;
        //Create user with userInformation json
        User user = UserWSAdapter.JsonToItem(response);
        System.out.println(response);
        System.out.println("JsonToItem OK");

        while (isDelete == true){
            if (isCancelled()){
                break;
            }
            if(user instanceof User){
                System.out.println("If User instance OK");
                UserSQLiteAdapter userSQLiteAdapter = new UserSQLiteAdapter((Context)taskCompleted);
                userSQLiteAdapter.open();
                System.out.println("userSQLiteAdapter.open() OK");
                User userDB = userSQLiteAdapter.getUserByIdServer(user.getIdServer());
                System.out.println("UsernameDB OK");

                //if user exist in database
                if(userDB != null){
                    //if flow is more recent than user in local database
                    System.out.println("if user exist");
                    if (user.getUpdatedAt().compareTo(userDB.getUpdatedAt()) > 0){
                        System.out.println("update user");
                        result = userSQLiteAdapter.update(user);
                        System.out.println("update user OK");
                        userSQLiteAdapter.close();
                        if(result > 0){
                            resultMessage = MyQCMConstants.CONST_MESS_UPDATEDB;
                            return resultMessage;
                        }
                    }else{
                        System.out.println(MyQCMConstants.CONST_MESS_CREATEDB + userDB.getUsername());
                        resultMessage = MyQCMConstants.CONST_MESS_CREATEDB + userDB.getUsername();
                        return resultMessage;
                    }
                }else{
                    //if a user already exist in database
                    ArrayList<User> users = userSQLiteAdapter.getAllUser();
                    System.out.println("if user isnt exist in database : insert");

                    result = userSQLiteAdapter.insert(user);
                    System.out.println("After insert");
                    userSQLiteAdapter.close();
                    if(result > 0){
                        resultMessage = MyQCMConstants.CONST_MESS_CREATEDB + user.getUsername();
                        System.out.println(MyQCMConstants.CONST_MESS_CREATEDB + user.getUsername());
                        return resultMessage;
                    }
                }

            }else{
                resultMessage = null;
                return resultMessage;
            }
        }

        resultMessage = null;
        return resultMessage;
    }

    @Override
    protected void onPostExecute(String s) {
        taskCompleted.onTaskCompleted(s);
    }
}
