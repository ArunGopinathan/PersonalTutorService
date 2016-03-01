/**
 * 
 */
package edu.uta.cse.webservice.pts;


public class Day
{
private String Name;

private String EndTime;

private String StartTime;

public String getName ()
{
return Name;
}

public void setName (String Name)
{
this.Name = Name;
}

public String getEndTime ()
{
return EndTime;
}

public void setEndTime (String EndTime)
{
this.EndTime = EndTime;
}

public String getStartTime ()
{
return StartTime;
}

public void setStartTime (String StartTime)
{
this.StartTime = StartTime;
}

@Override
public String toString()
{
return "ClassPojo [Name = "+Name+", EndTime = "+EndTime+", StartTime = "+StartTime+"]";
}
}
	