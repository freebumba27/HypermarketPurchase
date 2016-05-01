package com.example.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hypermarketpurchase.ItemCategory;
import com.example.hypermarketpurchase.R;
import com.example.models.BranchListResponse;
import com.example.utils.ReuseableClass;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anirban on 5/1/16.
 */
public class BranchListAdapter extends RecyclerView.Adapter<BranchListAdapter.MyViewHolder> {
    private ArrayList<BranchListResponse> branchListResponses;
    private Context context;

    public BranchListAdapter(Context context) {
        this.context = context;
        branchListResponses = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_list_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (branchListResponses.get(position).getBranch_image() != null)
            Glide.with(context)
                    .load(branchListResponses.get(position).getBranch_image())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(holder.ImageViewBranchImage);
        else
            holder.ImageViewBranchImage.setImageResource(R.drawable.placeholder);

        holder.textViewBranchName.setText(branchListResponses.get(position).getBranch_name());
        holder.linearLayoutBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReuseableClass.saveInPreference("branchName", branchListResponses.get(position).getBranch_name(), context);
                Intent intent = new Intent(context, ItemCategory.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return branchListResponses.size();
    }

    public void addingList(ArrayList<BranchListResponse> list) {
        this.branchListResponses = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ImageViewBranchImage)
        ImageView ImageViewBranchImage;
        @Bind(R.id.textViewBranchName)
        TextView textViewBranchName;
        @Bind(R.id.linearLayoutBranch)
        LinearLayout linearLayoutBranch;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
