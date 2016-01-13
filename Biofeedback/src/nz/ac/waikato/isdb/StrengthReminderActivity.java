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

import nz.ac.waikato.isdb.assessment.SelfAssessmentActivity;
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

public class StrengthReminderActivity extends Activity {

	int numQuesPhy =4;
	int numQuesInt =8;
	int numQuesSoc =13;
	int numQuesInd =16;
	int total = numQuesInd+numQuesInt+numQuesPhy+numQuesSoc;
	HashMap< Integer, Integer> like = new HashMap<Integer, Integer>();
	HashMap< Integer, Integer> sortedLike = new HashMap<Integer, Integer>();

	SharedPreferences pref,pref1;
	TextView t1;
	Button random,activity,back;
	String[] reminderText = {"<b>Appearance:</b> You take care of yourself and present yourself well.  Do something today to put your best (dressed?) foot forward—pay special attention to something you like about the way you look, dress, or groom yourself. ",
			"<b>Health:</b>  You are healthy and wellness is important to you.  Do something extra to take care of yourself—eat some fruit or veggies, take a nap, take a cleansing breath, and appreciate your healthy body and all it is doing for you.",
			"<b>Motor skills/coordination:</b> You are agile and physically competent.  It may feel strange and awkward as your baby grows and your body changes, but you can adjust to this and keep your balance.  Try something out:  maybe some yoga, or a gentle stretching, and see how your centre of gravity is changing.  Appreciate your awareness of your body, and the little person doing somersaults within.",
			"<b>Fitness:</b> You are fit and strong; this will help both you and your baby over the next few months and years. Do something physical you enjoy today—take a walk, or during your regular exercise, pause to appreciate your body—all the work it is doing to grow your baby, and how your care for yourself contributes to holding your baby in a safe, strong womb.",
			"<b>Problem-solving:</b> You are clever and practical, able to solve the problems that pop up in life.  Think about the last time someone came to you with a problem, and you helped them to solve it.  How did you do that?  Are you intuitive, and the answer just seems to appear to you, or do you work things out logically, stepwise—or a bit of both?  What do you think is your best strategy for solving problems?",
			"<b>Creativity:</b> You are imaginative, innovative, inspired.  This may show in creative work, or play, or in the way you go about your daily chores.  Put a bit of extra spark into something you do today—be playful, and surprise yourself!",
			"<b>Verbal skills:</b> You are a good communicator—you express yourself well and get your point across.  Think about something you have to convey to someone today—either in person, or in writing—and stop to think through how you’ve done it, and what works well about that. ",
			"<b>Visual-spatial:</b> You are good at hands-on projects—making things work, putting things together.  Think about a project you are working on, or planning:  what is the next step?  What is satisfying about doing it?",
			"<b>Memory:</b> You’ve got a good memory—think about how you use this skill to make your day better:  take a minute to  wander in your happiest memory; remember a friend’s birthday or something special you meant to do.",
			"<b>Concentration:</b> You are a very focused person; you can use that to tune in to what you need to do, or what helps you, and screen out worries or troubles you don’t need to think about right now.",
			"<b>Knowledge:</b> You have an amazing fund of facts interesting things you know about. Consider Merlin’s advice:   \"The best thing for being sad,\" replied Merlin, beginning to puff and blow, \"is to learn something. That's the only thing that never fails\" (T.H. White, The Once and Future King)",
			"<b>Curiosity:</b> You are a mental explorer, curious and excited to find new things around every corner.  Think now, what have you noticed today that is fascinating, that deserves a second look?  Find out something new about it.",
			"<b>Compassion and kindness:</b> You are considerate and thoughtful of others.  Do something—a smile, a supportive e-mail, a cup of coffee—for someone else today, and then see if you can do that for yourself, too:  give yourself a treat, a moment to appreciate something you’ve done.",
			"<b>Friendliness:</b> You are friendly and approachable—say hello to someone new, or take a moment to ask a friend or acquaintance about themselves—and enjoy listening, seeing the spark that is genuine and interesting in that person, today.",
			"<b>Loyalty:</b> You are a faithful and loyal person, and people know they can count on you to be alongside them in their times of trouble.  In your own difficult times, try to treat yourself as you would a friend:  what do you need?  Who are your loyal friends?  Look to your internal compass and see what is important to you—let that guide you in your steps.",
			"<b>Honesty:</b> You are honest and trustworthy—people can count on your word.  Think about how you uphold this value in big and small ways in your life. ",
			"<b>Courtesy:</b> You are considerate and polite, careful about how you treat others and put them at ease.  Notice how you have done that today, pausing at a door, or responding with grace to an error.  Think about how this affects those around you. ",
			"<b>Discretion/tact:</b> You are tactful and discrete, aware of the potential impact of what you say.  Think about how this has shaped your relationships with people—do they respect your thoughtfulness?  What are the values that guide your discretion?",
			"<b>Generosity:</b> You are a generous person—look around and notice what ways you are giving of yourself—of your thoughts, time, resources—to others. ",
			"<b>Helpfulness:</b> You are helpful—when you see a need, you do what you can.  How have you helped someone else recently?  Is there something you could do now to help, and still take care of your own needs as well?  ",
			"<b>Modesty:</b> You are modest—you know what you do well, but you don’t need to trumpet it excessively.  Remind yourself, though, of something you do well—it could be cooking, or writing, or sport, or lending an ear.  Think about how you have developed that over time, and how it feels to know you are doing something well.",
			"<b>Sensitivity:</b> You are sensitive and attuned to emotions—this can be a source of strength, as you pick up on the nuances of feelings others may miss. In the midst of storm, take a breath and notice your emotions, and the emotions of others—pause and just watch these things, without having to respond. ",
			"<b>Forgiveness:</b> You can forgive and move on, preserving your relationships and building strength in them.  Forgive yourself, too, for whatever you might be picking on yourself for—you deserve the same compassion you give others. ",
			"<b>Leadership:</b> You are good leader—someone people respect, and you inspire them to get things done.  Think about how you are doing this today, in large or small ways—how can you be a role model, lead your family, friends, or colleagues in a positive direction. ",
			"<b>Affection:</b> You are warm and loving—take a moment now and tell someone you care for them—send a message or text, make a call, or say it in person. ",
			"<b>Persistence/perseverance:</b> You are persistent—you will stay the course, and do what it takes to get through to the finish line.  If there is something that is a struggle right now, think about times in the past when you’ve stuck with it, and been glad you did.  Is this a time like that?  What helped you at other times to carry on?  ",
			"<b>Faith:</b> Your faith is strong—remember what you believe in, and what is important to you; this will guide you in good times and bad. ",
			"<b>Sense of humour:</b> A laugh is worth a thousand words…you are good at finding the humour in between the lines.  Keep it up—if you can’t find it yourself, read something, watch something, listen to something funny.  Give yourself a giggle break. ",
			"<b>Patience:</b> You are patient—you can manage frustrations that might send someone else around the bend.  This is good—think how you do it, how you keep yourself on an even keel.  Remind yourself that patience is a virtue, but doesn’t mean you don’t have a right to expectations.  Use your patience to persist toward your goals. ",
			"<b>Reliability:</b> You are reliable and trustworthy—think about something you have done recently to act on your plans and responsibilities, to do what needs to be done. ",
			"<b>Responsibility:</b> You are honest and forthright, taking responsibility for your own actions.  Think about how this has shaped you and your relationships—do people trust you?  Do you feel strong knowing you have integrity? ",
			"<b>Courage:</b> “Whatever you can do, or dream you can do, begin it.  Boldness has genius, power and magic in it.”-Goethe ",
			"<b>Confidence:</b> Your confidence and faith in yourself can carry you far—remember the things you have accomplished in the past, and how you did them.  Make a plan for how you will take the next step toward something you anticipate being a challenge.  It might be breastfeeding, or juggling work and childcare, or a project at home.  How will you get started?  How will you keep going? ",
			"<b>Enthusiasm:</b> You approach the world with gusto and find the joy in life.  Look around you now and notice s is interesting, exciting, engaging. ",
			"<b>Flexibility:</b> You are good at adapting—you can see different options and aren’t afraid to change course and try a new strategy.  Look around at what you are doing now—work, or a project at home, or how you take care of your health—is it working?  Is there something you might try to do differently? ",
			"<b>Hopefulness/optimism:</b> You are an optimist, able to find hope even when dark clouds gather.  Remember that this is a strength for you—look for the light; you know it is there, and you can find it. ",
			"<b>Resourcefulness:</b>You are capable and practical—you know you will find a way to manage what you need to do, using what you have.  Think about how you’ve handled a recent challenge—small or large—when you found what you needed and used it resourcefully. ",
			"<b>Resiliency/toughness:</b> You are resilient—when things are hard, you have the tenacity to keep on trying.  Remember a time in the past when you ran into an obstacle, but persevered until you got what you needed. ",
			"<b>Stewardship:</b> You use your resources prudently, so you are more likely to have what you need, when you need it.  Think about how you have managed your resources—time, money, support—and how you can tap into reserves if you do have a need.  ",
			"<b>Thankfulness:</b> You are good at noticing and appreciating good things—and this is really good for you.  Stop and think about one thing that went well, or that you are thankful for, today. Let yourself think about this for a little while—what about it is good?  How does it feel to appreciate what you have?  ",
			"<b>Organisation:</b> You are good at preparing and planning, organising and arranging your life in a way that works for you.  If there is something that is feeling a bit out of control right now, think—is there a way you can do something to feel more prepared?  If so, what can you do?  If not, how can you let it go? "
	};
	int key=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_strength_reminders);
		pref1 = getSharedPreferences("randomstrings", MODE_PRIVATE);
		t1 =(TextView)findViewById(R.id.reminderText1);
		//reminderText = getResources().getStringArray(R.array.reminders);
		random = (Button)findViewById(R.id.buttonRandom);
		back = (Button)findViewById(R.id.buttonBack);

		activity = (Button)findViewById(R.id.buttonActivity);



		activity.setOnClickListener(new OnClickListener(
				) {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(), SelfAssessmentActivityResources.class);
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
					Toast.makeText(getApplicationContext(), "Answer some more questions in Self Assessment to get more Strength Reminders", Toast.LENGTH_LONG).show();
					activity.setVisibility(View.VISIBLE);
				}
				else
				{
					key++;
					setRandom();
					if((key == sortedLike.size()-1) && (sortedLike.size() == total))
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
			if((Integer)(entry.getValue()) != -1)
				rated.put(entry.getKey(), entry.getValue());
			else
				break;
		} 
		return rated;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		super.onResume();
		key=0;
		pref = getSharedPreferences("physical", MODE_PRIVATE);
		int j=0;
		for(int i=0;i<numQuesPhy;i++)
		{
			int val = pref.getInt("like"+i, -1);

			like.put(j,val);
			j++;
		}
		pref = getSharedPreferences("intellectual", MODE_PRIVATE);
		for(int i=0;i<numQuesInt;i++)
		{
			int val = pref.getInt("like"+i, -1);

			like.put(j,val);
			j++;
		}
		pref = getSharedPreferences("social", MODE_PRIVATE);
		for(int i=0;i<numQuesSoc;i++)
		{
			int val = pref.getInt("like"+i, -1);

			like.put(j,val);
			j++;
		}
		pref = getSharedPreferences("individual", MODE_PRIVATE);
		for(int i=0;i<numQuesInd;i++)
		{
			int val = pref.getInt("like"+i, -1);

			like.put(j,val);
			j++;
		}

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
