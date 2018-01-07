package io.picopalette.apps.docdroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.picopalette.apps.docdroid.R;

public class BystanderAccidentActivity extends AppCompatActivity {

    private EditText mVictimID;
    private LinearLayout mSetID;
    private ImageView mProblemImg;
    private TextView mProblemText;
    private TextView mProblemStatus;
    private TextView mLocation;
    private TextView mTime;
    private TextView mDate;
    private TextView mHospitalName;
    private TextView mHospitalAddr;
    private TextView mDriverName;
    private TextView mDriverDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bystander_accident);

        mVictimID = findViewById(R.id.victimAadhar);
        mSetID = findViewById(R.id.setAadharID);
        mProblemImg = findViewById(R.id.problemImg);
        mProblemText = findViewById(R.id.problemText);
        mProblemStatus = findViewById(R.id.problemStatus);
        mLocation = findViewById(R.id.locationDetails);
        mTime = findViewById(R.id.timeOfEmergency);
        mDate = findViewById(R.id.dateOfEmergency);
        mHospitalName = findViewById(R.id.hospitalName);
        mHospitalAddr = findViewById(R.id.hospitalAddrAndContact);
        mDriverName = findViewById(R.id.ambulanceDriverName);
        mDriverDetails = findViewById(R.id.ambulanceDetails);
    }
}
