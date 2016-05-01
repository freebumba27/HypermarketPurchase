package com.example.hypermarketpurchase;

import java.util.StringTokenizer;

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

public class HealthCareActivity extends Activity {

	CheckBox Lipstick, FairnLovely;
	TextView tvLipstick, tvFairnLovely, tvSelectedBranch;
	EditText etLipstickQ, etFairnLovelyQ;
	boolean LipstickFlag, FairnLovelyFlag;
	Button logout, cart;
	Bundle b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_healthcare);
		initialize();
		setListener();
	}
	
	protected void initialize() {
		Lipstick = (CheckBox) findViewById(R.id.cbLipstick);
		FairnLovely = (CheckBox) findViewById(R.id.cbFairnLovely);
		tvLipstick = (TextView) findViewById(R.id.tvLipstickPrice);
		tvFairnLovely = (TextView) findViewById(R.id.tvFairnLovelyPrice);
		etLipstickQ = (EditText) findViewById(R.id.etLipstickQuan);
		etFairnLovelyQ = (EditText) findViewById(R.id.etFairnLovelyQuan);
		LipstickFlag = false;
		FairnLovelyFlag = false;
		logout = (Button) findViewById(R.id.btnlogout);
		cart = (Button) findViewById(R.id.btncart);
		tvSelectedBranch = (TextView) findViewById(R.id.tvbranchselected);
		b = getIntent().getExtras();
		tvSelectedBranch.setText("Branch Selected: \n"
				+ b.getCharSequence("branchName"));
	}

	protected void setListener() {
		Lipstick.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LipstickFlag = !LipstickFlag;
				if (LipstickFlag) {
					etLipstickQ.setVisibility(View.VISIBLE);
				} else {
					etLipstickQ.setVisibility(View.INVISIBLE);
				}
			}
		});

		FairnLovely.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FairnLovelyFlag = !FairnLovelyFlag;
				if (FairnLovelyFlag) {
					etFairnLovelyQ.setVisibility(View.VISIBLE);
				} else {
					etFairnLovelyQ.setVisibility(View.INVISIBLE);
				}
			}
		});
		
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						HealthCareActivity.this);
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
								Intent j = new Intent(HealthCareActivity.this,
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
				if(Lipstick.isChecked()){
					Items it = new Items();
					it.setName(Lipstick.getText().toString());
					String price = tvLipstick.getText().toString();
					StringTokenizer st = new StringTokenizer(price, " ");

					while(st.hasMoreTokens()){
						String name = st.nextToken();
						double price1 = Double.parseDouble(st.nextToken());
						it.setPrice(price1);
					}
					if(etLipstickQ.getText().toString().matches("^\\d+$")){
						it.setQuantity(Integer.parseInt(etLipstickQ.getText().toString()));
					}else{
						Toast.makeText(getApplicationContext(), "Enter Digits Only", Toast.LENGTH_SHORT).show();
						return;
					}
					ItemsCount obj = new ItemsCount();
					obj.addItem(it);
					
				}
				
				if(FairnLovely.isChecked()){
					Items it = new Items();
					it.setName(FairnLovely.getText().toString());
					String price = tvFairnLovely.getText().toString();
					StringTokenizer st = new StringTokenizer(price, " ");

					while(st.hasMoreTokens()){
						String name = st.nextToken();
						double price1 = Double.parseDouble(st.nextToken());
						it.setPrice(price1);
					}
					if(etFairnLovelyQ.getText().toString().matches("^\\d+$")){
						it.setQuantity(Integer.parseInt(etFairnLovelyQ.getText().toString()));
					}else{
						Toast.makeText(getApplicationContext(), "Enter Digits Only", Toast.LENGTH_SHORT).show();
						return;
					}
					ItemsCount obj = new ItemsCount();
					obj.addItem(it);
					
				}
				
				Intent i = new Intent(HealthCareActivity.this, ItemCategory.class);
				i.putExtra("branchName", b.getCharSequence("branchName"));
				finish();
				startActivity(i);
				
			}
			
		});
		
	}
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(HealthCareActivity.this, ItemCategory.class);
		finish();
		startActivity(i);
	}

}
