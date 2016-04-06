/**
 * 
 */
package edu.uta.cse.personaltutorservice.Model_Objects;

import java.util.ArrayList;

/**
 * @author Arun
 *
 */
public class Services
{
    private ArrayList<Service> Services;

    public ArrayList<Service> getServices() {
        return Services;
    }

    public void setServices(ArrayList<Service> services) {
        Services = services;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [Service = "+Services+"]";
    }
}
