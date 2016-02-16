/**
 * Created by Arun on 2/15/2016.
 */
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