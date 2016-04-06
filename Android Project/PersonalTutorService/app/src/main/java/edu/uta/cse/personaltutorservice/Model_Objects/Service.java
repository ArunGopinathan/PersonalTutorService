/**
 * 
 */
package edu.uta.cse.personaltutorservice.Model_Objects;

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
    

    private User User;
    private String PricePerHour;


    private String catagory;
    private String subCatagory;
    private String tutorName;
    private double avgRating;
    private int numOfFeedbacks;
    private Address address;
    private String description;
    private double miles;
    private String isAdvertisment;
    private String initials;

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getIsAdvertisment() {
        return isAdvertisment;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getSubCatagory() {
        return subCatagory;
    }

    public void setSubCatagory(String subCatagory) {
        this.subCatagory = subCatagory;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }


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
    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
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
    public String getDescription() {
        return description;
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