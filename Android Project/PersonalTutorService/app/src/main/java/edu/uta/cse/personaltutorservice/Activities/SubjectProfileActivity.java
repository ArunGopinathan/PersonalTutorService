package edu.uta.cse.personaltutorservice.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import edu.uta.cse.personaltutorservice.Adapters.ReviewsListAdapter;
import edu.uta.cse.personaltutorservice.Adapters.ServicesListAdapter;
import edu.uta.cse.personaltutorservice.Fragments.AddFeedbackFragment;
import edu.uta.cse.personaltutorservice.Model_Objects.Service;
import edu.uta.cse.personaltutorservice.R;
import edu.uta.cse.personaltutorservice.Model_Objects.Services;
import edu.uta.cse.personaltutorservice.Request_Objects.GetAllReviewForServiceResponse;

public class SubjectProfileActivity extends AppCompatActivity {
String serviceId;
    SharedPreferences sharedPreferences;
    String miles,userId;
    public static String hostname = "http://personaltutor.uta.ngrok.io/PersonalTutorServiceWebService/PTSWebService/";
    public static String method = "GetServiceByServiceId/";
    public static String getAllReviewsForService = "GetAllReviewsForService/";
    public static String updateHelpfulCount = "UpdateHelpfulCount/";
    public static String updateUnHelpfulCount = "UpdateUnHelpfulCount/";
    public Services services = new Services();
    ProgressBar likeordislikeprogress;
    public GetAllReviewForServiceResponse getAllReviewForServiceResponse;

    public TextView tvCourseName,tvCategory, tvSubCategory, tvTutorName, tvPricePerHour,tvAddress,tvPhoneNumber,tvEmail,tvLike,tvDislike;
    boolean hasReview = false;
    public FloatingActionButton mAddFeedbackButton;
    public RatingBar ratingBar;

