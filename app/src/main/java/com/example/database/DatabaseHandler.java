package com.example.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "usersManager";

	// Contacts table name
	private static final String TABLE_USERS = "users";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASSWORD = "password";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
				+ KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, user.get_username()); // Contact Name
		values.put(KEY_EMAIL, user.get_email()); // Contact Phone
		values.put(KEY_PASSWORD, user.get_password());
		// Inserting Row
		db.insert(TABLE_USERS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	public User getUser(String username) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
		try{
		
		 cursor = db.query(TABLE_USERS, new String[] { KEY_ID,
				KEY_USERNAME, KEY_EMAIL, KEY_PASSWORD }, KEY_USERNAME + "=?",
				new String[] { String.valueOf(username) }, null, null, null, null);
		}catch(Exception e){
			User u = null;
			return u;
		}
		if (cursor != null)
			cursor.moveToFirst();
		else
			return null;

		User user = new User(Integer.parseInt(cursor.getString(0)),
				cursor.getString(2), cursor.getString(1), cursor.getString(3));
		// return contact
		return user;
	}

	// Getting All Contacts
	public ArrayList<User> getAllContacts() {
		ArrayList<User> contactList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User contact = new User();
				contact.set_id(Integer.parseInt(cursor.getString(0)));
				contact.set_username(cursor.getString(1));
				contact.set_email(cursor.getString(2));
				contact.set_password(cursor.getString(3));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	// Updating single contact
	public int updateContact(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, user.get_username());
		values.put(KEY_EMAIL, user.get_email());
		values.put(KEY_PASSWORD, user.get_password());

		// updating row
		return db.update(TABLE_USERS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(user.get_id()) });
	}

	// Deleting single contact
	public void deleteContact(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USERS, KEY_ID + " = ?",
				new String[] { String.valueOf(user.get_id()) });
		db.close();
	}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
