package fr.iia.cdsmat.myqcm.view.questionnaire;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.data.McqSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.QuestionSQLiteAdapter;
import fr.iia.cdsmat.myqcm.entity.Mcq;
import fr.iia.cdsmat.myqcm.entity.Question;

/**
 * Created by Antoine Trouvé on 06/07/2016.
 * antoinetrouve.france@gmail.com
 */
public class SubHeaderQuestionnaireFragment extends Fragment {
    McqSQLiteAdapter mcqSQLiteAdapter;
    QuestionSQLiteAdapter questionSQLiteAdapter;
    TextView timer_mcq;
    Mcq mcq;
    boolean isfinish = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sub_header_questionnaire, container, false);
        RelativeLayout relativ_subHeader = (RelativeLayout)rootView.findViewById(R.id.relative_layout_subHeader);

        int id_mcq =  getArguments().getInt("id_mcq");

        mcqSQLiteAdapter = new McqSQLiteAdapter(getActivity().getApplication());
        questionSQLiteAdapter = new QuestionSQLiteAdapter(getActivity().getApplication());
        questionSQLiteAdapter.open();
        mcqSQLiteAdapter.open();
        ArrayList<Question> questions = questionSQLiteAdapter.getAllQuestionByIdServerMCQ(id_mcq);
        questionSQLiteAdapter.close();
        mcq = mcqSQLiteAdapter.getMcqByIdServer(id_mcq);
        mcqSQLiteAdapter.close();

        TextView question_content = (TextView)rootView.findViewById(R.id.mcq_value);
        question_content.setText(mcq.getName());

        timer_mcq = (TextView)relativ_subHeader.findViewById(R.id.timer_mcq);
        long mcq_duration_mill = TimeUnit.MINUTES.toMillis( Long.valueOf(mcq.getCountdown()));
        final CounterMCQDuration timer = new CounterMCQDuration(mcq_duration_mill,1000,mcq.getCountdown());
        timer.start();
        return rootView;
    }

    public class CounterMCQDuration extends CountDownTimer {

        int duration;

        public CounterMCQDuration(long millisInFuture, long countDownInterval, int duration) {
            super(millisInFuture, countDownInterval);
            this.duration = duration;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis )));
            timer_mcq.setText(hms);
        }

        @Override
        public void onFinish() {
            Button previous_question = (Button) getActivity().findViewById(R.id.previous_question);
            previous_question.setVisibility(previous_question.INVISIBLE);
            TextView textViewNext = (TextView) getActivity().findViewById(R.id.next_question);
            textViewNext.setText("Terminer");
        }
    }
}
