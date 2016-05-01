package com.example.hypermarketpurchase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.events.GetBranchListEvent;
import com.example.jobs.GetBranchListJob;
import com.example.utils.ReuseableClass;
import com.example.widget.BranchListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class BranchActivity extends Activity {

    Intent i;
    @Bind(R.id.btnlogout)
    Button btnlogout;
    @Bind(R.id.branch_list)
    RecyclerView branchList;
    @Bind(R.id.progress)
    ProgressBar progress;

    LinearLayoutManager linearLayoutManager;
    BranchListAdapter branchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_branch);
        ButterKnife.bind(this);

        branchListAdapter = new BranchListAdapter(BranchActivity.this);
        branchList.setLayoutManager(linearLayoutManager);
        branchList.setHasFixedSize(true);
        linearLayoutManager = new GridLayoutManager(this, 2);
        branchList.setLayoutManager(linearLayoutManager);
        branchList.setAdapter(branchListAdapter);

        addBranchData();
    }

    private void addBranchData() {
        if (ReuseableClass.haveNetworkConnection(this)) {
            progress.setVisibility(View.VISIBLE);

            MyApplication.addJobInBackground(new GetBranchListJob());
        } else
            Toast.makeText(this, R.string.error_internet_connection, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btnlogout)
    protected void logout() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(BranchActivity.this);
        dialog.setTitle("Logout");
        dialog.setMessage("Are you sure you want to logout?");
        dialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        ItemsCount o = new ItemsCount();
                        o.setlist();
                        Intent j = new Intent(BranchActivity.this, MainActivity.class);
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

//    fujirah.setOnClickListener(new View.OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            i = new Intent(BranchActivity.this, ItemCategory.class);
//            finish();
//            i.putExtra("branchName", fujirah.getText().toString());
//            startActivity(i);
//        }
//    });


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

    public void onEventMainThread(GetBranchListEvent.Success event) {
        progress.setVisibility(View.INVISIBLE);

        if (event.getOrderListResponses().size() > 0)
            branchListAdapter.addingList(event.getOrderListResponses());
        //else
        //textViewNoData.setVisibility(View.VISIBLE);
    }

    public void onEventMainThread(GetBranchListEvent.Fail event) {
        progress.setVisibility(View.INVISIBLE);
        if (event.getEx() != null) {
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage(event.getEx().getMessage())
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}
