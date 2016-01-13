package nz.ac.waikato.isdb;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class InformationActivity extends Activity {


	TextView t1,t2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information);

		Intent intent = getIntent();

		int button = intent.getIntExtra("button",-1);

		switch (button) {
		case 1:
			t1 =(TextView)findViewById(R.id.textHeading1);
			t2 = (TextView)findViewById(R.id.textView1);
			break;
		case 2:
			t1 =(TextView)findViewById(R.id.textHeading2);
			t2 = (TextView)findViewById(R.id.textView2);
			break;
		case 3:
			t1 =(TextView)findViewById(R.id.textHeading3);
			t2 = (TextView)findViewById(R.id.textView3);
			break;

		default:
			break;
		}
		t1.setVisibility(View.VISIBLE);
		t2.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SharedPreferences pref;
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
		case R.id.action_settings:
			Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Reset");
			dialog.setMessage("Are you sure?");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences pref;
					pref = getSharedPreferences("stress", MODE_PRIVATE);
					pref.edit().clear().commit();
					pref = getSharedPreferences("randomstringsstress", MODE_PRIVATE);
					pref.edit().clear().commit();
					pref = getSharedPreferences("physical", MODE_PRIVATE);
					pref.edit().clear().commit();
					pref = getSharedPreferences("intellectual", MODE_PRIVATE);
					pref.edit().clear().commit();
					pref = getSharedPreferences("social", MODE_PRIVATE);
					pref.edit().clear().commit();
					pref = getSharedPreferences("individual", MODE_PRIVATE);
					pref.edit().clear().commit();
					pref = getSharedPreferences("randomstrings", MODE_PRIVATE);
					pref.edit().clear().commit();
					Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
					intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					startActivity(intent1);

				}
			});
			dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();

				}
			});
			dialog.show();

			return true;
		case R.id.action_about:
			Intent intent = new Intent(getApplicationContext(),AboutUsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
