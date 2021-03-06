package com.example.nminhanh.spacesharing.Fragment.MainPages;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nminhanh.spacesharing.MainActivity;
import com.example.nminhanh.spacesharing.R;
import com.example.nminhanh.spacesharing.SignInActivity;
import com.example.nminhanh.spacesharing.UserInfoActivity;
import com.example.nminhanh.spacesharing.Utils.FacebookConnectUtils;
import com.example.nminhanh.spacesharing.WelcomeActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.Arrays;

import javax.security.auth.callback.Callback;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private static final String TAG = "MinhAnh:AccountFragment";
    private static final int REQUEST_EDIT_PROFILE = 1;

    public AccountFragment() {
        // Required empty public constructor
    }

    View view;
    CircleImageView mImageViewProfile;
    TextView mTextViewName;
    TextView mTextViewEmail;
    TextView mTextViewPhone;
    Button mBtnEditProfile;
    TextView mTextViewFacebookIntro;
    TextView mTextViewFacebookName;
    Button mBtnConnectFacebook;
    Button mBtnChangeLanguage;
    Button mBtnChangePassword;
    Button mBtnPolicy;
    Button mBtnRateApp;
    Button mBtnSignOut;
    RelativeLayout mLayoutRecommendSignIn;
    Button mBtnRecommnedSignIn;


    SignOutListener mSignOutListener;
    ShowFacebookLoadingListener mShowLoadingListener;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mCurrentUser;
    CallbackManager mFacebookCallbackManager;


    @Override
    public void onAttach(Context context) {
        mSignOutListener = (SignOutListener) context;
        mShowLoadingListener = (ShowFacebookLoadingListener) context;
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);
        intitializeView();

        mFacebookCallbackManager = CallbackManager.Factory.create();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();

        if (mCurrentUser != null) {
            mLayoutRecommendSignIn.setVisibility(View.GONE);
            updateUIWithUserInfo();
        } else {
            mLayoutRecommendSignIn.setVisibility(View.VISIBLE);
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
                    if (isLinkedWithFacebook()) {
                        LoginManager.getInstance().logOut();
                    }
                    mLayoutRecommendSignIn.setVisibility(View.VISIBLE);
                    mSignOutListener.onSignOut();
                }
            }
        });

        mBtnConnectFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLinkedWithFacebook()) {
                    showUnlinkFacebookDialog();
                } else {
                    mShowLoadingListener.onShowingFacebookLoading();

                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                    LoginManager.getInstance().logInWithReadPermissions(AccountFragment.this, Arrays.asList("email", "public_profile"));
                }
            }
        });

        mBtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editInfoIntent = new Intent(getActivity(), UserInfoActivity.class);
                editInfoIntent.putExtra("user name", mCurrentUser.getDisplayName());
                editInfoIntent.putExtra("user mail", mCurrentUser.getEmail());
                editInfoIntent.putExtra("user phone", mCurrentUser.getPhoneNumber());
                editInfoIntent.putExtra("provider", "account management");
                startActivityForResult(editInfoIntent, REQUEST_EDIT_PROFILE);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isLinkedWithFacebook()) {
            LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d(TAG, "registerCallback:onSuccess");
                    LinkWithFacebookAccount(loginResult.getAccessToken());

                }

                @Override
                public void onCancel() {
                    Log.d(TAG, "registerCallback:onCancel");
                    mShowLoadingListener.onHidingFacebookLoading();
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d(TAG, "registerCallback:onError", error);
                }
            });
        }
    }

    private void LinkWithFacebookAccount(AccessToken accessToken) {
        AuthCredential mFbCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mCurrentUser.linkWithCredential(mFbCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mShowLoadingListener.onHidingFacebookLoading();
                    Toast.makeText(getContext(), "Kết nối thành công!", Toast.LENGTH_SHORT).show();
                    updateUIWithUserInfo();
                } else {
                    Log.d(TAG, task.getException().getMessage());
                    mShowLoadingListener.onHidingFacebookLoading();
                    AlertDialog mFacebookErrorDialog = new AlertDialog.Builder(getContext())
                            .setIcon(R.drawable.facebook_logo_colored)
                            .setTitle("Lỗi khi liên kết tài khoản Facebook")
                            .setMessage("Tài khoản Facebook hiện tại đã liên kết với một ứng dụng khác, vui lòng đăng xuất bằng ứng dụng Facebook và thử lại bằng một tài khoản khác")
                            .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    mFacebookErrorDialog.show();
                }
            }
        });
    }

    private void showUnlinkFacebookDialog() {
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this.getContext())
                .setTitle("Huỷ liên kết với tài khoản Facebook")
                .setMessage("Bạn có thực sự muốn hủy liên kết với tài khoản Facebook " + mTextViewFacebookName.getText().toString() + " không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        mCurrentUser.unlink("facebook.com").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Hủy liên kết thành công", Toast.LENGTH_SHORT).show();
                                    updateUIWithUserInfo();
                                    LoginManager.getInstance().logOut();
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(this.getResources().getDrawable(R.drawable.facebook_logo_colored, null));
        AlertDialog mFacebookDialog = mDialogBuilder.create();
        mFacebookDialog.show();
    }

    private void updateUIWithUserInfo() {
        mTextViewName.setText(mCurrentUser.getDisplayName());
        mTextViewEmail.setText(mCurrentUser.getEmail());
        mTextViewPhone.setText(mCurrentUser.getPhoneNumber());
        if (mCurrentUser.getPhotoUrl() != null) {
            Glide.with(this).load(mCurrentUser.getPhotoUrl()).into(mImageViewProfile);
        }
        if (isLinkedWithFacebook()) {
            mTextViewFacebookIntro.setText("Tài khoản Facebook:");
            mTextViewFacebookName.setText(mCurrentUser.getDisplayName());
            mTextViewFacebookName.setVisibility(View.VISIBLE);
            mBtnConnectFacebook.setText("Hủy kết nối");
            mBtnConnectFacebook.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        } else {
            mTextViewFacebookIntro.setText(R.string.fragment_account_facebook_into_string);
            mTextViewFacebookName.setText("");
            mTextViewFacebookName.setVisibility(View.GONE);
            mBtnConnectFacebook.setText("Kết nối ngay");
            mBtnConnectFacebook.setTextColor(getResources().getColor(R.color.facebook_color, null));
        }
    }

    private boolean isLinkedWithFacebook() {
        for (UserInfo user : mCurrentUser.getProviderData()) {
            if (user.getProviderId().equals("facebook.com")) {
                return true;
            }
        }
        return false;
    }

    private void intitializeView() {
        mImageViewProfile = view.findViewById(R.id.account_image);
        mTextViewName = view.findViewById(R.id.account_text_view_name);
        mTextViewEmail = view.findViewById(R.id.account_text_view_email);
        mTextViewPhone = view.findViewById(R.id.account_text_view_phone);
        mBtnEditProfile = view.findViewById(R.id.account_button_edit_profile);

        mTextViewFacebookIntro = view.findViewById(R.id.account_facebook_text_view_intro);
        mTextViewFacebookName = view.findViewById(R.id.account_facebook_text_view_name);
        mBtnConnectFacebook = view.findViewById(R.id.account_button_connect_facebook);

        mBtnChangeLanguage = view.findViewById(R.id.account_button_language);
        mBtnChangePassword = view.findViewById(R.id.account_button_password);
        mBtnPolicy = view.findViewById(R.id.account_button_policy);
        mBtnRateApp = view.findViewById(R.id.account_button_rate);

        mBtnSignOut = view.findViewById(R.id.account_button_sign_out);
        mLayoutRecommendSignIn = view.findViewById(R.id.account_layout_recommend_sign_in);
        mBtnRecommnedSignIn = view.findViewById(R.id.account_button_sign_in);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_PROFILE) {
            if (resultCode == RESULT_OK) {
                updateUIWithUserInfo();
            }
        }
    }
}
