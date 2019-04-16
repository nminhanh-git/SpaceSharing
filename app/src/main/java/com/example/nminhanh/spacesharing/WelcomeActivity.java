package com.example.nminhanh.spacesharing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class WelcomeActivity extends AppCompatActivity {
    Button mBtnSignIn;
    Button mBtnSignUp;
    Button mBtnFacebook;
    TextView mTextViewNoSignIn;
    ImageView mImageViewWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initialize();

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        mTextViewNoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialize() {
        mBtnSignIn = findViewById(R.id.btn_welcome_sign_in);
        mBtnSignUp = findViewById(R.id.btn_welcome_sign_up);
        mBtnFacebook = findViewById(R.id.welcome_fb_button);
        mImageViewWelcome = findViewById(R.id.image_view_welcome);
        mTextViewNoSignIn = findViewById(R.id.welcome_text_view_no_sign_in);
        Glide.with(this).load(R.drawable.welcome_image).into(mImageViewWelcome);
    }
}
