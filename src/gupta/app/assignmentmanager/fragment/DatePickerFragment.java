package gupta.app.assignmentmanager.fragment;

import gupta.app.assignmentmanager.task.AddTask;
import gupta.app.assignmentmanager.task.EditTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.R.integer;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
//import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	int optionChooser; // tells the system if it is edit or adding a task
	int year;
	int month;
	int day;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		optionChooser = getArguments().getInt("options");
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		
		/*final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);*/
		
		year = getArguments().getInt("year");
		month = getArguments().getInt("month");
		day = getArguments().getInt("day");

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		
		switch (optionChooser) {
		case 0: // AddTask
			((AddTask) getActivity()).updateDate(year, month, day);
			break;
		case 1: // ModifyTask
			((EditTask) getActivity()).updateDate(year, month, day);
			break;
		}
		
		
				
	}
	
	private String convertMonthToString(int month) {
		String monthString = "";
		switch (month) {
		case 1: monthString = "Jan";
		break;
		
		case 2: monthString = "Feb";
		break;
		
		case 3: monthString = "Mar";
		break;
		
		case 4: monthString = "Apr";
		break;
		
		case 5: monthString = "May";
		break;
		
		case 6: monthString = "Jun";
		break;
		
		case 7: monthString = "Jul";
		break;
		
		case 8: monthString = "Aug";
		break;
		
		case 9: monthString = "Sep";
		break;
		
		case 10: monthString = "Oct";
		break;
		
		case 11: monthString = "Nov";
		break;
		
		case 12: monthString = "Dec";
		break;
		
		default: monthString = "Invalid Month";
		break;

		}
		return monthString;
	}
}
