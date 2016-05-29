package fr.iia.cdsmat.myqcm.view.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.data.webservice.CategoryWSAdapter;
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

        //Set default fragment (fragment app_bar_menu.xml)
        MainFragmentList fragment = new MainFragmentList();

        //FrameLayout in app_bar_home.xml
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
        fragmentTransaction.commit();

        CategoryWSAdapter categoryWSAdapter = new CategoryWSAdapter();
        categoryWSAdapter.getCategoryRequest(1,
                MyQCMConstants.CONST_IPSERVER + MyQCMConstants.CONST_URL_BASE + MyQCMConstants.CONST_URL_USERCATEGORIES,
                new CategoryWSAdapter.CallBack(){
            @Override
            public void methods(String reponse){
                System.out.println("Reponse = " + reponse);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Send result ?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_disconnect){
            //Return to Login Activity
            Intent intent = new Intent(MenuActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
