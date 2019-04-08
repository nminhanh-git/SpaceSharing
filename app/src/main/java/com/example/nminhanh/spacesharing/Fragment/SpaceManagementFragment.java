package com.example.nminhanh.spacesharing.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nminhanh.spacesharing.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpaceManagementFragment extends Fragment {


    public SpaceManagementFragment() {
        // Required empty public constructor
    }


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_space_management, container, false);


        return view;
    }


}
