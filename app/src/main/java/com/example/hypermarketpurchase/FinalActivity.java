package com.example.hypermarketpurchase;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FinalActivity extends Activity {

	Button logout, home;
	TextView tvSelectedBranch, totalAmount;
	Bundle b;
	ItemsCount obj = new ItemsCount();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_final);
		
		initialize();
		setListeners();
		calculateTotal();
	}
	
	protected void initialize(){
		logout = (Button) findViewById(R.id.btnlogout);
		home = (Button) findViewById(R.id.btnHome);
		tvSelectedBranch = (TextView) findViewById(R.id.tvbranchselected);
		b = getIntent().getExtras();
		tvSelectedBranch.setText("Branch Selected: \n"
				+ b.getCharSequence("branchName"));
		totalAmount = (TextView) findViewById(R.id.tvtotalamount);
		
	}
	
	protected void setListeners(){
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						FinalActivity.this);
				dialog.setTitle("Logout");
				dialog.setMessage("Are you sure you want to logout?");
				dialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
								Intent j = new Intent(FinalActivity.this,
										MainActivity.class);
								obj.setlist();
								startActivity(j);
							}
						});

				dialog.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				dialog.show();

			}
		});
		
		home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
	
	protected void calculateTotal(){
		double total=0;
		ArrayList<Items> l = obj.getList();
		//Toast.makeText(getApplicationContext(), "Your count is " + l.size(), Toast.LENGTH_SHORT).show();
		for(int i=0; i<l.size(); i++ ){
			total += l.get(i).getPrice()*l.get(i).getQuantity();
		}
		String str = total + "";
		totalAmount.setText(str);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent i = new Intent(FinalActivity.this, ItemCategory.class);
		i.putExtra("branchName", b.getCharSequence("branchName"));
		finish();
		startActivity(i);
	}
}
