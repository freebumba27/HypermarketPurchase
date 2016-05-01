package com.example.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hypermarketpurchase.Items;
import com.example.hypermarketpurchase.R;
import com.example.models.ProductListResponse;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anirban on 5/1/16.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    private ArrayList<ProductListResponse> productListResponses;
    private Context context;

    public HashMap<Integer, Items> getItemsHashMap() {
        return itemsHashMap;
    }

    private HashMap<Integer, Items> itemsHashMap;

    public ProductListAdapter(Context context) {
        this.context = context;
        productListResponses = new ArrayList<>();
        itemsHashMap = new HashMap<>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (productListResponses.get(position).getProduct_image() != null)
            Glide.with(context)
                    .load(productListResponses.get(position).getProduct_image())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(holder.ImageViewProductImage);
        else
            holder.ImageViewProductImage.setImageResource(R.drawable.placeholder);

        holder.textViewProductName.setText(productListResponses.get(position).getProduct_name());
        holder.textViewProductPrice.setText("AED " + productListResponses.get(position).getProduct_price());
        holder.linearLayoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBoxProduct.performClick();
            }
        });

        holder.checkBoxProduct.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            holder.spinnerQuantity.setVisibility(View.VISIBLE);

                            if (Integer.parseInt(holder.spinnerQuantity.getTag().toString()) != 0) {
                                Items items = new Items();
                                items.setName(productListResponses.get(position).getProduct_name());
                                items.setPrice(Double.parseDouble(productListResponses.get(position).getProduct_price()));
                                items.setQuantity(1);

                                itemsHashMap.put(Integer.parseInt(productListResponses.get(position).getId()), items);
                            }
                        } else {
                            holder.spinnerQuantity.setVisibility(View.INVISIBLE);
                            itemsHashMap.remove(Integer.parseInt(productListResponses.get(position).getId()));
                        }
                    }
                }
        );

        holder.spinnerQuantity.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        holder.spinnerQuantity.setTag(pos);

                        Items items = new Items();
                        items.setName(productListResponses.get(position).getProduct_name());
                        items.setPrice(Double.parseDouble(productListResponses.get(position).getProduct_price()));
                        items.setQuantity(pos);

                        itemsHashMap.put(Integer.parseInt(productListResponses.get(position).getId()), items);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        //On Nothing selection do something
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return productListResponses.size();
    }

    public void addingList(ArrayList<ProductListResponse> list) {
        this.productListResponses = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ImageViewProductImage)
        ImageView ImageViewProductImage;
        @Bind(R.id.textViewProductName)
        TextView textViewProductName;
        @Bind(R.id.textViewProductPrice)
        TextView textViewProductPrice;
        @Bind(R.id.linearLayoutCategory)
        LinearLayout linearLayoutCategory;
        @Bind(R.id.checkBoxProduct)
        CheckBox checkBoxProduct;
        @Bind(R.id.spinnerQuantity)
        Spinner spinnerQuantity;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
