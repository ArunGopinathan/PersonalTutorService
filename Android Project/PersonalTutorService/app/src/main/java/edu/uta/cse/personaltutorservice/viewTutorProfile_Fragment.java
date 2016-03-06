package edu.uta.cse.personaltutorservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Mousa Almotairi & Funda Karapinar on 3/3/2016.
 */
public class viewTutorProfile_Fragment extends Fragment {

    View rootView;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.viewtutorprofile_layout, container, false);
         sharedPreferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId",null);
        if(userId == null){
            getActivity().finish();
        }

        ((TextView)rootView.findViewById(R.id.update_et_firstname)).setText(sharedPreferences.getString("FirstName",null));
        ((TextView)rootView.findViewById(R.id.update_et_lastname)).setText(sharedPreferences.getString("LastName",null));
        ((TextView)rootView.findViewById(R.id.update_et_email)).setText(sharedPreferences.getString("Email",null));
        ((TextView)rootView.findViewById(R.id.update_et_phonenumber)).setText(sharedPreferences.getString("PhoneNumber",null));
        ((TextView)rootView.findViewById(R.id.update_et_address)).setText(sharedPreferences.getString("Address",null));
        ((ImageView) rootView.findViewById(R.id.update_iv_sms)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("address", sharedPreferences.getString("PhoneNumber", null));
                sendIntent.putExtra("sms_body", "");
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
            }
        });

        ((ImageView) rootView.findViewById(R.id.update_iv_phone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + sharedPreferences.getString("PhoneNumber", null)));
                startActivity(callIntent);
            }
        });

        ((ImageView)rootView.findViewById(R.id.update_iv_email_send)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",sharedPreferences.getString("Email",null), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject:");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        return rootView;
    }


}
