package fr.iia.cdsmat.myqcm.view.menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.data.webservice.UserWSAdapter;

/**
 * Class managing Profil's fragment depending of Menu Activity
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class ProfilFragment extends Fragment {

    public ProfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);



    }

}
