package fr.iia.cdsmat.myqcm.view.mcq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.iia.cdsmat.myqcm.R;

/**
 * Created by Antoine Trouvé on 30/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class QuestionFragmentContent extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the fragment layout file
        return inflater.inflate(R.layout.fragment_question_content, container, false);

    }
}
