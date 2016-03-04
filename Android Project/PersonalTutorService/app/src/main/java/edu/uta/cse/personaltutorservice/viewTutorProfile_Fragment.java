package edu.uta.cse.personaltutorservice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mousa Almotairi & Funda Karapinar on 3/3/2016.
 */
public class viewTutorProfile_Fragment extends Fragment {

    View rootView;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.viewtutorprofile_layout, container, false);

        return rootView;
    }
}
