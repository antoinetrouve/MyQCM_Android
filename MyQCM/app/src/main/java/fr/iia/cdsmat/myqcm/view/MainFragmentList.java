package fr.iia.cdsmat.myqcm.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.configuration.Utils;
import fr.iia.cdsmat.myqcm.data.CategorySQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.CategoryWSAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.ConnectionWSAdapter;
import fr.iia.cdsmat.myqcm.entity.Category;
import fr.iia.cdsmat.myqcm.view.login.LoginActivity;

/**
 * Class managing Category list view
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class MainFragmentList extends ListFragment {
    private Boolean isServerReachable;
    CategoryWSAdapter categoryWSAdapter;
    CategorySQLiteAdapter categorySQLiteAdapter;
    ProgressDialog dialog;
    ArrayList<Category> categories;
    int userIdServer;

    public MainFragmentList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(fab.VISIBLE);

        //Get arguments from MenuActivity
        //-------------------------------
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("FirstConnection")) {
                boolean isFirstConnection = getArguments().getBoolean("FirstConnection");
                if (args.containsKey("UserIdServer")) {
                    userIdServer = getArguments().getInt("UserIdServer", 0);
                    categoryWSAdapter = new CategoryWSAdapter(getActivity().getBaseContext());
                    isServerReachable = Utils.CheckInternetConnection(getActivity().getBaseContext());

                    //User id OK and first connection for the user
                    //---------------------------------------------
                    if (userIdServer != 0 && isFirstConnection == true && isServerReachable == true) {

                        //Manage dialog connection
                        //--------------------------
                        dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Récupération des informations pour votre première connexion...");
                        dialog.setCancelable(false);
                        dialog.setInverseBackgroundForced(false);
                        dialog.show();

                        // get Category list from Web server
                        //-----------------------------------
                        categoryWSAdapter.getCategoryRequest(
                                userIdServer,
                                MyQCMConstants.CONST_IPSERVER
                                        + MyQCMConstants.CONST_URL_BASE
                                        + MyQCMConstants.CONST_URL_USERCATEGORIES,
                                new CategoryWSAdapter.CallBack() {
                                    @Override
                                    public void methods(String response) {
                                        System.out.println("Response Main FragmentList FirstConnection : " + response);

                                        //If get Category flow is impossible
                                        //-----------------------------------
                                        if (response.equals("OnFailure") == true) {
                                            dialog.hide();
                                            System.out.println(response);
                                            System.out.println("context = " + getActivity().getBaseContext());
                                            categoryWSAdapter.CategoryErrorMessage(getActivity().getBaseContext(), 1);

                                        } else {

                                            //Else Insert data in database
                                            //----------------------------
                                            categories = CategoryWSAdapter.ResponseToList(response);
                                            if (categories.isEmpty() != false) {
                                                dialog.hide();
                                                System.out.println(response);
                                                System.out.println("context = " + getActivity().getBaseContext());
                                                categoryWSAdapter.CategoryErrorMessage(getActivity().getBaseContext(), 2);
                                            } else {
                                                int error = 0;
                                                System.out.println(response);
                                                categorySQLiteAdapter = new CategorySQLiteAdapter(getActivity().getBaseContext());
                                                categorySQLiteAdapter.open();
                                                for (Category category : categories) {
                                                    long result = categorySQLiteAdapter.insert(category);
                                                    if (result < 0) {
                                                        error = 1;
                                                    }
                                                }
                                                categorySQLiteAdapter.close();

                                                if (error == 1) {
                                                    dialog.hide();
                                                    categoryWSAdapter.CategoryErrorMessage(getActivity().getBaseContext(), 3);
                                                }

                                                // Create Array Adapter to show category list
                                                //--------------------------------------------
                                                ArrayAdapter<Category> arrayAdapter = new ArrayAdapter<Category>(
                                                        getActivity(),
                                                        R.layout.fragmentrow_main,
                                                        R.id.main_textViewRow,
                                                        categories);
                                                setListAdapter(arrayAdapter);
                                                dialog.hide();
                                            }
                                        }
                                    }
                                });
                        setRetainInstance(true);
                    } else {
                        //Else is NOT first connection for user

                        //Update data in database
                        //-----------------------
                        if (userIdServer != 0 && isServerReachable == true) {
                            categoryWSAdapter.getCategoryRequest(userIdServer,
                                    MyQCMConstants.CONST_IPSERVER + MyQCMConstants.CONST_URL_BASE + MyQCMConstants.CONST_URL_USERCATEGORIES);
                            setRetainInstance(true);
                        }

                        //Get Category existing in database
                        //---------------------------------
                        categorySQLiteAdapter = new CategorySQLiteAdapter(getActivity().getBaseContext());
                        categorySQLiteAdapter.open();
                        categories = categorySQLiteAdapter.getAllCategory();
                        categorySQLiteAdapter.close();

                        if (categories != null) {
                            // Create Array Adapter to set view
                            ArrayAdapter<Category> arrayAdapter = new ArrayAdapter<Category>(
                                    getActivity(),
                                    R.layout.fragmentrow_main,
                                    R.id.main_textViewRow,
                                    categories);
                            setListAdapter(arrayAdapter);
                        }
                    }
                }
            }
        }else {
            //Not enough arguments to manage fragment
            //---------------------------------------
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getBaseContext()).create();
            alertDialog.setTitle("Message d'erreur");
            alertDialog.setMessage("Un problème est survenu lors de votre connexion. Veuillez-vous reconnecter svp." +
                    "Si le problème persiste, contactez votre administrateur.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Event called when Item is selected on list
     *
     * @param l
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        //Get the value of categ
        int idCateg = categories.get(position).getIdServer();

        // set the fragment initially
        McqFragmentList fragment = new McqFragmentList();

        // create a Bundle To store Value
        Bundle categBundle = new Bundle();

        // Put Messag inside The Bundle
        categBundle.putInt("id_categ", idCateg);
        categBundle.putInt("id_user", userIdServer);

        //Set the Bundle on the Fragment
        fragment.setArguments(categBundle);

        //Set Fragment list mcq
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
