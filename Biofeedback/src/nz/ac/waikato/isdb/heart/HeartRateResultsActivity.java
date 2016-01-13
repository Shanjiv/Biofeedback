package nz.ac.waikato.isdb.heart;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import nz.ac.waikato.isdb.R;

public class HeartRateResultsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_results);

        Bundle extras = getIntent().getExtras();

        int data = extras.getInt("heartRate");

        TextView t = (TextView) findViewById(R.id.textViewHeartRate);
        t.setText(Integer.toString(data));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hear_rate_results, menu);
        return true;
    }
}
