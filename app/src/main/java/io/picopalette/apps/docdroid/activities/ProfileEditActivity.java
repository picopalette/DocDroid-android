package io.picopalette.apps.docdroid.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.picopalette.apps.docdroid.R;
import io.picopalette.apps.docdroid.helpers.VolleySingleton;

public class ProfileEditActivity extends AppCompatActivity {


    private EditText mAadhar;
    private EditText mMedicalIssues;
    private Button mProfileSet;
    private CheckedTextView mDonateBlood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        final SharedPreferences sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mAadhar = findViewById(R.id.aadharEditText);
        mMedicalIssues = findViewById(R.id.medicalIssuesSignupEditText);
        mProfileSet = findViewById(R.id.setProfileButton);
        mDonateBlood = findViewById(R.id.donateBlood);

        mDonateBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDonateBlood.isChecked()) {
                    mDonateBlood.setChecked(false);
                    mDonateBlood.setCheckMarkDrawable(R.drawable.ic_uncheck_mark_button);
                } else {
                    mDonateBlood.setChecked(true);
                    mDonateBlood.setCheckMarkDrawable(R.drawable.ic_check_mark_button);
                }
            }
        });

        mProfileSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aadhar = mAadhar.getText().toString();
                String issues = mMedicalIssues.getText().toString();
                Boolean donate = mDonateBlood.isChecked();

                JSONObject signUpData = new JSONObject();
                try {

                    signUpData.put("aadhar", aadhar);
                    signUpData.put("issues", issues);
                    signUpData.put("blood_donate",donate);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = sharedPreferences.getString("url", "") + "/api/updateUserProfile";

                JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, signUpData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject userObject = response;
                            Toast.makeText(getApplicationContext(), userObject.getString("saved"), Toast.LENGTH_LONG).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(loginRequest);
            }
        });

    }
}
