package fr.iia.cdsmat.myqcm.view.result;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.configuration.Utils;
import fr.iia.cdsmat.myqcm.data.QuestionSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.checkboxListManageAnswer.CompleteMCQFunctionAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.ResultWSAdapter;
import fr.iia.cdsmat.myqcm.entity.Answer;
import fr.iia.cdsmat.myqcm.entity.Question;
import fr.iia.cdsmat.myqcm.entity.Result;
import fr.iia.cdsmat.myqcm.view.menu.MenuActivity;

/**
 * Class managing result view
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class ResultActivity extends AppCompatActivity {
    ResultWSAdapter resultWSAdapter;
    CompleteMCQFunctionAdapter completeMCQFunctionAdapter;
    ArrayList<Answer> answers;
    ArrayList<Question> questions;
    Question ToGetMCQ;
    QuestionSQLiteAdapter questionSQLiteAdapter;
    Result result;
    ProgressDialog dialog;
    boolean isConnected;
    int id_user ;
    ArrayList<Integer> id_answers;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        // get list of answers
        String answersJson = intent.getStringExtra("answers");
        String questionsJson = intent.getStringExtra("questions");
        id_user = intent.getIntExtra("id_user", 0);
        id_answers = new ArrayList<>();

        resultWSAdapter = new ResultWSAdapter(this);
        completeMCQFunctionAdapter = new CompleteMCQFunctionAdapter();
        answers = completeMCQFunctionAdapter.responseToListAnswer(answersJson);
        questions = completeMCQFunctionAdapter.responseToListQuestion(questionsJson);
        ToGetMCQ = questions.get(0);
        System.out.println("question = " + ToGetMCQ.getName());
        System.out.println(answersJson);
        for (Answer answer : answers) {
            if (answer.isSelected() == true) {
                id_answers.add(answer.getIdServer());
                System.out.println(" id answer_server = " + answer.getIdServer());
            }
        }
        questionSQLiteAdapter = new QuestionSQLiteAdapter(this);
        questionSQLiteAdapter.open();
        Question tempquestion = questionSQLiteAdapter.getQuestionByIdServer(ToGetMCQ.getIdServer());
        questionSQLiteAdapter.close();
        result = new Result();
        result.setIdUser(id_user);
        result.setIdMcq(tempquestion.getMcq().getIdServer());
        result.setIdAnswers(id_answers);

        String resultJson = resultWSAdapter.resultToJSON(result);
        System.out.println("flux resultats envoyes = " + resultJson);
        isConnected = Utils.CheckInternetConnection(ResultActivity.this);
        final AlertDialog.Builder endQuestionnaire = new AlertDialog.Builder(ResultActivity.this);
        endQuestionnaire.setTitle("Synchronisation du résultat");


        if (isConnected == true) {
            dialog = new ProgressDialog(ResultActivity.this);
            dialog.setMessage("Envoi des résultats ...");
            dialog.setCancelable(false);
            dialog.setInverseBackgroundForced(false);
            dialog.show();
            resultWSAdapter.sendResultRequest(resultJson, MyQCMConstants.CONST_IPSERVER
                    + MyQCMConstants.CONST_URL_BASE
                    + MyQCMConstants.CONST_URL_SEND_RESULT,
                    new ResultWSAdapter.CallBack() {

                @Override
                public void methods(String response) {

                    dialog.hide();
                    System.out.println(" response = " + response);

                    if (response.equals("true")) {
                        endQuestionnaire.setMessage("vos réponses on été prises en compte. " +
                                "Merci de contacter votre administrateur pour connaitre votre score.");
                    } else {
                        endQuestionnaire.setMessage("Vos réponses n'ont pas été prises en compte.");
                    }
                    endQuestionnaire.setIcon(R.drawable.ic_menu_help);
                    endQuestionnaire.setPositiveButton("Accueil",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ResultActivity.this, MenuActivity.class);
                                    intent.putExtra("FirstConnection", false);
                                    intent.putExtra("UserIdServer", id_user);
                                    startActivity(intent);
                                }
                            }).show();
                }
            });
        } else {
            endQuestionnaire.setMessage("Attention vous ne disposez pas d'une connexion, vos réponses n'ont pas été prises en compte.");
            endQuestionnaire.setIcon(R.drawable.ic_menu_help);
            endQuestionnaire.setPositiveButton("Accueil",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ResultActivity.this, MenuActivity.class);
                            intent.putExtra("FirstConnection", false);
                            intent.putExtra("UserIdServer", id_user);
                            startActivity(intent);
                        }
                    }).show();
        }
    }
    // overrideBack button to prevent the user from leaving the questionnaire
    @Override
    public void onBackPressed () {
    }
}
