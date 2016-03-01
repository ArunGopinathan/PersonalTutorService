/**
 * 
 */
package edu.uta.cse.webservice.pts;


public class Days
{
private Day Day;

public Day getDay ()
{
return Day;
}

public void setDay (Day Day)
{
this.Day = Day;
}

@Override
public String toString()
{
return "ClassPojo [Day = "+Day+"]";
}
}