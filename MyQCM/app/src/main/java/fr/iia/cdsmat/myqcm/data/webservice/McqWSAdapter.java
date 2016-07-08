package fr.iia.cdsmat.myqcm.data.webservice;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.data.AnswerSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.CategorySQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.McqSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.QuestionSQLiteAdapter;
import fr.iia.cdsmat.myqcm.entity.Answer;
import fr.iia.cdsmat.myqcm.entity.Category;
import fr.iia.cdsmat.myqcm.entity.Mcq;
import fr.iia.cdsmat.myqcm.entity.Question;

/**
 * Class to manage Mcq with webservice
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 29/05/2016
 */
public class McqWSAdapter {
    String response;
    ArrayList<Answer> answersFlux = new ArrayList<Answer>();
    ArrayList<Question> questionsFlux = new ArrayList<Question>();
    Context context;
    McqSQLiteAdapter mcqSQLiteAdapter;
    QuestionSQLiteAdapter questionSQLiteAdapter;
    AnswerSQLiteAdapter answerSQLiteAdapter;
    CategorySQLiteAdapter categorySQLiteAdapter;

    /**
     * McqWSAdapter's constructor
     * @param context
     */
    public McqWSAdapter(Context context) {
        this.context = context;
    }

    /**
     * Get json flow Mcq to update and get if connection is available
     * @param userIdServer
     * @param categoryIdServer
     * @param url
     */
    public void getMcqRequest (Integer userIdServer,final Integer categoryIdServer ,String url){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(MyQCMConstants.CONST_CONNECT_TIMEOUT);
        asyncHttpClient.setTimeout(MyQCMConstants.CONST_SET_TIMEOUT);
        RequestParams params = new RequestParams();
        params.put(MyQCMConstants.CONST_VALUE_USERID, userIdServer);
        params.put(MyQCMConstants.CONST_VALUE_CATEGORYID,categoryIdServer);

        asyncHttpClient.post(url + "." + MyQCMConstants.CONST_FLOW_FORMAT, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;
                System.out.println("flux mcq = " + response);
                ArrayList<Mcq> mcqs = responseToList(response);
                ManageMcqDB(mcqs,categoryIdServer);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = responseString;
                System.out.println("On failure");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                String str = null;
                try {
                    str = new String(responseBytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("On failure = " + str);
                response = "false";
            }
        });
    }

