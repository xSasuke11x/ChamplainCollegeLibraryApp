package edu.champlain.champlaincollegelibraryapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity {

	private Spinner spinner1;
	private Button btnSubmit;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        addListenerOnButton();
    	addListenerOnSpinnerItemSelection();
    }
     
    public void addListenerOnSpinnerItemSelection() {
    	spinner1 = (Spinner) findViewById(R.id.spinner1);
    	spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
     
      // get the selected dropdown list value
	public void addListenerOnButton() {
     
    	spinner1 = (Spinner) findViewById(R.id.spinner1);
    	btnSubmit = (Button) findViewById(R.id.btnSubmit);
     
    	btnSubmit.setOnClickListener(new OnClickListener() {
     
	    	@Override
	    	public void onClick(View v) {
	     
	    		Toast.makeText(MainActivity.this,
	    		"OnClickListener : " + 
	                    "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()),
	    			Toast.LENGTH_SHORT).show();
	    	}
    	});
	}
      
	public class CustomOnItemSelectedListener implements OnItemSelectedListener {
		 
		public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
			Toast.makeText(parent.getContext(), 
				"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
				Toast.LENGTH_SHORT).show();
		}
		 
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
		 
	}  


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
