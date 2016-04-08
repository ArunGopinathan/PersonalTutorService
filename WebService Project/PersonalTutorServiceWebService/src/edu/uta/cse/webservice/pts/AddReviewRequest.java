/**
 * 
 */
package edu.uta.cse.webservice.pts;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Arun
 *
 */
public class AddReviewRequest {

	private Review Review;

	public Review getReview() {
		return Review;
	}

	public void setReview(Review review) {
		Review = review;
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
