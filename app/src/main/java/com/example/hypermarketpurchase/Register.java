package com.example.hypermarketpurchase;

import java.util.List;

import com.example.database.DatabaseHandler;
import com.example.database.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	EditText email, username, password;
	User user;
	List<User> users;
	DatabaseHandler db;
	Button signUp;
	boolean userValid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_sign_up_screen);
		initialize();
		setListener();

	}

	protected void initialize() {
		email = (EditText) findViewById(R.id.etEmail);
		username = (EditText) findViewById(R.id.etUserName);
		password = (EditText) findViewById(R.id.etPass);
		db = new DatabaseHandler(this);
		users = db.getAllContacts();
		user = new User();
		signUp = (Button) findViewById(R.id.btnSingUp);
		userValid = false;
	}

	protected void getValues() {
		boolean usernameExist = false;
		for (User u : users) {
			if (u.get_username().equals(username.getText().toString()) || u.get_email().equals(email.getText().toString())) {
				usernameExist = true;
				Toast.makeText(getApplicationContext(), "Username/Email already exists",
						Toast.LENGTH_SHORT).show();
				return;
			}
		}
		if (!isValidEmail(email.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Please Enter Valid Email",
					Toast.LENGTH_SHORT).show();
		} else if (password.getText().toString().length() < 4) {
			Toast.makeText(getApplicationContext(),
					"Password length should be at least 4 letters",
					Toast.LENGTH_SHORT).show();
		} else if (usernameExist) {
			Toast.makeText(getApplicationContext(), "Username already exists",
					Toast.LENGTH_SHORT).show();
		} else if (username.getText().toString().equals("")
				|| email.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "Please Fill all Fields",
					Toast.LENGTH_SHORT).show();
		} else {
			userValid = true;
			user.set_email(email.getText().toString());
			user.set_password(password.getText().toString());
			user.set_username(username.getText().toString());
		}

	}

	protected void setListener() {
		signUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getValues();
				if(userValid){
					db.addUser(user);
					Toast.makeText(getApplicationContext(), "Registered Successfully",
							Toast.LENGTH_SHORT).show();
					finish();
					Intent i = new Intent(Register.this,MainActivity.class);
					startActivity(i);
				}
			}
		});
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}
}
