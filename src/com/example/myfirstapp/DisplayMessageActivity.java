package com.example.myfirstapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		int age = 0;
		String data = "";
		String name = "";
		
		//----------------------------------------------------------------------------------
		
		
		
		// Initializing instance variables
		List headlines = new ArrayList();
		List links = new ArrayList();
		 
		try {
		    URL url = new URL("http://blog.hig.no/mobile/feed/");
		 
		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    factory.setNamespaceAware(false);
		    XmlPullParser xpp = factory.newPullParser();
		 
		        // We will get the XML from an input stream
		    xpp.setInput(getInputStream(url), "UTF_8");
		 
		        /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
		         * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
		         * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
		         * so we should skip the "<title>" tag which is a child of "<channel>" tag,
		         * and take in consideration only "<title>" tag which is a child of "<item>"
		         *
		         * In order to achieve this, we will make use of a boolean variable.
		         */
		    boolean insideItem = false;
		 
		        // Returns the type of current event: START_TAG, END_TAG, etc..
		    int eventType = xpp.getEventType();
		    while (eventType != XmlPullParser.END_DOCUMENT) {
		        if (eventType == XmlPullParser.START_TAG) {
		 
		            if (xpp.getName().equalsIgnoreCase("item")) {
		                insideItem = true;
		            } else if (xpp.getName().equalsIgnoreCase("title")) {
		                if (insideItem)
		                    headlines.add(xpp.nextText()); //extract the headline
		            } else if (xpp.getName().equalsIgnoreCase("link")) {
		                if (insideItem)
		                    links.add(xpp.nextText()); //extract the link of article
		            }
		        }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
		            insideItem=false;
		        }
		 
		        eventType = xpp.next(); //move to next element
		    }
		 
		} catch (MalformedURLException e) {
		    e.printStackTrace();
		} catch (XmlPullParserException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		
		
		//----------------------------------------------------------------------------------
		
		SQLiteDatabase myDB= null;
		String TableName = "myTable";
		 
		 
		  /* Create a Database. */
		  try {
		   myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);
		 
		   /* Create a Table in the Database. */
		   myDB.execSQL("CREATE TABLE IF NOT EXISTS "
		     + TableName
		     + " (Field1 VARCHAR, Field2 INT(3));");
		 

		   /*retrieve data from database*/ 
		   Cursor c = myDB.rawQuery("SELECT * FROM " + TableName , null);
		 
		   int Column1 = c.getColumnIndex("Field1");
		   int Column2 = c.getColumnIndex("Field2");
		 
		   // Check if our result was valid.
		   c.moveToFirst();
		   if (c != null) {
		    // Loop through all Results
		    do {
		     name = c.getString(Column1);
		     age = c.getInt(Column2);
		     data =data +name+"/"+age+"\n";
		    }while(c.moveToNext());
		   }
		   
		  
		  TextView textView = new TextView(this);
		  textView.setTextSize(15);
		  textView.setText(name + " was the previous value, " + message + " is the current.");
		
		  setContentView(textView);
		  
		  /* Insert data to a Table*/
		   myDB.execSQL("INSERT INTO "
		     + TableName
		     + " (Field1, Field2)"
		     + " VALUES ('"+message+"', 22);");
		  }
		  catch(Exception e) {
		   Log.e("Error", "Error", e);
		  } finally {
		   if (myDB != null)
		    myDB.close();
		  }
	}

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
	
	public void save() {
		
	}
	

 
	public InputStream getInputStream(URL url) {
		   try {
		       return url.openConnection().getInputStream();
		   } catch (IOException e) {
		       return null;
		     }
		}


}
