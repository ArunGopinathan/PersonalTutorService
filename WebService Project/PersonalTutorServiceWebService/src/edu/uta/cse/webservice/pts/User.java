
package edu.uta.cse.webservice.pts;

/**
 * @author Arun
 *
 */
/*This is a generated Class, from JSON using http://pojo.sodhanalibrary.com/
 * and edited accordingly to annotate the fields
 * */

/*
 * JSON used to generate the User and the Address Class
{
  "User": {
    "FirstName": "Arun",
    "LastName": "Gopinathan",
    "Email": "arun.gopinathan@mavs.uta.edu",
    "Password": "1234",
    "UserType": "1",
    "Address": {
      "AddressLine1": "513 Summit Ave",
      "AddressLine2": "Apt 178",
      "City": "Arlington",
      "State": "TX",
      "ZipCode": "76013",
      "Lattitude": "ABC",
      "Longitude": "DEF"
    },
    "PhoneNumber": "682-234-0909"
  }
}
 */
import com.google.gson.annotations.SerializedName;

public class User
{
	
    private String UserType;

    private String Email;

    private Address Address;

    private String Password;

    private String FirstName;

    private String PhoneNumber;

    private String LastName;

    public String getUserType ()
    {
        return UserType;
    }

    public void setUserType (String UserType)
    {
        this.UserType = UserType;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public Address getAddress ()
    {
        return Address;
    }

    public void setAddress (Address Address)
    {
        this.Address = Address;
    }

    public String getPassword ()
    {
        return Password;
    }

    public void setPassword (String Password)
    {
        this.Password = Password;
    }

    public String getFirstName ()
    {
        return FirstName;
    }

    public void setFirstName (String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getPhoneNumber ()
    {
        return PhoneNumber;
    }

    public void setPhoneNumber (String PhoneNumber)
    {
        this.PhoneNumber = PhoneNumber;
    }

    public String getLastName ()
    {
        return LastName;
    }

    public void setLastName (String LastName)
    {
        this.LastName = LastName;
    }

    @Override
    public String toString()
    {
        return "User [UserType = "+UserType+", Email = "+Email+", Address = "+Address+", Password = "+Password+", FirstName = "+FirstName+", PhoneNumber = "+PhoneNumber+", LastName = "+LastName+"]";
    }
}
