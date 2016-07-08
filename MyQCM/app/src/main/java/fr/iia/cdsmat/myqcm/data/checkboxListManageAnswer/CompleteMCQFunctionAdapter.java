package fr.iia.cdsmat.myqcm.data.checkboxListManageAnswer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fr.iia.cdsmat.myqcm.entity.Answer;
import fr.iia.cdsmat.myqcm.entity.Question;

/**
 * Class to manage all parameters flow for mcq
 * Created by Antoine Trouve on 06/07/2016.
 * antoinetrouve.france@gmail.com
 */
public class CompleteMCQFunctionAdapter {

    /**
     * Deserialize json flow for question's list
     * @param response
     * @return Question list
     */
    public ArrayList<Question> responseToListQuestion(String response){
        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Question>>(){}.getType();

        ArrayList<Question> questions = new ArrayList<Question>();
        questions = (ArrayList<Question>) gson.fromJson(response, collectionType);

        return questions;
    }

    /**
     * Deserialize json flow for answer's list
     * @param response
     * @return Answer list
     */
    public ArrayList<Answer> responseToListAnswer(String response){
        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Answer>>(){}.getType();

        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers = (ArrayList<Answer>) gson.fromJson(response, collectionType);

        return answers;
    }

    /**
     * Get question from a selected position in list
     * @param questions
     * @param positionInList
     * @return Question
     */
    public Question questionShow (ArrayList<Question> questions, int positionInList){
        Question question = null;
        System.out.println("Nombre de question dans la liste =" +questions.size());
        if(questions != null){
            if(positionInList != -1){
                question = questions.get(positionInList);
            }
            else {
                System.out.println("questionShow : position = 0");
            }
        }
        else {
            System.out.println("questionShow : List of question is empty");
        }
        return question;
    }

    /**
     * Get answers from a question selected
     * @param answers
     * @param question
     * @return Answer list
     */
    public ArrayList<Answer> answersShow (ArrayList<Answer> answers, Question question){
        ArrayList<Answer> answerShow = new ArrayList<>();

        if(answers != null){
            if(question != null){
                for (Answer answer : answers){
                    System.out.println("Answer is selected = " + answer.isSelected());
                    System.out.println();
                    if(answer.getQuestion().getIdServer() == question.getIdServer()){
                        answerShow.add(answer);
                    }
                }
            }else {
                System.out.println("answersQuestion : Question is null");
            }
        }
        else {
            System.out.println("answersQuestion : List of answer is empty");
        }
        return answerShow;
    }

    /**
     * Serialize list questions into a json format
     * @param questions
     * @return questions list json
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
     * Serialize list answers to json flow
     * @param answers
     * @return answers json
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
