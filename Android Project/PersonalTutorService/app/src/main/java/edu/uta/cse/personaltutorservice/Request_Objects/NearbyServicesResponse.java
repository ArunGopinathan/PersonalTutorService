/**
 * 
 */
package edu.uta.cse.personaltutorservice.Request_Objects;

import edu.uta.cse.personaltutorservice.Model_Objects.Services;

/**
 * @author Arun
 *
 */
public class NearbyServicesResponse
{
    private edu.uta.cse.personaltutorservice.Model_Objects.Services Services;

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