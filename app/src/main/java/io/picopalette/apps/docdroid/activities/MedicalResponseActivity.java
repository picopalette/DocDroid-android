package io.picopalette.apps.docdroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.picopalette.apps.docdroid.R;

public class MedicalResponseActivity extends AppCompatActivity {

    private String emergencyType;
    public static final String mEmergencyReason = "Emergency Reason";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_response);

        Intent intent = getIntent();
        emergencyType = intent.getStringExtra(mEmergencyReason);

        getSupportActionBar().setTitle(emergencyType);
    }

}
