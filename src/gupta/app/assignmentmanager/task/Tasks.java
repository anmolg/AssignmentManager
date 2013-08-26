package gupta.app.assignmentmanager.task;

import gupta.app.assignmentmanager.fragment.DatePickerFragment;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class Tasks extends FragmentActivity {
	
	protected String convertMonthToString(int month) {
		String monthString = "";
		switch (month) {
		case 0: monthString = "Jan";
		break;
		
		case 1: monthString = "Feb";
		break;
		
		case 2: monthString = "Mar";
		break;
		
		case 3: monthString = "Apr";
		break;
		
		case 4: monthString = "May";
		break;
		
		case 5: monthString = "Jun";
		break;
		
		case 6: monthString = "Jul";
		break;
		
		case 7: monthString = "Aug";
		break;
		
		case 8: monthString = "Sep";
		break;
		
		case 9: monthString = "Oct";
		break;
		
		case 10: monthString = "Nov";
		break;
		
		case 11: monthString = "Dec";
		break;
		
		default: monthString = "Invalid Month";
		break;

		}
		return monthString;
	}
	
	protected Boolean checkCurrentTime(long epoch) {
		Calendar now = Calendar.getInstance();
		
		if (epoch < now.getTimeInMillis()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	protected void goToViewTask() {
		Intent refresh = new Intent (this, ViewTask.class);
		startActivity(refresh);
	}
	
	protected void createDatePicker(View v, Bundle b) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.setArguments(b);
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
}
