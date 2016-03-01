/**
 * 
 */
package edu.uta.cse.webservice.pts;

public class Availability
{
    private Days Days;

    public Days getDays ()
    {
        return Days;
    }

    public void setDays (Days Days)
    {
        this.Days = Days;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Days = "+Days+"]";
    }
}

