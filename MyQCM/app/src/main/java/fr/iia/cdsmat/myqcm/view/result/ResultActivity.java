package fr.iia.cdsmat.myqcm.view.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.iia.cdsmat.myqcm.view.menu.MenuActivity;

/**
 * Class managing result view
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the fragment initially
        Intent intent = new Intent(ResultActivity.this, MenuActivity.class);
        startActivity(intent);

        /**
         * TODO : Get user result
         * TODO : Send result to WebService by AsyncTask
         * TODO : If connection to WebService unavailable save result into local database
         */

    }
}
