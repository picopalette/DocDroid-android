package io.picopalette.apps.docdroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class SignUpActivity extends AppCompatActivity {

    private EditText mBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText nameEditText = (EditText) findViewById(R.id.nameSignupEditText);
        final EditText phoneEditText = (EditText) findViewById(R.id.phoneSignupEditText);
        final EditText addressEditText = (EditText) findViewById(R.id.addressSignupEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordSignupEditText);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);

        mBlood = findViewById(R.id.bloodGroupEditText);
        final SharedPreferences sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String bloodGroup = mBlood.getText().toString();

                JSONObject signUpData = new JSONObject();
                try {
                    signUpData.put("name", name);
                    signUpData.put("phone", phone);
                    signUpData.put("address", address);
                    signUpData.put("password", password);
                    signUpData.put("blood_group", bloodGroup);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = sharedPreferences.getString("url", "") + "/api/signUpUser";

                JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, signUpData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject userObject = response;
                            Toast.makeText(getApplicationContext(), "Welcome " + userObject.getString("name"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
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
                VolleySingleton.getInstance(SignUpActivity.this).getRequestQueue().add(loginRequest);
            }
        });

    }
}
