package gupta.app.assignmentmanager.task;

import gupta.app.assignmentmanager.R;
import gupta.app.assignmentmanager.R.id;
import gupta.app.assignmentmanager.R.layout;
import gupta.app.assignmentmanager.R.menu;
import gupta.app.assignmentmanager.database.Task;
import gupta.app.assignmentmanager.database.TaskDB;
import gupta.app.assignmentmanager.fragment.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.AlertDialog;
//import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;


public class AddTask extends Tasks {
	
	private TextView dateOutput;
	private int fYear;
	private int fMonth;
	private int fDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		Button confirmTask = (Button) findViewById(R.id.AddTaskButton);
		Button addMoreTask = (Button) findViewById(R.id.addMoreTaskButton);
		
		confirmTask.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if(addTask()) {
					goToViewTask();
				}
				
			}
			
		});
		
		addMoreTask.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (addTask()) {
					refresh();
				}
			}
			
		});
		
		dateOutput = (TextView) findViewById(R.id.dateOutput);
		Calendar now = Calendar.getInstance(java.util.Locale.getDefault());
		fYear = now.get(Calendar.YEAR);
		fMonth = now.get(Calendar.MONTH);
		fDay = now.get(Calendar.DAY_OF_MONTH);
		String strMonth = convertMonthToString(fMonth);
		
		dateOutput.setText(new StringBuilder()
        // Month is 0 based, just add 1
        .append("Due Date: ").append(strMonth).append("-").append(fDay).append("-")
        .append(fYear).append(" "));
		
		dateOutput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				showDatePickerDialog(view);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_task, menu);
		return true;
	}
	
	
	public void showDatePickerDialog(View v) {
		Bundle bundle = new Bundle();
		bundle.putInt("options", 0); // to distinguish add and modify
		bundle.putInt("year", fYear);
		bundle.putInt("month", fMonth);
		bundle.putInt("day", fDay);
	    super.createDatePicker(v, bundle);
	}

	
	public Boolean addTask() {
		EditText editTextSubject = (EditText) findViewById(R.id.addTaskSubject);
		EditText editTextTitle = (EditText) findViewById(R.id.addTaskTitle);
		
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
				Task task = new Task(0, subject, title, dateEpoch);
				
				TaskDB db = new TaskDB(this);
				db.addTask(task);
				return true;
			}
			
			else {
				AlertDialog.Builder nonParse = new AlertDialog.Builder(AddTask.this);
				
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
		Intent refresh = new Intent (this, AddTask.class);
		startActivity(refresh);
		this.finish();
	}
	
	protected void goToViewTask() {
		super.goToViewTask();
		this.finish();
	}
	
	public void updateDate(int year, int month, int day) {
		fYear = year;
		fMonth = month; 
		fDay = day;
		String strMonth = convertMonthToString(fMonth);
		
		dateOutput.setText(new StringBuilder()
        // Month is 0 based, just add 1
        .append("Due Date: ").append(strMonth).append("-").append(day).append("-")
        .append(year).append(" "));
	}

	

}
