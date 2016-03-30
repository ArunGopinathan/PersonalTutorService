/**
 * 
 */
package edu.uta.cse.personaltutorservice;

/**
 * @author Arun
 *
 */
public class Service
{
    private String ServiceLattitude;

    private String ServiceName;

    private String ServiceId;

    private String ServiceLongitude;
    
    private Category Category;
    
    private SubCategory SubCategory;
    
    private Address address;
    
    private int avgRating;
    private int numOfFeedbacks;
    private String description;
    private double miles;
    private String isAdvertisment;
    private User User;
    private String PricePerHour;

    public String getPricePerHour ()
    {
        return PricePerHour;
    }

    public void setPricePerHour (String PricePerHour)
    {
        this.PricePerHour = PricePerHour;
    }
    public String getServiceLattitude ()
    {
        return ServiceLattitude;
    }

    public void setServiceLattitude (String ServiceLattitude)
    {
        this.ServiceLattitude = ServiceLattitude;
    }

    public String getServiceName ()
    {
        return ServiceName;
    }

    public void setServiceName (String ServiceName)
    {
        this.ServiceName = ServiceName;
    }

    public String getServiceId ()
    {
        return ServiceId;
    }

    public void setServiceId (String ServiceId)
    {
        this.ServiceId = ServiceId;
    }

    public String getServiceLongitude ()
    {
        return ServiceLongitude;
    }

    public void setServiceLongitude (String ServiceLongitude)
    {
        this.ServiceLongitude = ServiceLongitude;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ServiceLattitude = "+ServiceLattitude+", ServiceName = "+ServiceName+", ServiceId = "+ServiceId+", ServiceLongitude = "+ServiceLongitude+"]";
    }

	public Category getCategory() {
		return Category;
	}

	public void setCategory(Category category) {
		Category = category;
	}

	public SubCategory getSubCategory() {
		return SubCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		SubCategory = subCategory;
	}
	public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }
    public int getNumOfFeedbacks() {
        return numOfFeedbacks;
    }

    public void setNumOfFeedbacks(int numOfFeedbacks) {
        this.numOfFeedbacks = numOfFeedbacks;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public String isAdvertisment() {
        return isAdvertisment;
    }

    public void setIsAdvertisment(String isAdvertisment) {
        this.isAdvertisment = isAdvertisment;
    }

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}
}