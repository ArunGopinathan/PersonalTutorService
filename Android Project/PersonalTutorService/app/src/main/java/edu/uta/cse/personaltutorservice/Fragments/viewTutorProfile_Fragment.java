package edu.uta.cse.personaltutorservice.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

import edu.uta.cse.personaltutorservice.Model_Objects.Address;
import edu.uta.cse.personaltutorservice.R;
import edu.uta.cse.personaltutorservice.Model_Objects.Service;
import edu.uta.cse.personaltutorservice.Model_Objects.Services;
import edu.uta.cse.personaltutorservice.Adapters.ServicesListAdapter;
import edu.uta.cse.personaltutorservice.Request_Objects.UpdateProfileRequestObject;

/**
 * Created by Mousa Almotairi & Funda Karapinar on 3/3/2016.
 */
public class viewTutorProfile_Fragment extends Fragment implements View.OnClickListener {

    public static String hostname = "http://personaltutor.uta.ngrok.io/PersonalTutorServiceWebService/PTSWebService/";
    public static String updateProfileMethod = "UpdateTutorInfo";
    public static String getAllServicesByUsername = "GetAllServiceByUsername";
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
    ServicesListAdapter slAdapeter;

    CardView cv_serviceItem;
    RecyclerView rv_servicelist;

    RecyclerView.Adapter servicesAdapter;
    RecyclerView.LayoutManager servicesLayoutManager;

    String requestJson = "",updateResult="";

    Services services;
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
        et_address.setText(sharedPreferences.getString("Address", null));
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

        //Cardview
//        cv_serviceItem = (CardView) rootView.findViewById(R.id.frag_course_cv_all_service);
        rv_servicelist = (RecyclerView) rootView.findViewById(R.id.frag_updateprofile_service_list);
        rv_servicelist.setHasFixedSize(true);
        rv_servicelist.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAllServicesAsyncTask task = new getAllServicesAsyncTask();
        task.execute();
        while(true) {
            if(services != null){
                slAdapeter = new ServicesListAdapter(getActivity(), services);
                rv_servicelist.setAdapter(slAdapeter);
                int viewHeight = slAdapeter.getItemCount() * 350;
                rv_servicelist.getLayoutParams().height = viewHeight;
                Log.w("PTS-Android", "Itemcount: " + slAdapeter.getItemCount());
                break;
            }
        }
        //setAdapter();
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

    public void getAllServicesByUsername(String Email){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + getAllServicesByUsername +"/"+Email;
            Log.w("PTS-Android",url);
            HttpGet getRequest = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
            Log.w("PTS-Android", httpResponse.getStatusLine().toString());
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
                JsonParser parser = new JsonParser();
                Gson gson = new Gson();// create a gson object
                //  JsonObject obj = (JsonObject) parser.parse(result);
                services = gson.fromJson(result,Services.class);
                Log.w("PTS-Android", "test: " + services.getServices().get(0).getAddress().toString());

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    private class getAllServicesAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            getAllServicesByUsername(sharedPreferences.getString("Email", null));
            return null;
        }

        protected void onPostExecute(Void aVoid) {

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
                updateProfileAsyncTask task = new updateProfileAsyncTask();
                task.execute();

                tv_requiredtext.setTextColor(Color.parseColor("#4CAF50"));
                tv_requiredtext.setText("Update Success!");
                tv_requiredtext.setVisibility(View.INVISIBLE);


            }


        }
    };

    public String generateRegisterTutorServiceRequestJson() {
        String result = "";
        UpdateProfileRequestObject request = new UpdateProfileRequestObject();

        request.setFirstname(mFirstname);
        request.setLastname(mLastname);
        request.setEmail(mEmail);
        request.setPhonenumber(mPhonenumber);
        // Address address = new Address();


        result = UpdateProfileRequestObject.toJsonString(request);
        return result;
    }

    public String UpdateProfile(){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try{
            String url = hostname + updateProfileMethod;
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader("Content-Type", "application/json");
            StringEntity postentity = new StringEntity(requestJson, "UTF-8");
            postentity.setContentType("application/json");
            postRequest.setEntity(postentity);

            HttpResponse httpResponse = httpclient.execute(postRequest);

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        return result;


    }

    private class updateProfileAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            requestJson = generateRegisterTutorServiceRequestJson();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // progressBar.animate();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //progressBar.setVisibility(View.GONE);
            if (updateResult.equals("YES")) {
                Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();



            } else {
                Toast.makeText(getActivity(), "Update Failed, Please Try again Later", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            updateResult =  UpdateProfile();
            return null;
        }
    }

    private void setAdapter() {
        ArrayList<Service> dummyServiceList= new ArrayList<Service>();
        Services services = new Services();
        Service dummyService = new Service();
        Address address = new Address();
        address.setAddressLine1("930 Benge Dr");
        address.setCity("Arlington");
        address.setState("TX");
        address.setZipCode("76013");
        dummyService.setAddress(address);
        dummyService.setAvgRating(50);
        dummyService.setCatagory("Android");
        dummyService.setSubCatagory("Backend");
        dummyService.setDescription("what the fxxk");
        dummyService.setInitials("Z,C");
        dummyService.setTutorName("Zhenyu Chen");
        dummyService.setMiles(20.0);
        dummyService.setNumOfFeedbacks(123);

        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        dummyServiceList.add(dummyService);
        services.setServices(dummyServiceList);









        slAdapeter = new ServicesListAdapter(getActivity(),services);
        rv_servicelist.setAdapter(slAdapeter);
        int viewHeight = slAdapeter.getItemCount() * 350;
        rv_servicelist.getLayoutParams().height = viewHeight;

    }
}
