package com.example.nminhanh.spacesharing.Fragment.MainPages;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nminhanh.spacesharing.R;
import com.example.nminhanh.spacesharing.SignInActivity;
import com.example.nminhanh.spacesharing.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }

    View view;
    CircleImageView mImageViewProfile;
    TextView mTextViewName;
    TextView mTextViewEmail;
    TextView mTextViewPhone;
    Button mBtnEditProfile;
    Button mBtnConnectFacebook;
    Button mBtnChangeLanguage;
    Button mBtnChangePassword;
    Button mBtnPolicy;
    Button mBtnRateApp;
    Button mBtnSignOut;
    RelativeLayout mLayoutRecommendSignIn;
    Button mBtnRecommnedSignIn;

    SignOutListener mSignOutListener;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mCurrentUser;


    @Override
    public void onAttach(Context context) {
        mSignOutListener = (SignOutListener) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);
        intitializeView();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();


        if (mCurrentUser != null) {
            mLayoutRecommendSignIn.setVisibility(View.GONE);
        } else {
            mBtnRecommnedSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signInIntent = new Intent(getActivity(), WelcomeActivity.class);
                    startActivity(signInIntent);
                }
            });
        }

        mBtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentUser != null) {
                    mFirebaseAuth.signOut();
                    mLayoutRecommendSignIn.setVisibility(View.VISIBLE);
                    mSignOutListener.onSignOut();
                }
            }
        });
        return view;
    }

    private void intitializeView() {
        mImageViewProfile = view.findViewById(R.id.account_image);
        mTextViewName = view.findViewById(R.id.account_text_view_name);
        mTextViewEmail = view.findViewById(R.id.account_text_view_email);
        mTextViewPhone = view.findViewById(R.id.account_text_view_phone);
        mBtnEditProfile = view.findViewById(R.id.account_button_edit_profile);

        mBtnConnectFacebook = view.findViewById(R.id.account_button_connect_facebook);

        mBtnChangeLanguage = view.findViewById(R.id.account_button_language);
        mBtnChangePassword = view.findViewById(R.id.account_button_password);
        mBtnPolicy = view.findViewById(R.id.account_button_policy);
        mBtnRateApp = view.findViewById(R.id.account_button_rate);

        mBtnSignOut = view.findViewById(R.id.account_button_sign_out);
        mLayoutRecommendSignIn = view.findViewById(R.id.account_layout_recommend_sign_in);
        mBtnRecommnedSignIn = view.findViewById(R.id.account_button_sign_in);
    }

}
