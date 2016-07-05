package fr.iia.cdsmat.myqcm.view.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.configuration.Password;
import fr.iia.cdsmat.myqcm.configuration.Utils;
import fr.iia.cdsmat.myqcm.data.AsyncTask.ManageDBUserAsyncTask;
import fr.iia.cdsmat.myqcm.data.AsyncTask.OnTaskCompleted;
import fr.iia.cdsmat.myqcm.data.webservice.ConnectionWSAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.UserWSAdapter;
import fr.iia.cdsmat.myqcm.data.UserSQLiteAdapter;
import fr.iia.cdsmat.myqcm.entity.User;
import fr.iia.cdsmat.myqcm.view.menu.MenuActivity;

/**
 * Class managing Login view
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class LoginActivity extends AppCompatActivity implements OnTaskCompleted {

    ConnectionWSAdapter connectionWSAdapter;
    UserSQLiteAdapter userSQLiteAdapter;
    ProgressDialog dialog;
    long result;
    ManageDBUserAsyncTask manageDBUserAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Define the content view of this activity
        setContentView(R.layout.activity_login);
        final EditText etLogin = (EditText)this.findViewById(R.id.etLogin);
        final EditText etPassword = (EditText)this.findViewById(R.id.etPassword);
        final Button btConnexion = (Button)this.findViewById(R.id.btConnexion);
        userSQLiteAdapter = new UserSQLiteAdapter(this);

        if (btConnexion != null){
            btConnexion.setOnClickListener(new View.OnClickListener() {
                /**
                 * Call Button connexion's OnClick event to start new activity (MenuActivity)
                 * @param v
                 */
                @Override
                public void onClick(View v) {

                    //Get login and password
                    //-----------------------
                    final String login = etLogin.getText().toString();
                    final String password = etPassword.getText().toString();
                    System.out.println("login = " + login + " password = " + password);

                    //Test Connection
                    //---------------
                    boolean isConnected = Utils.CheckInternetConnection(LoginActivity.this);
                    System.out.println("is connected ? : " + isConnected);

                    if (isConnected != false) {
                        //Manage dialog connection
                        //--------------------------
                        dialog=new ProgressDialog(LoginActivity.this);
                        dialog.setMessage("Tentative de connexion ...");
                        dialog.setCancelable(false);
                        dialog.setInverseBackgroundForced(false);
                        dialog.show();

                        //Request User Information
                        //-------------------------
                        connectionWSAdapter = new ConnectionWSAdapter();
                        connectionWSAdapter.ConnectionRequest(
                            MyQCMConstants.CONST_IPSERVER
                                    + MyQCMConstants.CONST_URL_BASE
                                    + MyQCMConstants.CONST_URL_AUTH,
                            login, password,
                            new ConnectionWSAdapter.CallBack()  {

                                @Override
                                public void methods(String response) {
                                    System.out.println("Response login Activity = " + response);

                                    //If user is not authenticated
                                    //-----------------------------
                                    if (response.equals("false") == true) {
                                        dialog.hide();
                                        connectionWSAdapter.connectionErrorMessage(LoginActivity.this);
                                        System.out.println(response);
                                    } else {

                                        //Else Create user with json information
                                        //---------------------------------------
                                        User user = UserWSAdapter.JsonToItem(response);
                                        //Hash password and convert hash code to string
                                        String pwd = Password.toHexString(Password.sha512(password));
                                        user.setPassword(pwd);
                                        System.out.println("JsonToItem OK");
                                        System.out.println("User created by json : "
                                                + user.getUsername() + " idServer = "
                                                + user.getIdServer() + " password = " + user.getPassword());

                                        if (user instanceof User) {

                                            userSQLiteAdapter = new UserSQLiteAdapter(LoginActivity.this);
                                            userSQLiteAdapter.open();
                                            System.out.println("userSQLiteAdapter.open() OK");

                                            //Get all users existing in local database
                                            ArrayList<User> users = userSQLiteAdapter.getAllUser();

                                            //Get user authenticated in local database
                                            User userDB = userSQLiteAdapter.getUserByIdServer(user.getIdServer());

                                            //if a user exist in database and is not the current user
                                            //-------------------------------------------------------
                                            if (users != null && userDB == null) {
                                                //Manage only one account authorized
                                                UserMessage(LoginActivity.this, user);
                                            } else if (userDB != null) {
                                                //If user exist and have to update
                                                //----------------------------------
                                                if (userDB.getUpdatedAt() != null) {
                                                    if (user.getUpdatedAt().compareTo(userDB.getUpdatedAt()) > 0) {
                                                        //Try update User
                                                        try {
                                                            manageDBUserAsyncTask = new ManageDBUserAsyncTask(LoginActivity.this, user);
                                                            manageDBUserAsyncTask.execute(user).get();
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        } catch (ExecutionException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }

                                                //user exist and is up to date Launch Main Activity
                                                //---------------------------------------------------
                                                userSQLiteAdapter.close();
                                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                                intent.putExtra("FirstConnection", false);
                                                intent.putExtra("UserIdServer", user.getIdServer());
                                                dialog.hide();
                                                startActivity(intent);
                                            } else {
                                                //User does not exist in database and have to be created
                                                result = userSQLiteAdapter.insert(user);
                                                userSQLiteAdapter.close();
                                                if (result > 0) {
                                                    dialog.hide();
                                                    Toast.makeText(LoginActivity.this, MyQCMConstants.CONST_MESS_CREATEDB + user.getUsername(), Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                                    intent.putExtra("FirstConnection",true);
                                                    intent.putExtra("UserIdServer", user.getIdServer());
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(LoginActivity.this, MyQCMConstants.CONST_MESS_CREATEDBERROR, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
                                }
                            });

                    } else {
                        //Get user authenticated in local database
                        userSQLiteAdapter = new UserSQLiteAdapter(LoginActivity.this);
                        userSQLiteAdapter.open();
                        //Get all users existing in local database
                        ArrayList<User> users = userSQLiteAdapter.getAllUser();
                        //Get user in database for authentification
                        String pwd = Password.toHexString(Password.sha512(password));
                        User userDB = userSQLiteAdapter.getUserByLoginPassword(login,pwd);
                        userSQLiteAdapter.close();
                        //if user exist in database
                        if (userDB != null) {
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            intent.putExtra("FirstConnection",false);
                            intent.putExtra("UserIdServer", userDB.getIdServer());
                            startActivity(intent);

                        //else if user does not exist but another one storaged in database
                        } else if (users != null && userDB == null) {
                            //Show error connection message
                            ConnectionMessage(LoginActivity.this,2);
                        } else {
                            //Show error connection message
                            ConnectionMessage(LoginActivity.this,1);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onTaskCompleted(String response) {
        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
    }


    /**
     * Message which manage new user integration
     * @param context
     * @param user (user to insert in database)
     */
    private void UserMessage(final Context context, final User user) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        System.out.println("OK ManageUserMessage");
        System.out.println("OK set alert dialog");
        alertDialogBuilder.setTitle("Attention compte utilisateur déjà existant !");
        alertDialogBuilder.setMessage("Si vous continuez, l'application effaçera toutes les données relatives à cet utilisateur. " +
                "Êtes-vous sûr de vouloir continuer ?");
        alertDialogBuilder.setPositiveButton("OUI",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("OK click on yes");

                        //Delete database
                        Boolean isDeleted = context.deleteDatabase(MyQCMConstants.APP_DB_NAME + MyQCMConstants.APP_DB_EXTENSION);
                        if (isDeleted == true) {
                            System.out.println("OK database deleted");
                            //New User have to be created
                            UserSQLiteAdapter userSQLiteAdapter = new UserSQLiteAdapter(context);
                            userSQLiteAdapter.open();
                            result = userSQLiteAdapter.insert(user);
                            userSQLiteAdapter.close();
                            if (result > 0) {
                                Toast.makeText(context, MyQCMConstants.CONST_MESS_CREATEDB + user.getUsername(), Toast.LENGTH_SHORT).show();
                                //Launch main activity
                                Intent intent = new Intent(context,MenuActivity.class);
                                intent.putExtra("FirstConnection",true);
                                intent.putExtra("UserIdServer", user.getIdServer());
                                startActivity(intent);
                            } else {
                                Toast.makeText(context, MyQCMConstants.CONST_MESS_CREATEDBERROR, Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        } else {
                            Toast.makeText(context, MyQCMConstants.CONST_MESS_CREATEDBERROR, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alertDialogBuilder.setNegativeButton("NON",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //After logout redirect user to Login Activity
                        Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
                        //Closing all the activities
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //Add new Flag to start new activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Message which manage error connection
     * @param context
     * @param idError (1=Internet Connection error; 2=Need Internet Connection to registered new account)
     */
    private void ConnectionMessage(final Context context, int idError){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Erreur de connection");
        if (idError == 1) {
            alertDialogBuilder.setMessage("Pas de connexion Internet. Verifier votre connexion." +
                    " Si le problème persiste, veuillez contacter votre administrateur.");
        }else if (idError == 2) {
            alertDialogBuilder.setMessage("Pas de connexion Internet." +
                    " Un compte existant a été détecté sur ce terminal mobile. Si c'est votre première connexion," +
                    " un accès Internet est nécessaire. Sinon, veuillez essayer à nouveau." +
                    " Si le problème persiste, veuillez contacter votre administrateur.");
        }

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //After logout redirect user to Login Activity
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                //Closing all the activities
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //Add new Flag to start new activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
