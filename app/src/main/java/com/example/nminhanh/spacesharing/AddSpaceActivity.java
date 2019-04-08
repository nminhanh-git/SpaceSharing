package com.example.nminhanh.spacesharing;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.example.nminhanh.spacesharing.Fragment.AddAddressFragment;
import com.example.nminhanh.spacesharing.Fragment.AddDescriptionFragment;
import com.example.nminhanh.spacesharing.Fragment.AddOtherFragment;
import com.example.nminhanh.spacesharing.Fragment.AddPagerAdapter;
import com.example.nminhanh.spacesharing.Model.Space;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.springindicator.SpringIndicator;

public class AddSpaceActivity extends AppCompatActivity implements AddAddressFragment.AddressReceiver, AddDescriptionFragment.DescriptionReceiver, AddOtherFragment.OtherReceiver {

    Toolbar mToolbar;
    Button mBtnCancel;
    Button mBtnContinue;

    TextView mTextviewNote;

    ViewPager mViewPagerAdd;
    SpringIndicator mIndicator;

    Space currentSpace;
    ArrayList<String> mImagePath;
    stepContinueListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_space);
        currentSpace = new Space();
        initialize();
    }

    private void initialize() {
        mToolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mBtnCancel = findViewById(R.id.add_btn_cancel);
        mBtnContinue = findViewById(R.id.add_btn_continue);

        mTextviewNote = findViewById(R.id.add_textview_note);
        String text = "Những mục có dấu <font color=#e83841>*</font> là những mục bắt buộc";
        mTextviewNote.setText(Html.fromHtml(text));

        mViewPagerAdd = findViewById(R.id.add_viewpager);
        List<Fragment> fragmentList = getFragmentList();
        AddPagerAdapter adapter = new AddPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPagerAdd.setAdapter(adapter);
        mIndicator = findViewById(R.id.add_indicator);
        mIndicator.setViewPager(mViewPagerAdd);

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (mViewPagerAdd.getCurrentItem()) {
                    case 0:
                        finish();
                    case 1:
                        mBtnCancel.setText("Hủy");
                        mBtnContinue.setText("Tiếp tục");
                        mViewPagerAdd.setCurrentItem(0);
                        break;
                    case 2:
                        mBtnCancel.setText("Trở về");
                        mBtnContinue.setText("Tiếp tục");
                        mViewPagerAdd.setCurrentItem(1);
                        break;
                }
            }
        });

        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener = (stepContinueListener) getSupportFragmentManager().getFragments().get(mViewPagerAdd.getCurrentItem());
                switch (mViewPagerAdd.getCurrentItem()) {
                    case 0:
                        mBtnCancel.setText("Trở về");
                        mBtnContinue.setText("Tiếp tục");
                        listener.onContinue();
                        mViewPagerAdd.setCurrentItem(1);
                        break;
                    case 1:
                        mBtnCancel.setText("Trở về");
                        mBtnContinue.setText("Lưu");
                        listener.onContinue();
                        mViewPagerAdd.setCurrentItem(2);
                        break;
                    case 2:
                        listener.onContinue();
                        setResult(RESULT_OK);
                        finish();
                        break;
                }
            }
        });
    }

    private List<Fragment> getFragmentList() {
        List<Fragment> list = new ArrayList<>();
        return list;
    }


    @Override
    public void onAddressReceived(String title, String addressNumber, String cityId, String districtId, String wardId, ArrayList<String> imagePath) {
        currentSpace.setTieuDe(title);
        currentSpace.setDiaChiPho(addressNumber);
        currentSpace.setThanhPhoId(cityId);
        currentSpace.setQuanId(districtId);
        currentSpace.setPhuongId(wardId);
        mImagePath = new ArrayList<>(imagePath);
    }

    @Override
    public void onDescriptionReceived(double size, double price, String des) {
        currentSpace.setDienTich(size);
        currentSpace.setGia(price);
        currentSpace.setMoTa(des);
    }


    @Override
    public void onOtherReceived(String type, String door, int bedRoom, int bathRoom, String detail) {
        currentSpace.setLoai(type);
        currentSpace.setHuongCua(door);
        currentSpace.setSoPhongVeSinh(bathRoom);
        currentSpace.setSoPhongNgu(bedRoom);
        currentSpace.setThongTinPhapLy(detail);
    }
}
