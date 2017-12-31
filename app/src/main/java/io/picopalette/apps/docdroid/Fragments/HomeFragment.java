package io.picopalette.apps.docdroid.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import io.picopalette.apps.docdroid.R;

/**
 * Created by Aswin Sundar on 31-12-2017.
 */

public class HomeFragment extends Fragment {

    private LinearLayout heartpainLayout;
    private LinearLayout accidentLayout;

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
                mB.setView(alertView);
                final AlertDialog dialog = mB.create();
                dialog.show();
                return true;
            }
        });
        accidentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder mB = new AlertDialog.Builder(getContext());
                View alertView = inflater.inflate(R.layout.dialog_accident,null);
                mB.setView(alertView);
                final AlertDialog dialog = mB.create();
                dialog.show();
                return true;
            }
        });
        return view;
    }
}
