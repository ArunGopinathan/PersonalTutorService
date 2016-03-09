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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Mousa Almotairi & Funda Karapinar on 3/3/2016.
 */
public class viewTutorProfile_Fragment extends Fragment implements View.OnClickListener {

    View rootView;
    SharedPreferences sharedPreferences;
    ImageView iv_firstname;
    ImageView iv_lastname;
    ImageView iv_email;
    ImageView iv_phonenumber;
    ImageView iv_address;

    EditText et_firstname;
    EditText et_lastname;
    EditText et_email;
    EditText et_phonenumber;
    EditText et_address;
    TextView tv_requiredtext;
    Button bt_update;

    String mFirstname;
    String mLastname;
    String mEmail;
    String mPhonenumber;
    String mAddress;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.viewtutorprofile_layout, container, false);
         sharedPreferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId",null);
        if(userId == null){
            getActivity().finish();
        }

        et_firstname =   ((EditText)rootView.findViewById(R.id.update_et_firstname));
        et_firstname.setText(sharedPreferences.getString("FirstName", null));

        et_lastname =  ((EditText)rootView.findViewById(R.id.update_et_lastname));
        et_lastname.setText(sharedPreferences.getString("LastName", null));

        et_email =  ((EditText)rootView.findViewById(R.id.update_et_email));
        et_email.setText(sharedPreferences.getString("Email", null));


        et_phonenumber = ((EditText)rootView.findViewById(R.id.update_et_phonenumber));
        et_phonenumber.setText(sharedPreferences.getString("PhoneNumber", null));

        et_address = ((EditText)rootView.findViewById(R.id.update_et_address));
        et_address.setText(sharedPreferences.getString("Address",null));
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
                        "mailto", sharedPreferences.getString("Email", null), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject:");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        iv_firstname =(ImageView) rootView.findViewById(R.id.update_iv_firstname);
        iv_lastname =(ImageView) rootView.findViewById(R.id.update_iv_lastname);
        iv_email=(ImageView) rootView.findViewById(R.id.update_iv_email);
        iv_phonenumber =(ImageView) rootView.findViewById(R.id.update_iv_phonenumber);
        iv_address =(ImageView) rootView.findViewById(R.id.update_iv_address);

        tv_requiredtext = (TextView) rootView.findViewById(R.id.update_tv_requiredField);

        bt_update = (Button) rootView.findViewById(R.id.bt_update);
        bt_update.setOnClickListener(mUpdateClickListener);

        setEditButtonClickListener();

        return rootView;
    }
    private void setEditButtonClickListener() {
        iv_firstname.setOnClickListener(this);
        iv_lastname.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        iv_phonenumber.setOnClickListener(this);
        iv_address.setOnClickListener(this);

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onClick(View view) {

        if (view == iv_firstname) {
            et_firstname.setEnabled(true);
            et_firstname.requestFocus();
        } else if (view == iv_lastname) {
            et_lastname.setEnabled(true);
            et_lastname.requestFocus();
        } else if (view == iv_email) {
            et_email.setEnabled(true);
            et_email.requestFocus();
        } else if (view == iv_phonenumber) {
            et_phonenumber.setEnabled(true);
            et_phonenumber.requestFocus();
        } else if (view == iv_address) {
            et_address.setEnabled(true);
            et_address.requestFocus();
        }

    }
    View.OnClickListener mUpdateClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            mFirstname = et_firstname.getText().toString();
            mLastname = et_lastname.getText().toString();
            mEmail = et_email.getText().toString();
            mPhonenumber = et_phonenumber.getText().toString();
            mAddress = et_address.getText().toString();


            if (mFirstname.equals("") || mEmail.equals("") || mPhonenumber.equals("") || mAddress.equals("")) {
                tv_requiredtext.setVisibility(View.VISIBLE);
                return;
            }else{
//                tv_requiredtext.setTextColor(Color.parseColor("#4CAF50"));
//                tv_requiredtext.setText("Update Success!");
               // tv_requiredtext.setVisibility(View.INVISIBLE);

            }


        }
    };

}
