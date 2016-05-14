package fr.iia.cdsmat.myqcm.view.mcq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.iia.cdsmat.myqcm.R;

/**
 * Class managing Question's fragment header view depending of Mcq Activity
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class QuestionFragmentHeader extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the fragment layout file
        return inflater.inflate(R.layout.fragment_question_header, container, false);

        /**
         * TODO : Show qcm's countdown and manage countdown
         * TODO : Show qcm's name to see where user is located
         * TODO : Manage erro message if countdown is ended and result not send yet
         */
    }
}
