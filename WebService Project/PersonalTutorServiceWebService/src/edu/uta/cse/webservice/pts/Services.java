/**
 * 
 */
package edu.uta.cse.webservice.pts;

/**
 * @author Arun
 *
 */
public class Services
{
    private Service[] Services;

    public Service[] getServices ()
    {
        return Services;
    }

    public void setServices (Service[] Service)
    {
        this.Services = Service;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Service = "+Services+"]";
    }
}
