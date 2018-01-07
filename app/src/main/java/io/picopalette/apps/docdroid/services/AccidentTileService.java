package io.picopalette.apps.docdroid.services;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.service.quicksettings.TileService;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.picopalette.apps.docdroid.helpers.VolleySingleton;

/**
 * Created by ramkumar on 05/01/18.
 */

public class AccidentTileService extends TileService {
    private String sosSelfUrl;

    @Override
    public void onClick() {
        super.onClick();
    }
}
