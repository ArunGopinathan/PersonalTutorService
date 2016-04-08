/**
 * 
 */
package edu.uta.cse.webservice.pts;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * @author Arun
 *
 */
public class GetAllReviewForServiceResponse {

	private ArrayList<Review> Reviews;

	public ArrayList<Review> getReviews() {
		return Reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		Reviews = reviews;
	}
	
	public static String toJsonFromObject(GetAllReviewForServiceResponse response){
		String result = "";
		Gson gson = new Gson();

		try {
			result = gson.toJson(response, GetAllReviewForServiceResponse.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
		}
}
