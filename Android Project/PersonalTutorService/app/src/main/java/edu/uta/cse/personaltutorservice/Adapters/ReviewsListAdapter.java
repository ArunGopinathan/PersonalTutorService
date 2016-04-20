package edu.uta.cse.personaltutorservice.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import edu.uta.cse.personaltutorservice.Activities.SubjectProfileActivity;
import edu.uta.cse.personaltutorservice.Model_Objects.Review;
import edu.uta.cse.personaltutorservice.Model_Objects.Service;
import edu.uta.cse.personaltutorservice.Model_Objects.Services;
import edu.uta.cse.personaltutorservice.R;
import edu.uta.cse.personaltutorservice.Request_Objects.GetAllReviewForServiceResponse;

/**
 * Created by Chris on 4/12/2016.
 */
public class ReviewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Review> data;
    private LayoutInflater inflater;
    private Context mContext;
    private SubjectProfileActivity.OnItemClickListener.OnItemClickCallback onItemClickCallback;
    public ReviewsListAdapter(Context context, GetAllReviewForServiceResponse responseReviewlist,SubjectProfileActivity.OnItemClickListener.OnItemClickCallback onItemClickCallback){
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.onItemClickCallback = onItemClickCallback;
        this.data = responseReviewlist.getReviews();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feedbacks_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("ARJUN", "MajorListAdapter() - onBindViewHolder() postion : " + position);
        createServiceRow((ViewHolder)holder,position);
    }

    private void createServiceRow(ViewHolder holder, int position){
        if(data.size() != 0) {
            Review curReview = data.get(position);

            Log.w("PTS-Android", "Itemcount: " + curReview);

            if(curReview.getIsAnonymous().equals("0")){
                holder.tv_initials.setText(curReview.getUser().getInitials());
                holder.tv_ratername.setText(curReview.getUser().getFirstName() + " " + curReview.getUser().getLastName());
            }else{
                holder.tv_initials.setText("G");
                holder.tv_ratername.setText("Guest");
            }

            holder.rb_rating.setRating(Float.parseFloat(curReview.getRating()));
            holder.tv_category.setText(curReview.getCategory().getCategoryName());
            holder.tv_subcategory.setText(curReview.getSubcategory().getSubCategoryName());
            holder.tv_title.setText(curReview.getTitle());
            holder.tv_comment.setText(curReview.getComment());
            holder.tv_date.setText(curReview.getDate());

            holder.tv_like.setText(curReview.getHelpfulCount());
            holder.tv_dislike.setText(curReview.getUnHelpfulCount());
            holder.iv_like.setOnClickListener(new SubjectProfileActivity.OnItemClickListener(position, onItemClickCallback));
            holder.iv_dislike.setOnClickListener(new SubjectProfileActivity.OnItemClickListener(position, onItemClickCallback));
            Log.w("PTS-Android", "holder : " + holder.toString());
        }else{
            Log.w("PTS-Android", "holder : " + "No Data");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_initials;
        TextView tv_ratername;
        TextView tv_date;
        RatingBar rb_rating;
        TextView tv_category;
        TextView tv_subcategory;
        TextView tv_title;
        TextView tv_comment;
        ImageView iv_like;
        ImageView iv_dislike;
        TextView tv_like;
        TextView tv_dislike;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_initials = (TextView) itemView.findViewById(R.id.fb_list_item_user_initials);
            tv_ratername = (TextView) itemView.findViewById(R.id.fb_list_item_user_name);
            tv_date = (TextView) itemView.findViewById(R.id.fb_list_item_fb_date);
            rb_rating = (RatingBar) itemView.findViewById(R.id.fb_list_item_ratingbar);
            tv_category = (TextView) itemView.findViewById(R.id.fb_list_item_category_name);
            tv_subcategory = (TextView) itemView.findViewById(R.id.fb_list_item_subcategory_name);
            tv_title = (TextView) itemView.findViewById(R.id.fb_list_item_fb_title);
            tv_comment = (TextView) itemView.findViewById(R.id.fb_list_item_fb_comment);
            iv_like = (ImageView) itemView.findViewById(R.id.fb_list_item_like_feedback);
            iv_dislike = (ImageView) itemView.findViewById(R.id.fb_list_item_dislike_feedback);
            tv_like = (TextView) itemView.findViewById(R.id.fb_list_item_num_like);
            tv_dislike = (TextView) itemView.findViewById(R.id.fb_list_item_num_dislike);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
