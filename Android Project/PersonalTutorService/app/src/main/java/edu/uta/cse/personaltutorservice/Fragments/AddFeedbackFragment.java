package edu.uta.cse.personaltutorservice.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.uta.cse.personaltutorservice.Model_Objects.Review;
import edu.uta.cse.personaltutorservice.R;
import edu.uta.cse.personaltutorservice.Request_Objects.AddReviewRequest;
import edu.uta.cse.personaltutorservice.Request_Objects.UpdateProfileRequestObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFeedbackFragment extends DialogFragment {

    public static AddFeedbackFragment mfragment;
    public static String hostname = "http://personaltutor.uta.ngrok.io/PersonalTutorServiceWebService/PTSWebService/";
    public static String addReviewMethod = "AddReview";
    View mView;
    EditText mTitle,mComment;
    RatingBar mRatingBar;
    CheckBox mIsAnonymousCheck;

    String date;
    Integer serviceId,ratingUserId;
Activity mActivity;
    String title,comment,isAnoymous;
    Float rating;
    String requestJson = "",addReviewResult="";

    public static AddFeedbackFragment newInstance(int serviceId, int ratingUserId){
        mfragment = new AddFeedbackFragment();
        Bundle args = new Bundle();
        args.putInt("SERVICE_ID", serviceId);
        args.putInt("RATING_USER_ID", ratingUserId);
        mfragment.setArguments(args);
        return mfragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        mView = inflater.inflate(R.layout.dialog_new_feedback, null);
        initView(mView);

        serviceId = getArguments().getInt("SERVICE_ID");
        ratingUserId = getArguments().getInt("RATING_USER_ID");

        builder.setView(mView)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        DateFormat dateFormat = DateFormat.getDateTimeInstance();
                        Date curdate = new Date();
                        date = dateFormat.format(curdate);

                        if (mTitle.getText().toString().equals("") || mTitle.getText().length() == 0) {

                            mTitle.setError("Title can not be blank!");
                        } else {
                            title = mTitle.getText().toString();
                            mTitle.setError(null);
                        }
                        if (!mComment.getText().toString().equals("")) {
                            comment = mComment.getText().toString();
                            mComment.setError(null);
                        } else {
                            mComment.setError("Description can not be blank!");

                        }
                        rating = mRatingBar.getRating();
                        if(rating==0.0){
                            rating = (float)1.0;
                        }
                        if (mIsAnonymousCheck.isChecked()) {
                            isAnoymous = "1";
                        } else {
                            isAnoymous = "0";
                        }


                        if (mTitle.getError() == null && mComment.getError() == null) {
                            addFeedbackAsyncTask task = new addFeedbackAsyncTask();
                            task.execute();
                        }else{
                            Toast.makeText(getActivity(), "Error Occured. Please try again", Toast.LENGTH_SHORT).show();

                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddFeedbackFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
    private void initView(View view){
        mTitle = (EditText) view.findViewById(R.id.add_feedback_title);
        mComment = (EditText) view.findViewById(R.id.add_feedback_comments);
        mRatingBar = (RatingBar) view.findViewById(R.id.add_feedback_rating);
        mIsAnonymousCheck = (CheckBox) view.findViewById(R.id.add_feedback_isAnoymous);

    }

    public void generateAddFeedbackRequestJson(){

    }

    public class addFeedbackAsyncTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            while (true){
                if(title.equals("")||comment.equals("")||isAnoymous.equals("")){
                    continue;
                }else {
                    requestJson = generateAddReviewRequestJson();
                    break;
                }
            }


            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // progressBar.animate();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //progressBar.setVisibility(View.GONE);
            if (addReviewResult.equals("YES")) {
                Toast.makeText(mActivity, "Review added Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = mActivity.getIntent();
                mActivity.finish();
                mActivity.startActivity(intent);

            } else {
                Toast.makeText(mActivity, "Add Failed, Please Try again Later", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            addReviewResult=  addReview();
            return null;
        }
    }


    public String generateAddReviewRequestJson() {
        String result = "";
        AddReviewRequest addReviewRequest = new AddReviewRequest();
        Review review = new Review();
        review.setServiceId(serviceId+"");
        review.setTitle(title);
        review.setComment(comment);
        review.setRating(rating+"");
        review.setIsAnonymous(isAnoymous);
        review.setDate(date);
        review.setRaterUserId(ratingUserId+"");
        addReviewRequest.setReview(review);
        result = AddReviewRequest.toJsonString(addReviewRequest);
        return result;
    }

    public String addReview(){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try{
            String url = hostname + addReviewMethod;
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
}
