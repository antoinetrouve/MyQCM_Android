package fr.iia.cdsmat.myqcm.view.mcq;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import fr.iia.cdsmat.myqcm.R;

/**
 * Created by Antoine Trouvé on 30/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class McqActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq);

        //Set the fragment initially
        QuestionFragmentHeader fragmentQuestionHeader = new QuestionFragmentHeader();
        FragmentTransaction fragmentTransactionHeader = getSupportFragmentManager().beginTransaction();
        fragmentTransactionHeader.replace(R.id.mcq_header, fragmentQuestionHeader);
        fragmentTransactionHeader.commit();

        //Set the fragment initially
        QuestionFragmentContent fragmentQuestionContent = new QuestionFragmentContent();
        FragmentTransaction fragmentTransactionContent = getSupportFragmentManager().beginTransaction();
        fragmentTransactionContent.replace(R.id.mcq_content, fragmentQuestionContent);
        fragmentTransactionContent.commit();

    }
}
