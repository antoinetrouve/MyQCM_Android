package fr.iia.cdsmat.myqcm.data.checkboxListManageAnswer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.entity.Answer;

/**
 * Class to manage answer list depending selected checkbox
 * Created by Antoine Trouvé on 06/07/2016.
 * antoinetrouve.france@gmail.com
 */
public class AnswerCheckBoxAdapter extends ArrayAdapter<Answer>{
    public ArrayList<Answer> answers;
    public Context context;

    /**
     * AnswerCheckBoxAdapter's constructor
     * @param answers
     * @param context
     * @param ViewResourceId
     */
    public AnswerCheckBoxAdapter(ArrayList<Answer> answers, Context context,int ViewResourceId) {
        super(context,ViewResourceId, answers);
        this.answers = answers;
        this.context = context;
    }

    private class AnswerHolder {
        TextView textview_value_answer;
        CheckBox checkbox_item_answer;
    }

    /**
     * Get view for selected question and
     * @param position
     * @param convertView
     * @param parent
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AnswerHolder answerHolder =  null;
        Log.v("convertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.row_fragment_list_answer, null);

            //List element
            answerHolder = new AnswerHolder();
            answerHolder.textview_value_answer = (TextView) convertView.findViewById(R.id.textview_value_answer);
            answerHolder.checkbox_item_answer = (CheckBox) convertView.findViewById(R.id.checkbox_item_answer);
            convertView.setTag(answerHolder);

            answerHolder.checkbox_item_answer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    TextView textView = (TextView) v;
                    Answer answer = (Answer) cb.getTag();
                    answer.setSelected(cb.isChecked());
                }
            });
        }
        else {
            answerHolder = (AnswerHolder) convertView.getTag();
        }

        Answer answer = answers.get(position);
        answerHolder.textview_value_answer.setText(answer.getValue());
        answerHolder.checkbox_item_answer.setChecked(answer.isSelected());
        answerHolder.checkbox_item_answer.setTag(answer);

        return convertView;
    }
}
