package nz.ac.waikato.isdb.breathing;

import java.util.Timer;
import java.util.TimerTask;

import nz.ac.waikato.isdb.R;
import nz.ac.waikato.isdb.R.id;
import nz.ac.waikato.isdb.R.layout;
import nz.ac.waikato.isdb.R.menu;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

public class BreathingLearnActivity extends Activity {

	GraphViewSeries exampleSeries;
	GraphViewSeries learningCurve;
	GraphViewSeries marker;
	GraphViewData[] markerData;

	int count = 0;
	int xAxis = 0;
	boolean increasing = true;
	boolean run = true;
	double timeInterval = 0;
	double seconds = 5;

	int maxVal = 0;

	private Timer myTimer;

	double value = -90;
	double learnStartVal = -90;

	double framesPerSecond = 1000.0000 / 60;

	@Override
	protected void onPause() {
		super.onPause();
		myTimer.cancel();
	}

	@Override
	protected void onResume() {
		super.onResume();

		myTimer = new Timer();

		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
			}

		}, 0, (long) framesPerSecond);
	}

	@SuppressLint("NewApi")
	private void setLayoutWidth() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();

		int width = 0;

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
			// Do something for froyo and above versions
			display.getSize(size);

			width = size.x;

		} else {
			width = display.getWidth();
		}

		int buttonsWidth = width / 10 * 2;
		int graphWidth = width / 10 * 8;

		RelativeLayout buttons = (RelativeLayout) findViewById(R.id.layoutButtonSection);
		LinearLayout graph = (LinearLayout) findViewById(R.id.layoutGraphLearning);

		LayoutParams buttonParams = (LayoutParams) buttons.getLayoutParams();
		buttonParams.width = buttonsWidth;

		LayoutParams graphParams = (LayoutParams) graph.getLayoutParams();
		graphParams.width = graphWidth;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breathing_learn);
		// Show the Up button in the action bar.
		setupActionBar();
		setLayoutWidth();

		TextView intervalText = (TextView) findViewById(R.id.textViewInterval);
		intervalText.setText(seconds + "");

		GraphViewData[] graphData = new GraphViewData[0];
		GraphViewData[] learningData = new GraphViewData[0];
		markerData = new GraphViewData[0];

		GraphViewSeriesStyle gStyle = new GraphViewSeriesStyle(Color.GREEN, 20);
		GraphViewSeriesStyle mStyle = new GraphViewSeriesStyle(Color.RED, 100);

		setInterval(seconds);

		exampleSeries = new GraphViewSeries(graphData);
		learningCurve = new GraphViewSeries("Cosinus curve", gStyle,
				learningData);
		marker = new GraphViewSeries("MarkerCurve", mStyle, markerData);

		GraphView graphView = new LineGraphView(this // context
				, "Good Breathing Demo" // heading
		);

		graphView.addSeries(exampleSeries);
		graphView.addSeries(learningCurve);
		graphView.addSeries(marker);
		// data
		graphView.getGraphViewStyle().setNumHorizontalLabels(11);
		// graphView.getGraphViewStyle().setNumVerticalLabels(6);
		graphView.setVerticalLabels(new String[] { "Exhale", "", "Inhale" });
		graphView.setHorizontalLabels(new String[] {});

		graphView.setViewPort(0, 100 * timeInterval);
		graphView.setScrollable(true);
		graphView.setScalable(true);

		SystemClock.uptimeMillis();

		LinearLayout layout = (LinearLayout) findViewById(R.id.layoutGraphLearning);
		layout.addView(graphView);

		myTimer = new Timer();

		// aiming for 60 frames/ticks per second for smooth animation
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
			}

		}, 0, (long) framesPerSecond);

	}

	private void setInterval(double seconds) {
		timeInterval = seconds * 2;
	}

	public void increment(View v) {
		// does something very interesting
		TextView intervalText = (TextView) (TextView) findViewById(R.id.textViewInterval);

		if (seconds < 15) {
			seconds++;
			setInterval(seconds);
		}

		intervalText.setText(seconds + "");
	}

	public void decrement(View v) {
		// does something very interesting
		TextView intervalText = (TextView) findViewById(R.id.textViewInterval);
		if (seconds > 3) {
			seconds--;
			setInterval(seconds);

		}

		intervalText.setText(seconds + "");
	}

	private void TimerMethod() {
		// This method is called directly by the timer
		// and runs in the same thread as the timer.

		// We call the method that will work with the UI
		// through the runOnUiThread method.
		this.runOnUiThread(Timer_Tick);
	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {

			// This method runs in the same thread as the UI.

			// Do something to the UI thread here
			runGraph();

		}
	};

	private void runGraph() {

		double exampVal = Math.toRadians(value);
		double learnVal = Math.toRadians(learnStartVal);
		int val = 200 * (int) timeInterval;

		if (val > maxVal) {

			maxVal = val;
		}

		GraphViewData gData = new GraphViewData(xAxis, -1);

		// exampleSeries.appendData(gData, true, (200 * (int) timeInterval));
		exampleSeries.appendData(gData, true, maxVal);

		// 3 second delay
		if (count >= 180) {
			GraphViewData lData = new GraphViewData(xAxis - 180,
					Math.sin(learnVal));
			learningCurve.appendData(lData, false, maxVal);
			learnStartVal += 360 / 60 / timeInterval;
			marker.appendData(lData, false, 10);

		}

		// value += (6 / (timeInterval) / 2);
		// 1 full cycle is 360 deg, divide that by 60 (frames per sec) /
		// timeInterval
		value += 360 / 60 / timeInterval;

		xAxis++;
		count++;
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.breathing_learn, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
