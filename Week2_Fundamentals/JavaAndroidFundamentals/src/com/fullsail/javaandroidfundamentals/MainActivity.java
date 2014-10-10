package com.fullsail.javaandroidfundamentals;

import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	// Global Scope
	TextView aButton; 
	TextView sButton;
	TextView text2;
	TextView text1;
	EditText eText;
	
	// HashSet Array Declaration
	HashSet<String> list = new HashSet<String>();

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        //attaching to xml names
        aButton = (TextView) this.findViewById(R.id.button1);
        aButton.setGravity(Gravity.CENTER);
        
        text2 = (TextView) this.findViewById(R.id.textView1);
        
        
        text1 = (TextView) this.findViewById(R.id.textView2);
        text1.setGravity(Gravity.CENTER);
        
        
        //Click on Saved Input will display alert
        text2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Complete!", Toast.LENGTH_LONG).show();
			}
		});
        
        // listener for show items event on emulator
        aButton.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				
		    	list.add("Books");
		    	list.add("Newspapers");
		    	list.add("Magazines");
		    	
		    	
		    	
		    	// Size of HashSet from int converted to string for TextView display
		    	System.out.println("Sise of HashSet : " + list.size()); // to console
		    	text1.setText(list.size() + " Stored items "); // to TextView
		    	
		    	//Display all contents
		    	String listString = "";
		    	
		    	//for loop
		        for (String s : list) {
		            listString += s + " - ";
		        }
		        //set string to view
		        text2.setText(listString);
		    	}
			});
        
        
        //Save Entered text Section
        //attaching to xml names
        sButton = (TextView) this.findViewById(R.id.button2);
        sButton.setGravity(Gravity.CENTER);
        
        eText = (EditText) this.findViewById(R.id.editText1);
        
        
        //Save listener
        
        sButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				
				
				 //Log.v("EditText", eText.getText().toString());
				if( !list.add(eText.getText().toString()) )
				{
					System.out.println("Not Unique Item");
					Toast.makeText(getApplicationContext(), "Already Saved!", Toast.LENGTH_LONG).show();
				} else 
				{
					System.out.println("Unique Entry Added");
					Toast.makeText(getApplicationContext(), "Saved To Items.", Toast.LENGTH_LONG).show();
				}
				//Resets EditText to placeholder
				eText.setText("");
			}
          });
    	}
    //custom method
    //custom method to get median UNRESPONSIVE ---- TESTING --- FAILED.
//    public double getMedian(double[] doubleArray, List list){
//    	
//    	int factor = list.length - 1;
//    	double[] first = new double[(int) ((double) factor / 2)];
//    	double[] last = new double[first.length];
//    	double[] middleNumbers = new double[1];
//    	for(int i = 0; i < first.length; i++){
//    		first[i] = list[i];
//    	}
//    	 for (int i = list.length; i > last.length; i--) {
//    	        last[i] = list[i];
//    	 }
//    	 
//    	 for (int i = 0; i <= list.length; i++) {
//    	        if (list[i] != first[i] || list[i] != last[i]) middleNumbers[i] = list[i];
//    	 }
//    	 if (list.length % 2 == 0) {
//    	        double total = middleNumbers[0] + middleNumbers[1];
//    	        return total / 2;
//    	    } else {
//    	    	System.out.println(middleNumbers);
//    	        return middleNumbers[0];
//    	    }
//    	
//   
//    	
//    }
    	
		
    	
    	
   
    	
    
}
