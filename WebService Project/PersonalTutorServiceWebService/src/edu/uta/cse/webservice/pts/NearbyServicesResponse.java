/**
 * 
 */
package edu.uta.cse.webservice.pts;

/**
 * @author Arun
 *
 */
public class NearbyServicesResponse
{
    private Services Services;

    public Services getServices ()
    {
        return Services;
    }

    public void setServices (Services Services)
    {
        this.Services = Services;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Services = "+Services+"]";
    }
}