    public int clickposition = -1;
    ReviewsListAdapter rlAdapter;
    RecyclerView rv_reviewlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_profile);
        serviceId = getIntent().getStringExtra("SERVICE_ID");
        sharedPreferences = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("UserId", null);
       // miles = getIntent().getStringExtra("DISTANCE");
        tvCourseName = (TextView) findViewById(R.id.service_details_coursename);
        tvCategory = (TextView) findViewById(R.id.service_details_category);
        tvSubCategory = (TextView) findViewById(R.id.service_details_subcategory);
        tvTutorName = (TextView) findViewById(R.id.service_details_tutorname);
        tvPricePerHour = (TextView) findViewById(R.id.service_details_priceperhour);
        tvAddress = (TextView) findViewById(R.id.service_details_address);
        tvPhoneNumber = (TextView) findViewById(R.id.service_details_phonenumber);
        tvEmail = (TextView) findViewById(R.id.service_details_email);
        likeordislikeprogress = (ProgressBar) findViewById(R.id.like_dislike_ProgressBar);
        mAddFeedbackButton = (FloatingActionButton)findViewById(R.id.floating_af_button);
        mAddFeedbackButton.setOnClickListener(mOnAddFeedbackClickListener);

        rv_reviewlist = (RecyclerView) findViewById(R.id.feedback_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_reviewlist.setHasFixedSize(true);
        rv_reviewlist.setLayoutManager(layoutManager);

        ratingBar =(RatingBar) findViewById(R.id.service_details_rating);

        getAllReviewsForServiceAsyncTask task_review = new getAllReviewsForServiceAsyncTask();
        task_review.execute();

        while(true) {
            if(getAllReviewForServiceResponse != null){

                rlAdapter = new ReviewsListAdapter(this, getAllReviewForServiceResponse,onItemClickCallback);
                rv_reviewlist.setAdapter(rlAdapter);
                int viewHeight = rlAdapter.getItemCount() * 520;
                rv_reviewlist.getLayoutParams().height = viewHeight;
                Log.w("PTS-Android", "Itemcount: " + rlAdapter.getItemCount());
                break;
            }
        }
       NearbyTutorsAsyncTask task = new NearbyTutorsAsyncTask();
       task.execute();


    }

    public View.OnClickListener mOnAddFeedbackClickListener =  new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            AddFeedbackFragment fragment = AddFeedbackFragment.newInstance(Integer.parseInt(serviceId),Integer.parseInt(userId));

            fragment.show(getFragmentManager(), "missiles");
        }
    };

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
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = (JsonObject)parser.parse(result);
                services = gson.fromJson(jsonObject.get("Services").toString(),Services.class);

                Log.w("PTS-Android", "Services:" + services.getServices().get(0).toString());
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
            Log.w("PTS-Android", services.toString());
            tvCourseName.setText(services.getServices().get(0).getSubCategory().getSubCategoryName() + " by" + services.getServices().get(0).getUser().getLastName());
            tvCategory.setText(services.getServices().get(0).getCategory().getCategoryName());
            tvSubCategory.setText(services.getServices().get(0).getSubCategory().getSubCategoryName());
            tvTutorName.setText(services.getServices().get(0).getUser().getLastName()+","+services.getServices().get(0).getUser().getFirstName());
            tvTutorName.setText(services.getServices().get(0).getPricePerHour());
            String address ="";
            address+=services.getServices().get(0).getAddress().getAddressLine1();
            if(services.getServices().get(0).getAddress().getAddressLine2()!=""){
                address+=", "+services.getServices().get(0).getAddress().getAddressLine2();
            }
            address+=","+services.getServices().get(0).getAddress().getCity();
            address+=","+services.getServices().get(0).getAddress().getState();
            address+=","+services.getServices().get(0).getAddress().getZipCode();

            tvAddress.setText(address);
            tvPhoneNumber.setText(services.getServices().get(0).getUser().getPhoneNumber());
            tvPricePerHour.setText(services.getServices().get(0).getPricePerHour());
            ratingBar.setRating((float)services.getServices().get(0).getAvgRating());
            tvEmail.setText(services.getServices().get(0).getUser().getEmail());

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

    private class getAllReviewsForServiceAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            getAllReviewsForService(serviceId);
            return null;
        }

        protected void onPostExecute(Void aVoid) {


        }
    }

    public void getAllReviewsForService(String serviceId){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + getAllReviewsForService + serviceId;
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
                getAllReviewForServiceResponse = gson.fromJson(result,GetAllReviewForServiceResponse.class);
                Log.w("PTS-Android", "test: " + getAllReviewForServiceResponse.getReviews().get(0).getTitle());
                hasReview = true;
            }else{
                hasReview = false;
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

    public static class OnItemClickListener implements View.OnClickListener {
        private int position;
        private OnItemClickCallback onItemClickCallback;

        public OnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
            this.position = position;
            this.onItemClickCallback = onItemClickCallback;
        }

        @Override
        public void onClick(View view) {
            onItemClickCallback.onItemClicked(view, position);
        }

        public interface OnItemClickCallback {
            void onItemClicked(View view, int position);
        }
    }

    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            switch (view.getId()){
                case R.id.fb_list_item_like_feedback:
                    LinearLayout parentView1 = (LinearLayout) view.getParent();
                    tvLike = (TextView) parentView1.findViewById(R.id.fb_list_item_num_like);
                    tvLike.setText((Integer.parseInt(tvLike.getText().toString()) + 1)+"");

                    while(clickposition != position){
                        clickposition = position;
                        updateHelpfulCountAsyncTask task = new updateHelpfulCountAsyncTask();
                        task.execute();
                    }
                    break;
                case R.id.fb_list_item_dislike_feedback:
                    LinearLayout parentView2 = (LinearLayout) view.getParent();
                    tvDislike =(TextView) parentView2.findViewById(R.id.fb_list_item_num_dislike);
                    tvDislike.setText((Integer.parseInt(tvDislike.getText().toString())+1)+"");
                    while(clickposition != position){
                        clickposition = position;
                        updateUnHelpfulCountAsyncTask task = new updateUnHelpfulCountAsyncTask();
                        task.execute();
                    }
                    break;
            }
        }
    };


    private String updateHelpfulCount(int position){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + updateHelpfulCount + getAllReviewForServiceResponse.getReviews().get(position).getReviewId();
            HttpGet getRequest = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(getRequest);

            Log.w("PTS-Android", httpResponse.getStatusLine().toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }


        return result;
    }    private String updateUnHelpfulCount(int position){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + updateUnHelpfulCount + getAllReviewForServiceResponse.getReviews().get(position).getReviewId();
            HttpGet getRequest = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(getRequest);

            Log.w("PTS-Android", httpResponse.getStatusLine().toString());

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
    private class updateHelpfulCountAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            likeordislikeprogress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            updateHelpfulCount(clickposition);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            likeordislikeprogress.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Like +1", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            likeordislikeprogress.animate();
        }
    }
    private class updateUnHelpfulCountAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            likeordislikeprogress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            updateUnHelpfulCount(clickposition);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            likeordislikeprogress.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Dislike +1", Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            likeordislikeprogress.animate();
        }
    }
}
