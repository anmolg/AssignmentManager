package gupta.app.assignmentmanager;

import java.util.Calendar;

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
		int year = Integer.parseInt(editTextYear.getText().toString());
		int month = Integer.parseInt(editTextMonth.getText().toString());
		int day = Integer.parseInt(editTextDay.getText().toString());
		
		Calendar fullDate = Calendar.getInstance();
		fullDate.set(year, month, day);
		
		Task task = new Task(0, subject, title, fullDate);
		
		TaskDB db = new TaskDB(this);
		db.addTask(task);
		
	}

}
