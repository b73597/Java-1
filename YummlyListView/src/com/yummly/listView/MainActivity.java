package com.yummly.listView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yummly.utils.ResponseGenerator;

public class MainActivity extends Activity {
	ListView list;
	TextView receipeView;
	TextView ingredientsView;
	EditText searchText;
	Button searchButton;
	ArrayList<HashMap<String, String>> receipeList;

	//Yummly account details here (app ID and app Key)

	public static final String APP_ID = "d4c0ac95";
	public static final String APP_KEY = "421961af8f79b99be0d64fb1b1414229";

	// URL to get JSON Array
	private static String url = "http://api.yummly.com/v1/api/recipes?_app_id="
			+ APP_ID + "&_app_key=" + APP_KEY;

	// Constants Declaration, Since these are being used multiple times make them final and use
	private static final String STRING_RECEIPE = "RECEIPE";
	private static final String STRING_INGREDIENTS = "INGREDIENTS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);
		receipeList = new ArrayList<HashMap<String, String>>();

		// Creates the search button and adds a listener to perform it's tasks
		searchButton = (Button) findViewById(R.id.searchBtn);
		searchButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (isOnline()) {
					//System.out.println("Network available");
					//Check for available connection
					Toast.makeText(getBaseContext(), "Connected to Network" , Toast.LENGTH_SHORT ).show();
					new JSONParse().execute(); // Here it calls the JSONParse, with Async Task
				} else {
					//System.out.println("Network isnt available");
					// Will display if no connection is available
					Toast.makeText(getBaseContext(), "Network is not Available" , Toast.LENGTH_SHORT ).show();
				}
										
			}
		});

	}
	
	//Check for connectivity
	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}
	//AsyncTask
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog progressDialog;

		// This method runs First, it kind of prepares the things. Method displays the progress Bar and also to clear previously
		// listed Items
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			receipeView = (TextView) findViewById(R.id.receipe);
			ingredientsView = (TextView) findViewById(R.id.ingredients);
			//Progress indicator with text
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Retrieving Data from Yummly ...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(true);
			progressDialog.show();
			// Clears the List to avoid the new content being appended to the
			// existing content
			receipeList.clear();
		}

		// This method runs (Secondly) during the execution in the background,
		// so we use it to fetch and load data form Yummly API
		@Override
		protected JSONObject doInBackground(String... args) {

			ResponseGenerator jParser = new ResponseGenerator();

			// Retrieves the Search Text
			searchText = (EditText) findViewById(R.id.searchText);
			String query = searchText.getText().toString();

			// Checks weather user has included a search String, if not
			// parameter 'q' will not be incorporated for the API Call

			String completeUrl = url;
			if (query != null && !("".equals(query))) {
				// We Should always Encode the URL , Didn't encode the entire
				// URL since its hard coded on top 
				try {
					completeUrl += "&q=" + URLEncoder.encode(query, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Getting JSON from URL
			JSONObject json = jParser.getJSONFromUrl(completeUrl);
			return json;
		}

		// This runs last (Third). Here it dismisses the progress bar first, and
		// then clears the user entered data in the search field, and populates
		// the extracted data from ResponseGenerator
		@Override
		protected void onPostExecute(JSONObject json) {
			progressDialog.dismiss();

			try {

				// Observe a sample response from the SearchReceipe for better understanding
				

				// Retrieves 'matches' JSON Array
				JSONArray array = json.getJSONArray("matches");

				for (int i = 0; i < array.length(); i++) {

					JSONObject matchObject = array.getJSONObject(i);
					JSONArray ingrediants = matchObject
							.getJSONArray("ingredients");
					HashMap<String, String> map = new HashMap<String, String>();
					StringBuilder ingrediantsBuilder = new StringBuilder();

					// Receive all the ingredients one by one and adds a
					// return between two of them to list them down one
					// by one
					
					for (int j = 0; j < ingrediants.length(); j++) {
						ingrediantsBuilder.append(ingrediants.getString(j)
								+ "\n");
					}

					// Maintains a map containing the Recipe Name (Bold Strings
					// in List) and ingredients list
					map.put(STRING_RECEIPE, matchObject.getString("id"));
					map.put(STRING_INGREDIENTS, ingrediantsBuilder.toString());

					// Adds all the items to the map
					receipeList.add(map);
					list = (ListView) findViewById(R.id.list);

					// Adds the recipe List dynamically to the UI
					ListAdapter adapter = new SimpleAdapter(MainActivity.this,
							receipeList, R.layout.list_view, new String[] {
									STRING_RECEIPE, STRING_INGREDIENTS },
							new int[] { R.id.receipe, R.id.ingredients });

					list.setAdapter(adapter);
				}

				// Clears the Search text after populating data, so user can
				// type in a new search
				searchText.setText("");

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

}
