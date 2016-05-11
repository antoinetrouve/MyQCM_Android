package fr.iia.cdsmat.myqcm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.entity.Category;
import fr.iia.cdsmat.myqcm.entity.Mcq;
import fr.iia.cdsmat.myqcm.view.mcq.McqActivity;
import fr.iia.cdsmat.myqcm.view.menu.MenuActivity;

/**
 * Class to manage view of list Mcq
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class McqFragmentList extends ListFragment{


    public McqFragmentList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragmentlist_mcq, container, false);
        String name = getArguments().getString("name");
        Category category = new Category(name);
        ArrayList<Category> list = new ArrayList<Category>();
        list.add(category);
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(
                getActivity(),
                R.layout.fragmentrow_mcq,
                R.id.mcq_textViewRow,
                list);

        //create list datasource
/*        ArrayList<Mcq> list = new ArrayList<Mcq>();
        Date date = new Date();
        Category category = new Category(1,2,"ObjectiveC",date);
        Category category2 = new Category(2,3,"WindowsPhone",date);
        Category category3 = new Category(3,4,"Android",date);
        Mcq mcq = new Mcq(1,2,"Lancer un projet",true,60, date, date,date,category);
        Mcq mcq2 = new Mcq(2,3,"Les vues",true,60, date, date,date,category2);
        Mcq mcq3 = new Mcq(3,4,"Architecture du projet",true,60, date, date,date,category3);
        list.add(mcq);
        list.add(mcq2);
        list.add(mcq3);*/

        //Create Adapter
        /*ArrayAdapter<Mcq> adapter = new ArrayAdapter<Mcq>(
                getActivity(),
                R.layout.fragmentrow_mcq,
                R.id.mcq_textViewRow,
                list);*/
        setListAdapter(adapter);
        //Retain listfragment instance across configuration changes
        setRetainInstance(true);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        /*ViewGroup viewGroup = (ViewGroup)view;
        TextView textView = (TextView)viewGroup.findViewById(R.id.mcq_textViewRow);
        Toast.makeText(getActivity(), textView.getText().toString(), Toast.LENGTH_SHORT).show();*/
        //super.onListItemClick(l, v, position, id);
        //Return to Login Activity
        Intent intent = new Intent(getActivity(),McqActivity.class);
        startActivity(intent);
    }
}
