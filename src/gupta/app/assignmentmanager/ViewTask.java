package gupta.app.assignmentmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTask extends Activity {

	List<Map<String, String>> taskList = new ArrayList<Map<String, String>>();
	List<Task> allTasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task);

		TaskDB db = new TaskDB(this);
		allTasks = db.getAllTasks();
		convertToMappedList();

		ListView lv = (ListView) findViewById(R.id.listView);



		SimpleAdapter simpleAdpt = new SimpleAdapter(this, 
				taskList, 
				android.R.layout.simple_list_item_1, 
				new String[] {"task"}, 
				new int[] {android.R.id.text1});



		lv.setAdapter(simpleAdpt);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
				TextView clickedView = (TextView) view;
				Task task = allTasks.get(position);
				Calendar taskDate = task.getDate();
				int month = taskDate.get(Calendar.MONTH);
				int year = taskDate.get(Calendar.YEAR);
				

				Toast.makeText(ViewTask.this, 
						"Item with id ["+id+"] - Position ["+position+"] - Planet ["+clickedView.getText()+"] Date - " + Integer.toString(year), 
						Toast.LENGTH_SHORT).show();

			}
		});
		
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView clickedView = (TextView) view;
				Task task = allTasks.get(position);
				Toast.makeText(ViewTask.this, Integer.toString(task.getId()), Toast.LENGTH_SHORT).show();
				
				deleteTask(task);
				return true;
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

	private void convertToMappedList() {
		for (int i = 0; i < allTasks.size(); i++) {
			HashMap<String, String> planet = new HashMap<String, String>();
			planet.put("task", allTasks.get(i).getTitle());
			taskList.add(planet);
		}



	}

	private String convertMonthToString(int month) {
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_task, menu);
		return true;
	}



}
