package fr.iia.cdsmat.myqcm.view.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.data.UserSQLiteAdapter;
import fr.iia.cdsmat.myqcm.entity.User;
import fr.iia.cdsmat.myqcm.view.login.LoginActivity;
import fr.iia.cdsmat.myqcm.view.MainFragmentList;

/**
 * Class managing Menu view
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //MenuActivity component
    NavigationView navigationView = null;
    Toolbar toolbar = null;
    ImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Set default fragment without Arguments
        MainFragmentList fragment = new MainFragmentList();
        //FrameLayout in app_bar_home.xml
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //Get extra from LoginActivity
        //-----------------------------
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bundle bundle = new Bundle();
            if (extras.containsKey("FirstConnection")) {
                boolean isFirstConnection = intent.getBooleanExtra("FirstConnection", false);
                if (isFirstConnection == true) {
                    bundle.putBoolean("FirstConnection", true);
                } else {
                    bundle.putBoolean("FirstConnection", false);
                }
            }
            if (extras.containsKey("UserIdServer")) {
                int userIdServer = intent.getIntExtra("UserIdServer", 0);
                bundle.putInt("UserIdServer",userIdServer);
            }
            //Set fragment's arguments
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(fab.VISIBLE);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
                alertDialog.setTitle("Aide");
                alertDialog.setIcon(R.drawable.ic_menu_help);
                alertDialog.setMessage("Pour accéder aux questionnaires,"
                        + " Sélectionnez un élément dans la liste");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        //Set menu layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatemwentWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profil) {

            //Set profil fragment
            ProfilFragment fragment = new ProfilFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_rules) {
            //Set rules fragment
            RulesFragment fragment = new RulesFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_help) {
            //Set help fragment
            HelpFragment fragment = new HelpFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_legalMention) {
            //Set LegalMention fragment
            LegalMentionFragment fragment = new LegalMentionFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
            fragmentTransaction.commit();

        }else if (id == R.id.nav_accueil) {

            //Set Main fragment
            MainFragmentList fragment = new MainFragmentList();

            //Set argument to MainFragmentList
            Bundle categoryBundle = new Bundle();
            categoryBundle.putBoolean("FirstConnection",false);

            //Get user's IdServer
            UserSQLiteAdapter userSQLiteAdapter = new UserSQLiteAdapter(MenuActivity.this);
            userSQLiteAdapter.open();
            ArrayList<User> users = userSQLiteAdapter.getAllUser();
            userSQLiteAdapter.close();
            if (users.size() == 1) {
                for (User user : users) {
                    categoryBundle.putInt("UserIdServer",user.getIdServer());
                }
            }else{
                categoryBundle.putInt("UserIdServer",0);
            }

            fragment.setArguments(categoryBundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_disconnect){
            //After logout redirect user to Login Activity
            Intent intent = new Intent(MenuActivity.this,LoginActivity.class);
            //Closing all the activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //Add new Flag to start new activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
