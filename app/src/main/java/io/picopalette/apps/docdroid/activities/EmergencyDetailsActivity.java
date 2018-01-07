package io.picopalette.apps.docdroid.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.picopalette.apps.docdroid.R;

/**
 * Created by ramkumar on 05/01/18.
 */

public class EmergencyDetailsActivity extends AppCompatActivity {

    private ImageView mProblemImgIV;
    private TextView mProblemTextTV;
    private TextView mProblemStatusTV;
    private TextView mLocationTV;
    private TextView mTimeTV;
    private TextView mDateTV;
    private TextView mHospitalNameTV;
    private TextView mHospitalAddrTV;
    private TextView mDriverNameTV;
    private TextView mDriverDetailsTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        mProblemImgIV = findViewById(R.id.emerDetails_ProblemImg);
        mProblemTextTV = findViewById(R.id.emerDetails_ProblemText);
        mProblemStatusTV = findViewById(R.id.emerDetails_ProblemStatus);
        mLocationTV = findViewById(R.id.emerDetails_Location);
        mTimeTV = findViewById(R.id.emerDetails_Time);
        mDateTV = findViewById(R.id.emerDetails_Date);
        mHospitalNameTV = findViewById(R.id.emerDetails_hospiName);
        mHospitalAddrTV = findViewById(R.id.emerDetails_hospidetails);
        mDriverNameTV = findViewById(R.id.emerDetails_DriverName);
        mDriverDetailsTV = findViewById(R.id.emerDetails_AmbulanceDetails);


    }
}
