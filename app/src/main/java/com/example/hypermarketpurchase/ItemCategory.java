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

import com.example.events.GetBranchListEvent;
import com.example.events.GetCategoryListEvent;
import com.example.jobs.GetCategoryListJob;
import com.example.utils.ReuseableClass;
import com.example.widget.CategoryListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ItemCategory extends Activity {

    TextView tvSelectedBranch;
    Bundle b;
    Button logout, done;
    ItemsCount obj;

    @Bind(R.id.categoryList)
    RecyclerView categoryList;
    @Bind(R.id.progress)
    ProgressBar progress;

    CategoryListAdapter categoryListAdapter;
    LinearLayoutManager linearLayoutManager;
    @Bind(R.id.btnlogout)
    Button btnlogout;
    @Bind(R.id.tvbranchselected)
    TextView tvbranchselected;
    @Bind(R.id.btndone)
    Button btndone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        initialize();

        categoryListAdapter = new CategoryListAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);

        categoryList.setLayoutManager(linearLayoutManager);
        categoryList.setAdapter(categoryListAdapter);

        addCategoryData();
    }

    private void addCategoryData() {
        if (ReuseableClass.haveNetworkConnection(this)) {
            progress.setVisibility(View.VISIBLE);

            MyApplication.addJobInBackground(new GetCategoryListJob());
        } else
            Toast.makeText(this, R.string.error_internet_connection, Toast.LENGTH_LONG).show();
    }

    protected void initialize() {
        tvSelectedBranch = (TextView) findViewById(R.id.tvbranchselected);
        b = getIntent().getExtras();
        tvSelectedBranch.setText("Branch Selected: \n"
                + b.getCharSequence("branchName"));

        obj = new ItemsCount();
        obj.setBundle(b);
        obj.setIntent(getIntent());
    }

    @OnClick(R.id.btnlogout)
    protected void logout() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(ItemCategory.this);
        dialog.setTitle("Logout");
        dialog.setMessage("Are you sure you want to logout?");
        dialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        ItemsCount o = new ItemsCount();
                        o.setlist();
                        Intent j = new Intent(ItemCategory.this, MainActivity.class);
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

    @OnClick(R.id.btndone)
    protected void btndone() {

        if (obj.getList() == null || obj.getList().size() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please buy something", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent i = new Intent(ItemCategory.this,
                    FinalActivity.class);
            i.putExtra("branchName", b.getCharSequence("branchName"));
            finish();
            startActivity(i);
        }
    }

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

    public void onEventMainThread(GetCategoryListEvent.Success event) {
        progress.setVisibility(View.INVISIBLE);

        if (event.getCategoryListResponses().size() > 0)
            categoryListAdapter.addingList(event.getCategoryListResponses());
        //else
        //textViewNoData.setVisibility(View.VISIBLE);
    }

    public void onEventMainThread(GetBranchListEvent.Fail event) {
        progress.setVisibility(View.INVISIBLE);
        if (event.getEx() != null) {
            new AlertDialog.Builder(this)
                    .setMessage(event.getEx().getMessage())
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

//    @Override
//    public void onBackPressed() {
//        Intent i = new Intent(ItemCategory.this, BranchActivity.class);
//        ItemsCount o = new ItemsCount();
//        o.setlist();
//        finish();
//        startActivity(i);
//    }
}
