package fr.iia.cdsmat.myqcm.view;


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

import fr.iia.cdsmat.myqcm.R;

/**
 * A simple {@link ListFragment} subclass.
 */
public class MainFragment extends ListFragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_main, container, false);

        //create list datasource
        ArrayList<String> list = new ArrayList<String>();
        list.add("category1");
        list.add("category2");
        list.add("category3");
        //Create Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.fragmentrow_main,
                R.id.main_textViewRow,
                list);
        setListAdapter(adapter);
        //Retain listfragment instance across configuratin changes
        setRetainInstance(true);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        ViewGroup viewGroup = (ViewGroup)view;
        TextView textView = (TextView)viewGroup.findViewById(R.id.main_textViewRow);
        Toast.makeText(getActivity(),textView.getText().toString(),Toast.LENGTH_SHORT).show();
        //super.onListItemClick(l, v, position, id);
    }
}
