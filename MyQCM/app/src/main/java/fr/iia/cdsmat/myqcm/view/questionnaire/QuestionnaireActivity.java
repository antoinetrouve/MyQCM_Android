package fr.iia.cdsmat.myqcm.view.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.data.AnswerSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.QuestionSQLiteAdapter;
import fr.iia.cdsmat.myqcm.entity.Answer;
import fr.iia.cdsmat.myqcm.entity.Question;
import fr.iia.cdsmat.myqcm.entity.Result;

/**
 * Class to manage view linked to question fragment. Get question list
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 06/07/2016
 */
public class QuestionnaireActivity extends AppCompatActivity {

    QuestionSQLiteAdapter questionSQLiteAdapter;
    AnswerSQLiteAdapter answerSQLiteAdapter;
    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    String questionsJson;
    String answersJson;
    int questionsPositionList;
    int naviguationValue;

    /**
     * Call to do something when view are created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        // Get element pass in params
        Intent intent = getIntent();
        int id_mcq = intent.getIntExtra("id_mcq", 0);
        int id_user = intent.getIntExtra("id_user",0);

        // Get the list of Questions and linked answer transform to json
        questionSQLiteAdapter = new QuestionSQLiteAdapter(QuestionnaireActivity.this);
        answerSQLiteAdapter = new AnswerSQLiteAdapter(QuestionnaireActivity.this);
        questionSQLiteAdapter.open();
        questions = new ArrayList<>();
        questions = questionSQLiteAdapter.getAllQuestionByIdServerMCQ(id_mcq);
        questionsJson = listQuestionsToJSON(questions);
        System.out.println("Flux question : " + questionsJson);

        answers = new ArrayList<>();
        answerSQLiteAdapter.open();

        if (questions != null){
            for (Question question : questions) {
                ArrayList<Answer> tempList = answerSQLiteAdapter.getAllAnswerById_server_question(question.getIdServer());
                if(tempList != null)
                {
                    for (Answer answer : tempList) {
                        answer.setQuestion(question);
                    }
                    if (tempList.size() != 0) {
                        answers.addAll(tempList);
                    }
                }
            }
        }

        for (Answer answer : answers) {
            System.out.println("answers.getquestion = "+ answer.getQuestion().getName());
        }
        answersJson = listAnswersToJSON(answers);
        System.out.println("ANSWER json" + answersJson);

        // Question start in the item 0
        questionsPositionList = 0;

        // value of the button next = 1 & previous = 0
        naviguationValue = 1;

        // Add element on bundle
        Bundle bundleContent = new Bundle();
        bundleContent.putString("list_question" , questionsJson);
        bundleContent.putString("list_answer", answersJson);
        bundleContent.putInt("questions_position", questionsPositionList);
        bundleContent.putInt("navigation_value",naviguationValue);
        bundleContent.putInt("id_user", id_user);
        Bundle bundleSubHeader = new Bundle();
        bundleSubHeader.putInt("id_mcq", id_mcq);
        bundleSubHeader.putString("name_question", "BoB");

        // set the fragment initially for the sub Header with is bundle
        SubHeaderQuestionnaireFragment fragmentHeader = new SubHeaderQuestionnaireFragment();
        FragmentTransaction fragmentTransactionHeader =
                getSupportFragmentManager().beginTransaction();
        fragmentHeader.setArguments(bundleSubHeader);
        fragmentTransactionHeader.replace(R.id.header_questionnaire, fragmentHeader);
        fragmentTransactionHeader.commit();

        // set the fragment initially for the content of my questionnaire with is bundle
        ContentQuestionnaireFragment fragmentContent = new ContentQuestionnaireFragment();
        FragmentTransaction fragmentTransactionContent =
                getSupportFragmentManager().beginTransaction();
        fragmentContent.setArguments(bundleContent);
        fragmentTransactionContent.replace(R.id.content_questionnaire, fragmentContent);
        fragmentTransactionContent.commit();
    }

    /**
     * overrideBack button to prevent the user from leaving the questionnaire
     */
    @Override
    public void onBackPressed() {
    }

    /**
     * Serialize question list to json format
     * @param questions
     * @return
     */
    public String listQuestionsToJSON(ArrayList<Question> questions){
        String questionsJSON;

        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Question>>(){}.getType();

        questionsJSON = gson.toJson(questions, collectionType);

        return questionsJSON;
    }

    /**
     * Serialize answer list to json format
     * @param answers
     * @return
     */
    public String listAnswersToJSON(ArrayList<Answer> answers){
        String answersJSON;

        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Answer>>(){}.getType();

        answersJSON = gson.toJson(answers, collectionType);

        return answersJSON;
    }
}
