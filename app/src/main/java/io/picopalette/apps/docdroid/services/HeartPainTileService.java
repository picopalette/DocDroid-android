package io.picopalette.apps.docdroid.services;

import android.content.DialogInterface;
import android.content.Intent;
import android.service.quicksettings.TileService;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.Toast;

import io.picopalette.apps.docdroid.activities.EmergencyDetailsActivity;
import io.picopalette.apps.docdroid.activities.MedicalResponseActivity;

/**
 * Created by ramkumar on 05/01/18.
 */

public class HeartPainTileService extends TileService {
    @Override
    public void onClick() {
        super.onClick();
        //report SOS
        Intent intent = new Intent(getApplicationContext(),
                EmergencyDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityAndCollapse(intent);
    }
}
