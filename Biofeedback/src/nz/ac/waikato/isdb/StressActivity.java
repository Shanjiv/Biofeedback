package nz.ac.waikato.isdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nz.ac.waikato.isdb.ui.LikertScale;
import nz.ac.waikato.isdb.ui.LikertScaleStrategy;
import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class StressActivity extends Activity {


	LinearLayout l1,l2,l3,l4;


	ArrayList<LikertScaleStrategy> like = new ArrayList<LikertScaleStrategy>();
	ArrayList<TextView> text = new ArrayList<TextView>();
	ArrayList<LinearLayout> layout = new ArrayList<LinearLayout>();
	ArrayList<EditText> editBtn = new ArrayList<EditText>();
	int ids[] = {R.id.likert1,R.id.likert2,R.id.likert3,R.id.likert4,R.id.likert5,R.id.likert6,R.id.likert7,R.id.likert8,R.id.likert9,R.id.likert10,R.id.likert11,R.id.likert12,R.id.likert13,R.id.likert14,R.id.likert15,R.id.likert16,R.id.likert17,R.id.likert18,R.id.likert19,R.id.likert20,R.id.likert21,R.id.likert22,R.id.likert23};
	int numQues =23,numText=24,numLay=4,numEdit=4;
	int textIds[] ={R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4,R.id.textView5,R.id.textView6,R.id.textView7,R.id.textView8,R.id.textView9,R.id.textView10,R.id.textView11,R.id.textView12,R.id.textView13,R.id.textView14,R.id.textView15,R.id.textView16,R.id.textView17,R.id.textView18,R.id.textView19,R.id.textView20,R.id.textView21,R.id.textView22,R.id.textView23,R.id.textView24};
	int layIds[]={R.id.edit1,R.id.edit2,R.id.edit3,R.id.edit4};
	int editIds[]={R.id.editText1,R.id.editText2,R.id.editText3,R.id.editText4};
	SharedPreferences pref;
	OnSharedPreferenceChangeListener listener;
	OnClickListener clickListner;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stress_assessment);


		for(int i =0;i<numQues;i++)
		{
			like.add((LikertScaleStrategy) findViewById(ids[i]));
		}
		for(int i=0;i<numText;i++)
			text.add((TextView)findViewById(textIds[i]));
		for(int i=0;i<numLay;i++)
			layout.add((LinearLayout)findViewById(layIds[i]));
		for(int i=0;i<numEdit;i++)
			editBtn.add((EditText)findViewById(editIds[i]));
		l1 = (LinearLayout)findViewById(R.id.options1);
		l2 = (LinearLayout)findViewById(R.id.options2);
		l3 = (LinearLayout)findViewById(R.id.options3);
		l4 = (LinearLayout)findViewById(R.id.options4);

		clickListner = new OnClickListener() {

			@Override
			public void onClick(View v) {
				View parent;
				Log.d("ashwini", "clcik  " + (v instanceof EditText));
				if(!(v instanceof EditText))
				{

					InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					in.hideSoftInputFromWindow(v.getApplicationWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
				Log.d("ashwini", "clcik  "+ v.getClass());
				switch (v.getId()) {
				case R.id.textView5:
				case R.id.editText1:
					parent = layout.get(0);
					if(parent.isSelected())
						parent.setSelected(false);
					else
						parent.setSelected(true);
					break;
				case R.id.textView13:
				case R.id.editText2:
					parent = layout.get(1);
					if(parent.isSelected())
						parent.setSelected(false);
					else
						parent.setSelected(true);
					break;
				case R.id.textView18:
				case R.id.editText3:
					parent = layout.get(2);
					if(parent.isSelected())
						parent.setSelected(false);
					else
						parent.setSelected(true);
					break;
				case R.id.textView24:
				case R.id.editText4:
					parent = layout.get(3);
					if(parent.isSelected())
						parent.setSelected(false);
					else
						parent.setSelected(true);
					break;

				default:
					if(!(v.isSelected()))
						v.setSelected(true);
					else
						v.setSelected(false);
					break;
				}


			}
		};

		for (TextView txt : text) {
			txt.setOnClickListener(clickListner);
		}
		for (LinearLayout lay : layout) {
			lay.setOnClickListener(clickListner);
		}
		for(EditText ed : editBtn)
		{

			ed.setOnClickListener(clickListner);

			ed.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean arg1) {
					Log.d("ashwini "," onFocusChange "+ arg1);
					View parent;
					switch (v.getId()) {

					case R.id.editText1:
						parent = layout.get(0);
						if(parent.isSelected())
							parent.setSelected(false);
						else
							parent.setSelected(true);
						break;

					case R.id.editText2:
						parent = layout.get(1);
						if(parent.isSelected())
							parent.setSelected(false);
						else
							parent.setSelected(true);
						break;

					case R.id.editText3:
						parent = layout.get(2);
						if(parent.isSelected())
							parent.setSelected(false);
						else
							parent.setSelected(true);
						break;

					case R.id.editText4:
						parent = layout.get(3);
						if(parent.isSelected())
							parent.setSelected(false);
						else
							parent.setSelected(true);
						break;

					default:

						break;
					}
				}
			});
		}

		listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
				Log.d("ashwini "," pref changed");
				if(key.equals("buttonClick"))
				{
					String val = prefs.getString(key, null);

					if(val!=null)
					{
						String[] values =  val.split(",");

						int btnVal = Integer.parseInt(values[0]);
						int btnId = Integer.parseInt(values[1]);
						Log.d("ashwini "," pref "+ btnVal + " id "+ btnId);
						if(btnVal>2)
						{
							switch (btnId) {
							case R.id.likert1:
								if(l1.getVisibility()!=View.VISIBLE)
								{
									l1.setVisibility(View.VISIBLE);
									SlideToDown(l1);
								}

								break;
							case R.id.likert2:
								if(l2.getVisibility()!=View.VISIBLE)
								{
									l2.setVisibility(View.VISIBLE);
									SlideToDown(l2);
								}

								break;
							case R.id.likert5:
								if(l3.getVisibility()!=View.VISIBLE)
								{

									l3.setVisibility(View.VISIBLE);
									SlideToDown(l3);
								}

								break;

							case R.id.likert22:
								if(l4.getVisibility()!=View.VISIBLE)
								{
									l4.setVisibility(View.VISIBLE);
									SlideToDown(l4);
								}

								break;

							default:
								break;
							}
						}
						else
						{

							switch (btnId) {
							case R.id.likert1:
								if(l1.getVisibility()==View.VISIBLE)
								{
									SlideToUp(l1);
									l1.setVisibility(View.GONE);
								}


								break;
							case R.id.likert2:
								if(l2.getVisibility()==View.VISIBLE)
								{
									SlideToUp(l2);
									l2.setVisibility(View.GONE);
								}break;
							case R.id.likert5:
								if(l3.getVisibility()==View.VISIBLE)
								{
									SlideToUp(l3);
									l3.setVisibility(View.GONE);
								}break;
							case R.id.likert22:
								if(l4.getVisibility()==View.VISIBLE)
								{
									SlideToUp(l4);
									l4.setVisibility(View.GONE);
								}
								break;

							default:
								break;
							}

						}
					}
				}
			}
		};

		pref = getSharedPreferences("stress", MODE_PRIVATE);
		pref.registerOnSharedPreferenceChangeListener(listener);

	}



	public void SlideToUp(final LinearLayout rl_footer) {
		Animation hide = AnimationUtils.loadAnimation(this, R.anim.fadeup);
		rl_footer.startAnimation(hide);

	}
	public void SlideToDown(final LinearLayout rl_footer) {
		Animation hide = AnimationUtils.loadAnimation(this, R.anim.fadedown);
		rl_footer.startAnimation(hide);

	}
	@Override
	protected void onResume() {
		super.onResume();
		for(int i=0;i<numQues;i++)
		{
			int val = pref.getInt("like"+i, -1);
			like.get(i).setValue(val);
			if(val>2)
			{
				switch (i) {
				case 0:
					l1.setVisibility(View.VISIBLE);
					break;
				case 1:
					l2.setVisibility(View.VISIBLE);
					break;
				case 4:
					l3.setVisibility(View.VISIBLE);
					break;
				case 21:
					l4.setVisibility(View.VISIBLE);
					break;

				default:
					break;
				}
			}
		}
		for(int i=0;i<numText;i++)
		{
			boolean sel = pref.getBoolean("text"+i, false); ;
			switch (i) {
			case 4:

				layout.get(0).setSelected(sel);
				break;
			case 12:
				layout.get(1).setSelected(sel);			
				break;
			case 17:
				layout.get(2).setSelected(sel);			
				break;
			case 23:
				layout.get(3).setSelected(sel);			
				break;

			default:
				text.get(i).setSelected(sel);			
				break;
			}

		}


		for(int i=0;i<numEdit;i++)
		{
			String str = pref.getString("edit"+i, "");
			Log.d("ashwini","str ===== "+str);
			editBtn.get(i).setText(str);

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
		pref.edit().putInt("totalStress", total).apply();

		for(int i=0;i<numText;i++)
		{
			switch (i) {
			case 4:
				pref.edit().putBoolean("text"+i, layout.get(0).isSelected()).apply();
				break;
			case 12:
				pref.edit().putBoolean("text"+i, layout.get(1).isSelected()).apply();
				break;
			case 17:
				pref.edit().putBoolean("text"+i, layout.get(2).isSelected()).apply();
				break;
			case 23:
				pref.edit().putBoolean("text"+i, layout.get(3).isSelected()).apply();
				break;

			default:
				pref.edit().putBoolean("text"+i, text.get(i).isSelected()).apply();
				break;
			}

		}

		for(int i=0;i<numEdit;i++)
		{
			String str = editBtn.get(i).getText().toString();
			pref.edit().putString("edit"+i, str).apply();
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
		switch (item.getItemId()) {
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

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	public void actionDone(View view)
	{
		finish();
	}
}
