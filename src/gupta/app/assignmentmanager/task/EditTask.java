package gupta.app.assignmentmanager.task;

import gupta.app.assignmentmanager.R;
import gupta.app.assignmentmanager.database.Task;
import gupta.app.assignmentmanager.database.TaskDB;
import gupta.app.assignmentmanager.fragment.DatePickerFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
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

public class EditTask extends Tasks {
	
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
				createDatePicker(arg0);
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
	

	private void createDatePicker(View v) {
		Bundle bundle = new Bundle();
		bundle.putInt("options", 1);
		bundle.putInt("year", fYear);
		bundle.putInt("month", fMonth);
		bundle.putInt("day", fDay);
		super.createDatePicker(v, bundle);
		
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
						dialog.dismiss();
					}
					
				});
				
				nonParse.show();
				return false;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();	
		}
		return false;
	}
	
	protected void goToViewTask() {
		super.goToViewTask();
		this.finish();
	}
	
	
	public void updateDate(int year, int month, int day) {
		fYear = year;
		fMonth = month; 
		fDay = day;
		String strMonth = convertMonthToString(month);  // to account for the month being 0-11 and not 1-12
		
		dateOutput.setText(new StringBuilder()
        .append("Due Date: ").append(strMonth).append("-").append(day).append("-")
        .append(year).append(" "));
	}

	
}
