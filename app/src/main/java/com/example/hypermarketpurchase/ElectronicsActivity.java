package com.example.hypermarketpurchase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class ElectronicsActivity extends Activity {

	CheckBox iPhone, samsung;
	TextView tviPhone, tvSamsung, tvSelectedBranch;
	EditText etiPhoneQ, etSamsungQ;
	boolean iPhoneFlag, samsungFlag;
	Button logout, cart;
	Bundle b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_products);
		initialize();
		setListener();
	}

	protected void initialize() {
		iPhone = (CheckBox) findViewById(R.id.cbiPhone5s);
		samsung = (CheckBox) findViewById(R.id.cbSamsung6s);
		tviPhone = (TextView) findViewById(R.id.tviPhone5sPrice);
		tvSamsung = (TextView) findViewById(R.id.tvSamsung6sPrice);
		etiPhoneQ = (EditText) findViewById(R.id.etiPhone5s);
		etSamsungQ = (EditText) findViewById(R.id.etSamsung6s);
		iPhoneFlag = false;
		samsungFlag = false;
		logout = (Button) findViewById(R.id.btnlogout);
		cart = (Button) findViewById(R.id.btncart);
		tvSelectedBranch = (TextView) findViewById(R.id.tvbranchselected);
		b = getIntent().getExtras();
		tvSelectedBranch.setText("Branch Selected: \n"
				+ b.getCharSequence("branchName"));
	}

	protected void setListener() {
		iPhone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iPhoneFlag = !iPhoneFlag;
				if (iPhoneFlag) {
					etiPhoneQ.setVisibility(View.VISIBLE);
				} else {
					etiPhoneQ.setVisibility(View.INVISIBLE);
				}
			}
		});

		samsung.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				samsungFlag = !samsungFlag;
				if (samsungFlag) {
					etSamsungQ.setVisibility(View.VISIBLE);
				} else {
					etSamsungQ.setVisibility(View.INVISIBLE);
				}
			}
		});
		
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						ElectronicsActivity.this);
				dialog.setTitle("Logout");
				dialog.setMessage("Are you sure you want to logout?");
				dialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
								ItemsCount o = new ItemsCount();
								o.setlist();
								Intent j = new Intent(ElectronicsActivity.this,
										MainActivity.class);
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
		
		cart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(iPhone.isChecked()){
					Items it = new Items();
					it.setName(iPhone.getText().toString());
					String price = tviPhone.getText().toString();
					StringTokenizer st = new StringTokenizer(price, " ");

					while(st.hasMoreTokens()){
						String name = st.nextToken();
						double price1 = Double.parseDouble(st.nextToken());
						it.setPrice(price1);
					}
					if(etiPhoneQ.getText().toString().matches("^\\d+$")){
						it.setQuantity(Integer.parseInt(etiPhoneQ.getText().toString()));
					}else{
						Toast.makeText(getApplicationContext(), "Enter Digits Only", Toast.LENGTH_SHORT).show();
						return;
					}
					ItemsCount obj = new ItemsCount();
					obj.addItem(it);
					
				}
				
				if(samsung.isChecked()){
					Items it = new Items();
					it.setName(samsung.getText().toString());
					String price = tvSamsung.getText().toString();
					StringTokenizer st = new StringTokenizer(price, " ");

					while(st.hasMoreTokens()){
						String name = st.nextToken();
						double price1 = Double.parseDouble(st.nextToken());
						it.setPrice(price1);
					}
					if(etSamsungQ.getText().toString().matches("^\\d+$")){
						it.setQuantity(Integer.parseInt(etSamsungQ.getText().toString()));
					}else{
						Toast.makeText(getApplicationContext(), "Enter Digits Only", Toast.LENGTH_SHORT).show();
						return;
					}
					ItemsCount obj = new ItemsCount();
					obj.addItem(it);
					
				}
				
				Intent i = new Intent(ElectronicsActivity.this, ItemCategory.class);
				i.putExtra("branchName", b.getCharSequence("branchName"));
				finish();
				startActivity(i);
				
			}
			
		});
		
	}
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(ElectronicsActivity.this, ItemCategory.class);
		i.putExtra("branchName", b.getCharSequence("branchName"));
		finish();
		startActivity(i);
	}
}
