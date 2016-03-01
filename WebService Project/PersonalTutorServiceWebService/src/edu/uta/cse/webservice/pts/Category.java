/**
 * 
 */
package edu.uta.cse.webservice.pts;

public class Category
{
private String CategoryName;

private String CategoryId;

private String isCategoryActive;

public String getCategoryName ()
{
return CategoryName;
}

public void setCategoryName (String CategoryName)
{
this.CategoryName = CategoryName;
}

public String getCategoryId ()
{
return CategoryId;
}

public void setCategoryId (String CategoryId)
{
this.CategoryId = CategoryId;
}

public String getIsCategoryActive ()
{
return isCategoryActive;
}

public void setIsCategoryActive (String isCategoryActive)
{
this.isCategoryActive = isCategoryActive;
}

@Override
public String toString()
{
return "ClassPojo [CategoryName = "+CategoryName+", CategoryId = "+CategoryId+", isCategoryActive = "+isCategoryActive+"]";
}
}


