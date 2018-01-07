package io.picopalette.apps.docdroid.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.group.Group;
import com.onegravity.contactpicker.picture.ContactPictureType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.picopalette.apps.docdroid.activities.EmergencyContacts;
import io.picopalette.apps.docdroid.activities.MedicalResponseActivity;
import io.picopalette.apps.docdroid.R;
import io.picopalette.apps.docdroid.activities.ProfileEditActivity;
import io.picopalette.apps.docdroid.helpers.VolleySingleton;

import static android.content.Context.LOCATION_SERVICE;


public class HomeFragment extends Fragment {

    private LinearLayout heartpainLayout;
    private LinearLayout accidentLayout;
    private ArrayList<String> mycontacts;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private String sosSelfUrl;

    private LinearLayout checklist1;
    private LinearLayout checklist2;
    private LinearLayout checklist3;
    private LinearLayout checklist4;

    private AppCompatImageView checkic1;
    private AppCompatImageView checkic2;
    private AppCompatImageView checkic3;
    private AppCompatImageView checkic4;

    public static final String mEmergencyReason = "Emergency Reason";

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        heartpainLayout = view.findViewById(R.id.reportHeartPainLayout);

        accidentLayout = view.findViewById(R.id.reportAccidentLayout);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        sosSelfUrl = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("url", "") + "/api/sos";

        checklist1 = (LinearLayout) view.findViewById(R.id.checklist1);
        checklist2 = (LinearLayout) view.findViewById(R.id.checklist2);
        checklist3 = (LinearLayout) view.findViewById(R.id.checklist3);
        checklist4 = (LinearLayout) view.findViewById(R.id.checklist4);

        checkic1 = (AppCompatImageView) view.findViewById(R.id.checklistic1);
        checkic2 = (AppCompatImageView) view.findViewById(R.id.checklistic2);
        checkic3 = (AppCompatImageView) view.findViewById(R.id.checklistic3);
        checkic4 = (AppCompatImageView) view.findViewById(R.id.checklistic4);

        View.OnClickListener editProfileListener = (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
                startActivity(intent);
            }
        });

        checklist1.setOnClickListener(editProfileListener);

        checklist3.setOnClickListener(editProfileListener);

        checklist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_CONTACTS)) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_CONTACTS},
                                2);
                        Toast.makeText(getContext(), "Contact Permission is Necessary for this App", Toast.LENGTH_SHORT).show();

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_CONTACTS},
                                2);
                    }
                } else {
                    Intent intent = new Intent(getContext(), ContactPickerActivity.class)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                            .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                    startActivityForResult(intent, 1);
                }
            }
        });
        heartpainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder mB = new AlertDialog.Builder(getContext());
                View alertView = inflater.inflate(R.layout.dialog_heartpain, null);
                Button yesToAmb = alertView.findViewById(R.id.getMeAmbForHeart);
                TextView yesToAmbForElse = alertView.findViewById(R.id.getAmbForElseHeart);
                mB.setView(alertView);
                final AlertDialog dialog = mB.create();
                dialog.show();

                yesToAmb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendSos("heart");
                        dialog.dismiss();
                    }
                });

//                yesToAmbForElse.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        sendSos("heart");
//                        dialog.dismiss();
//                    }
//                });
                return true;
            }
        });
        accidentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder mB = new AlertDialog.Builder(getContext());
                View alertView = inflater.inflate(R.layout.dialog_accident, null);
                Button getAmb = alertView.findViewById(R.id.getAmbForAccident);
                mB.setView(alertView);
                final AlertDialog dialog = mB.create();
                dialog.show();
                getAmb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendSos("accident");
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK &&
                data != null && data.hasExtra(ContactPickerActivity.RESULT_CONTACT_DATA)) {
            // we got a result from the contact picker
            // process contacts
            List<Contact> contacts = (List<Contact>) data.getSerializableExtra(ContactPickerActivity.RESULT_CONTACT_DATA);
            mycontacts = new ArrayList<>();
            for (Contact contact : contacts) {
                // process the contacts...
                mycontacts.add(contact.getPhone(1));
                Log.d("health", String.valueOf(contact));
            }

            postData();

        }
    }

    private void postData() {

        String contactUrl = sharedPreferences.getString("url", null) + "/api/updateEmergencyContacts";
        Log.d("check", contactUrl);

        JSONArray jsonArray = new JSONArray(mycontacts);
        Log.d("contacts", String.valueOf(jsonArray));

        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Method.POST, contactUrl, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(getContext()).getRequestQueue().add(jsonArrayRequest);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {

        Log.d("code", String.valueOf(requestCode));
        Log.d("permission", String.valueOf(permissions));
        Log.d("result", String.valueOf(grantResult));

        if (requestCode == 2) {
            Intent intent = new Intent(getContext(), ContactPickerActivity.class)
                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                    .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
            startActivityForResult(intent, 1);
        }
    }

    private void sendSos(final String problem) {

        final LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = (new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("loc", location.toString());
                JSONObject data = new JSONObject();
                try {
                    data.put("lat", String.valueOf(location.getLatitude()));
                    data.put("log", String.valueOf(location.getLongitude()));
                    data.put("problem", problem);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, sosSelfUrl, data, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("res", response.toString());
                        Toast.makeText(getActivity(), "Ambulance arriving", Toast.LENGTH_LONG).show();
                        Log.d("hospital", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Ambulance arriving", Toast.LENGTH_LONG).show();
                    }
                });
                VolleySingleton.getInstance(getActivity()).getRequestQueue().add(jsonObjectRequest);
                mLocationManager.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        2);
                Toast.makeText(getContext(), "Contact Permission is Necessary for this App", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        2);
            }
            Toast.makeText(getActivity(), "Enable Location Permission", Toast.LENGTH_SHORT).show();
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
}
