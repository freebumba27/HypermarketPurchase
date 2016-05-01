package com.example.database;

public class User {

	// private variables
	int _id;
	String _email;
	String _username;
	String _password;

	// Empty constructor
	public User() {
		// TODO Auto-generated constructor stub
	}

	// constructor

	public User(int _id, String _email, String _username, String _password) {

		this._id = _id;
		this._email = _email;
		this._username = _username;
		this._password = _password;
	}

	// constructor
	public User(String _email, String _username, String _password) {

		this._email = _email;
		this._username = _username;
		this._password = _password;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_email() {
		return _email;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

	public String get_username() {
		return _username;
	}

	public void set_username(String _username) {
		this._username = _username;
	}

	public String get_password() {
		return _password;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	@Override
	public String toString() {
		return "User [_id=" + _id + ", _email=" + _email + ", _username="
				+ _username + ", _password=" + _password + "]";
	}


}
