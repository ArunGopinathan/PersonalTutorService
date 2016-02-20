package edu.uta.cse.personaltutorservice;

/**
 * Created by Arun on 2/15/2016.
 */
/* This is a Generated Class, generated from JSON using pojo.sodhanalibrary.com/*/
public class Address
{
    private String State;

    private String Longitude;

    private String Lattitude;

    private String ZipCode;

    private String AddressLine1;

    private String City;

    private String AddressLine2;

    public String getState ()
    {
        return State;
    }

    public void setState (String State)
    {
        this.State = State;
    }

    public String getLongitude ()
    {
        return Longitude;
    }

    public void setLongitude (String Longitude)
    {
        this.Longitude = Longitude;
    }

    public String getLattitude ()
    {
        return Lattitude;
    }

    public void setLattitude (String Lattitude)
    {
        this.Lattitude = Lattitude;
    }

    public String getZipCode ()
    {
        return ZipCode;
    }

    public void setZipCode (String ZipCode)
    {
        this.ZipCode = ZipCode;
    }

    public String getAddressLine1 ()
    {
        return AddressLine1;
    }

    public void setAddressLine1 (String AddressLine1)
    {
        this.AddressLine1 = AddressLine1;
    }

    public String getCity ()
    {
        return City;
    }

    public void setCity (String City)
    {
        this.City = City;
    }

    public String getAddressLine2 ()
    {
        return AddressLine2;
    }

    public void setAddressLine2 (String AddressLine2)
    {
        this.AddressLine2 = AddressLine2;
    }

    @Override
    public String toString()
    {
        return "edu.uta.cse.personaltutorservice.Address [State = "+State+", Longitude = "+Longitude+", Lattitude = "+Lattitude+", ZipCode = "+ZipCode+", AddressLine1 = "+AddressLine1+", City = "+City+", AddressLine2 = "+AddressLine2+"]";
    }
}

