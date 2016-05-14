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

        //Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragmentlist_mcq, container, false);

        /**
         * TODO : Get Argument
         * TODO : Create Category object with GetCategoryByName(String name) -> CategorySQLiteAdapter
         * TODO : Get Mcq list of this category with McqByCategoryId(int id) -> McqSQLiteAdapter
         * TODO : Add Mcq list in an arrayAdpater to show into the view
         */

        // Get Argument and create object
        String name = getArguments().getString("name");
        Category category = new Category(name);

        ArrayList<Category> list = new ArrayList<Category>();
        list.add(category);

        //Create Adapter
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(
                getActivity(),
                R.layout.fragmentrow_mcq,
                R.id.mcq_textViewRow,
                list);

        setListAdapter(adapter);
        //Retain listfragment instance across configuration changes
        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),McqActivity.class);
        startActivity(intent);
    }
}
