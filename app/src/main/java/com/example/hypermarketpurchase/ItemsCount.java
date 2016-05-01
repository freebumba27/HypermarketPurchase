package com.example.hypermarketpurchase;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

public class ItemsCount {

	private static int count = 0;
	private static Intent intent;
	private static Bundle bundle;
	private static ArrayList<Items> list = new ArrayList<Items>();

	
	public void setlist(){
		list = null;
		list = new ArrayList<Items>();
	}
	
	public void addItem(Items obj){
		list.add(obj);
	}
	
	public ArrayList<Items> getList(){
		return list;
	}
	
	public static Intent getIntent() {
		return intent;
	}

	public static void setIntent(Intent intent) {
		ItemsCount.intent = intent;
	}

	public static Bundle getBundle() {
		return bundle;
	}

	public static void setBundle(Bundle bundle) {
		ItemsCount.bundle = bundle;
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		ItemsCount.count = count;
	}
}
