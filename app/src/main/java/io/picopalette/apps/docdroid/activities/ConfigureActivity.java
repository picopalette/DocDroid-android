package io.picopalette.apps.docdroid.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;


import io.picopalette.apps.docdroid.R;
import io.picopalette.apps.docdroid.helpers.PersistentCookieStore;
import io.picopalette.apps.docdroid.helpers.VolleySingleton;

public class ConfigureActivity extends AppCompatActivity {

    private ImageView send;
    private EditText ipET;
    private TextView ipTV;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog dialog;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(getApplicationContext()), CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(cookieManager);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        mSharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        proceed();
        send = (ImageView) findViewById(R.id.sendImage);
        ipET = (EditText) findViewById(R.id.ipEditText);
        ipTV = (TextView) findViewById(R.id.ipTextView);

        mEditor = mSharedPreferences.edit();

        ipTV.setText(mSharedPreferences.getString("url",""));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip;
                ip = ipET.getText().toString();
                if(ip.equals(""))
                {
                    Toast.makeText(ConfigureActivity.this, "IP cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ipTV.setText(ip);
                    mEditor.putString("url",ip);
                    ipET.setText("");
                    mEditor.apply();
                    proceed();
                }
            }
        });
    }

    private void proceed() {
        dialog = ProgressDialog.show(ConfigureActivity.this, "Connecting", "Please wait while we connect", true, false);
        String url = mSharedPreferences.getString("url", "") + "/api/getUserProfile";
        JsonObjectRequest checkRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        dialog.dismiss();
                        Log.d("Response", response.toString());
                        Intent intent = new Intent(ConfigureActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                dialog.dismiss();
                if(error.networkResponse == null) {
                    Toast.makeText(getApplicationContext(), "Check your IP", Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 401) {
                    try {
                        JSONObject response = new JSONObject(new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers)));
                        Log.d(String.valueOf(error.networkResponse.statusCode), response.getString("error"));
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } catch(UnsupportedEncodingException | JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        queue.add(checkRequest);
    }
}
