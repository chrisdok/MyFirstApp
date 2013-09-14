package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {


	
	//---------------ON_CREATE-------------------------------------------------------------------
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

		String name = "";
		
		
		//-----------SAVE TO SQLITE-----------------------------------------------------------------------
		/*
		 * @author: http://stackoverflow.com/questions/3037767/create-sqlite-database-in-android
		 */
		
		
		SQLiteDatabase myDB= null;
		String TableName = "myTable";
		 
		  /* Create a Database. */
		  try {
		   myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);
		 
		   /* Create a Table in the Database. */
		   myDB.execSQL("CREATE TABLE IF NOT EXISTS "
		     + TableName
		     + " (Field1 VARCHAR);");
		 
		   
		  
		  TextView textView = new TextView(this);
		  textView.setTextSize(15);
		  textView.setText(message + " was saved.");
		
		  setContentView(textView);
		  
		  /* Insert data to a Table*/
		   myDB.execSQL("INSERT INTO "
		     + TableName
		     + " (Field1)"
		     + " VALUES ('"+message+"');");
		  }
		  catch(Exception e) {
		   Log.e("Error", "Error", e);
		  } finally {
		   if (myDB != null)
		    myDB.close();
		  }
	}
	
	//----------------------------------------------------------------------------------------------

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
