package edu.champlain.champlaincollegelibraryapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends Activity {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	        Connection conn =DriverManager.getConnection("jdbc:sqlserver://KENNEDY-PC\\SQLEXPRESS;databaseName=ChamplainLibrary");                   

	        System.out.println("connected");
	        Statement statement=conn.createStatement();
	        ResultSet resultSet=statement.executeQuery("select * from [user]");
	        while(resultSet.next()){
	            System.out.println(" "+resultSet.getString(1)+" "+resultSet.getNString(2));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
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
	
	// Submit button goes to next activity
	public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, ReturnScreen.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
