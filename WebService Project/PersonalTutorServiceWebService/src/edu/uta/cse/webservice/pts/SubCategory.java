/**
 * 
 */
package edu.uta.cse.webservice.pts;


public class SubCategory
{
private String SubCategoryId;

private String CategoryId;

private String isSubCategoryActive;

private String SubCategoryName;

public String getSubCategoryId ()
{
    return SubCategoryId;
}

public void setSubCategoryId (String SubCategoryId)
{
    this.SubCategoryId = SubCategoryId;
}

public String getCategoryId ()
{
    return CategoryId;
}

public void setCategoryId (String CategoryId)
{
    this.CategoryId = CategoryId;
}

public String getIsSubCategoryActive ()
{
    return isSubCategoryActive;
}

public void setIsSubCategoryActive (String isSubCategoryActive)
{
    this.isSubCategoryActive = isSubCategoryActive;
}

public String getSubCategoryName ()
{
    return SubCategoryName;
}

public void setSubCategoryName (String SubCategoryName)
{
    this.SubCategoryName = SubCategoryName;
}

@Override
public String toString()
{
    return "ClassPojo [SubCategoryId = "+SubCategoryId+", CategoryId = "+CategoryId+", isSubCategoryActive = "+isSubCategoryActive+", SubCategoryName = "+SubCategoryName+"]";
}
}