package com.example.hypermarketpurchase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.database.DatabaseHandler;
import com.example.database.User;
import com.example.utils.ReuseableClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    Button register, login;
    User user;
    DatabaseHandler db;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in_screen);
        initialize();
        setListeners();
        DatabaseHandler db = new DatabaseHandler(this);
        List<User> l = db.getAllContacts();
        if (l.size() > 0) {
            for (User u : l) {
                Log.e("user" + u.get_id(), " " + u.toString());
                //System.out.println(u.toString());
            }
        } else {
            Log.e("listerror", "count is 0");
        }
    }

    public void initialize() {
        register = (Button) findViewById(R.id.btnRegister);
        login = (Button) findViewById(R.id.btnSignIn);
        user = null;
        db = new DatabaseHandler(this);
        username = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPass);
    }

    public void setListeners() {
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please FIll all Fields",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.getText().toString().length() < 4) {
                    Toast.makeText(getApplicationContext(), "Password length should be at least 4 letters",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<User> ls = db.getAllContacts();
                String us = username.getText().toString();
                String pw = password.getText().toString();
                for (int i = 0; i < ls.size(); i++) {
                    if (ls.get(i).get_password().equals(pw) && ls.get(i).get_username().equals(us)) {
                        user = new User();
                        user.set_password(pw);
                        user.set_username(us);
                        ReuseableClass.saveInPreference("email", ls.get(i).get_email(), MainActivity.this);
                    }
                }


                if (user == null) {
                    Toast.makeText(getApplicationContext(), "Please Register First",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (user != null) {
                    Log.e("userDetails", user.toString());
                    if (user.get_password().equals(password.getText().toString()) && user.get_username().equals(username.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Authenticated",
                                Toast.LENGTH_SHORT).show();
                        finish();

                        Intent i = new Intent(MainActivity.this, BranchActivity.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Credentials",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
