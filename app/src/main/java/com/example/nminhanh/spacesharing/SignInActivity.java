package com.example.nminhanh.spacesharing;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ImageView mImageViewSignIn;
    EditText mEditTextAccount;
    EditText mEditTextPassword;
    Button mButtonSignIn;
    ImageButton mImgButtonShowPass;
    TextView mTextViewforgetPassword;
    RelativeLayout mLayoutLoading;
    RelativeLayout mLayoutSignIn;
    ImageButton mBtnBack;
    ImageView mImageLogo;

    String userName = "";
    String password = "";
    boolean isShowingPassword = false;
    boolean isPhoneNumber = false;
    boolean isSignInBtnClicked = false;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initialize();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mEditTextAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    userName = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String res = mEditTextAccount.getText().toString();
                if (!res.equals("")) {
                    userName = res;
                    if (Character.isLetter(userName.charAt(0))) {
                        mEditTextPassword.setVisibility(View.VISIBLE);
                        if (mEditTextPassword.getError() == null) {
                            mImgButtonShowPass.setVisibility(View.VISIBLE);
                        }
                        mTextViewforgetPassword.setVisibility(View.VISIBLE);
                        if (!userName.contains("@")) {
                            mEditTextAccount.setError("Bạn nhập E-mail chưa đúng định dạng");
                        } else {
                            mEditTextAccount.setError(null);
                            isPhoneNumber = false;
                        }
                    } else if (Character.isDigit(userName.charAt(0))) {
                        mEditTextPassword.setVisibility(View.GONE);
                        mImgButtonShowPass.setVisibility(View.GONE);
                        mTextViewforgetPassword.setVisibility(View.INVISIBLE);
                        if (userName.length() != 10) {
                            mEditTextAccount.setError("Bạn nhập số điện thoại chưa đúng định dạng");
                        } else {
                            mEditTextAccount.setError(null);
                            isPhoneNumber = true;
                        }
                    }
                }
            }
        });

        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    password = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!password.isEmpty() && isSignInBtnClicked) {
                    mEditTextPassword.setError(null);
                    mImgButtonShowPass.setVisibility(View.VISIBLE);
                    isSignInBtnClicked = false;
                }
            }
        });

        mImgButtonShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowingPassword) {
                    mEditTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isShowingPassword = true;
                } else {
                    mEditTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isShowingPassword = false;
                }
                mEditTextPassword.setSelection(mEditTextPassword.length());
                Typeface mTypeface = ResourcesCompat.getFont(SignInActivity.this, R.font.roboto_reg);
                mEditTextPassword.setTypeface(mTypeface);
            }
        });

        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSignInBtnClicked = true;
                if (!(userName.isEmpty() || password.isEmpty())
                        && mEditTextAccount.getError() == null && mEditTextPassword.getError() == null) {
                    signIn();
                } else {
                    if (userName.isEmpty()) {
                        mEditTextAccount.setError("Bạn chưa nhập e-mail hoặc số điện thoại");
                    }
                    if (!isPhoneNumber && password.isEmpty()) {
                        mEditTextPassword.setError("Bạn chưa nhập mật khẩu");
                        mImgButtonShowPass.setVisibility(View.GONE);
                    }
                }
            }
        });

        mTextViewforgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initialize() {
        mToolbar = findViewById(R.id.sign_in_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mImageLogo = mToolbar.findViewById(R.id.sign_in_logo);
        Glide.with(this).asBitmap().load(R.drawable.logo).into(mImageLogo);

        mLayoutSignIn = findViewById(R.id.sign_in_layout);
        mImageViewSignIn = findViewById(R.id.image_view_sign_in);
        Glide.with(this).load(R.drawable.sign_in_image).into(mImageViewSignIn);
        mEditTextAccount = findViewById(R.id.edit_text_sign_in_account);
        mEditTextPassword = findViewById(R.id.edit_text_sign_in_pass);
        mButtonSignIn = findViewById(R.id.sign_in_btn_sign_in);
        mImgButtonShowPass = findViewById(R.id.sign_up_btn_show_hide_password);
        mTextViewforgetPassword = findViewById(R.id.sign_in_text_view_forgot_pass);
        mLayoutLoading = findViewById(R.id.sign_in_loading_layout);
        mBtnBack = findViewById(R.id.sign_in_btn_back);
    }

    private void signIn() {
        mLayoutLoading.setVisibility(View.VISIBLE);
        if (!isPhoneNumber) {
            mFirebaseAuth.signInWithEmailAndPassword(userName, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mLayoutLoading.setVisibility(View.GONE);
                                Intent goToMainActivityIntent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(goToMainActivityIntent);
                                finish();
                            } else {
                                mLayoutLoading.setVisibility(View.GONE);
                                Toast.makeText(SignInActivity.this, "Đăng nhập thất bại. Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {

        }
    }
}
