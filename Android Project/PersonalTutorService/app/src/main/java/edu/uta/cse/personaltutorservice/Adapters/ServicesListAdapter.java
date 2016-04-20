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

import edu.uta.cse.personaltutorservice.Model_Objects.Service;
import edu.uta.cse.personaltutorservice.Model_Objects.Services;
import edu.uta.cse.personaltutorservice.R;

/**
 * Created by Chris on 3/26/2016.
 */
public class ServicesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Service> data;
        private LayoutInflater inflater;
        private Context mContext;
    public ServicesListAdapter(Context context, Services services){
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.data = services.getServices();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.services_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    private void checkDevider(int position, ViewHolder holder) {
        if (position == (data.size() - 1)) {
            holder.devider.setVisibility(View.INVISIBLE);
        } else {
            holder.devider.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("ARJUN", "MajorListAdapter() - onBindViewHolder() postion : " + position);
        createServiceRow((ViewHolder)holder,position);
    }

    private void createServiceRow(ViewHolder holder, int position){
        Service curService = data.get(position);
        Log.w("PTS-Android", "current service: " + curService);
        holder.tv_maincategory.setText(curService.getCategory().getCategoryName() + " - " + curService.getSubCategory().getSubCategoryName());
        holder.tv_firstname.setText(curService.getUser().getFirstName());
        holder.tv_lastname.setText(curService.getUser().getLastName());
        holder.tv_initials.setText(curService.getUser().getInitials());
        holder.rb_avgrating.setRating((float)curService.getAvgRating());
        holder.tv_numOfFeedbacks.setText(curService.getNumOfFeedbacks() + "");
        holder.tv_address.setText(curService.getAddress().toString());
        holder.tv_description.setText(curService.getDescription());
        holder.tv_withinMiles.setText(curService.getMiles() + "");
        if(curService.isAdvertisment().equals("YES")){
            holder.iv_ad.setVisibility(View.VISIBLE);
        }

        Log.w("PTS-Android", "holder : " + holder.toString());
        checkDevider(position, holder);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_initials;
        TextView tv_maincategory;
        TextView tv_firstname;
        TextView tv_lastname;
        RatingBar rb_avgrating;
        TextView tv_numOfFeedbacks;
        TextView tv_address;
        TextView tv_description;
        TextView tv_withinMiles;
        LinearLayout devider;
        ImageView iv_ad;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_initials = (TextView) itemView.findViewById(R.id.course_list_item_tv_initials);
            tv_maincategory = (TextView) itemView.findViewById(R.id.service_list_item_tv_maincatagory);
            tv_firstname = (TextView) itemView.findViewById(R.id.course_list_item_tv_tutor_firstname);
            tv_lastname = (TextView) itemView.findViewById(R.id.course_list_item_tv_tutor_lasttname);
            rb_avgrating = (RatingBar) itemView.findViewById(R.id.service_list_item_ratingbar);
            tv_numOfFeedbacks = (TextView) itemView.findViewById(R.id.service_list_item_tv_num_feedback);
            tv_address = (TextView) itemView.findViewById(R.id.service_list_item_tv_address);
            tv_description = (TextView) itemView.findViewById(R.id.service_list_item_tv_description);
            tv_withinMiles = (TextView) itemView.findViewById(R.id.service_list_item_tv_distance);
            iv_ad = (ImageView) itemView.findViewById(R.id.service_details_iv_ad);
            devider = (LinearLayout) itemView.findViewById(R.id.course_list_item_devider);

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
