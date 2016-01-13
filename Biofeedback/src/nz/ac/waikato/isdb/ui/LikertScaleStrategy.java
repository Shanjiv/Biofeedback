package nz.ac.waikato.isdb.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import nz.ac.waikato.isdb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * UI element representing a Likert scale from 1 to 5.
 */
public class LikertScaleStrategy extends LinearLayout implements View.OnClickListener {
	/** return value if no button has been pressed */
	static int NO_SELECTION = -1;
	SharedPreferences pref;

	/** all toggle buttons of this Likert scale */
	List<ToggleButton> buttons;

	public LikertScaleStrategy(Context context) {
		super(context);
		init();
	}

	public LikertScaleStrategy(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LikertScaleStrategy(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	protected void init() {
		Context ctxt = getContext();
		if (ctxt == null)
			return;
		pref = ctxt.getSharedPreferences("stress", ctxt.MODE_PRIVATE);
		buttons = new ArrayList<ToggleButton>();

		//--A1--
		String label = "1";
		ToggleButton button = new ToggleButton(ctxt);
		button.setTextColor(Color.TRANSPARENT);
		button.setText(label);
		button.setTextOn(label);
		button.setTextOff(label);
		button.setBackgroundResource(R.drawable.purple_likert_button_minusminus);
		button.setOnClickListener(this);
		addView(button);
		buttons.add(button);
		//--A2--
		label = "2";
		button = new ToggleButton(ctxt);
		button.setTextColor(Color.TRANSPARENT);
		button.setText(label);
		button.setTextOn(label);
		button.setTextOff(label);
		button.setBackgroundResource(R.drawable.purple_likert_button_minus);
		button.setOnClickListener(this);
		addView(button);
		buttons.add(button);
		//--A3--
		label = "3";
		button = new ToggleButton(ctxt);
		button.setTextColor(Color.TRANSPARENT);
		button.setTextOn(label);
		button.setText(label);
		button.setTextOff(label);
		button.setBackgroundResource(R.drawable.purple_likert_button_neutral);
		button.setOnClickListener(this);
		addView(button);
		buttons.add(button);
		//--B1--
		label = "4";
		button = new ToggleButton(ctxt);
		button.setTextColor(Color.TRANSPARENT);
		button.setText(label);
		button.setTextOn(label);
		button.setTextOff(label);
		button.setBackgroundResource(R.drawable.purple_likert_button_plus);
		button.setOnClickListener(this);
		addView(button);
		buttons.add(button);
		//--B2--
		label = "5";
		button = new ToggleButton(ctxt);
		button.setTextColor(Color.TRANSPARENT);
		button.setText(label);
		button.setTextOn(label);
		button.setTextOff(label);
		button.setBackgroundResource(R.drawable.purple_likert_button_plusplus);
		button.setOnClickListener(this);
		addView(button);
		buttons.add(button);

	}

	/**
	 * Get the selected value. If none has been selected then -1 is returned.
	 *
	 * @return selected value or -1
	 */
	public int getValue() {
		ToggleButton checkedButton = getCheckedButton();
		int val =  checkedButton == null ? NO_SELECTION : Integer.parseInt(checkedButton.getText().toString());
		return val;
	}


	public void setValue(int val)
	{
		for(ToggleButton button : buttons)
		{
			if(Integer.parseInt(button.getText().toString()) == val)
			{
				button.setChecked(true);
				continue;

			}
			button.setChecked(false);
		}
	}

	@Override
	public void onClick(View view) {
		Log.d("ashwini "," clickkk ");


		ToggleButton clickedButton = (ToggleButton) view;
		for (ToggleButton button : buttons) {
			if (button.equals(clickedButton))
				continue;
			button.setChecked(false);
		}
		pref.edit().putString("buttonClick", getValue()+","+getId()).apply();
	}

	/**
	 * Gets the currently checked button. If none has been checked then null is return.
	 *
	 * @return checked button or null
	 */
	protected ToggleButton getCheckedButton() {
		for (ToggleButton button : buttons) {
			if (button.isChecked())
				return button;
		}
		return null;
	}
}
