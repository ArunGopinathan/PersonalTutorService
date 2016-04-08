/**
 * 
 */
package edu.uta.cse.webservice.pts;

/**
 * @author Arun
 *
 */
public class Review
{
    private String ReviewId;

    private String Rating;

    private String UnHelpfulCount;

    private String HelpfulCount;

    private String RaterUserId;

    private String Comment;

    private String ServiceId;

    private String UserId;

    private String isAnonymous;

    private String Title;

    public String getReviewId ()
    {
        return ReviewId;
    }

    public void setReviewId (String ReviewId)
    {
        this.ReviewId = ReviewId;
    }

    public String getRating ()
    {
        return Rating;
    }

    public void setRating (String Rating)
    {
        this.Rating = Rating;
    }

    public String getUnHelpfulCount ()
    {
        return UnHelpfulCount;
    }

    public void setUnHelpfulCount (String UnHelpfulCount)
    {
        this.UnHelpfulCount = UnHelpfulCount;
    }

    public String getHelpfulCount ()
    {
        return HelpfulCount;
    }

    public void setHelpfulCount (String HelpfulCount)
    {
        this.HelpfulCount = HelpfulCount;
    }

    public String getRaterUserId ()
    {
        return RaterUserId;
    }

    public void setRaterUserId (String RaterUserId)
    {
        this.RaterUserId = RaterUserId;
    }

    public String getComment ()
    {
        return Comment;
    }

    public void setComment (String Comment)
    {
        this.Comment = Comment;
    }

    public String getServiceId ()
    {
        return ServiceId;
    }

    public void setServiceId (String ServiceId)
    {
        this.ServiceId = ServiceId;
    }

    public String getUserId ()
    {
        return UserId;
    }

    public void setUserId (String UserId)
    {
        this.UserId = UserId;
    }

    public String getIsAnonymous ()
    {
        return isAnonymous;
    }

    public void setIsAnonymous (String isAnonymous)
    {
        this.isAnonymous = isAnonymous;
    }

    public String getTitle ()
    {
        return Title;
    }

    public void setTitle (String Title)
    {
        this.Title = Title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ReviewId = "+ReviewId+", Rating = "+Rating+", UnHelpfulCount = "+UnHelpfulCount+", HelpfulCount = "+HelpfulCount+", RaterUserId = "+RaterUserId+", Comment = "+Comment+", ServiceId = "+ServiceId+", UserId = "+UserId+", isAnonymous = "+isAnonymous+", Title = "+Title+"]";
    }
}
		