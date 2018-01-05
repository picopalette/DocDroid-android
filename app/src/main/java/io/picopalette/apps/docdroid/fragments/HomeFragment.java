package io.picopalette.apps.docdroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.picopalette.apps.docdroid.activities.MedicalResponseActivity;
import io.picopalette.apps.docdroid.R;

/**
 * Created by Aswin Sundar on 31-12-2017.
 */

public class HomeFragment extends Fragment {

    private LinearLayout heartpainLayout;
    private LinearLayout accidentLayout;

    public static final String mEmergencyReason = "Emergency Reason";

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        heartpainLayout = view.findViewById(R.id.reportHeartPainLayout);
        accidentLayout = view.findViewById(R.id.reportAccidentLayout);
        heartpainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder mB = new AlertDialog.Builder(getContext());
                View alertView = inflater.inflate(R.layout.dialog_heartpain,null);
                Button yesToAmb = alertView.findViewById(R.id.getMeAmbForHeart);
                TextView yesToAmbForElse = alertView.findViewById(R.id.getAmbForElseHeart);
                mB.setView(alertView);
                final AlertDialog dialog = mB.create();
                dialog.show();
                yesToAmb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),"Message Sent...Medical Assistance will be initiated",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                yesToAmbForElse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), MedicalResponseActivity.class);
                        intent.putExtra(mEmergencyReason,"Heart-Pain");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
        accidentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder mB = new AlertDialog.Builder(getContext());
                View alertView = inflater.inflate(R.layout.dialog_accident,null);
                Button getAmb = alertView.findViewById(R.id.getAmbForAccident);
                mB.setView(alertView);
                final AlertDialog dialog = mB.create();
                dialog.show();
                getAmb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), MedicalResponseActivity.class);
                        intent.putExtra(mEmergencyReason,"Accident");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
        return view;
    }
}
