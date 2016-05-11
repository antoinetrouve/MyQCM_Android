package fr.iia.cdsmat.myqcm.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

/**
 * Class managing Category list view
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class MainFragmentList extends ListFragment {


    public MainFragmentList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_main, container, false);

        //create list datasource
        ArrayList<Category> list = new ArrayList<Category>();
        Date date = new Date();
        Category category = new Category(1,2,"ObjectiveC",date);
        Category category2 = new Category(2,3,"WindowsPhone",date);
        Category category3 = new Category(3,4,"Android",date);
        list.add(category);
        list.add(category2);
        list.add(category3);

        //Create Adapter
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(
                getActivity(),
                R.layout.fragmentrow_main,
                R.id.main_textViewRow,
                list);
        setListAdapter(adapter);
        //Retain listfragment instance across configuration changes
        setRetainInstance(true);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        ViewGroup viewGroup = (ViewGroup)view;
        TextView textView = (TextView)viewGroup.findViewById(R.id.main_textViewRow);
        //Toast.makeText(getActivity(),textView.getText().toString(), Toast.LENGTH_SHORT).show();
        //super.onListItemClick(l, v, position, id);
        McqFragmentList fragment = new McqFragmentList();
        Bundle categoryBundle = new Bundle();
        categoryBundle.putString("name",textView.getText().toString());
        fragment.setArguments(categoryBundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
        fragmentTransaction.commit();
    }


}
