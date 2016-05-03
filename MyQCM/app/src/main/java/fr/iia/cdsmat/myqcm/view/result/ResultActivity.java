package fr.iia.cdsmat.myqcm.view.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.iia.cdsmat.myqcm.view.menu.MenuActivity;

/**
 * Created by Antoine Trouvé on 03/05/2016.
 * antoinetrouve.france@gmail.com
 */
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the fragment initially
        Intent intent = new Intent(ResultActivity.this, MenuActivity.class);
        startActivity(intent);

    }
}
