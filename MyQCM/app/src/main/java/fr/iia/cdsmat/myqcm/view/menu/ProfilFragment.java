package fr.iia.cdsmat.myqcm.view.menu;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fr.iia.cdsmat.myqcm.R;
import fr.iia.cdsmat.myqcm.configuration.MyQCMConstants;
import fr.iia.cdsmat.myqcm.data.UserSQLiteAdapter;
import fr.iia.cdsmat.myqcm.data.webservice.UserWSAdapter;
import fr.iia.cdsmat.myqcm.entity.User;

/**
 * Class managing Profil's fragment depending of Menu Activity
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class ProfilFragment extends Fragment {
    UserSQLiteAdapter userSQLiteAdapter;

    public ProfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflate the fragment layout file
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profil, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(fab.INVISIBLE);

        userSQLiteAdapter = new UserSQLiteAdapter(getActivity().getBaseContext());
        userSQLiteAdapter.open();
        ArrayList<User> users = new ArrayList<>();
        users = userSQLiteAdapter.getAllUser();
        if (users != null) {
            User userDB = users.get(0);
            System.out.println("Userid = " + userDB.getUsername());
            String username = userDB.getUsername();
            RelativeLayout relativeLayoutProfilHeader = (RelativeLayout) rootView.findViewById(R.id.profile_layout_header);

            //Header
            RelativeLayout relativeLayoutProfil = (RelativeLayout) relativeLayoutProfilHeader.findViewById(R.id.profile_layout);
            TextView textViewProfilName = (TextView) relativeLayoutProfil.findViewById(R.id.user_profile_name);
            textViewProfilName.setText(username);

            //Content profil
            LinearLayout linearLayoutContent = (LinearLayout) relativeLayoutProfilHeader.findViewById(R.id.profile_layout_content);
            TextView textViewProfilUsername = (TextView) linearLayoutContent.findViewById(R.id.user_profil_identifiant);
            textViewProfilUsername.setText(MyQCMConstants.CONST_PROFIL_USERNAME + " " + username);

            TextView textViewProfilEmail = (TextView) linearLayoutContent.findViewById(R.id.user_profil_email);
            textViewProfilEmail.setText(MyQCMConstants.CONST_PROFIL_EMAIL + " " + userDB.getEmail());

        }
        // Inflate the layout for this fragment
        return rootView;
    }

}
