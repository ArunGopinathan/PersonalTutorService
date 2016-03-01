/**
 * 
 */
package edu.uta.cse.webservice.pts;

/**
 * @author Arun
 *
 */
public class RegisterServiceRequest
{
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
        return "ClassPojo [PricePerHour = "+PricePerHour+", WillingToTravelInMiles = "+WillingToTravelInMiles+", Advertise = "+Advertise+", Category = "+Category+", Availability = "+Availability+", SubCategory = "+SubCategory+"]";
    }
}
