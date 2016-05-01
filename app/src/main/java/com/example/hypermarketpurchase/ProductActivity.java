package com.example.hypermarketpurchase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events.ProductListEvent;
import com.example.jobs.GetProductListJob;
import com.example.utils.ReuseableClass;
import com.example.widget.ProductListAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ProductActivity extends Activity {

    Bundle b;

    @Bind(R.id.tvbranchselected)
    TextView tvbranchselected;
    @Bind(R.id.btnlogout)
    Button btnlogout;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.productList)
    RecyclerView productList;
    @Bind(R.id.btncart)
    Button btncart;

    ProductListAdapter productListAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        initialize();
        setListener();

        productListAdapter = new ProductListAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);

        productList.setLayoutManager(linearLayoutManager);
        productList.setAdapter(productListAdapter);

        addProductData();
    }

    private void addProductData() {
        if (ReuseableClass.haveNetworkConnection(this)) {
            progress.setVisibility(View.VISIBLE);

            MyApplication.addJobInBackground(new GetProductListJob(b.getString("categoryId")));
        } else
            Toast.makeText(this, R.string.error_internet_connection, Toast.LENGTH_LONG).show();
    }

    protected void initialize() {
        b = getIntent().getExtras();
        tvbranchselected.setText("Branch Selected: \n"
                + ReuseableClass.getFromPreference("branchName", this));
    }

    @OnClick(R.id.btnlogout)
    protected void logout() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(ProductActivity.this);
        dialog.setTitle("Logout");
        dialog.setMessage("Are you sure you want to logout?");
        dialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        ItemsCount o = new ItemsCount();
                        o.setlist();
                        Intent j = new Intent(ProductActivity.this, MainActivity.class);
                        startActivity(j);
                    }
                });

        dialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    @OnClick(R.id.btncart)
    protected void addingToCart() {
        ArrayList<Items> arrayList =
                new ArrayList<>(productListAdapter.getItemsHashMap().values());

        ItemsCount itemsCount = new ItemsCount();
        itemsCount.addAllItem(arrayList);

//        productListAdapter.getItemsHashMap();

        Intent i = new Intent(ProductActivity.this, ItemCategory.class);
        finish();
        startActivity(i);
    }

    protected void setListener() {
//		cart.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if(iPhone.isChecked()){
//					Items it = new Items();
//					it.setName(iPhone.getText().toString());
//					String price = tviPhone.getText().toString();
//					StringTokenizer st = new StringTokenizer(price, " ");
//
//					while(st.hasMoreTokens()){
//						String name = st.nextToken();
//						double price1 = Double.parseDouble(st.nextToken());
//						it.setPrice(price1);
//					}
//					if(etiPhoneQ.getText().toString().matches("^\\d+$")){
//						it.setQuantity(Integer.parseInt(etiPhoneQ.getText().toString()));
//					}else{
//						Toast.makeText(getApplicationContext(), "Enter Digits Only", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					ItemsCount obj = new ItemsCount();
//					obj.addItem(it);
//
//				}
//
//				if(samsung.isChecked()){
//					Items it = new Items();
//					it.setName(samsung.getText().toString());
//					String price = tvSamsung.getText().toString();
//					StringTokenizer st = new StringTokenizer(price, " ");
//
//					while(st.hasMoreTokens()){
//						String name = st.nextToken();
//						double price1 = Double.parseDouble(st.nextToken());
//						it.setPrice(price1);
//					}
//					if(etSamsungQ.getText().toString().matches("^\\d+$")){
//						it.setQuantity(Integer.parseInt(etSamsungQ.getText().toString()));
//					}else{
//						Toast.makeText(getApplicationContext(), "Enter Digits Only", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					ItemsCount obj = new ItemsCount();
//					obj.addItem(it);
//
//				}
//
//				Intent i = new Intent(ProductActivity.this, ItemCategory.class);
//				i.putExtra("branchName", b.getCharSequence("branchName"));
//				finish();
//				startActivity(i);
//
//			}
//
//		});

    }

//    public void onBackPressed() {
//        Intent i = new Intent(ProductActivity.this, ItemCategory.class);
//        i.putExtra("branchName", b.getCharSequence("branchName"));
//        finish();
//        startActivity(i);
//    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(ProductListEvent.Success event) {
        progress.setVisibility(View.INVISIBLE);

        if (event.getProductListResponses().size() > 0)
            productListAdapter.addingList(event.getProductListResponses());
        //else
        //textViewNoData.setVisibility(View.VISIBLE);
    }

    public void onEventMainThread(ProductListEvent.Fail event) {
        progress.setVisibility(View.INVISIBLE);
        if (event.getEx() != null) {
            new AlertDialog.Builder(this)
                    .setMessage(event.getEx().getMessage())
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}
