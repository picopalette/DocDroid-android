package io.picopalette.apps.docdroid.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.picopalette.apps.docdroid.R;

/**
 * Created by Aswin Sundar on 31-12-2017.
 */

public class MedHistoryFragment extends Fragment {

    public static MedHistoryFragment newInstance() {
        return new MedHistoryFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_med_history,container,false);
        return view;
    }
}
