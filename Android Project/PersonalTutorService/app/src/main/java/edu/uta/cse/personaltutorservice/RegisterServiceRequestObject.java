/**
 * 
 */
package edu.uta.cse.personaltutorservice;

import android.util.Log;

import com.google.gson.Gson;

/**
 * @author Arun
 *
 */
public class RegisterServiceRequestObject
{
	private int UserId;
    private String PricePerHour;

    private String WillingToTravelInMiles;

    private String Advertise;

    private Category Category;

    private Availability Availability;

    private SubCategory SubCategory;

    public String getPricePerHour ()
    {
        return PricePerHour;
    }

    public void setPricePerHour (String PricePerHour)
    {
        this.PricePerHour = PricePerHour;
    }

    public String getWillingToTravelInMiles ()
    {
        return WillingToTravelInMiles;
    }

    public void setWillingToTravelInMiles (String WillingToTravelInMiles)
    {
        this.WillingToTravelInMiles = WillingToTravelInMiles;
    }

    public String getAdvertise ()
    {
        return Advertise;
    }

    public void setAdvertise (String Advertise)
    {
        this.Advertise = Advertise;
    }

    public Category getCategory ()
    {
        return Category;
    }

    public void setCategory (Category Category)
    {
        this.Category = Category;
    }

    public Availability getAvailability ()
    {
        return Availability;
    }

    public void setAvailability (Availability Availability)
    {
        this.Availability = Availability;
    }

    public SubCategory getSubCategory ()
    {
        return SubCategory;
    }

    public void setSubCategory (SubCategory SubCategory)
    {
        this.SubCategory = SubCategory;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PricePerHour = "+PricePerHour+", WillingToTravelInMiles = "+WillingToTravelInMiles+", Advertise = "+Advertise+", Category = "+Category+", Availability = "+Availability+", SubCategory = "+SubCategory+"]"+"userId="+UserId;
    }

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

    public static String toJsonString(RegisterServiceRequestObject registerServiceRequestObject){
        Gson gson = new Gson();
        String result = "";
        RegisterServiceRequest request = new RegisterServiceRequest();
        request.setRegisterServiceRequestObject(registerServiceRequestObject);
        try{
            result = gson.toJson(request, RegisterServiceRequest.class);
            Log.w("PTS-Android", result);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return result;

    }
}
