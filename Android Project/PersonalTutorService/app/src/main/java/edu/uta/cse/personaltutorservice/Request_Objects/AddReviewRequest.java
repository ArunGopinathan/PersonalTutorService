package edu.uta.cse.personaltutorservice.Request_Objects;


import android.util.Log;

import com.google.android.gms.wearable.internal.AddLocalCapabilityResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.uta.cse.personaltutorservice.Model_Objects.Review;
/**
 * Created by Chris on 4/9/2016.
 */
public class AddReviewRequest {
    private Review Review;

    public Review getReview() {
        return Review;
    }

    public void setReview(Review review) {
        Review = review;
    }

    public static String toJsonString(AddReviewRequest addReviewRequest){
        Gson gson = new Gson();
        String result = "";
        try{
            result = gson.toJson(addReviewRequest, AddReviewRequest.class);
            Log.w("PTS-Android", result);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return result;

    }

    public static AddReviewRequest getObjectFromJson(String json){
        AddReviewRequest request = new AddReviewRequest();
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(json);
        Gson gson = new Gson();// create a gson object
        try {
            // the user json is down one level, so get that and convert to java
            // object
            request = gson.fromJson(obj.get("AddReviewRequest").toString(), AddReviewRequest.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return request;

    }



}
