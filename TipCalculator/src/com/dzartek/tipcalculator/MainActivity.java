package com.dzartek.tipcalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;


public class MainActivity extends Activity implements OnSeekBarChangeListener, TextWatcher {
	
	//Currency & Percent formatters
	private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
	
	private double billAmount = 0.0;  // bill amount entered by user
	private double customPercent = 0.18;  // initial custom tip percentage
	private int numberOfPersons = 1;  //Initial amount of people
	private TextView amountDisplayTextView;  // shows formatted bill amount
	private TextView percentCustomTextView;  // shows custom tip percentage
	private TextView tip15TextView; // shows 15% tip
	private TextView total15TextView; // shows total with 15% tip
	private TextView tipCustomTextView; // shows custom tip amount
	private TextView totalCustomTextView;  // shows total with custom tip
	private TextView totalPersonsTextView; //Show total persons splitting the check 
	private TextView tip15ShareTextview;  // Show shared tip for 15%
	private TextView tipCustomShareTextView; // Show shared tip for custom%
	private TextView totalCheck15ShareTextView; //show total check split for 15%
	private TextView totalCheckCustomShareTextView; //show total check split for custom%
	private EditText amountEditText;
	private double fifteenPercentTip, fifteenPercentTotal, customTip, customTotal;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  // Call superclass's version
		setContentView(R.layout.activity_main);  // inflate the GUI
				
		// Get the references to the textview's that MainActivity interacts with programatically
		amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
		percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
		tip15TextView = (TextView) findViewById(R.id.tip15TextView);
		total15TextView = (TextView) findViewById(R.id.total15TextView);
		tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
		totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);
		totalPersonsTextView = (TextView) findViewById(R.id.personsTextView);
		tip15ShareTextview = (TextView) findViewById(R.id.tip15ShareTextView);  
		tipCustomShareTextView = (TextView) findViewById(R.id.tipCustomShareTextView); 
		totalCheck15ShareTextView = (TextView)findViewById(R.id.totalCheck15ShareTextView); 
		totalCheckCustomShareTextView = (TextView)findViewById(R.id.totalCheckCustomShareTextView);
				
		// Update GUI based on billAmount and customPercent
		amountDisplayTextView.setText(currencyFormat.format(billAmount));
		updateStandard();  //update the 15% tip textviews
		updateCustom();  // update the custom tip textviews
		upDateSharedCheck();  //update sharing the tip and check
		
		// Set amountEditText's TextWatcher
		amountEditText = (EditText) findViewById(R.id.amountEditText);
		amountEditText.addTextChangedListener(this);
		
		// set customTipSeekbar's OnSeekBarChangeListener
		SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
		customTipSeekBar.setOnSeekBarChangeListener(this);
		
		//Set total persons splitting the check
		SeekBar personsSeekBar = (SeekBar) findViewById(R.id.totalPersonsSeekBar);
		personsSeekBar.setOnSeekBarChangeListener(this);
		
		/*		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		*/
	}

	
	// Update 15% tip TextViews
	private void updateStandard() {
		//calculate 15% tip and total
		fifteenPercentTip = billAmount * 0.15;
		fifteenPercentTotal = billAmount + fifteenPercentTip;
		
		// Display 15% tip & total formatted as currency
		tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
		total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
		
		
	}
	
	
	// updates the custome tip and total textviews
	private void updateCustom(){
		// show custompercent in percentcustomTextView formatted as %
		percentCustomTextView.setText(percentFormat.format(customPercent));
		
		//Calculate the custom tip and total
		customTip = billAmount * customPercent;
		customTotal = billAmount + customTip;
		
		// Display custom tip & total formatted as currency
		tipCustomTextView.setText(currencyFormat.format(customTip));
		totalCustomTextView.setText(currencyFormat.format(customTotal));
		
	}
	
	
	// Shared 15% Tip and total check
	private void upDateSharedCheck() {
		// TODO Auto-generated method stub
		totalPersonsTextView.setText(Integer.toString(numberOfPersons));
		
		// Display Standard shared tip & total formatted as currency
		double fifteenPercentSplit = fifteenPercentTip / numberOfPersons;
		double fifteenPercentTotalSplit = fifteenPercentTotal / numberOfPersons;
		
		BigDecimal bd1 = new BigDecimal(Double.toString(fifteenPercentSplit));
		bd1 = bd1.setScale(2, RoundingMode.UP);
		
		BigDecimal bd2 = new BigDecimal(Double.toString(fifteenPercentTotalSplit));
		bd2 = bd2.setScale(2, RoundingMode.UP);
		
		tip15ShareTextview.setText(currencyFormat.format(bd1));
		totalCheck15ShareTextView.setText(currencyFormat.format(bd2));
		
		// Display custom shared tip & total formatted as currency
		double customPersentSplit = customTip / numberOfPersons;
		double customTotalSplit = customTotal / numberOfPersons;
		
		BigDecimal bd3 = new BigDecimal(Double.toString(customPersentSplit));
		bd3 = bd3.setScale(2, RoundingMode.UP);
		BigDecimal bd4 = new BigDecimal(Double.toString(customTotalSplit));
		bd4 = bd4.setScale(2, RoundingMode.UP);
		//Toast.makeText(this, "Value is: " + customTotalSplit, Toast.LENGTH_LONG).show();
		tipCustomShareTextView.setText(currencyFormat.format(bd3));
		totalCheckCustomShareTextView.setText(currencyFormat.format(bd4));
		
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

		
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
		switch (seekBar.getId()) {
		case R.id.customTipSeekBar:
			// sets customPercent to position of the seekBar's thumb
			customPercent = progress / 100.0;
			updateCustom();
			upDateSharedCheck();
			
			break;
			
		case R.id.totalPersonsSeekBar:
			numberOfPersons = progress;
			upDateSharedCheck();
			break;
		}
		
		
		
	}


	


	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
		try {
			billAmount = Double.parseDouble(s.toString()) / 100.00;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			billAmount = 0.0;
		}
		
		// Display currency formatted bill amount
		amountDisplayTextView.setText(currencyFormat.format(billAmount));
		updateStandard();
		updateCustom();
		upDateSharedCheck();  //update sharing the tip and check
		
	}


	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
			
}
