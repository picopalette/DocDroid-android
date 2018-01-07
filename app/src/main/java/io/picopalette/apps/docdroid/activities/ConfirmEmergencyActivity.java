package io.picopalette.apps.docdroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import io.picopalette.apps.docdroid.R;

public class ConfirmEmergencyActivity extends AppCompatActivity {

    private CardView mYESEmergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_emergency);

        mYESEmergency = findViewById(R.id.emergencyYES);

    }
}
