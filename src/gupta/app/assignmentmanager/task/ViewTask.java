package gupta.app.assignmentmanager.task;

import gupta.app.assignmentmanager.R;
import gupta.app.assignmentmanager.R.id;
import gupta.app.assignmentmanager.R.layout;
import gupta.app.assignmentmanager.R.menu;
import gupta.app.assignmentmanager.database.Task;
import gupta.app.assignmentmanager.database.TaskDB;
import gupta.app.assignmentmanager.fragment.TaskOptionsFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ViewTask extends FragmentActivity {

	List<Map<String, String>> taskList = new ArrayList<Map<String, String>>();
	List<Task> allTasks;
	TaskDB db = new TaskDB(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task);

		allTasks = db.getAllNewTasks();
		convertToMappedList();
		

		//deleteAllOldTasks();
		
		//allTasks = db.getAllTasks(); // after old tasks are removed

		ListView lv = (ListView) findViewById(R.id.listView);
		Button addTaskButton = (Button) findViewById(R.id.addTaskButton);



		SimpleAdapter simpleAdpt = new SimpleAdapter(this, 
				taskList, 
				android.R.layout.simple_list_item_1, 
				new String[] {"task"}, 
				new int[] {android.R.id.text1});



		lv.setAdapter(simpleAdpt);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
				//TextView clickedView = (TextView) view;
				Task task = allTasks.get(position);
				Long epoch = task.getDate();
				Date date = new Date (epoch);
				DateFormat df = new SimpleDateFormat("EEE, MMM d, ''yy", java.util.Locale.getDefault());
				String finalDate = df.format(date);


				Toast.makeText(ViewTask.this, 
						"You have a(n) " + task.getTitle() + " from " + task.getSubject() + " due on " + finalDate, 
						Toast.LENGTH_LONG).show();

			}
		});
		
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				
				/*
				Task task = allTasks.get(position);
				deleteTask(task);
				return true;
				*/
				Bundle args = new Bundle();
				Task task = allTasks.get(position);
				//int taskId = task.getId();
				args.putInt("position", position);
				DialogFragment optionsFragment = new TaskOptionsFragment();
				optionsFragment.setArguments(args);
				optionsFragment.show(getSupportFragmentManager(), "optionsMenu");
				return true;
			}
		});
		
		addTaskButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				goToAddTask();
				
			}
			
		});

	}
	
	public void deleteTask(Task task) {
		TaskDB db = new TaskDB(this);
		db.deleteTask(task);
		Intent refresh = new Intent (this, ViewTask.class);
		startActivity(refresh);
		this.finish();
	}
	
	public void deleteTask(int position) {
		Task task = allTasks.get(position);
		deleteTask(task);
	}
	
	public void getToModifyTask(int position) {
		Task task = allTasks.get(position);
		Bundle bundle = new Bundle(); // I think unnecessary
		bundle.putInt("taskID", task.getId()); // I think unnecessary
		Intent modifyTask = new Intent (this, EditTask.class);
		modifyTask.putExtra("taskID", task.getId());
		startActivity(modifyTask);
	}

	private void convertToMappedList() {
		for (int i = 0; i < allTasks.size(); i++) {
			HashMap<String, String> taskView = new HashMap<String, String>();
			Task task = allTasks.get(i);
			Long epoch = task.getDate();
			Date date = new Date (epoch);
			DateFormat df = new SimpleDateFormat("MMM dd", java.util.Locale.getDefault());
			String finalDate = df.format(date);
			String appear = finalDate + " - " + task.getSubject() + ": " + task.getTitle();
			taskView.put("task", appear);
			taskList.add(taskView);
		}



	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_task, menu);
		return true;
	}
	
	private void goToAddTask() {
		Intent refresh = new Intent (this, AddTask.class);
		startActivity(refresh);
		this.finish();
	}



}
