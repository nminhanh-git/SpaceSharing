package com.example.nminhanh.spacesharing;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ImageView mImageViewLogo;
    ImageButton mBtnBack;

    ImageView mImageSignUp;

    Button mBtnContinue;

    EditText mEditPhone;
    String phone;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeView();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    phone = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mEditPhone.getText().toString().isEmpty()) {
                    phone = mEditPhone.getText().toString();
                    if (phone.charAt(0) != '0' || phone.length() != 10) {
                        mEditPhone.setError("Bạn nhập số điện thoại chưa đúng định dạng");
                    } else {
                        mEditPhone.setError(null);
                    }
                }
            }
        });

        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phone.isEmpty() && mEditPhone.getError() == null) {
                    verifyNumber(phone);
                } else {
                    if (mEditPhone.getText().toString().isEmpty()) {
                        mEditPhone.setError("Bạn chưa nhập số điện thoại");
                    }
                }
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void verifyNumber(String phone) {
    }

    private void initializeView() {
        mToolbar = findViewById(R.id.sign_up_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mImageViewLogo = mToolbar.findViewById(R.id.sign_up_logo);
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.logo)
                .into(mImageViewLogo);
        mBtnBack = findViewById(R.id.sign_up_btn_back);

        mEditPhone = findViewById(R.id.sign_up_edit_text_phone);
        mBtnContinue = findViewById(R.id.sign_up_btn_continue);
        mImageSignUp = findViewById(R.id.sign_up_image_view);
        Glide.with(this).load(R.drawable.sign_up_image).into(mImageSignUp);
    }
}
