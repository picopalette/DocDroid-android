package io.picopalette.apps.docdroid.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import java.lang.reflect.Method;
import java.security.PrivateKey;
import java.util.PriorityQueue;

import io.picopalette.apps.docdroid.R;

public class MedicalResponseActivity extends AppCompatActivity {

    private String emergencyType;
    private ProgressDialog progressDialog;
    public static final String mEmergencyReason = "Emergency Reason";
    private TextView textView;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_response);
        getSupportActionBar().setTitle(emergencyType);
    }

}
