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

public class TvAudioActivity extends Activity {

	CheckBox sony, audionic;
	TextView tvsony, tvaudionic, tvSelectedBranch;
	EditText etsonyQ, etaudionicQ;
	boolean sonyFlag, audionicFlag;
	Button logout, cart;
	Bundle b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_tvaudio);
		initialize();
		setListener();
	}
	protected void initialize() {
		sony = (CheckBox) findViewById(R.id.cbSonyLED);
		audionic = (CheckBox) findViewById(R.id.cbAudionic);
		tvsony = (TextView) findViewById(R.id.tvSonyLEDPrice);
		tvaudionic = (TextView) findViewById(R.id.tvAudionicPrice);
		etsonyQ = (EditText) findViewById(R.id.etSonyLEDQuan);
		etaudionicQ = (EditText) findViewById(R.id.etAudionicQuan);
		sonyFlag = false;
		audionicFlag = false;
		logout = (Button) findViewById(R.id.btnlogout);
		cart = (Button) findViewById(R.id.btncart);
		tvSelectedBranch = (TextView) findViewById(R.id.tvbranchselected);
		b = getIntent().getExtras();
		tvSelectedBranch.setText("Branch Selected: \n"
				+ b.getCharSequence("branchName"));
	}

	protected void setListener() {
		sony.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sonyFlag = !sonyFlag;
				if (sonyFlag) {
					etsonyQ.setVisibility(View.VISIBLE);
				} else {
					etsonyQ.setVisibility(View.INVISIBLE);
				}
			}
		});

		audionic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				audionicFlag = !audionicFlag;
				if (audionicFlag) {
					etaudionicQ.setVisibility(View.VISIBLE);
				} else {
					etaudionicQ.setVisibility(View.INVISIBLE);
				}
			}
		});
		
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						TvAudioActivity.this);
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
								Intent j = new Intent(TvAudioActivity.this,
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
				if(sony.isChecked()){
					Items it = new Items();
					it.setName(sony.getText().toString());
					String price = tvsony.getText().toString();
					StringTokenizer st = new StringTokenizer(price, " ");

					while(st.hasMoreTokens()){
						String name = st.nextToken();
						double price1 = Double.parseDouble(st.nextToken());
						it.setPrice(price1);
					}
					if(etsonyQ.getText().toString().matches("^\\d+$")){
						it.setQuantity(Integer.parseInt(etsonyQ.getText().toString()));
					}else{
						Toast.makeText(getApplicationContext(), "Enter Digits Only", Toast.LENGTH_SHORT).show();
						return;
					}
					ItemsCount obj = new ItemsCount();
					obj.addItem(it);
					
				}
				
				if(audionic.isChecked()){
					Items it = new Items();
					it.setName(audionic.getText().toString());
					String price = tvaudionic.getText().toString();
					StringTokenizer st = new StringTokenizer(price, " ");

					while(st.hasMoreTokens()){
						String name = st.nextToken();
						double price1 = Double.parseDouble(st.nextToken());
						it.setPrice(price1);
					}
					if(etaudionicQ.getText().toString().matches("^\\d+$")){
						it.setQuantity(Integer.parseInt(etaudionicQ.getText().toString()));
					}else{
						Toast.makeText(getApplicationContext(), "Enter Digits Only", Toast.LENGTH_SHORT).show();
						return;
					}
					ItemsCount obj = new ItemsCount();
					obj.addItem(it);
					
				}
				
				Intent i = new Intent(TvAudioActivity.this, ItemCategory.class);
				i.putExtra("branchName", b.getCharSequence("branchName"));
				finish();
				startActivity(i);
				
			}
			
		});
		
	}
	
	
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(TvAudioActivity.this, ItemCategory.class);
		finish();
		startActivity(i);
	}
}
