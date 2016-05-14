package fr.iia.cdsmat.myqcm.view.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.data.AnswerSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.UserSQLiteAdapter;
import fr.iia.cdsmat.myqcm.view.menu.MenuActivity;

/**
 * Class managing Login view
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Define the content view of this activity
        setContentView(R.layout.activity_login);
        final EditText etLogin = (EditText)this.findViewById(R.id.etLogin);
        final EditText etPassword = (EditText)this.findViewById(R.id.etPassword);
        Button btConnexion = (Button)this.findViewById(R.id.btConnexion);

        //creation test of local database
        //UserSQLiteAdapter user = new UserSQLiteAdapter(this);
        //user.open();

        btConnexion.setOnClickListener(new View.OnClickListener() {
            /**
             * Call Button connexion's OnClick event to start new activity (MenuActivity)
             * @param v
             */
            @Override
            public void onClick(View v) {
                /**
                 *  TODO : Authentification method to send login and password encrypted to Webservice
                 *  TODO : Get webservice answer (if true = intent instanciation and redirection)
                 */
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
