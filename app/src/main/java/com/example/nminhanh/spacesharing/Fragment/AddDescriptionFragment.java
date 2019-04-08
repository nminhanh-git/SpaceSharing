package com.example.nminhanh.spacesharing.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nminhanh.spacesharing.Model.Space;
import com.example.nminhanh.spacesharing.R;
import com.example.nminhanh.spacesharing.stepContinueListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDescriptionFragment extends Fragment implements stepContinueListener {
    public AddDescriptionFragment() {
        // Required empty public constructor
    }

    public interface DescriptionReceiver {
        public void onDescriptionReceived(double size, double price, String des);
    }

    DescriptionReceiver receiver;
    View view;

    EditText mEditTextSize;
    EditText mEditTextPrice;
    EditText mEditTextDescription;

    @Override
    public void onAttach(Context context) {
        receiver = (DescriptionReceiver) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_description, container, false);
        initialize();
        return view;
    }

    private void initialize() {
        mEditTextSize = view.findViewById(R.id.add_description_edit_text_size);
        mEditTextPrice = view.findViewById(R.id.add_description_price);
        mEditTextDescription = view.findViewById(R.id.add_description_des);
    }


    @Override
    public void onContinue() {
        String size = mEditTextSize.getText().toString();
        String price = mEditTextPrice.getText().toString();
        String des = mEditTextDescription.getText().toString();

        receiver.onDescriptionReceived(Double.valueOf(size), Double.valueOf(price), des);
    }
}
