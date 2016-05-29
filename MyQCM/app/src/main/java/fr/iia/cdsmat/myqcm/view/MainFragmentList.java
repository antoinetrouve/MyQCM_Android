package fr.iia.cdsmat.myqcm.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.data.CategorySQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.CategoryWSAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.ConnectionWSAdapter;
import fr.iia.cdsmat.myqcm.entity.Category;

/**
 * Class managing Category list view
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class MainFragmentList extends ListFragment {
    private Boolean isServerReachable;

    public MainFragmentList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);


        /*isServerReachable = isURLReachable(this.getContext(), MyQCMConstants.CONST_URL);
        if(isServerReachable)
        {*/
            FlowCategoryAsyncTask asyncTask = new FlowCategoryAsyncTask();
            asyncTask.execute();
        /*}*/

        // get the list of Categ in the Web server
        final CategoryWSAdapter categoryWSAdapter = new CategoryWSAdapter(getActivity().getBaseContext());
        final CategorySQLiteAdapter categorySQLiteAdapter = new CategorySQLiteAdapter(getActivity().getApplicationContext());

        // open DB to get the list Categ on DB
        categorySQLiteAdapter.open();
        ArrayList<Category> categories = categorySQLiteAdapter.getAllCategory();
        categorySQLiteAdapter.close();

        if(categories != null) {
            // Create Array Adapter to set the CategoriesDB on the fragment list and in TextView
            ArrayAdapter<Category> arrayAdapter = new ArrayAdapter<Category>(
                    getActivity(),
                    R.layout.fragmentrow_main,
                    R.id.main_textViewRow,
                    categories);
            setListAdapter(arrayAdapter);
        }
        categoryWSAdapter.getCategoryRequest(1,
                MyQCMConstants.CONST_IPSERVER + MyQCMConstants.CONST_URL_BASE + MyQCMConstants.CONST_URL_USERCATEGORIES);
        setRetainInstance(true);

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Event called when Item is selected on list
     *
     * @param l
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        ViewGroup viewGroup = (ViewGroup) view;
        TextView textView = (TextView) viewGroup.findViewById(R.id.main_textViewRow);
        McqFragmentList fragment = new McqFragmentList();

        /**
         * TODO : Get selectedItem value
         * TODO : Send value selected
         */

        //create Bundle to send information and set Argument into fragment
        Bundle categoryBundle = new Bundle();
        categoryBundle.putString("name", textView.getText().toString());
        fragment.setArguments(categoryBundle);

        //Set Fragment list mcq
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public class FlowCategoryAsyncTask extends AsyncTask<Integer, Void, String> {

        public FlowCategoryAsyncTask() {
            super();
        }

        @Override
        protected String doInBackground(Integer... params) {
            //create list datasource to test
            /*ArrayList<Category> list = new ArrayList<Category>();
            Date date = new Date();
            Category category = new Category(1, "ObjectiveC", date);
            Category category2 = new Category(2, "WindowsPhone", date);
            Category category3 = new Category(3, "Android", date);
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
            setRetainInstance(true);*/
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
