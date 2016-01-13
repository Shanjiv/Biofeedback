package nz.ac.waikato.isdb.breathing;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import nz.ac.waikato.isdb.R;

import java.util.Timer;
import java.util.TimerTask;


public class GraphActivity extends Activity {
	
	GraphViewSeries exampleSeries;
	private long startTime = 0L;
	int count = 0;
	int xAxis = 0;
	boolean increasing = true;
	boolean run = true;
	
	private Timer myTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		// Show the Up button in the action bar.
		setupActionBar();
		
		GraphViewData[] graphData = new GraphViewData[0];
		exampleSeries = new GraphViewSeries(graphData);
		
		GraphView graphView = new LineGraphView(  
			      this // context  
			      , "GraphViewDemo" // heading  
			);  
		
		graphView.addSeries(exampleSeries); // data  
		graphView.getGraphViewStyle().setNumHorizontalLabels(6);
		graphView.setViewPort(2, 10);  
		graphView.setScrollable(true);
		graphView.setScalable(true); 
		
		startTime = SystemClock.uptimeMillis();
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layoutGraph);  
		layout.addView(graphView); 
		
		myTimer = new Timer();
	    
		myTimer.schedule(new TimerTask() {          
	        @Override
	        public void run() {
	            TimerMethod();
	        }

	    }, 0, 1000);
	
		
	}
	
	private void TimerMethod()
	{
	    //This method is called directly by the timer
	    //and runs in the same thread as the timer.

	    //We call the method that will work with the UI
	    //through the runOnUiThread method.
	    this.runOnUiThread(Timer_Tick);
	}
	
	private Runnable Timer_Tick = new Runnable() {
	    public void run() {

	    //This method runs in the same thread as the UI.               

	    //Do something to the UI thread here
	    	runGraph();

	    }
	};

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
		getMenuInflater().inflate(R.menu.graph, menu);
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
	
	private void runGraph()
	{
			if(count == 0)
			{
				increasing = true;
			}
			if(count == 5)
			{
				increasing = false;
			}
			
			if(increasing)
			{
				count ++;
			}
			else
			{
				count --;
			}
			GraphViewData gData = new GraphViewData(xAxis, count);  
			
			exampleSeries.appendData(gData, true, 500);
			
			xAxis ++;
		
	}
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    
	   
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    
	   
	   
	  }

}
