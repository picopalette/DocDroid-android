package io.picopalette.apps.docdroid.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.group.Group;
import com.onegravity.contactpicker.picture.ContactPictureType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.picopalette.apps.docdroid.activities.EmergencyContacts;
import io.picopalette.apps.docdroid.activities.MedicalResponseActivity;
import io.picopalette.apps.docdroid.R;
import io.picopalette.apps.docdroid.helpers.NetworkHelper;


public class HomeFragment extends Fragment {

    private LinearLayout heartpainLayout;
    private LinearLayout accidentLayout;
    private TextView emergencyContacts;
    private ArrayList<String> mycontacts;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private LinearLayout mCompleteProfile;
    private LinearLayout mAddEmergencyContacts;
    private LinearLayout mEnrollBloodDonation;
    private LinearLayout mStartHelpingOthers;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        heartpainLayout = view.findViewById(R.id.reportHeartPainLayout);
        emergencyContacts = view.findViewById(R.id.emergencyContacts);
        accidentLayout = view.findViewById(R.id.reportAccidentLayout);
        mCompleteProfile = view.findViewById(R.id.completeProfileHF);
        mAddEmergencyContacts = view.findViewById(R.id.addEmergencyContactsHF);
        mEnrollBloodDonation = view.findViewById(R.id.bloodDonationEnrollHF);
        mStartHelpingOthers = view.findViewById(R.id.startHelpingOthersHF);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");


        emergencyContacts.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(getContext(),"Contact Permission is Necessary for this App",Toast.LENGTH_SHORT).show();

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_CONTACTS},
                                2);
                    }
                }
                else
                {
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

    public void onActivityResult(int requestCode ,int resultCode , Intent data) {



        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK &&
                data != null && data.hasExtra(ContactPickerActivity.RESULT_CONTACT_DATA)) {

            // we got a result from the contact picker

            // process contacts
            List<Contact> contacts = (List<Contact>) data.getSerializableExtra(ContactPickerActivity.RESULT_CONTACT_DATA);
            mycontacts = new ArrayList<String>();
            for (Contact contact : contacts) {
                // process the contacts...
                mycontacts.add(contact.getDisplayName() + ":" + contact.getPhone(1));
                Log.d("health", String.valueOf(contact));
            }

            postData();

        }
    }

    private void postData() {

        String contactUrl = sharedPreferences.getString("url",null)+"api/updateEmergencyContacts";
        Log.d("check",contactUrl);

        JSONArray jsonArray = new JSONArray(mycontacts);
        Log.d("contacts", String.valueOf(jsonArray));


        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Method.POST, contactUrl, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        NetworkHelper.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResult) {

        Log.d("code", String.valueOf(requestCode));
        Log.d("permission", String.valueOf(permissions));
        Log.d("result", String.valueOf(grantResult));


        if (requestCode == 2){
            Intent intent = new Intent(getContext(), ContactPickerActivity.class)
                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                    .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
            startActivityForResult(intent, 1);
        }
    }





}
