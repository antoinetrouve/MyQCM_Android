package fr.iia.cdsmat.myqcm.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.data.AsyncTask.ManageDBUserAsyncTask;
import fr.iia.cdsmat.myqcm.data.AsyncTask.OnTaskCompleted;
import fr.iia.cdsmat.myqcm.data.webservice.ConnectionWSAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.UserWSAdapter;
import fr.iia.cdsmat.myqcm.data.UserSQLiteAdapter;
import fr.iia.cdsmat.myqcm.view.menu.MenuActivity;

/**
 * Class managing Login view
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class LoginActivity extends AppCompatActivity implements OnTaskCompleted {

    ConnectionWSAdapter connectionWSAdapter;
    UserWSAdapter userWSAdapter;
    UserSQLiteAdapter userSQLiteAdapter;
    ProgressDialog dialog;

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
                    //Manage dialog connection
                    //--------------------------
                    dialog=new ProgressDialog(LoginActivity.this);
                    dialog.setMessage("Tentative de connexion ...");
                    dialog.setCancelable(false);
                    dialog.setInverseBackgroundForced(false);
                    dialog.show();

                    //Get login and password
                    //-----------------------
                    final String login = etLogin.getText().toString();
                    String password = etPassword.getText().toString();
                    System.out.println("login = " + login + " password = " + password);
                    //Hash password and convert hash code to string
                    //password = Password.toHexString(Password.sha512(password));

                    connectionWSAdapter = new ConnectionWSAdapter();
                    connectionWSAdapter.ConnectionRequest(MyQCMConstants.CONST_IPSERVER
                            + MyQCMConstants.CONST_URL_BASE
                            + MyQCMConstants.CONST_URL_AUTH,
                            login, password, new ConnectionWSAdapter.CallBack() {
                        @Override
                        public void methods(String response) {
                            System.out.println("Response login Activity = " + response);
                            if (response.equals("false") == true) {
                                dialog.hide();
                                connectionWSAdapter.connectionErrorMessage(LoginActivity.this);
                                System.out.println(response);
                            } else {
                                System.out.println(response);
                                ManageDBUserAsyncTask userAsyncTask = new ManageDBUserAsyncTask(LoginActivity.this,response);
                                try {
                                    userAsyncTask.execute(response).get();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }

                                //Launch main activity
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                dialog.hide();
                                startActivity(intent);
                            }
                        }
                    });
                }
            });
        }

    }

    @Override
    public void onTaskCompleted(String response) {
        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
    }
}
