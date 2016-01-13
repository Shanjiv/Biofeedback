package nz.ac.waikato.isdb.breathing;

import nz.ac.waikato.isdb.R;
import nz.ac.waikato.isdb.R.layout;
import nz.ac.waikato.isdb.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class BreathingRateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breathing_rate);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.breathing_rate, menu);
		return true;
	}

	public void showInstructions(View view) {
		Intent breathingRateIntent = new Intent(this, BreathingInstructionsActivity.class);
		startActivity(breathingRateIntent);
	}

	public void showLearning(View view) {
		Intent breathingRateIntent = new Intent(this, BreathingLearnActivity.class);
		startActivity(breathingRateIntent);
	}

	public void showMeasure(View view) {
		Intent breathingRateIntent = new Intent(this, BreathingMeasureActivity.class);
		startActivity(breathingRateIntent);
	}
}
