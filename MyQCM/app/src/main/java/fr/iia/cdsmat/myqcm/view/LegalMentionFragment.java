package fr.iia.cdsmat.myqcm.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.iia.cdsmat.myqcm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LegalMentionFragment extends Fragment {


    public LegalMentionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_legal_mention, container, false);
    }

}
