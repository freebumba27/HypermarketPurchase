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
import android.widget.TextView;
import android.widget.Toast;

public class ItemCategory extends Activity {

	TextView tvSelectedBranch;
	Bundle b;
	Button logout, electronics, homeApp, tvAudio, babyCare, beauty, done;
	ItemsCount obj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_category);
		initialize();
		setListeners();

	}

	protected void initialize() {
		tvSelectedBranch = (TextView) findViewById(R.id.tvbranchselected);
		b = getIntent().getExtras();
		tvSelectedBranch.setText("Branch Selected: \n"
				+ b.getCharSequence("branchName"));
		logout = (Button) findViewById(R.id.btnlogout);
		electronics = (Button) findViewById(R.id.btnElectronics);
		// homeApp = (Button) findViewById(R.id.btnHomeApp);
		// babyCare = (Button) findViewById(R.id.btnBabyCare);
		beauty = (Button) findViewById(R.id.btnbeauty);
		tvAudio = (Button) findViewById(R.id.btntvAudio);
		done = (Button) findViewById(R.id.btndone);
		obj = new ItemsCount();
		obj.setBundle(b);
		obj.setIntent(getIntent());
	}

	protected void setListeners() {

		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						ItemCategory.this);
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
								Intent j = new Intent(ItemCategory.this,
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

		electronics.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ItemCategory.this,
						ElectronicsActivity.class);
				i.putExtra("branchName", b.getCharSequence("branchName"));
				finish();
				startActivity(i);
			}
		});

		tvAudio.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ItemCategory.this, TvAudioActivity.class);
				i.putExtra("branchName", b.getCharSequence("branchName"));
				finish();
				startActivity(i);

			}
		});

		beauty.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ItemCategory.this,
						HealthCareActivity.class);
				i.putExtra("branchName", b.getCharSequence("branchName"));
				finish();
				startActivity(i);

			}
		});

		done.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

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
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(ItemCategory.this, BranchActivity.class);
		ItemsCount o = new ItemsCount();
		o.setlist();
		finish();
		startActivity(i);
	}

}
