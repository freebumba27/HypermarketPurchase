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
import com.example.hypermarketpurchase.ProductActivity;
import com.example.hypermarketpurchase.R;
import com.example.models.CategoryListResponse;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anirban on 5/1/16.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {
    private ArrayList<CategoryListResponse> categoryListResponses;
    private Context context;
    private String branchName;

    public CategoryListAdapter(Context context, String branchName) {
        this.context = context;
        this.branchName = branchName;
        categoryListResponses = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (categoryListResponses.get(position).getCategory_image() != null)
            Glide.with(context)
                    .load(categoryListResponses.get(position).getCategory_image())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(holder.ImageViewCategoryImage);
        else
            holder.ImageViewCategoryImage.setImageResource(R.drawable.placeholder);

        holder.textViewCategoryName.setText(categoryListResponses.get(position).getCategory_name());
        holder.linearLayoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("categoryId", categoryListResponses.get(position).getId()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryListResponses.size();
    }

    public void addingList(ArrayList<CategoryListResponse> list) {
        this.categoryListResponses = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ImageViewCategoryImage)
        ImageView ImageViewCategoryImage;
        @Bind(R.id.textViewCategoryName)
        TextView textViewCategoryName;
        @Bind(R.id.linearLayoutCategory)
        LinearLayout linearLayoutCategory;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
