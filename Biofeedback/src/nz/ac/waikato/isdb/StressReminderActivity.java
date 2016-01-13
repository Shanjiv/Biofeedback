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
import java.util.Random;
import java.util.Set;

import nz.ac.waikato.isdb.assessment.SelfAssessmentActivityResources;
import nz.ac.waikato.isdb.ui.LikertScale;
import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StressReminderActivity extends Activity {

	int numQues =23;
	HashMap< Integer, Integer> like = new HashMap<Integer, Integer>();
	HashMap< Integer, Integer> sortedLike = new HashMap<Integer, Integer>();
	SharedPreferences pref,pref1;
	TextView t1;
	Button random,activity,back;
	String[] reminderText={"<b>Exercise: </b> Moderate exercise can help with anxiety and stress almost immediately, and if you exercise regularly, it can improve mood and energy levels.  Of course, while you are pregnant, you should check with your maternity caregiver, especially if you are starting something new—but even 10 or 15 minutes a day of walking (ideally outside, in the sunlight) can give you a boost.",
			"<b>Mini-break: </b>If you find yourself getting wound up—or just have a few minutes between things—give yourself a mini-break.  Breathe, slowly, and stop:  focus on what is happening now, around you.  Look at a tree, or the sky, or even the lovely symmetry of the steering wheel…just be.  Choose a mantra—some words to say to yourself. ",
			"<b>Biofeedback: </b>Practice getting your body to settle down, with some kind of cue (that’s the feedback) about your level of physiological stress (that’s the bio).  With the __ module, it’s showing you your heart rate, so you can see how, when you breathe slowly and settle your thoughts, your body gets settled, too.",
			"<b>Journaling: </b>Many people find that writing about their feelings and thoughts helps to make them clearer, and can sometimes help clear them out of your head.  Get a notebook, and write for 10 minutes—don’t worry about anyone else every seeing it—just write what you really feel. Or do it on a computer or tablet—whatever works for you.  Then close the book/file.  This can help for worries, frustrations, and confusion.",
			"<b>Music:  </b>Whistle while you work?  Music can be a break in itself—take a few minutes and listen to your favourite song, or something inspiring—or it can add a background that helps make something else you have to do more positive—something upbeat while folding the laundry, or getting through the bills.  If you’re a singer, of course, this could be a more active option:  sing to yourself, practice some lullabies—or if you play an instrument, play what makes you happy.  One study found that 30 minutes a day of listening to music helped to improve the mood of pregnant women.",
			"<b>Guided imagery:  </b>In guided imagery, you listen to a narration describing a relaxing scene, and imagine yourself in it—you can choose among several in the Imagery module—so you could transport yourself to walk along a beach, or in a favourite place, to take a fantasy break for a few minutes.",
			"<b>Body scan:  </b>In a body scan, you take a few minutes to listen to your body, mentally experiencing how you feel, physically, in the moment.  You can do this on your own, or use an audio guide module, “Body Scan”",
			"<b>Stretching: </b>Take 2/3 minutes and stretch whatever part of your body that feels like it needs it—feel your muscles as you raise your arms over your head, or move slowly and gently into an easy stretch.  Breathe.",
			"<b>Relaxation breathing: </b>Take a few minutes and pay attention to your breathing.  Notice how as you breathe in, slight tension builds, and then as you exhale, your body relaxes.  Slow down your breathing, so that you breathe in slowly, hold it, and exhale.  If you like a structure, you can do what is called “square breathing”:  inhale to a slow count of four, hold it to a count of four, exhale to a count of four, wait to a count of four, and then start over.    Do that a few times.  Breathe from your belly (as much as you can, being as it may be a bit full right now).  Feel your lungs expand.  Especially pay attention to how the tension drains out as you exhale.",
			"<b>Meditation:  </b>There are many traditions of meditation you may have tried or heard about.  Meditation practice can focus on a mantra (a special word), or mindfulness (paying attention to what is happening now), or breath, or a particular prayer.  If this interests you, read or learn more about a form of meditation that appeals to you.  Meditation can be done sitting, walking, standing, or even lying down. ",
			"<b>Prayer:</b>Prayer may be a traditional set of words for your faith, or a personal conversation with the divine.  Many people find prayer helpful in times of stress and change. You may pray for help, for strength, for peace, for a particular outcome, or use words as a way to express gratitude, faith, worry, sadness—any thought or feeling.  Consider whether you would like to learn a particular prayer, or find a book of prayers to read, or just take some time for connection with your spirituality.",
			"<b>Massage: </b>Gentle massage can help with physical and emotional tension.  There are professional massage therapists who specialise in working with pregnant women.  You might also ask your partner for a massage—could be just your feet, or shoulders, or lower back, or whatever you are comfortable with.  One study taught partners of pregnant women to provide gentle massage, and found that not only did the women feel better, but the relationship improved. ",
			"<b>Yoga: </b>Yoga is a slow, meditative form of exercise that has been used with pregnant women across cultures and settings; studies have shown that many women feel this improves their mental and physical wellbeing.  Often special yoga classes are available for pregnant women.  Other, similar forms of meditative exercise include Tai Chi and Qi Gong.",
			"<b>Gardening: </b>Getting out in the garden and helping things grow—or clearing away what shouldn’t be growing—might be what takes you out of your worries.  You can see what you’ve done.   Of course, be careful not to overdo it and strain something—but deadheading the roses might be just what the doctor ordered.",
			"<b>Laughter: </b>Laughter may actually be the best medicine—or at least a good one.  Read a funny book, watch a comedy, go out with some friends who make you laugh.  Make a plan now—what is likely to tickle your funny bone?",
			"<b>Reading: </b>Transport yourself with a story, or something that fascinates you.  For some people, reading is a trip away from the world, and that could be just what you need.  What is best for you—a book with short chapters, which you can read for 10 minutes?  Or a fantasy or thriller you can’t put down?",
			"<b>Make a list: </b>If something is worrying you—if you are afraid you won’t get it done, or there just seem to be too many things you’re juggling, and it all seems impossible—sometimes making a list can help.  Break it down into doable bits, and tick them off.  Maybe even make a schedule of when you can do each. ",
			"<b>Call a friend: </b>For some people, it is really helpful to talk through their quandaries with someone who knows them—what is happening?  What shall I do?  Choose who you call thoughtfully—someone who is constructive, who will help you without ratcheting up the stress.  Sometimes, calling a friend might be for when you need NOT to talk about problems, but just to go out (or stay in) and do something you enjoy.",
			"<b>Take a bath:</b>For some, a bath can be a time just for you, to relax, to feel warmth and nice smells, maybe with some bubbles or oil…focus on the now, not on planning or worrying.  If you find your mind going back to the stresses, play with the bubbles.",
			"<b>Play: </b>What do you do that is playful?  Sometimes being with someone…a child, or a pet…who is completely absorbed in what you are doing—playing with a ball, or a toy, or blowing bubbles—can be a break from stress.  You could also play a game—in the real world, or on your phone or tablet.",
			"<b>Go outside: </b>For many people, being outside, especially in a natural setting, can lift your mood.  Especially in winter, getting a bit of sun and air can also do wonders for your wellbeing, even if the weather is not the best.",
			"<b>Feed yourself: </b>You (and your baby) need food to live and grow.  Remember to nurture  yourself with healthy, satisfying snacks.",
			"<b>Create something [art, or craft, or food?]: </b>Create something [art, or craft, or food?]: <b></b>Making something can provide distraction, and the satisfaction of having a tangible product.  Could be sewing or knitting something for the baby, or baking, or drawing or painting or crafting—whatever is your thing.  You might even try something new—take a class, or look it up on the internet.  Trash to treasures?  "
	};
	int key=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stress_reminders);
		pref1 = getSharedPreferences("randomstringsstress", MODE_PRIVATE);
		t1 =(TextView)findViewById(R.id.reminderText1);
		//reminderText = getResources().getStringArray(R.array.stressreminders);
		random = (Button)findViewById(R.id.buttonRandom);
		back = (Button)findViewById(R.id.buttonBack);

		activity = (Button)findViewById(R.id.buttonActivity);
		activity.setOnClickListener(new OnClickListener(
				) {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(), StressActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("ashwini.next","k value "+key);
				key--;
				setRandom();
				if(key == 0)
				{
					back.setVisibility(View.GONE);
				}
				if(key <sortedLike.size()-1)
				{
					random.setVisibility(View.VISIBLE);
				}



			}
		});


		random.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("ashwini.next","k value "+key);
				if(key == sortedLike.size()-1)
				{
					Toast.makeText(getApplicationContext(), "Answer some more questions in Self Assessment to get more Strategy Suggestions", Toast.LENGTH_LONG).show();
					activity.setVisibility(View.VISIBLE);
				}
				else
				{
					key++;
					setRandom();
					if((key == sortedLike.size()-1) && (sortedLike.size() == numQues))
					{
						random.setVisibility(View.GONE);
					}

					if(key > 0)
					{
						back.setVisibility(View.VISIBLE);
					}
				}
			}
		});



	}

	public void setRandom()
	{


		List<Integer> keys      = new ArrayList<Integer>(sortedLike.keySet());

		t1.setText(Html.fromHtml(reminderText[keys.get( key )]));

	}
	private HashMap getHighRatedValues(HashMap map)
	{
		HashMap rated = new LinkedHashMap();
		Set set = sortedLike.entrySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			if((Integer)(entry.getValue()) >2)
				rated.put(entry.getKey(), entry.getValue());
			else
				break;
		} 
		return rated;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();


		key=0;
		pref = getSharedPreferences("stress", MODE_PRIVATE);
		int j=0;
		for(int i=0;i<numQues;i++)
		{
			int val = pref.getInt("like"+i, -1);

			like.put(j,val);
			j++;
		}

		like = sortByValues(like);
		sortedLike = getHighRatedValues(like);
		back.setVisibility(View.GONE);
		sortedLike = sortByValues(like);
		sortedLike = getHighRatedValues(sortedLike);
		if(sortedLike.size() == 0)
		{
			t1.setText("Answer Questions in Self Assessment to get Strength Reminders");
			activity.setVisibility(View.VISIBLE);
			random.setVisibility(View.GONE);
			back.setVisibility(View.GONE);
		}
		else 
		{
			activity.setVisibility(View.GONE);
			random.setVisibility(View.VISIBLE);
			setRandom();
		}


	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		pref1.edit().putInt("sortedarraylen", sortedLike.size()).apply();
	}

	@SuppressWarnings("unchecked")
	private static HashMap sortByValues(HashMap map) { 
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		} 
		return sortedHashMap;
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
