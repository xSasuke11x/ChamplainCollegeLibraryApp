package edu.champlain.champlaincollegelibraryapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReturnScreen extends Activity {

	public class NewActivity extends Activity {

	     @Override
	     protected void onCreate(Bundle savedInstanceState) {
	         super.onCreate(savedInstanceState);
	         setContentView(R.layout.return_screen);
	     }
	}
	
	// Return button goes to next activity
		public void onButtonClick(View view) {
	        Intent intent = new Intent(ReturnScreen.this, MainActivity.class);
	        startActivity(intent);
	    }
}