package com.fullsail.e_davila_advancedviews;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MyOtherActivity extends Activity {
	public static final String TEXT_TO_DISPLAY = "PickSomeTextToUseThatIsClear";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_other_activity);
		
		String text = getIntent().getExtras().getString(TEXT_TO_DISPLAY);
		TextView tv = (TextView) findViewById(R.id.textview);
		tv.setText(text);
		
		
		
	}
	
	
}
