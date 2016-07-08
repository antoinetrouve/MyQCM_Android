package fr.iia.cdsmat.myqcm.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.configuration.Utils;
import fr.iia.cdsmat.myqcm.data.McqSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.QuestionSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.McqWSAdapter;
import fr.iia.cdsmat.myqcm.entity.Mcq;
import fr.iia.cdsmat.myqcm.entity.Question;
import fr.iia.cdsmat.myqcm.view.questionnaire.QuestionnaireActivity;

/**
 * Class to manage view of list Mcq
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class McqFragmentList extends ListFragment{
    int idUser;
    int idCateg;
    ArrayList<Mcq> mcqs;
    boolean isConnected;

    public McqFragmentList() {
    }

    /**
     * Do something on create view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragmentlist_mcq, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(fab.VISIBLE);

        McqWSAdapter mcqWSAdapter = new McqWSAdapter(getActivity().getBaseContext());
        McqSQLiteAdapter mcqSQLiteAdapter = new McqSQLiteAdapter(getActivity().getBaseContext());


        idCateg = getArguments().getInt("id_categ");
        idUser = getArguments().getInt("id_user");
        System.out.println("ID_server de la catégorie est " + idCateg);
        // open DB to get the list mcq on DB
        mcqSQLiteAdapter.open();
        mcqs = mcqSQLiteAdapter.getAllMcqAvailable(idCateg);
        mcqSQLiteAdapter.close();

        if(mcqs != null) {
            ArrayAdapter<Mcq> arrayAdapter = new ArrayAdapter<Mcq>(
                    getActivity(),
                    R.layout.fragmentrow_mcq,
                    R.id.mcq_textViewRow,
                    mcqs);
            setListAdapter(arrayAdapter);
        }
        isConnected = Utils.CheckInternetConnection(getActivity().getBaseContext());
        if(isConnected == true) {
            mcqWSAdapter.getMcqRequest(idUser, idCateg, MyQCMConstants.CONST_IPSERVER
                    + MyQCMConstants.CONST_URL_BASE
                    + MyQCMConstants.CONST_URL_USERMCQS);
        }
        else {
            System.out.println("No connexion for get Mcq");
        }
        setRetainInstance(true);

        return rootView;
    }

    /**
     * Select a questions list depending mcq selected
     * @param l
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        ViewGroup viewGroup = (ViewGroup)view;
        int idMcq = mcqs.get(position).getIdServer();
        QuestionSQLiteAdapter questionSQLiteAdapter = new  QuestionSQLiteAdapter(getActivity());
        questionSQLiteAdapter.open();
        ArrayList<Question> questions = new ArrayList<>();
        questions = questionSQLiteAdapter.getAllQuestionByIdServerMCQ(idMcq);

        if (questions != null)
        {
            Intent intent = new Intent(getActivity().getBaseContext(), QuestionnaireActivity.class);
            intent.putExtra("id_mcq", idMcq);
            intent.putExtra("id_user",idUser);
            System.out.println("Id envoyer au questionnaire =" + idMcq);
            getActivity().startActivity(intent);
            startActivity(intent);
        }
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Choix du questionnaire");
            alertDialog.setMessage("Il n'y a pas de question liées à ce questionnaire." +
                    "Veuillez contacter votre administrateur.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}
