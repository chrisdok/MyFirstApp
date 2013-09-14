package com.example.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	//----------------SAVE_EDITTEXT_STATE------------------------------------------------------------------
	
	//Saves the value of the text field as a String
	@Override
	public void onPause() {
		super.onPause();
		SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		EditText text = (EditText)findViewById(R.id.edit_message);
		String textValue = text.getText().toString();
		
		editor.putString("EditTextString", textValue);
		editor.commit();
	}
	
	//Sets the value of the text field if an value has been saved via onPause()
	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
		String textValue = sharedPreferences.getString("EditTextString", "");
		EditText text = (EditText)findViewById(R.id.edit_message);
		text.setText(textValue);
	}
	
	//-----------------------------------------------------------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);		  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Sends message to a new activity, where it displays and saves in the local SQLite database
	public void sendMessage(View view){
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
	//Downloads an image and shows it in a new activity
	public void viewImage(View view) {
		Intent intent = new Intent(this, ShowImage.class);
		startActivity(intent);
	}
	
	public void viewMap(View view) {
		Intent intent = new Intent(this, ViewMap.class);
		startActivity(intent);
	}
}