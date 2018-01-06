package io.picopalette.apps.docdroid.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import io.picopalette.apps.docdroid.R;
import io.picopalette.apps.docdroid.helpers.VolleySingleton;

/**
 * Created by Aswin Sundar on 31-12-2017.
 */

public class ProfileFragment extends Fragment {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        final TextView nameTV = view.findViewById(R.id.profileName);
        final TextView numberTV = view.findViewById(R.id.profileNumber);
        final TextView bloodTV = view.findViewById(R.id.profileBlood);
        final CheckedTextView donateCTV = view.findViewById(R.id.profileDonate);
        final TextView addrTV = view.findViewById(R.id.profileAddress);
        final TextView aadharTV = view.findViewById(R.id.profileAadhar);
        final TextView issuesTV = view.findViewById(R.id.profileIssues);

        String url = sharedPreferences.getString("url", "") + "/api/getUserProfile";

        JsonObjectRequest profileRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try {
                            nameTV.setText(response.getString("name"));
                            numberTV.setText(response.getString("phone"));
                            bloodTV.setText(response.getString("blood_group"));
                            donateCTV.setChecked(response.getBoolean("blood_donate"));
                            addrTV.setText(response.getString("address"));
                            aadharTV.setText(response.getString("aadhar"));
                            issuesTV.setText(response.getString("issues"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                //Helpers.handleNetworkError(error, getActivity());
            }
        });

        VolleySingleton.getInstance(getActivity()).getRequestQueue().add(profileRequest);
        return view;
    }
}