    /**
     * Compare data in database with flow and make modification if needed
     * @param response
     * @param categoryIdServer
     */
    public void ManageMcqDB (ArrayList<Mcq> response,int categoryIdServer){
        if (response.isEmpty() == false) {
            // get category list in Flow and add on a listView
            ArrayList<Mcq> list = response;
            ArrayList<String> listResult = null;

            // Call the AsyncTask to add Category on the DB and returns result list
            try {
                listResult = new ManageDBMcqAsyncTask(categoryIdServer,list).execute().get();
                System.out.println(" list result "+ listResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Convert Json to list Item
     * @param response
     * @return Mcq list
     */
    public ArrayList<Mcq> responseToList(String response) {
        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Mcq>>(){}.getType();

        ArrayList<Mcq> mcqs = new ArrayList<Mcq>();
        mcqs = (ArrayList<Mcq>) gson.fromJson(response, collectionType);

        return mcqs;
    }

    /**
     * Inner AsyncTask class to manage data with Webservice
     */
    public class ManageDBMcqAsyncTask extends AsyncTask<Void,Void,ArrayList<String>> {
        int numCategory;
        ArrayList<Mcq> mcqs;

        /**
         * ManageDBMcqAsyncTask's constructor
         * @param numCategory
         * @param mcqs
         */
        public ManageDBMcqAsyncTask(int numCategory, ArrayList<Mcq> mcqs) {

            this.numCategory = numCategory;
            this.mcqs = mcqs;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {

            // Create the different Adapter , Answer and question to delete
            categorySQLiteAdapter = new CategorySQLiteAdapter(context);
            answerSQLiteAdapter = new AnswerSQLiteAdapter(context);
            questionSQLiteAdapter = new QuestionSQLiteAdapter(context);
            mcqSQLiteAdapter = new McqSQLiteAdapter(context);

            categorySQLiteAdapter.open();
            Category category = categorySQLiteAdapter.getCategoryByIdServer(numCategory);
            categorySQLiteAdapter.close();
            ArrayList<String> results = new ArrayList<>();
            mcqSQLiteAdapter.open();
            ArrayList<Mcq> mcqsDB = mcqSQLiteAdapter.getAllMcq();

            for(Mcq mcq : mcqs)
            {
                mcq.setCategory(category);
                Mcq tempMcq ;
                //Try to find a Mcq with this id_server
                tempMcq = mcqSQLiteAdapter.getMcqByIdServer(mcq.getIdServer());
                System.out.println("tempMcq = " + tempMcq);

                //If Categ not exist on Mobile DB
                if(tempMcq == null)
                {
                    //Add mcq on the DB
                    long result = mcqSQLiteAdapter.insert(mcq);
                    results.add(String.valueOf(result));
                }
                else
                {
                    if (mcq.getUpdatedAt().compareTo(tempMcq.getUpdatedAt()) > 0) {
                        long result = mcqSQLiteAdapter.update(mcq);
                        results.add(String.valueOf(result));
                    }
                }
                ArrayList<String> resultsQuestion = ManaqeQuestionsMcq(mcq);
            }
            //delete check is exist on the DB
            if(mcqsDB != null) {
                for (Mcq mcqDB : mcqsDB) {
                    Boolean isExist = false;
                    for (Mcq mcq : mcqs) {
                        if (mcq.getCategory().getIdServer() == mcqDB.getCategory().getIdServer()) {
                            if (mcq.getIdServer() == mcqDB.getIdServer()) {
                                isExist = true;
                            }
                        } else {
                            isExist = true;
                            System.out.println("Not the same Categ");}

                    }
                    if (isExist == false) {
                        long result = mcqSQLiteAdapter.delete(mcqDB);
                    }

                }
            }
            mcqSQLiteAdapter.close();
            questionSQLiteAdapter.open();
            ArrayList<Question> questionsDB = questionSQLiteAdapter.getAllQuestion();
            // delete question if not exist on the flow
            if(questionsDB != null) {
                for (Question questionDB : questionsDB) {
                    Boolean isExist = false;
                    for (Question question : questionsFlux) {
                        if (questionDB.getIdServer() == questionDB.getIdServer()) {
                            isExist = true;
                        }
                    }

                    if (isExist == false) {
                        long result = questionSQLiteAdapter.delete(questionDB);
                    }
                }
            }
            questionSQLiteAdapter.close();
            answerSQLiteAdapter.open();
            ArrayList<Answer> answersDB = answerSQLiteAdapter.getAllAnswer();
            // delete answer if not exist on the flow
            if(answersDB != null) {
                for (Answer answerDB : answersDB) {
                    Boolean isExist = false;
                    for (Answer answer : answersFlux) {
                        if (answer.getIdServer() == answerDB.getIdServer()) {
                            isExist = true;
                        }
                    }

                    if (isExist == false) {
                        long result = answerSQLiteAdapter.delete(answerDB);
                    }
                }
            }
            answerSQLiteAdapter.close();

            return null;
        }

        /**
         * Manage Question on the DB to add and update
         * @param mcq
         * @return List of string
         */
        protected ArrayList<String> ManaqeQuestionsMcq(Mcq mcq){
            questionSQLiteAdapter = new QuestionSQLiteAdapter(context);
            questionSQLiteAdapter.open();
            ArrayList<String> results = new ArrayList<String>() ;
            ArrayList<String> resultsAnswers;

            // compare the mcq question on questions on the DB
            for (Question question : mcq.getQuestions()) {
                question.setMcq(mcq);
                Question tempQuestion ;

                //question in DB with the same ID_SERVER
                tempQuestion = questionSQLiteAdapter.getQuestionByIdServer(question.getIdServer());

                if (tempQuestion == null) {
                    //Add question on the DB
                    long result = questionSQLiteAdapter.insert(question);
                    String resultString = String.valueOf(result);
                    System.out.println("result = " + resultString);

                    if (resultString == null) {
                        results.add(resultString);
                    } else {
                        System.out.println("Add question is null");
                    }
                } else {
                    // if question already exist on DB
                    System.out.println("Update des éléments");
                    if (question.getUpdatedAt().compareTo(tempQuestion.getUpdatedAt()) > 0) {
                        long result = questionSQLiteAdapter.update(question);
                        String resultString = String.valueOf(result);
                        System.out.println("Update question = " + resultString);
                        results.add(resultString);
                    }
                }
                if(question != null) {
                    System.out.println("Question before ad to flux = " + question);
                    questionsFlux.add(question);
                }
                // Manage question to
                resultsAnswers = ManageAnswersQuestion(question);
                System.out.println("Result Answers = " + resultsAnswers);
            }

            questionSQLiteAdapter.close();
            return results;
        }

        /**
         * Get question's answers list
         * @param question
         * @return String list
         */
        protected ArrayList<String> ManageAnswersQuestion(Question question) {
            answerSQLiteAdapter = new AnswerSQLiteAdapter(context);
            answerSQLiteAdapter.open();
            ArrayList<Answer> answers ;
            ArrayList<String> results = null ;

            answers = question.getAnswers();

            for(Answer answer : answers)
            {
                answer.setQuestion(question);
                Answer tempAnswer;

                tempAnswer = answerSQLiteAdapter.getAnswerByIdServer(answer.getIdServer());

                if(tempAnswer == null)
                {
                    //Add category on the DB
                    long result = answerSQLiteAdapter.insert(answer);
                    System.out.println("Add answer to DB  = " + result);
                    //results.add(String.valueOf(result));
                }
                else {
                    System.out.println("Update des éléments");
                    if (answer.getUpdatedAt().compareTo(tempAnswer.getUpdatedAt()) > 0) {
                        long result = answerSQLiteAdapter.update(answer);
                        System.out.println("Update answer to DB  = " + result);
                        //results.add(String.valueOf(result));
                    }
                }
                answersFlux.add(answer);
            }

            answerSQLiteAdapter.close();
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
        }
    }

}
