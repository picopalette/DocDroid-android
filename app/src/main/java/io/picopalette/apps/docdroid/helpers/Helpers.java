package io.picopalette.apps.docdroid.helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import io.picopalette.apps.docdroid.activities.LoginActivity;

/**
 * Created by ramkumar on 21/10/17.
 */

public class Helpers {
    public static boolean handleNetworkError(VolleyError error, Context context) {
        Log.e("Error", error.toString());
        if (error instanceof NoConnectionError || error.networkResponse == null) {
            Toast.makeText(context.getApplicationContext(), "can't connect to server", Toast.LENGTH_SHORT).show();
        } else if(error.networkResponse.statusCode == 401) {
            Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    public static SimpleDateFormat getDateFormatter() {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format;
    }
}
