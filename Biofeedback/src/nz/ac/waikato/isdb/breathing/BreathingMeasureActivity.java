package nz.ac.waikato.isdb.breathing;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import nz.ac.waikato.isdb.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BreathingMeasureActivity extends Activity implements
        SensorEventListener, OnClickListener {

    MediaPlayer mPlayer;

    float zAxis;
    GraphViewSeries exampleSeries;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    Timer myTimer;
    int count = 0;

    ArrayList<Float> zValues = new ArrayList<Float>();
    ArrayList<Integer> lowList = new ArrayList<Integer>();

    boolean increasing = true;
    boolean good = true;
    boolean running = true;
    boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing_measure);
        // Show the Up button in the action bar.
        setupActionBar();

        setUpSensors();

        myTimer = new Timer();
        paused = false;
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 1000);

        int size = 0;

        GraphViewData[] graphData = new GraphViewData[0];
        exampleSeries = new GraphViewSeries(graphData);

        GraphView graphView = new LineGraphView(this, "Breathing Graph");

        graphView.setClickable(true);
        graphView.setOnClickListener(this);

        graphView.addSeries(exampleSeries); // data
        graphView.getGraphViewStyle().setNumHorizontalLabels(6);
        graphView.setViewPort(2, 10);
        graphView.setScrollable(true);
        graphView.setScalable(true);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutGraph);

        layout.addView(graphView);

        mPlayer = MediaPlayer.create(this, R.raw.low_bubbles);
        mPlayer.setLooping(true);
        mPlayer.start();

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
        if (!running)
            return;

        zValues.add(zAxis);

        GraphViewData gData = new GraphViewData(count, zAxis);
        exampleSeries.appendData(gData, true, 500);

        if (count > 0) {
            float temp = zValues.get(count - 1);

            if (!increasing) {
                if (temp < zAxis) {
                    lowList.add(count - 1);
                    increasing = true;
                    checkBreathLength();
                }
            } else {
                if (temp > zAxis) {

                    increasing = false;

                }
            }

        }

        count++;
    }

    private void checkBreathLength() {
        if (paused || lowList.size() <= 1)
            return;

        int currentPeak = lowList.get(lowList.size() - 1);
        int previousPeak = lowList.get(lowList.size() - 2);

        if (good) {
            if (mPlayer == null)
                mPlayer = MediaPlayer.create(this, R.raw.low_bubbles);
        } else {
            if (mPlayer == null)
                mPlayer = MediaPlayer.create(this, R.raw.high_bubbles);
        }

        if (currentPeak - previousPeak < 5) {
            if (good) {
                good = false;
                mPlayer.stop();
                mPlayer.release();
                mPlayer = MediaPlayer.create(this, R.raw.high_bubbles);
            }
        } else {
            if (!good) {
                good = true;
                mPlayer.stop();
                mPlayer.release();
                mPlayer = MediaPlayer.create(this, R.raw.low_bubbles);
            }
        }

        if (mPlayer != null) {

            boolean playing = mPlayer.isPlaying();
            if (!playing) {
                mPlayer.setLooping(true);
                mPlayer.start();

            }
        }
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
        getMenuInflater().inflate(R.menu.breathing_measure, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        paused = false;
        if (good) {
            if (mPlayer == null)
                mPlayer = MediaPlayer.create(this, R.raw.low_bubbles);
        } else {
            if (mPlayer == null)
                mPlayer = MediaPlayer.create(this, R.raw.high_bubbles);
        }

        if (mPlayer != null) {
            mPlayer.start();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        paused = true;
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        zAxis = event.values[2];

    }

    public void setUpSensors() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
            List<Sensor> gravSensors = mSensorManager
                    .getSensorList(Sensor.TYPE_GRAVITY);
            for (int i = 0; i < gravSensors.size(); i++) {
                // NOTE: Don't know why the student was checking for the vendor
                // and the version.
                // But if commented in the App does not get any data on Annika's
                // phone.
                // if ((gravSensors.get(i).getVendor().contains("Google Inc."))
                // && (gravSensors.get(i).getVersion() == 3)) {
                // Use the version 3 gravity sensor.
                mSensor = gravSensors.get(i);
                // }
            }
        } else if (mSensorManager
                .getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            mSensor = mSensorManager
                    .getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        } else {
            // Use the accelerometer.
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                mSensor = mSensorManager
                        .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            } else {
                // Sorry, there are no accelerometers on your device.
                // You can't play this game.
            }
        }
    }

    public void pause(View view) {

        running = !running;

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        running = !running;
    }

}
