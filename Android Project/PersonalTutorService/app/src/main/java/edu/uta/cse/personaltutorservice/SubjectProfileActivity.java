package edu.uta.cse.personaltutorservice;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class SubjectProfileActivity extends AppCompatActivity {
String serviceId;
    String miles;
    public static String hostname = "http://personaltutor.uta.ngrok.io/PersonalTutorServiceWebService/PTSWebService/";
    public static String method = "GetServiceByServiceId/";
    public Services services = new Services();
    public TextView tvCourseName,tvCategory, tvSubCategory, tvTutorName, tvPricePerHour,tvAddress,tvPhoneNumber,tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_profile);
        serviceId = getIntent().getStringExtra("SERVICE_ID");
       // miles = getIntent().getStringExtra("DISTANCE");
        tvCourseName = (TextView) findViewById(R.id.service_details_coursename);
        tvCategory = (TextView) findViewById(R.id.service_details_category);
        tvSubCategory = (TextView) findViewById(R.id.service_details_subcategory);
        tvTutorName = (TextView) findViewById(R.id.service_details_tutorname);
        tvPricePerHour = (TextView) findViewById(R.id.service_details_priceperhour);
        tvAddress = (TextView) findViewById(R.id.service_details_address);
        tvPhoneNumber = (TextView) findViewById(R.id.service_details_phonenumber);
        tvEmail = (TextView) findViewById(R.id.service_details_email);

        NearbyTutorsAsyncTask task = new NearbyTutorsAsyncTask();
        task.execute();


    }
    private String getServiceDetails(){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + method + serviceId;
            HttpGet getRequest = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
            Log.w("PTS-Android", httpResponse.getStatusLine().toString());
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
                Gson gson = new Gson();
                services = gson.fromJson(result,Services.class);

                Log.w("PTS-Android", "Services:" + services.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }


        return result;
    }
    private class NearbyTutorsAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            getServiceDetails();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            tvCourseName.setText(services.getServices()[0].getSubCategory().getSubCategoryName() + " by" + services.getServices()[0].getUser().getLastName());
            tvCategory.setText(services.getServices()[0].getCategory().getCategoryName());
            tvSubCategory.setText(services.getServices()[0].getSubCategory().getSubCategoryName());
            tvTutorName.setText(services.getServices()[0].getUser().getLastName()+","+services.getServices()[0].getUser().getFirstName());
            tvTutorName.setText(services.getServices()[0].getPricePerHour());
            String address ="";
            address+=services.getServices()[0].getAddress().getAddressLine1();
            if(services.getServices()[0].getAddress().getAddressLine2()!=""){
                address+=", "+services.getServices()[0].getAddress().getAddressLine2();
            }
            address+=","+services.getServices()[0].getAddress().getCity();
            address+=","+services.getServices()[0].getAddress().getState();
            address+=","+services.getServices()[0].getAddress().getZipCode();

            tvAddress.setText(address);
            tvPhoneNumber.setText(services.getServices()[0].getUser().getPhoneNumber());
            tvPricePerHour.setText(services.getServices()[0].getPricePerHour());
            tvEmail.setText(services.getServices()[0].getUser().getEmail());

            ((ImageView) findViewById(R.id.update_iv_sms)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.putExtra("address", tvPhoneNumber.getText().toString());
                    sendIntent.putExtra("sms_body", "");
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(sendIntent);
                }
            });

            ((ImageView) findViewById(R.id.service_details_iv_phone)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + tvPhoneNumber.getText().toString()));
                    startActivity(callIntent);
                }
            });

            ((ImageView)findViewById(R.id.service_details_iv_email_send)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",tvEmail.getText().toString(), null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject:");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });

        }
    }
}
