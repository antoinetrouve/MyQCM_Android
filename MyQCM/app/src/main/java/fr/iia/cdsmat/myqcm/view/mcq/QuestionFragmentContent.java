package fr.iia.cdsmat.myqcm.view.mcq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.iia.cdsmat.myqcm.R;

/**
 * Class managing Question's fragment content view depending of Mcq Activity
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class QuestionFragmentContent extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the fragment layout file
        return inflater.inflate(R.layout.fragment_question_content, container, false);

        /**
         * TODO : Show answers list for this question
         * TODO : Create button in view to navigate between the questions
         * TODO : Save answer into a list OnClick event
         */

    }
}
