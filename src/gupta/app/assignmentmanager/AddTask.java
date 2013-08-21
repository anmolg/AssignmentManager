package gupta.app.assignmentmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddTask extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		Button confirmTask = (Button) findViewById(R.id.ConfirmTaskButton);
		
		confirmTask.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				addTask();
				
			}
			
		});
		
		Intent intent = getIntent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_task, menu);
		return true;
	}
	
	public void addTask() {
		EditText editTextSubject = (EditText) findViewById(R.id.addTaskSubject);
		EditText editTextTitle = (EditText) findViewById(R.id.addTaskTitle);
		EditText editTextYear = (EditText) findViewById(R.id.year);
		EditText editTextMonth = (EditText) findViewById(R.id.month);
		EditText editTextDay = (EditText) findViewById(R.id.day);
		
		String subject = editTextSubject.getText().toString();
		String title = editTextTitle.getText().toString();
		String year = editTextYear.getText().toString();
		String day = editTextDay.getText().toString();
		//int year = Integer.parseInt(editTextYear.getText().toString());
		int numMonth = Integer.parseInt(editTextMonth.getText().toString());
		//int day = Integer.parseInt(editTextDay.getText().toString());
		
		String strMonth = convertMonthToString(numMonth);
		
		String strDate = strMonth + " " + day + " " + year;
		SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
		Date date;
		try {
			date = df.parse(strDate);
			long dateEpoch = date.getTime();
			
			Task task = new Task(0, subject, title, dateEpoch);
			
			TaskDB db = new TaskDB(this);
			db.addTask(task);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
