package gupta.app.assignmentmanager.task;

import gupta.app.assignmentmanager.R;
import gupta.app.assignmentmanager.R.id;
import gupta.app.assignmentmanager.R.layout;
import gupta.app.assignmentmanager.R.menu;
import gupta.app.assignmentmanager.database.Task;
import gupta.app.assignmentmanager.database.TaskDB;
import gupta.app.assignmentmanager.fragment.DatePickerFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditTask extends FragmentActivity {
	
	private Button confirmTask;
	private TextView dateOutput;
	private EditText editTextSubject;
	private EditText editTextTitle;
	
	private int fYear;
	private int fMonth;
	private int fDay;
	
	private int taskId;
	private Task task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		
		// Getting the task to edit
		taskId = getIntent().getExtras().getInt("taskID");
		TaskDB db = new TaskDB(this);
		task = db.getTask(taskId);
		
		
		confirmTask = (Button) findViewById(R.id.ConfirmTaskButton);
		
		confirmTask.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if(modifyTask()) {
					goToViewTask();
				}
				
			}
			
		});
		
		// Inserting and getting the date
		dateOutput = (TextView) findViewById(R.id.dateOutput);
		long taskDate = task.getDate();
		Date date = new Date(taskDate);
		DateFormat df = new SimpleDateFormat("MMM-dd-yyyy", java.util.Locale.getDefault());
		String actualDate = df.format(date);
		Calendar taskDue = Calendar.getInstance();
		taskDue.setTime(date);
		fYear = taskDue.get(Calendar.YEAR);
		fMonth = taskDue.get(Calendar.MONTH);
		fDay = taskDue.get(Calendar.DAY_OF_MONTH);
		
		dateOutput.setText(new StringBuilder()
        .append("Due Date: ").append(actualDate));
		
		
		// Creating the DatePicker
		dateOutput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putInt("options", 1);
				bundle.putInt("year", fYear);
				bundle.putInt("month", fMonth);
				bundle.putInt("day", fDay);
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.setArguments(bundle);
			    newFragment.show(getSupportFragmentManager(), "datePicker");
			}
			
		});
		
		// Setting the values of Subject
		editTextSubject = (EditText) findViewById(R.id.addTaskSubject);
		editTextSubject.setText(task.getSubject());
		
		// Setting the values of Title
		editTextTitle = (EditText) findViewById(R.id.addTaskTitle);
		editTextTitle.setText(task.getTitle());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_task, menu);
		return true;
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	
	public Boolean modifyTask() {
		String subject = editTextSubject.getText().toString();
		String title = editTextTitle.getText().toString();
		String year = Integer.toString(fYear);
		String day = Integer.toString(fDay);
		
		String strMonth = convertMonthToString(fMonth);
		
		String strDate = strMonth + " " + day + " " + year;
		SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy", java.util.Locale.getDefault());
		Date date;
		try {
			date = df.parse(strDate);
			long dateEpoch = date.getTime();
			
			if (checkCurrentTime(dateEpoch)) {
				task.setDate(dateEpoch);
				task.setSubject(subject);
				task.setTitle(title);
				TaskDB db = new TaskDB(this);
				db.updateTask(task);
				return true;
			}
			
			else {
				AlertDialog.Builder nonParse = new AlertDialog.Builder(EditTask.this);
				
				nonParse.setTitle("Date has to be in the future!");
				
				nonParse
				.setMessage("Sorry! But we can't add a task which has an older due date than the current time!")
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
					
				});
				
				nonParse.show();
				return false;
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AlertDialog.Builder nonParse = new AlertDialog.Builder(this);
			
			nonParse.setTitle("Can't read the date!");
			
			nonParse
			.setMessage("Sorry! We are unable to interpret the date you entered. Please try again")
			.setCancelable(false)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
				
			});
		}
		return false;
	}
	
	private void refresh() {
		Intent refresh = new Intent (this, EditTask.class);
		startActivity(refresh);
		this.finish();
	}
	
	private void goToViewTask() {
		Intent refresh = new Intent (this, ViewTask.class);
		startActivity(refresh);
		this.finish();
	}
	
	private Boolean checkCurrentTime(long epoch) {
		Calendar now = Calendar.getInstance();
		
		if (epoch < now.getTimeInMillis()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void updateDate(int year, int month, int day) {
		fYear = year;
		fMonth = month; // to account for the month being 0-11 and not 1-12
		fDay = day;
		String strMonth = convertMonthToStringFull(month + 1);
		
		dateOutput.setText(new StringBuilder()
        // Month is 0 based, just add 1
        .append("Due Date: ").append(strMonth).append("-").append(day).append("-")
        .append(year).append(" "));
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
	
	private String convertMonthToStringFull(int month) {
		String monthString = "";
		switch (month) {
		case 1: monthString = "January";
		break;
		
		case 2: monthString = "February";
		break;
		
		case 3: monthString = "March";
		break;
		
		case 4: monthString = "April";
		break;
		
		case 5: monthString = "May";
		break;
		
		case 6: monthString = "June";
		break;
		
		case 7: monthString = "July";
		break;
		
		case 8: monthString = "August";
		break;
		
		case 9: monthString = "September";
		break;
		
		case 10: monthString = "October";
		break;
		
		case 11: monthString = "November";
		break;
		
		case 12: monthString = "December";
		break;
		
		default: monthString = "Invalid Month";
		break;

		}
		return monthString;
	}

}
