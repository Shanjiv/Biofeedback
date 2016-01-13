package nz.ac.waikato.isdb.assessment;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import nz.ac.waikato.isdb.AboutUsActivity;
import nz.ac.waikato.isdb.MainActivity;
import nz.ac.waikato.isdb.R;
import nz.ac.waikato.isdb.ui.LikertScale;

public class SelfAssessmentSocialActivity extends Activity {
	ArrayList<LikertScale> like = new ArrayList<LikertScale>();
	int ids[] = {R.id.likert1,R.id.likert2,R.id.likert3,R.id.likert4,R.id.likert5,R.id.likert6,R.id.likert7,R.id.likert8,R.id.likert9,R.id.likert10,R.id.likert11,R.id.likert12,R.id.likert13,R.id.likert13};
	int numQues =13;
	SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_self_assessment_social);
		// Show the Up button in the action bar.
		//setupActionBar();
		pref = getSharedPreferences("social", MODE_PRIVATE);
		for(int i =0;i<numQues;i++)
		{
			like.add((LikertScale) findViewById(ids[i]));
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		for(int i=0;i<numQues;i++)
		{
			int val = pref.getInt("like"+i, -1);
			like.get(i).setValue(val);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		int total =0 ;
		for(int i=0;i<numQues;i++)
		{
			pref.edit().putInt("like"+i, like.get(i).getValue()).apply();
			if(like.get(i).getValue()!= -1)
			{	total = total+1;
			}
		}
		pref.edit().putInt("total", total).apply();

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
					for(int i=0;i<numQues;i++)
					{
						int val = pref.getInt("like"+i, -1);
						like.get(i).setValue(val);
					}
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
	public void actionDone(View view)
	{
		finish();
	}
}
