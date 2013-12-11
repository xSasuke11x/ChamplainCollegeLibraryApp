package edu.champlain.champlaincollegelibraryapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.os.AsyncTask;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private Spinner spinner1;
	private Button btnSubmit;
	private String jsonResult;
	private String url = "http://localhost:8080/android_connect/mysql_connect.php";
	private ListView listView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView = (ListView) findViewById(R.id.listView1);
		accessWebService();
        
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
    
    private class JsonReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
				response.getEntity().getContent()).toString();
			}
	 
			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
	   		}
			return null;
		}
	 
		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	 
			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}
	 
			catch (IOException e) {
	    // e.printStackTrace();
				Toast.makeText(getApplicationContext(),
					"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}
	 
		@Override
		protected void onPostExecute(String result) {
			ListDrawer();
		}
	}// end async task
 
	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
		// passes values for the urls string array
		task.execute(new String[] { url });
	}
 
	// build hash set for list view
	public void ListDrawer() {
		List<Map<String, String>> infoList = new ArrayList<Map<String, String>>();
 
		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("information");
 
			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String number = jsonChildNode.optString("Number");
				String title = jsonChildNode.optString("Title");
				//String authorOrPublisher = jsonChildNode.optString("Author Or Publisher");
				//String type = jsonChildNode.optString("Type");
				//String location = jsonChildNode.optString("Location");
				//String callNumberOrISBNNumber = jsonChildNode.optString("Call Number Or ISBN Number");
				//String status = jsonChildNode.optString("Status");
				//String outPut = number + "-" + title + "-" + authorOrPublisher + "-" + type + "-" + location + "-" + callNumberOrISBNNumber + "-" + status;
				String outPut = number + "-" + title;
				infoList.add(createItem("Item Information", outPut));
			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
			Toast.LENGTH_SHORT).show();
		}
 
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, infoList,
			android.R.layout.simple_list_item_1,
			new String[] { "Item Information" }, new int[] { android.R.id.text1 });
			listView.setAdapter(simpleAdapter);
	}
 
	private HashMap<String, String> createItem(String number, String title) {
		HashMap<String, String> hashInformation = new HashMap<String, String>();
		hashInformation.put(number, title);
		return hashInformation;
	}
    
}
