package com.fullsail.e_davila_advancedviews;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.fullsail.e_davila_advancedviews.R;


public class Activity extends ListActivity implements OnItemClickListener {
	
	  ArrayList<HashMap<String,String>> gameCollection = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ListView listView=getListView();
        listView.setOnItemClickListener(this);
        
        
        //Populate view with data
        gameCollection.add(displayVideoGame("PlayStation","FIFA 14"));
        gameCollection.add(displayVideoGame("PlayStation","Thief"));
        gameCollection.add(displayVideoGame("PlayStation","Watch Dogs"));
        gameCollection.add(displayVideoGame("PlayStation","Battlefield 4"));
        gameCollection.add(displayVideoGame("PlayStation","Second Sun"));
        gameCollection.add(displayVideoGame("PlayStation","Mario Bros"));
        

        
        
        //Calling the Simple Adapter calling gameCollection List and setting the predefined layout
        SimpleAdapter adapter = new SimpleAdapter(this,this.gameCollection, android.R.layout.simple_list_item_1, new String[] {"PlayStation"},new int[] {android.R.id.text1});
        //set adapter to collection
        System.out.println(gameCollection);
        setListAdapter(adapter);
    }
    
    //Create Private Method - Returns HashMap with key-value pairs
    private HashMap<String, String> displayVideoGame(String key, String value)
    {
    	HashMap<String, String> videoGameHashMap = new HashMap<String, String>();
    	videoGameHashMap.put(key,  value);
		return videoGameHashMap;
    	
    }
    //adapter view
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3)
    {
    	String item=adapter.getItemAtPosition(position).toString();
    	Toast.makeText(Activity.this, "You Click on:"+item, Toast.LENGTH_SHORT).show();
    	Intent intent = new Intent(this, MyOtherActivity.class);
    	intent.putExtra(MyOtherActivity.TEXT_TO_DISPLAY, item);
    	startActivity(intent);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
