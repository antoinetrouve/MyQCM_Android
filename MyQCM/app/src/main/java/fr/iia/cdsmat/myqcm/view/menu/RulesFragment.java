package fr.iia.cdsmat.myqcm.view.menu;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;

/**
 * Class managing Rules's fragment depending of Menu Activity
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class RulesFragment extends Fragment {


    public RulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_rules, container, false);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(fab.INVISIBLE);
        return rootView;
    }

}
