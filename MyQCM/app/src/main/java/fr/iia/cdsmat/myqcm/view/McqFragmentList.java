package fr.iia.cdsmat.myqcm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.data.McqSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.McqWSAdapter;
import fr.iia.cdsmat.myqcm.entity.Category;
import fr.iia.cdsmat.myqcm.entity.Mcq;
import fr.iia.cdsmat.myqcm.view.mcq.McqActivity;
import fr.iia.cdsmat.myqcm.view.menu.MenuActivity;

/**
 * Class to manage view of list Mcq
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class McqFragmentList extends ListFragment{


    public McqFragmentList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragmentlist_mcq, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(fab.VISIBLE);

        McqWSAdapter mcqWSAdapter = new McqWSAdapter(getActivity().getBaseContext());
        McqSQLiteAdapter mcqSQLiteAdapter = new McqSQLiteAdapter(getActivity().getBaseContext());

        // open DB to get the list mcq on DB
        mcqSQLiteAdapter.open();
        ArrayList<Mcq> mcqs = mcqSQLiteAdapter.getAllMcq();
        mcqSQLiteAdapter.close();

        //Create Adapter
        if(mcqs != null) {
            ArrayAdapter<Mcq> arrayAdapter = new ArrayAdapter<Mcq>(
                    getActivity(),
                    R.layout.fragmentrow_mcq,
                    R.id.mcq_textViewRow,
                    mcqs);
            setListAdapter(arrayAdapter);
        }
        mcqWSAdapter.getMcqRequest(1, 3, MyQCMConstants.CONST_IPSERVER
                + MyQCMConstants.CONST_URL_BASE
                + MyQCMConstants.CONST_URL_USERMCQS);

        //Retain listfragment instance across configuration changes
        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),McqActivity.class);
        startActivity(intent);
    }
}
